package net.smileycorp.jeri;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class SimpleRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	private final IDrawable background;
	private IDrawableAnimated progress;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/simple_recipe.png");

	public SimpleRecipeCategory(IGuiHelper guiHelper, boolean animateArrow) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 67, 28);
		IDrawableStatic progressDrawable = guiHelper.createDrawable(TEXTURE, 0, 28, 24, 16);
		if (animateArrow) progress = guiHelper.createAnimatedDrawable(progressDrawable, 300, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		if (progress != null) progress.draw(minecraft, 21, 4);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
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
