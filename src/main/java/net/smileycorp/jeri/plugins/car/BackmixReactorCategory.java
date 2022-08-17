package net.smileycorp.jeri.plugins.car;

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
import net.smileycorp.jeri.ModDefinitions;

import com.google.common.collect.Lists;

import de.maxhenkel.car.fluids.ModFluids;

public class BackmixReactorCategory implements IRecipeCategory<BackmixReactorCategory.Wrapper> {

	private final IDrawable background;
	protected final IDrawableAnimated arrow;
	protected final IDrawableAnimated energy;

	public static final String ID = ModDefinitions.getName("backmix_reactor");

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/car/backmix_reactor.png");

	public BackmixReactorCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 128, 59);
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
		arrow.draw(minecraft, 66, 17);
		energy.draw(minecraft, 1, 47);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.car.BackmixReactor").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
		fluids.init(0, true, 23, 1, 16, 57, 3000, true, null);
		fluids.init(1, true, 45, 1, 16, 57, 3000, true, null);
		fluids.init(2, false, 112, 1, 16, 57, 3000, true, null);
		List<List<FluidStack>> inputs = ingredients.getInputs(VanillaTypes.FLUID);
		fluids.set(0, inputs.get(0));
		fluids.set(1, inputs.get(1));
		fluids.set(2, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
	}

	public static class Wrapper implements IRecipeWrapper {

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.FLUID, Lists.newArrayList(new FluidStack(ModFluids.CANOLA_OIL, 50), new FluidStack(ModFluids.METHANOL, 50)));
			ingredients.setOutput(VanillaTypes.FLUID, new FluidStack(ModFluids.CANOLA_METHANOL_MIX, 100));
		}

		@Override
		public List<String> getTooltipStrings( int mouseX, int mouseY) {
			if (mouseX >= 1 && mouseX <= 16 && mouseY >= 1 && mouseY <= 57) {
				return Lists.newArrayList("2000 RF");
			}
			return IRecipeWrapper.super.getTooltipStrings(mouseX, mouseY);
		}

	}

}
