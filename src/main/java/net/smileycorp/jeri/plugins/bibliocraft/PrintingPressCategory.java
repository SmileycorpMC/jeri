package net.smileycorp.jeri.plugins.bibliocraft;

import java.util.List;

import com.google.common.collect.Lists;

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
import net.smileycorp.jeri.ModUtils;
import net.smileycorp.jeri.SimpleCatalystRecipeCategory;

public class PrintingPressCategory extends SimpleCatalystRecipeCategory<PrintingPressCategory.Wrapper> {

	public static final String ID = ModDefinitions.getName("printing_press");

	public PrintingPressCategory(IGuiHelper guiHelper) {
		super(guiHelper, new ItemStack(Items.DYE, 1, 0), false);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.bibliocraft.PrintingPress").getFormattedText();
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

		private final List<ItemStack> inputs = Lists.newArrayList();
		private final List<ItemStack> outputs = Lists.newArrayList();

		public Wrapper() {
			for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
				if (enchantment.type != null) {
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
						outputs.add(ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
					}
				}
			}
			for (ItemStack output : outputs) {
				ItemStack input = new ItemStack(ItemEnchantedPlate.instance);
				input.setTagCompound(output.getTagCompound().copy());
				inputs.add(input);
			}
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, ModUtils.wrapList(inputs));
			ingredients.setOutputLists(VanillaTypes.ITEM, ModUtils.wrapList(outputs));
		}

		public ItemStack getInput(ItemStack output) {
			if (!ItemEnchantedBook.getEnchantments(output).hasNoTags()) {
				ItemStack input = new ItemStack(ItemEnchantedPlate.instance);
				input.setTagCompound(output.getTagCompound().copy());
				return input;
			}
			return ItemStack.EMPTY;
		}

		public ItemStack getOutput(ItemStack input) {
			if (!ItemEnchantedBook.getEnchantments(input).hasNoTags()) {
				ItemStack output = new ItemStack(Items.ENCHANTED_BOOK);
				output.setTagCompound(input.getTagCompound().copy());
				return output;
			}
			return ItemStack.EMPTY;
		}

	}

}
