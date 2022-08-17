package net.smileycorp.jeri.plugins.bibliocraft;

import javax.annotation.Nonnull;

import jds.bibliocraft.blocks.BlockTypesettingTable;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.smileycorp.jeri.JEIPluginJERI;
import net.smileycorp.jeri.JERIPlugin;

@JERIPlugin(modid = "bibliocraft")
public class BibliocraftPlugin implements JEIPluginJERI {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new TypesettingCategory(guiHelper));
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		//gun workbench
		registry.handleRecipes(TypesettingCategory.Wrapper.class, (r) -> r, TypesettingCategory.ID);
		registry.addRecipes(TypesettingCategory.getRecipes(), TypesettingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(BlockTypesettingTable.instance), TypesettingCategory.ID);
	}

}
