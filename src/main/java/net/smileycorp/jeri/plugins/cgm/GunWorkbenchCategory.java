package net.smileycorp.jeri.plugins.cgm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.plugins.cgm.GunWorkbenchCategory.Wrapper;

import com.mrcrayfish.guns.common.WorkbenchRegistry;

public class GunWorkbenchCategory implements IRecipeCategory<Wrapper> {

	public static final String ID = ModDefinitions.getName("gun_workbench");

	private final IDrawable background;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cgm/gun_workbench.png");

	public GunWorkbenchCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 143, 97);
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cgm.GunWorkbench").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<Wrapper> getRecipes() {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		for (Entry<ItemStack, List<ItemStack>> entry : WorkbenchRegistry.getRecipeMap().entrySet()) {
			recipes.add(new Wrapper(entry));
		}
		return recipes;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 118, 39);
		for (int i = 0; i <5; i++) for (int j = 0; j <4; j++) items.init((i*9)+j+1, true, j*18+3, i*18+3);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		List<List<ItemStack>> stacks = ingredients.getInputs(VanillaTypes.ITEM);
		for (int i = 0; i < stacks.size(); i++) items.set(i+1, stacks.get(i));
	}

	public static class Wrapper implements IRecipeWrapper {

		private final ItemStack output;
		private final List<ItemStack> inputs = new ArrayList<ItemStack>();

		public Wrapper(Entry<ItemStack, List<ItemStack>> recipe) {
			output = recipe.getKey();
			for (ItemStack stack : recipe.getValue()) inputs.add(stack);
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setOutput(VanillaTypes.ITEM, output);
			ingredients.setInputs(VanillaTypes.ITEM,  inputs);
		}

	}

}
