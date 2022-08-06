package net.smileycorp.jeri.plugins.car;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.jeri.ModDefinitions;

public abstract class CarMachineCategory implements IRecipeCategory<CarMachineRecipeWrapper> {

	private final IDrawable background;
	protected final IDrawableAnimated arrow;
	protected final IDrawableAnimated energy;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/car/car_machine.png");

	public CarMachineCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 155, 59);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(TEXTURE, 0, 68, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 160, IDrawableAnimated.StartDirection.LEFT, false);
		IDrawableStatic energyDrawable = guiHelper.createDrawable(TEXTURE, 24, 68, 16, 11);
		energy = guiHelper.createAnimatedDrawable(energyDrawable, 160, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		arrow.draw(minecraft, 66, 18);
		energy.draw(minecraft, 1, 48);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CarMachineRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, true, 37, 17);
		items.init(1, false, 101, 17);
		List<List<ItemStack>> stacks = ingredients.getInputs(VanillaTypes.ITEM);
		items.set(0, stacks.get(0));
		items.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
		fluids.init(0, false, 138, 1, 16, 57, 3000, true, null);
		fluids.set(0, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}

}
