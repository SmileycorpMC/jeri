package net.smileycorp.jeri.plugins.bibliocraft;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.api.SimpleRecipeCategory;
import net.smileycorp.jeri.api.SimpleRecipeWrapper;

public class TypewriterCategory extends SimpleRecipeCategory<SimpleRecipeWrapper> {

	public static final String ID = ModDefinitions.getName("typewriter");

	public TypewriterCategory(IGuiHelper guiHelper) {
		super(guiHelper, true);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.bibliocraft.Typewriter").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<SimpleRecipeWrapper> getRecipes() {
		List<SimpleRecipeWrapper> recipes = new ArrayList<>();
		recipes.add(new SimpleRecipeWrapper(new ItemStack(Items.PAPER, 8), new ItemStack(Items.WRITTEN_BOOK)));
		return recipes;
	}

}
