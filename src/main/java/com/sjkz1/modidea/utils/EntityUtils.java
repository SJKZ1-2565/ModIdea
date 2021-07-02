package com.sjkz1.modidea.utils;

import com.sjkz1.modidea.ModIdea;
import com.sjkz1.modidea.entity.DrownedWolfEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityUtils 
{

	
	 public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, ModIdea.MOD_ID);

	    public static final RegistryObject<EntityType<DrownedWolfEntity>> DROWNED_WOLF_ENTITY = create("drowned_wolf_entity", EntityType.Builder.create(DrownedWolfEntity::new, EntityClassification.CREATURE).size(0.6F, 0.85F));
	   
	    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
	        return REGISTER.register(name, () -> builder.build(ModIdea.MOD_ID + "." + name));
	    }

	    
	   
}
