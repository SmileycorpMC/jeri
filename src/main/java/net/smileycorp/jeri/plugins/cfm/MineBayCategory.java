package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.plugins.cfm.MineBayCategory.Wrapper;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

public class MineBayCategory implements IRecipeCategory<Wrapper> {

	public static final String ID = ModDefinitions.getName("minebay");

	private final IDrawable background;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/minebay.png");

	public MineBayCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 102, 65);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Minebay").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 72, 28);
		items.init(1, true, 13, 28);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
	}

	public static List<Wrapper> getRecipes() {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		for (RecipeData recipe : Recipes.getMineBayItems()) {
			recipes.add(new Wrapper(recipe));
		}
		return recipes;
	}

	public static class Wrapper implements IRecipeWrapper {

		private final ItemStack input;
		private final ItemStack output;

		public Wrapper(RecipeData recipe) {
			input = recipe.getCurrency();
			input.setCount(recipe.getPrice());
			output =  recipe.getInput();
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(input));
			ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(output));
		}

	}

}
