package net.smileycorp.jeri.plugins.car;

import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
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
	public List<String> getTooltipStrings( int mouseX, int mouseY) {
		if (mouseX >= 1 && mouseX <= 16 && mouseY >= 1 && mouseY <= 57) {
			return Lists.newArrayList("2000 RF");
		}
		return IRecipeWrapper.super.getTooltipStrings(mouseX, mouseY);
	}

}