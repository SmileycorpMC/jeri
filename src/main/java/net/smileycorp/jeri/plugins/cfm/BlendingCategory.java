package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class BlendingCategory implements IRecipeCategory<BlendingCategory.Wrapper> {

	public static final String ID = ModDefinitions.getName("blending");

	private final IDrawable background;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/blender.png");

	public BlendingCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 121, 54);
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
		return new TextComponentTranslation("jei.category.cfm.Blending").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, true, 65, 2);
		items.init(1, false, 99, 20);
		for (int i = 0; i <3; i++) for (int j = 0; j <3; j++) items.init((i*3)+j+2, true, j*18, i*18);
		items.set(0, new ItemStack(FurnitureItems.CUP));
		items.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		List<List<ItemStack>> stacks = ingredients.getInputs(VanillaTypes.ITEM);
		for (int i = 0; i < stacks.size(); i++) items.set(i+2, stacks.get(i));
	}

	public static List<Wrapper> getRecipes() {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		for (RecipeData recipe : Recipes.getRecipes("blender")) {
			recipes.add(new Wrapper(recipe));
		}
		return recipes;
	}

	public static class Wrapper implements IRecipeWrapper {

		private final ArrayList<ItemStack> inputs;
		private final ItemStack output;

		public Wrapper(RecipeData recipe) {
			inputs = recipe.getIngredients();
			output = new ItemStack(FurnitureItems.DRINK);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setIntArray("Colour", new int[]{recipe.getRed(), recipe.getBlue(), recipe.getGreen()});
			nbt.setInteger("HealAmount", recipe.getHealAmount());
			nbt.setString("Name", recipe.getDrinkName());
			output.setTagCompound(nbt);
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, inputs);
			ingredients.setOutputs(VanillaTypes.ITEM, Lists.newArrayList(output));
		}

	}

}
