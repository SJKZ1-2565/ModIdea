package com.sjkz1.modidea.entity.renderer;

import com.sjkz1.modidea.ModIdea;
import com.sjkz1.modidea.entity.DrownedWolfEntity;
import com.sjkz1.modidea.entity.model.DrownedWolfEntityModel;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DrownedWolfRenderer extends MobRenderer<DrownedWolfEntity, DrownedWolfEntityModel<DrownedWolfEntity>>
{

	public DrownedWolfRenderer(EntityRendererManager renderManagerIn)
	{
		super(renderManagerIn, new DrownedWolfEntityModel<>(),0.5F);
	}

	private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation(ModIdea.MOD_ID,"textures/entity/drowned_wolf.png");
	private static final ResourceLocation ANGRY_WOLF_TEXTURES = new ResourceLocation(ModIdea.MOD_ID,"textures/entity/drowned_wolf_angry.png");



	@Override
	public ResourceLocation getEntityTexture(DrownedWolfEntity entity) {
		if (entity.isTamed()) {
			return WOLF_TEXTURES;
		} else {
			return entity.isAngry() ? ANGRY_WOLF_TEXTURES : WOLF_TEXTURES;
		}
	}

}
