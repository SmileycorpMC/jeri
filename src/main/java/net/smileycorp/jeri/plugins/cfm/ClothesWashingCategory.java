package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.SimpleRecipeCategory;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

public class ClothesWashingCategory extends SimpleRecipeCategory<CFMWashingWrapper> {

	public static final String ID = ModDefinitions.getName("clothes_washing");

	public ClothesWashingCategory(IGuiHelper guiHelper) {
		super(guiHelper, true);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.ClothesWashing").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<CFMWashingWrapper> getRecipes() {
		List<CFMWashingWrapper> recipes = new ArrayList<CFMWashingWrapper>();
		for (RecipeData recipe : Recipes.getRecipes("washingmachine")) {
			recipes.add(new CFMWashingWrapper(recipe));
		}
		return recipes;
	}

}
