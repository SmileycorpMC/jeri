package net.smileycorp.jemi.plugins.rafradek_tf2_weapons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.config.Constants;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jemi.ModDefinitions;
import rafradek.TF2weapons.TF2weapons;
import rafradek.TF2weapons.common.MapList;
import rafradek.TF2weapons.item.ItemApplicableEffect;
import rafradek.TF2weapons.item.ItemFromData;
import rafradek.TF2weapons.item.ItemToken;
import rafradek.TF2weapons.item.crafting.AustraliumRecipe;
import rafradek.TF2weapons.item.crafting.JumperRecipe;
import rafradek.TF2weapons.item.crafting.OpenCrateRecipe;
import rafradek.TF2weapons.item.crafting.RecipeApplyEffect;
import rafradek.TF2weapons.item.crafting.RecipeToScrap;
import rafradek.TF2weapons.item.crafting.TF2CraftingManager;
import rafradek.TF2weapons.util.PropertyType;
import rafradek.TF2weapons.util.WeaponData;

import com.google.common.collect.Lists;

public class TF2CrafterCategory implements IRecipeCategory<TF2CrafterCategory.Wrapper> {

	public static final String ID = ModDefinitions.getName("tf2_crafter");

	private final ICraftingGridHelper craftingGridHelper;
	private final IDrawable background;

	public TF2CrafterCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 60, 116, 54);
		craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
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
		return new TextComponentTranslation("jei.category.rafradek_tf2_weapons.TF2Crafter").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<Wrapper> getRecipes() {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		for (IRecipe recipe : TF2CraftingManager.INSTANCE.getRecipeList()) {
			recipes.add(new Wrapper(recipe));
		}
		return recipes;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 94, 18);
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				int index = 1 + x + (y * 3);
				items.init(index, true, x * 18, y * 18);
			}
		}
		craftingGridHelper.setInputs(items, ingredients.getInputs(VanillaTypes.ITEM));
		if (wrapper.isShapeless()) recipeLayout.setShapeless();
		if (wrapper.getUpgradeInputSlot() == -1) items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		else {
			List<ItemStack> output = Lists.newArrayList();
			for (ItemStack stack : ingredients.getInputs(VanillaTypes.ITEM).get(wrapper.getUpgradeInputSlot())) {
				output.add(wrapper.upgradeStack(stack));
			}
			items.set(0, output);
		}
	}

	public static class Wrapper implements IRecipeWrapper {

		private final IRecipe recipe;
		private int upgradeInputSlot = -1;
		private Function<ItemStack, ItemStack> upgradeStackFunction = (stack) -> stack;

		public Wrapper(IRecipe recipe) {
			this.recipe = recipe;
		}

		public boolean isShapeless() {
			return recipe instanceof ShapelessRecipes;
		}

		public int getUpgradeInputSlot() {
			return upgradeInputSlot;
		}

		public ItemStack upgradeStack(ItemStack stack) {
			return upgradeStackFunction.apply(stack.copy());
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
			List<List<ItemStack>> inputs = Lists.newArrayList();
			if (recipe instanceof AustraliumRecipe) {
				upgradeInputSlot = 4;
				upgradeStackFunction = (stack) -> {
					stack.getTagCompound().setBoolean("Australium", true);
					return stack;
				};
				for (int i = 0; i < 9; i++) {
					if (i == 4) {
						List<ItemStack> weapons = Lists.newArrayList();
						for (Entry<String, WeaponData> entry : MapList.nameToData.entrySet()) {
							if (!entry.getValue().getBoolean(PropertyType.HIDDEN) &! (entry.getValue().getString(PropertyType.CLASS).equals("crate")))
								weapons.add(ItemFromData.getNewStack(entry.getValue()));
						}
						inputs.add(weapons);
					} else inputs.add(Lists.newArrayList(new ItemStack(TF2weapons.itemTF2, 1, 2)));
				}
			} else if (recipe instanceof RecipeApplyEffect) {
				ItemApplicableEffect type = (ItemApplicableEffect) ((RecipeApplyEffect) recipe).type;
				inputs.add(Lists.newArrayList(new ItemStack(type)));
				upgradeInputSlot = 1;
				upgradeStackFunction = (stack) -> {
					type.apply(new ItemStack(type), stack);
					return stack;
				};
				List<ItemStack> weapons = Lists.newArrayList();
				for (Entry<String, WeaponData> entry : MapList.nameToData.entrySet()) {
					if (!entry.getValue().getBoolean(PropertyType.HIDDEN) &! (entry.getValue().getString(PropertyType.CLASS).equals("crate")))
						weapons.add(ItemFromData.getNewStack(entry.getValue()));
				}
				inputs.add(weapons);
			} else if (recipe instanceof JumperRecipe) {
				String weaponClass = ItemFromData.getData(recipe.getRecipeOutput()).getString(PropertyType.CLASS);
				for (int i = 0; i < 9; i++) {
					if (i == 4) {
						List<ItemStack> weapons = Lists.newArrayList();
						for (Entry<String, WeaponData> entry : MapList.nameToData.entrySet()) {
							if (!entry.getValue().getBoolean(PropertyType.HIDDEN) && (entry.getValue().getString(PropertyType.CLASS).equals(weaponClass)))
								weapons.add(ItemFromData.getNewStack(entry.getValue()));
						}
						inputs.add(weapons);
					} else inputs.add(Lists.newArrayList(new ItemStack(Items.FEATHER)));
				}
			} else if (recipe instanceof OpenCrateRecipe) {
				inputs.add(Lists.newArrayList(new ItemStack(TF2weapons.itemTF2, 1, 7)));
				List<ItemStack> crates = Lists.newArrayList();
				for (Entry<String, WeaponData> entry : MapList.nameToData.entrySet()) {
					if (!entry.getValue().getBoolean(PropertyType.HIDDEN) && (entry.getValue().getString(PropertyType.CLASS).equals("crate")))
						crates.add(ItemFromData.getNewStack(entry.getValue()));
				}
				inputs.add(crates);
			} else if (recipe instanceof RecipeToScrap) {
				int token = ((RecipeToScrap) recipe).token;
				String tfclass = (token >= 0 && token < 9) ? ItemToken.CLASS_NAMES[token] : null;
				List<ItemStack> weapons = Lists.newArrayList();
				for (Entry<String, WeaponData> entry : MapList.nameToData.entrySet()) {
					if (!entry.getValue().getBoolean(PropertyType.HIDDEN) && (entry.getValue().get(PropertyType.SLOT).containsKey(tfclass) || tfclass == null)
							&! (entry.getValue().getString(PropertyType.CLASS).equals("crate")))
						weapons.add(ItemFromData.getNewStack(entry.getValue()));

				}
				for (int i = 0; i < (tfclass == null ? 2 : 3); i ++) {
					List<ItemStack> input = Lists.newArrayList(weapons);
					Collections.shuffle(input);
					inputs.add(input);
				}
			} else {
				for (Ingredient ingredient : recipe.getIngredients()) inputs.add(Lists.newArrayList(ingredient.getMatchingStacks()));
			}
			ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		}

	}

}
