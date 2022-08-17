package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

public class FreezingCategory implements IRecipeCategory<CFMRecipeWrapper> {

	public static final String ID = ModDefinitions.getName("freezing");

	private final IDrawable background;
	private final IDrawableAnimated ice;
	private final IDrawableAnimated bubbles;
	private final IDrawableAnimated progress;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/freezer.png");

	public FreezingCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 86, 44);
		IDrawableStatic iceDrawable = guiHelper.createDrawable(TEXTURE, 0, 44, 16, 1);
		ice = guiHelper.createAnimatedDrawable(iceDrawable, 200, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic bubbleDrawable = guiHelper.createDrawable(TEXTURE, 16, 44, 25, 15);
		bubbles = guiHelper.createAnimatedDrawable(bubbleDrawable, 20, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic progressDrawable = guiHelper.createDrawable(TEXTURE, 41, 44, 2, 16);
		progress = guiHelper.createAnimatedDrawable(progressDrawable, 160, IDrawableAnimated.StartDirection.BOTTOM, false);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		ice.draw(minecraft, 69, 16);
		bubbles.draw(minecraft, 20, 26);
		progress.draw(minecraft, 67, 27);
	}

	@Override
	public List<String> getTooltipStrings( int mouseX, int mouseY) {
		if (mouseX >= 69 && mouseX <= 84 && mouseY >= 1 && mouseY <= 16) {
			return Lists.newArrayList(new TextComponentTranslation("cfm.gui.ice_level")
			.getFormattedText().replace("/", "200"));
		}
		return IRecipeCategory.super.getTooltipStrings(mouseX, mouseY);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Freezing").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CFMRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 47, 26);
		items.init(1, true, 0, 26);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
	}

	public static List<CFMRecipeWrapper> getRecipes() {
		List<CFMRecipeWrapper> recipes = new ArrayList<CFMRecipeWrapper>();
		for (RecipeData recipe : Recipes.getRecipes("freezer")) {
			recipes.add(new CFMRecipeWrapper(recipe));
		}
		return recipes;
	}

}
