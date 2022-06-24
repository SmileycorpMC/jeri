package net.smileycorp.jemi.plugins.mod_lavacow;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.smileycorp.jemi.JEMIPlugin;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.google.common.collect.Lists;

@JEMIPlugin(modid = "mod_lavacow")
public class FishsUndeadRisingPlugin implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new IntestineCategory(guiHelper));
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		//intestines loot table
		registry.handleRecipes(IntestineCategory.Wrapper.class, (r) -> r, IntestineCategory.ID);
		registry.addRecipes(Lists.newArrayList(new IntestineCategory.Wrapper()), IntestineCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FishItems.INTESTINE), IntestineCategory.ID);
	}

}
