package net.smileycorp.jeri.plugins.bibliocraft;

import javax.annotation.Nonnull;

import jds.bibliocraft.blocks.BlockPrintingPress;
import jds.bibliocraft.blocks.BlockTypesettingTable;
import jds.bibliocraft.blocks.blockitems.BlockItemTypewriter;
import jds.bibliocraft.items.ItemChase;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.smileycorp.jeri.JEIPluginJERI;
import net.smileycorp.jeri.JERIPlugin;
import net.smileycorp.jeri.SimpleRecipeWrapper;

@JERIPlugin(modid = "bibliocraft")
public class BibliocraftPlugin implements JEIPluginJERI {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new TypesettingCategory(guiHelper));
		registry.addRecipeCategories(new PrintingPressCategory(guiHelper));
		registry.addRecipeCategories(new TypewriterCategory(guiHelper));
	}

	@Override
	public void register(@Nonnull IModRegistry registry) {
		//typesetting table
		registry.handleRecipes(TypesettingCategory.Wrapper.class, r->r, TypesettingCategory.ID);
		registry.addRecipes(TypesettingCategory.getRecipes(), TypesettingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(BlockTypesettingTable.instance), TypesettingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(ItemChase.instance), TypesettingCategory.ID);
		//printing press
		registry.handleRecipes(PrintingPressCategory.Wrapper.class, r->r, PrintingPressCategory.ID);
		registry.addRecipes(PrintingPressCategory.getRecipes(), PrintingPressCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(BlockPrintingPress.instance), PrintingPressCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(Items.DYE, 1, 0), PrintingPressCategory.ID);
		//printing press
		registry.handleRecipes(SimpleRecipeWrapper.class, r->r, TypewriterCategory.ID);
		registry.addRecipes(TypewriterCategory.getRecipes(), TypewriterCategory.ID);
		for (int i = 0; i< 16; i++) registry.addRecipeCatalyst(new ItemStack(BlockItemTypewriter.instance, 1, i), TypewriterCategory.ID);
	}

}
