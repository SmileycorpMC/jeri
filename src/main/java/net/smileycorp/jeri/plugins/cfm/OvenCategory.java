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

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

public class OvenCategory implements IRecipeCategory<CFMRecipeWrapper> {

	public static final String ID = ModDefinitions.getName("oven");

	private final IDrawable background;
	private final IDrawableAnimated progress;
	private final IDrawableAnimated flame;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/oven.png");

	public OvenCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 76, 44);
		IDrawableStatic progressDrawable = guiHelper.createDrawable(TEXTURE, 14, 44, 24, 16);
		progress = guiHelper.createAnimatedDrawable(progressDrawable, 300, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic flameDrawable = guiHelper.createDrawable(TEXTURE, 0, 44, 14, 14);
		flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		progress.draw(minecraft, 26, 6);
		flame.draw(minecraft, 30, 28);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Oven").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CFMRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 56, 5);
		items.init(1, true, 2, 5);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
	}

	public static List<CFMRecipeWrapper> getRecipes() {
		List<CFMRecipeWrapper> recipes = new ArrayList<CFMRecipeWrapper>();
		for (RecipeData recipe : Recipes.getRecipes("oven")) {
			recipes.add(new CFMRecipeWrapper(recipe));
		}
		return recipes;
	}

}
