package com.sjkz1.modidea.utils;

import com.sjkz1.modidea.entity.renderer.DrownedWolfRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderUtils 
{
	
	
	
	@OnlyIn(Dist.CLIENT)
	public static void init() 
	{
		 RenderingRegistry.registerEntityRenderingHandler(EntityUtils.DROWNED_WOLF_ENTITY.get(), DrownedWolfRenderer::new);
			 
	}
}
