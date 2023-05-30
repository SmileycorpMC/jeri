package net.smileycorp.jeri;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.smileycorp.jeri.api.loading.JEIPluginJERI;
import net.smileycorp.jeri.api.loading.JERIPlugin;

@JEIPlugin
@Mod(modid=ModDefinitions.MODID, name = ModDefinitions.NAME, version = ModDefinitions.NAME, dependencies = ModDefinitions.DEPENDENCIES)
public class JustEnoughRecipeIntegrations implements IModPlugin {

	static Set<JEIPluginJERI> JERI_PLUGINS = new HashSet<>();

	private static Logger logger = Logger.getLogger(ModDefinitions.NAME);

	private static IJeiHelpers helpers;
	private static IRecipeRegistry registry;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logInfo("fetching plugin data");
		String annotation = JERIPlugin.class.getCanonicalName();
		Set<ASMData> dataset = event.getAsmData().getAll(annotation);
		for (ASMData data : dataset) {
			String modid = (String) data.getAnnotationInfo().get("modid");
			if (Loader.isModLoaded(modid)) {
				try {
					Class<?> plugin = Class.forName(data.getClassName());
					JERI_PLUGINS.add((JEIPluginJERI) plugin.newInstance());
					logInfo("loading plugin for " + modid);
				} catch (Exception e) {
					logError("Error loading plugin: " + modid, e);
				}
			} else logInfo("mod " + modid + " not detected, skipping plugin");
		}
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		helpers = registry.getJeiHelpers();
		for (JEIPluginJERI plugin : JERI_PLUGINS) plugin.registerCategories(registry);
	}

	@Override
	public void register(IModRegistry registry) {
		for (JEIPluginJERI plugin : JERI_PLUGINS) plugin.register(registry);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime runtime) {
		registry = runtime.getRecipeRegistry();
		for (JEIPluginJERI plugin : JERI_PLUGINS) plugin.onRuntimeAvailable(runtime);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {
		for (JEIPluginJERI plugin : JERI_PLUGINS) plugin.registerLateRecipes(registry);
	}

	public static IJeiHelpers getHelpers() {
		return helpers;
	}

	public static IRecipeRegistry getRegistry() {
		return registry;
	}

	public static void logInfo(Object message) {
		logger.log(Level.INFO, String.valueOf(message));
	}

	public static void logError(Object message, Exception e) {
		logger.log(Level.SEVERE, String.valueOf(message));
		e.printStackTrace();
	}

}
