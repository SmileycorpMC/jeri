package net.smileycorp.jeri.plugins.car;

import java.awt.Color;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.Lists;

public class CarMachineRecipeWrapper implements IRecipeWrapper {

	protected final List<ItemStack> inputs;
	protected final ItemStack output;
	protected final FluidStack fluidOutput;

	public CarMachineRecipeWrapper(ItemStack input, ItemStack output, FluidStack fluidOutput) {
		this(Lists.newArrayList(input), output, fluidOutput);
	}

	public CarMachineRecipeWrapper(List<ItemStack> inputs, ItemStack output, FluidStack fluidOutput) {
		this.inputs = inputs;
		this.output = output;
		this.fluidOutput = fluidOutput;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = Lists.newArrayList();
		inputs.add(this.inputs);
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, output);
		ingredients.setOutput(VanillaTypes.FLUID, fluidOutput);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
		minecraft.fontRenderer.drawString("2000 RF", 0, height-7, Color.DARK_GRAY.getRGB());
	}

}