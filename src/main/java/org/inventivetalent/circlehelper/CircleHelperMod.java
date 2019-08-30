package org.inventivetalent.circlehelper;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CircleHelperMod.MODID,
	 name = CircleHelperMod.NAME,
	 version = CircleHelperMod.VERSION)
public class CircleHelperMod {

	public static final String MODID   = "circlehelper";
	public static final String NAME    = "Circle Helper Mod";
	public static final String VERSION = "@VERSION@";

	private static Logger logger;



	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		logger.info("Hello World!");

		MinecraftForge.EVENT_BUS.register(new RenderListener(this));
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new CircleCommand());

	}

}
