package net.smileycorp.jeri.plugins.car;

import java.awt.Color;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.smileycorp.jeri.ModDefinitions;

import com.google.common.collect.Lists;

import de.maxhenkel.car.fluids.ModFluids;

public class CarWorkshopCategory implements IRecipeCategory<CarWorkshopCategory.Wrapper> {

	private final IDrawable background;

	public static final String ID = ModDefinitions.getName("car_workshop");

	public static final ResourceLocation TEXTURE = new ResourceLocation("car", "jei_car_workshop_crafting.png");

	public CarWorkshopCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 128, 68);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {

	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.car.CarWorkshop").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		for (int i = 0; i <5; i++) for (int j = 0; j <4; j++) items.init((i*9)+j+1, true, j*18+3, i*18+3);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		List<List<ItemStack>> stacks = ingredients.getInputs(VanillaTypes.ITEM);
		for (int i = 0; i < stacks.size(); i++) items.set(i+1, stacks.get(i));
	}

	public static class Wrapper implements IRecipeWrapper {

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.FLUID, Lists.newArrayList(new FluidStack(ModFluids.CANOLA_OIL, 50), new FluidStack(ModFluids.METHANOL, 50)));
			ingredients.setOutput(VanillaTypes.FLUID, new FluidStack(ModFluids.CANOLA_METHANOL_MIX, 100));
		}

		@Override
		public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
			minecraft.fontRenderer.drawString("2000 RF", 0, height-7, Color.DARK_GRAY.getRGB());
		}

	}

}
