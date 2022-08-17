package net.smileycorp.jeri.plugins.cfm;

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
import net.smileycorp.jeri.ModDefinitions;

public abstract class CFMFuelCategory implements IRecipeCategory<CFMFuelWrapper> {

	private final IDrawable background;
	private final IDrawable icon;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/cfm_fuels.png");

	public CFMFuelCategory(IGuiHelper guiHelper, ItemStack icon) {
		this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 67, 28);
		this.icon = guiHelper.createDrawableIngredient(icon);
	}

	@Override
	public final IDrawable getBackground() {
		return background;
	}

	@Override
	public final String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	@Nullable
	public final IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CFMFuelWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, true, 1, 5);
		items.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
	}

}
