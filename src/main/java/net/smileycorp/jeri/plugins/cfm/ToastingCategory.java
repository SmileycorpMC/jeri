package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.api.SimpleRecipeCategory;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

public class ToastingCategory extends SimpleRecipeCategory<CFMRecipeWrapper> {

	public static final String ID = ModDefinitions.getName("toasting");

	public ToastingCategory(IGuiHelper guiHelper) {
		super(guiHelper, true);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Toasting").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<CFMRecipeWrapper> getRecipes() {
		List<CFMRecipeWrapper> recipes = new ArrayList<CFMRecipeWrapper>();
		for (RecipeData recipe : Recipes.getRecipes("toaster")) {
			recipes.add(new CFMRecipeWrapper(recipe));
		}
		return recipes;
	}

}
