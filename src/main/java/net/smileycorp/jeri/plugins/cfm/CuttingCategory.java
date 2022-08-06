package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class CuttingCategory implements IRecipeCategory<CFMRecipeWrapper> {

	public static final String ID = ModDefinitions.getName("cutting");

	private final IDrawable background;
	private final IDrawable board;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/cutting_board.png");

	public CuttingCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 66, 41);
		board = guiHelper.createDrawableIngredient(new ItemStack(FurnitureBlocks.CHOPPING_BOARD));
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	@Nullable
	public IDrawable getIcon() {
		return board;
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Cutting").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CFMRecipeWrapper recipeCFMRecipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 47, 19);
		items.init(1, true, 1, 19);
		items.init(2, true, 24, 1);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		items.set(2, new ItemStack(FurnitureItems.KNIFE));
	}

	public static List<CFMRecipeWrapper> getRecipes() {
		List<CFMRecipeWrapper> recipes = new ArrayList<CFMRecipeWrapper>();
		for (RecipeData recipe : Recipes.getRecipes("choppingboard")) {
			recipes.add(new CFMRecipeWrapper(recipe));
		}
		return recipes;
	}

}
