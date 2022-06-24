package net.smileycorp.jemi;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@JEIPlugin
@Mod(modid=ModDefinitions.MODID, name = ModDefinitions.NAME, version = ModDefinitions.NAME, dependencies = ModDefinitions.DEPENDENCIES)
public class JustEnoughModIntegrations implements IModPlugin {

	private static Set<IModPlugin> JEMI_PLUGINS = new HashSet<IModPlugin>();

	private static Logger logger = Logger.getLogger(ModDefinitions.NAME);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		String annotation = JEMIPlugin.class.getCanonicalName();
		Set<ASMData> dataset = event.getAsmData().getAll(annotation);
		for (ASMData data : dataset) {
			String modid = (String) data.getAnnotationInfo().get("modid");
			if (Loader.isModLoaded(modid)) {
				try {
					Class<?> plugin = Class.forName(data.getClassName());
					if (IModPlugin.class.isAssignableFrom(plugin)) JEMI_PLUGINS.add((IModPlugin) plugin.newInstance());
				} catch (Exception e) {
					logError("Error loading plugin: " + modid, e);
				}
			} else logInfo("mod " + modid + " not detected, skipping plugin");
		}
	}
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		for (IModPlugin plugin : JEMI_PLUGINS) plugin.registerCategories(registry);
	}

	@Override
	public void register(IModRegistry registry) {
		for (IModPlugin plugin : JEMI_PLUGINS) plugin.register(registry);
	}

	public static void logInfo(Object message) {
		logger.info(String.valueOf(message));
	}

	public static void logError(Object message, Exception e) {
		logger.log(Level.SEVERE, String.valueOf(message));
		e.printStackTrace();
	}


}
