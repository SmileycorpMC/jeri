package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.api.SimpleCatalystRecipeCategory;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class CuttingCategory extends SimpleCatalystRecipeCategory<CFMRecipeWrapper> {

	public static final String ID = ModDefinitions.getName("cutting");

	private final IDrawable board;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/cutting_board.png");

	public CuttingCategory(IGuiHelper guiHelper) {
		super(guiHelper, new ItemStack(FurnitureItems.KNIFE), false);
		board = guiHelper.createDrawableIngredient(new ItemStack(FurnitureBlocks.CHOPPING_BOARD));
	}

	@Override
	public ResourceLocation getTexture() {
		return TEXTURE;
	}

	@Override
	@Nullable
	public IDrawable getIcon() {
		return board;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Cutting").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<CFMRecipeWrapper> getRecipes() {
		List<CFMRecipeWrapper> recipes = new ArrayList<CFMRecipeWrapper>();
		for (RecipeData recipe : Recipes.getRecipes("choppingboard")) {
			recipes.add(new CFMRecipeWrapper(recipe));
		}
		return recipes;
	}

}
