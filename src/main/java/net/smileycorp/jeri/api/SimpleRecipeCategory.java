package net.smileycorp.jeri.api;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.jeri.ModDefinitions;

public abstract class SimpleRecipeCategory<T extends IRecipeWrapper> extends JERICategoryBase<T> {

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/simple_recipe.png");

	public SimpleRecipeCategory(IGuiHelper guiHelper, boolean animateArrow) {
		super(guiHelper, animateArrow, 67, 28, 21, 4);
	}

	@Override
	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 48, 5);
		items.init(1, true, 1, 5);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
	}

}
