package net.smileycorp.jeri.plugins.bibliocraft;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jds.bibliocraft.items.ItemChase;
import jds.bibliocraft.items.ItemEnchantedPlate;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocus.Mode;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.api.ModUtils;
import net.smileycorp.jeri.api.SimpleCatalystRecipeCategory;

public class TypesettingCategory extends SimpleCatalystRecipeCategory<TypesettingCategory.Wrapper> {

	public static final String ID = ModDefinitions.getName("typesetting");

	public TypesettingCategory(IGuiHelper guiHelper) {
		super(guiHelper, new ItemStack(ItemChase.instance), false);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.bibliocraft.Typesetting").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients ingredients) {
		super.setRecipe(recipeLayout, wrapper, ingredients);
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		IFocus<?> focus = recipeLayout.getFocus();
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		if (focus.getMode() == Mode.INPUT) {
			if (focus.getValue() instanceof ItemStack) {
				ItemStack output = wrapper.getOutput((ItemStack) focus.getValue());
				if (!output.isEmpty()) items.set(0, output);
			}
		} else if (focus.getMode() == Mode.OUTPUT) {
			if (focus.getValue() instanceof ItemStack) {
				ItemStack input = wrapper.getInput((ItemStack) focus.getValue());
				if (!input.isEmpty()) items.set(1, input);
			}
		}
	}

	public static List<Wrapper> getRecipes() {
		return Lists.newArrayList(new Wrapper());
	}

	public static class Wrapper implements IRecipeWrapper {

		private final Map<ItemStack, ItemStack> recipes = Maps.newHashMap();

		public Wrapper() {
			for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
				if (enchantment.type != null) {
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
						ItemStack input = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i));
						ItemStack output = new ItemStack(ItemEnchantedPlate.instance);
						output.setTagCompound(input.getTagCompound().copy());
						recipes.put(input, output);
					}
				}
			}
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<ItemStack> inputs = Lists.newArrayList();
			List<ItemStack> outputs = Lists.newArrayList();
			for (Entry<ItemStack, ItemStack> entry : recipes.entrySet()) {
				inputs.add(entry.getKey());
				outputs.add(entry.getValue());
			}
			ingredients.setInputLists(VanillaTypes.ITEM, ModUtils.wrapList(inputs));
			ingredients.setOutputLists(VanillaTypes.ITEM, ModUtils.wrapList(outputs));
		}

		public ItemStack getInput(ItemStack output) {
			if (recipes.containsValue(output)) for (Entry<ItemStack, ItemStack> entry : recipes.entrySet())
				if (entry.getValue().equals(output)) return entry.getKey();
			if (ItemEnchantedBook.getEnchantments(output).hasNoTags()) return ItemStack.EMPTY;
			ItemStack input = new ItemStack(Items.ENCHANTED_BOOK);
			input.setTagCompound(output.getTagCompound().copy());
			return input;
		}

		public ItemStack getOutput(ItemStack input) {
			if (recipes.containsKey(input)) return recipes.get(input);
			if (ItemEnchantedBook.getEnchantments(input).hasNoTags()) return ItemStack.EMPTY;
			ItemStack output = new ItemStack(ItemEnchantedPlate.instance);
			input.setTagCompound(output.getTagCompound().copy());
			return output;
		}

	}

}
