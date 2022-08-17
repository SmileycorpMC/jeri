package net.smileycorp.jeri;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;

public class SimpleRecipeWrapper implements IRecipeWrapper  {

	private final ItemStack input;
	private final ItemStack output;

	public SimpleRecipeWrapper(ItemStack input, ItemStack output) {
		this.input = input;
		this.output =  output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(input));
		ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(output));
	}

}
