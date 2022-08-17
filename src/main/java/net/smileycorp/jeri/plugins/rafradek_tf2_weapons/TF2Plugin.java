package net.smileycorp.jeri.plugins.rafradek_tf2_weapons;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.smileycorp.jeri.JEIPluginJERI;
import net.smileycorp.jeri.JERIPlugin;
import rafradek.TF2weapons.TF2weapons;

@JERIPlugin(modid="rafradek_tf2_weapons")
public class TF2Plugin implements JEIPluginJERI {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new TF2CrafterCategory(guiHelper));
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		//tf2 crafter
		registry.handleRecipes(TF2CrafterRecipeWrapper.class, (r) -> r, TF2CrafterCategory.ID);
		registry.addRecipes(TF2CrafterCategory.getRecipes(), TF2CrafterCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(TF2weapons.blockCabinet), TF2CrafterCategory.ID);
	}

}
