package com.sjkz1.modidea.entity;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class DrownedWolfEntity extends WolfEntity{

	public DrownedWolfEntity(EntityType<? extends WolfEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Overwrite
	protected void applyEntityAI() {
		this.goalSelector.addGoal(1, new DrownedWolfEntity.GoToWaterGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, DrownedEntity.class)).setCallsForHelp(ZombifiedPiglinEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.TARGET_DRY_BABY));
	}


	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F).createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		if (this.getItemStackFromSlot(EquipmentSlotType.OFFHAND).isEmpty() && this.rand.nextFloat() < 0.03F) {
			this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.NAUTILUS_SHELL));
			this.inventoryHandsDropChances[EquipmentSlotType.OFFHAND.getIndex()] = 2.0F;
		}

		return spawnDataIn;
	}

	public static boolean func_223332_b(EntityType<DrownedEntity> p_223332_0_, IServerWorld p_223332_1_, SpawnReason reason, BlockPos p_223332_3_, Random p_223332_4_) {
		Optional<RegistryKey<Biome>> optional = p_223332_1_.func_242406_i(p_223332_3_);
		boolean flag = p_223332_1_.getDifficulty() != Difficulty.PEACEFUL && (reason == SpawnReason.SPAWNER || p_223332_1_.getFluidState(p_223332_3_).isTagged(FluidTags.WATER));
		if (!Objects.equals(optional, Optional.of(Biomes.RIVER)) && !Objects.equals(optional, Optional.of(Biomes.FROZEN_RIVER))) {
			return p_223332_4_.nextInt(40) == 0 && func_223333_a(p_223332_1_, p_223332_3_) && flag;
		} else {
			return p_223332_4_.nextInt(15) == 0 && flag;
		}
	}

	@SuppressWarnings("deprecation")
	private static boolean func_223333_a(IWorld p_223333_0_, BlockPos p_223333_1_) {
		return p_223333_1_.getY() < p_223333_0_.getSeaLevel() - 5;
	}



	static class GoToWaterGoal extends Goal {
		private final CreatureEntity field_204730_a;
		public double field_204731_b;
		private double field_204732_c;
		private double field_204733_d;
		private final double field_204734_e;
		private final World field_204735_f;

		public GoToWaterGoal(CreatureEntity p_i48910_1_, double p_i48910_2_) {
			this.field_204730_a = p_i48910_1_;
			this.field_204734_e = p_i48910_2_;
			this.field_204735_f = p_i48910_1_.world;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() {
			if (!this.field_204735_f.isDaytime()) {
				return false;
			} else if (this.field_204730_a.isInWater()) {
				return false;
			} else {
				Vector3d vector3d = this.func_204729_f();
				if (vector3d == null) {
					return false;
				} else {
					this.field_204731_b = vector3d.x;
					this.field_204732_c = vector3d.y;
					this.field_204733_d = vector3d.z;
					return true;
				}
			}
		}

		@Nullable
		private Vector3d func_204729_f() {
			Random random = this.field_204730_a.getRNG();
			BlockPos blockpos = this.field_204730_a.getPosition();

			for(int i = 0; i < 10; ++i) {
				BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
				if (this.field_204735_f.getBlockState(blockpos1).matchesBlock(Blocks.WATER)) {
					return Vector3d.copyCenteredHorizontally(blockpos1);
				}
			}

			return null;
		}
	}
}




