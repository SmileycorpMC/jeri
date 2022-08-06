package net.smileycorp.jeri.plugins.cfm;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.api.RecipeData;

public class CFMRecipeWrapper implements IRecipeWrapper {

	private final ItemStack input;
	private final ItemStack output;

	public CFMRecipeWrapper(RecipeData recipe) {
		input = recipe.getInput();
		output =  recipe.getOutput();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(input));
		ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(output));
	}

}