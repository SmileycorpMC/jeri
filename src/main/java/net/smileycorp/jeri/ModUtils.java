package net.smileycorp.jeri;

import java.util.List;

import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;

public class ModUtils {

	public static List<List<ItemStack>> wrapList(List<ItemStack> list) {
		List<List<ItemStack>> wrapped = Lists.newArrayList();
		wrapped.add(list);
		return wrapped;
	}

}
