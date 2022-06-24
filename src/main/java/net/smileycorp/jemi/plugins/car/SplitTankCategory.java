package net.smileycorp.jemi.plugins.car;

import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.jemi.ModDefinitions;

import com.google.common.collect.Lists;

import de.maxhenkel.car.fluids.ModFluids;

public class SplitTankCategory implements IRecipeCategory<SplitTankCategory.Wrapper> {

	private final IDrawable background;
	protected final IDrawableAnimated arrow;

	public static final String ID = ModDefinitions.getName("split_tank");

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/car/split_tank.png");

	public SplitTankCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 109, 59);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(TEXTURE, 0, 59, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 160, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		arrow.draw(minecraft, 32, 17);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.car.SplitTank").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
		fluids.init(0, true, 1, 1, 16, 57, 3000, true, null);
		fluids.init(1, false, 71, 1, 16, 57, 3000, true, null);
		fluids.init(2, false, 92, 1, 16, 57, 3000, true, null);
		fluids.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
		List<List<FluidStack>> outputs = ingredients.getOutputs(VanillaTypes.FLUID);
		fluids.set(1, outputs.get(0));
		fluids.set(2, outputs.get(1));
	}

	public static class Wrapper implements IRecipeWrapper {

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.FLUID, new FluidStack(ModFluids.CANOLA_METHANOL_MIX, 100));
			ingredients.setOutputs(VanillaTypes.FLUID, Lists.newArrayList(new FluidStack(ModFluids.GLYCERIN, 10), new FluidStack(ModFluids.BIO_DIESEL, 100)));
		}

	}

}
