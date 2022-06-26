package net.smileycorp.jeri.plugins.cgm;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.smileycorp.jeri.JERIPlugin;

@JERIPlugin(modid = "cgm")
public class MrCrayfishGunsPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new GunWorkbenchCategory(guiHelper));
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		//gun workbench
		registry.handleRecipes(GunWorkbenchCategory.Wrapper.class, (r) -> r, GunWorkbenchCategory.ID);
		registry.addRecipes(GunWorkbenchCategory.getRecipes(), GunWorkbenchCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(com.mrcrayfish.guns.init.ModBlocks.WORKBENCH), GunWorkbenchCategory.ID);
	}

}
