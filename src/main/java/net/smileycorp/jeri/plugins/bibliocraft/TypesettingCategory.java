package net.smileycorp.jeri.plugins.bibliocraft;

import java.util.List;

import jds.bibliocraft.items.ItemChase;
import jds.bibliocraft.items.ItemEnchantedPlate;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.ModUtils;
import net.smileycorp.jeri.SimpleCatalystRecipeCategory;

import com.google.common.collect.Lists;

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

	public static List<Wrapper> getRecipes() {
		return Lists.newArrayList(new Wrapper());
	}

	public static class Wrapper implements IRecipeWrapper {

		private final List<ItemStack> inputs = Lists.newArrayList();
		private final List<ItemStack> outputs = Lists.newArrayList();

		public Wrapper() {
			for (Enchantment enchantment : Enchantment.REGISTRY) {
				if (enchantment.type != null) {
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
						inputs.add(ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i)));
					}
				}
			}
			for (ItemStack input : inputs) {
				ItemStack output = new ItemStack(ItemEnchantedPlate.instance);
				output.setTagCompound(input.getTagCompound().copy());
				outputs.add(output);
			}
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, ModUtils.wrapList(inputs));
			ingredients.setOutputLists(VanillaTypes.ITEM, ModUtils.wrapList(outputs));
		}

	}

}
