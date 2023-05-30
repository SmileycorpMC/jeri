package net.smileycorp.jeri.api;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.jeri.ModDefinitions;

public abstract class SimpleCatalystRecipeCategory<T extends IRecipeWrapper> extends JERICategoryBase<T> {

	private final ItemStack catalyst;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/simple_catalyst_recipe.png");

	public SimpleCatalystRecipeCategory(IGuiHelper guiHelper, ItemStack catalyst, boolean animateArrow) {
		super(guiHelper, animateArrow, 66, 41, 21, 18);
		this.catalyst = catalyst;
	}

	@Override
	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, T wrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 47, 19);
		items.init(1, true, 1, 19);
		items.init(2, true, 24, 1);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		items.set(2, catalyst);
	}

	public ItemStack getCatalyst() {
		return catalyst;
	}

}
