package net.smileycorp.jeri.api.loading;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IRecipeRegistry;

public interface JEIPluginJERI extends IModPlugin {

	public default void registerLateRecipes(IRecipeRegistry registry) {}

	public default void onPlayerJoinWorld(IRecipeRegistry registry) {}

}
