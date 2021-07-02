package com.sjkz1.modidea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sjkz1.modidea.entity.DrownedWolfEntity;
import com.sjkz1.modidea.utils.EntityUtils;
import com.sjkz1.modidea.utils.RenderUtils;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(ModIdea.MOD_ID)
public class ModIdea 
{
	public static final String MOD_ID = "modidea";
	public static final Logger LOGGER = LogManager.getLogger();


	
	public ModIdea() 
	{
		final IEventBus Bus = FMLJavaModLoadingContext.get().getModEventBus();
		Bus.addListener(this::registerClient);
		Bus.addListener(this::setup);
		EntityUtils.REGISTER.register(Bus);
		LOGGER.info("Mod loaded!");
	}


	private void registerClient(FMLClientSetupEvent event) 
	{
		RenderUtils.init();
	}
	
	private void setup(FMLCommonSetupEvent event) 
	{
		registerEntityAttributes();
	}

	
	@SuppressWarnings("deprecation")
	private void registerEntityAttributes() {
		GlobalEntityTypeAttributes.put(EntityUtils.DROWNED_WOLF_ENTITY.get(), DrownedWolfEntity.registerAttributes().create());
	}
}
