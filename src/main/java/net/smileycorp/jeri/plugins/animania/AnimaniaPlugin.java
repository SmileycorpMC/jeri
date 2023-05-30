package net.smileycorp.jeri.plugins.animania;

import javax.annotation.Nonnull;

import com.animania.addons.farm.common.handler.FarmAddonItemHandler;
import com.animania.common.handler.AddonHandler;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.smileycorp.jeri.api.loading.JEIPluginJERI;
import net.smileycorp.jeri.api.loading.JERIPlugin;

@JERIPlugin(modid = "animania")
public class AnimaniaPlugin implements JEIPluginJERI {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		if (AddonHandler.isAddonLoaded("farm")) {
			IJeiHelpers jeiHelpers = registry.getJeiHelpers();
			IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
			registry.addRecipeCategories(new CheeseMoldCategory(guiHelper));
		}
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		//cheese mold
		if (AddonHandler.isAddonLoaded("farm")) {
			registry.handleRecipes(CheeseMoldCategory.Wrapper.class, r->r, CheeseMoldCategory.ID);
			registry.addRecipes(CheeseMoldCategory.getRecipes(), CheeseMoldCategory.ID);
			registry.addRecipeCatalyst(new ItemStack(FarmAddonItemHandler.cheeseMold), CheeseMoldCategory.ID);
		}
	}

}
