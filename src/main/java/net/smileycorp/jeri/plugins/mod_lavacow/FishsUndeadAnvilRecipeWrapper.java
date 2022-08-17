package net.smileycorp.jeri.plugins.mod_lavacow;

import java.util.List;

import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class FishsUndeadAnvilRecipeWrapper extends AnvilRecipeWrapper {

	public static List<FishsUndeadAnvilRecipeWrapper> getRecipes() {
		List<FishsUndeadAnvilRecipeWrapper> list = Lists.newArrayList();
		for (Item item : ForgeRegistries.ITEMS) if (item instanceof ItemSword || item instanceof ItemTool) {
			list.add(new FishsUndeadAnvilRecipeWrapper(item, FishItems.POISONSPORE, ModEnchantments.POISONOUS, 3));
			list.add(new FishsUndeadAnvilRecipeWrapper(item, FishItems.UNDYINGHEART, ModEnchantments.LIFESTEAL, 3));
			list.add(new FishsUndeadAnvilRecipeWrapper(item, FishItems.ACIDICHEART, ModEnchantments.CORROSIVE, 1));
		}
		return list;
	}

	public FishsUndeadAnvilRecipeWrapper(Item tool, Item ingredient, Enchantment enchant, int level) {
		super(Lists.newArrayList(new ItemStack(tool)), Lists.newArrayList(new ItemStack(ingredient)), makeOutput(tool, enchant, level));
	}

	private static List<ItemStack> makeOutput(Item tool, Enchantment enchant, int level) {
		ItemStack stack = new ItemStack(tool);
		EnchantmentHelper.setEnchantments(ImmutableMap.of(enchant, level), stack);
		return Lists.newArrayList(stack);
	}

}
