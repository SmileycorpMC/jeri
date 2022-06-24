package net.smileycorp.jemi.plugins.mod_lavacow;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jemi.ModDefinitions;
import net.smileycorp.jemi.plugins.mod_lavacow.IntestineCategory.Wrapper;

import com.Fishmod.mod_LavaCow.util.LootTableHandler;

public class IntestineCategory implements IRecipeCategory<Wrapper> {

	public static final String ID = ModDefinitions.getName("intestines");

	private final IDrawable background;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/mod_lavacow/intestines.png");

	public IntestineCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 167, 113);
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
		return new TextComponentTranslation("jei.category.mod_lavacow.Intestines").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		for (int i = 0; i <3; i++) {
			for (int j = 0; j <4; j++) {
				items.init((i*4)+j, false, j*40+6, i*36+12);
			}
		}
		List<List<ItemStack>> stacks = ingredients.getOutputs(VanillaTypes.ITEM);
		for (int i = 0; i < stacks.size(); i++) {
			items.set(i, stacks.get(i));
		}

	}

	public static class Wrapper implements IRecipeWrapper {

		private final List<ItemStack> outputs = new ArrayList<ItemStack>();

		public Wrapper() {
			List<Entry<ItemStack, Float>> entries = new ArrayList<Entry<ItemStack, Float>>(LootTableHandler.LOOT_INTESTINE.entrySet());
			entries.sort(Collections.reverseOrder(Entry.comparingByValue()));
			for (Entry<ItemStack, Float> entry : entries) {
				outputs.add(entry.getKey());
			}
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setOutputs(VanillaTypes.ITEM,  outputs);
		}

		@Override
		public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
			for (int i = 0; i <3; i++) {
				for (int j = 0; j <4; j++) {
					int chance = Math.round(LootTableHandler.LOOT_INTESTINE.get(outputs.get((i*4)+j)) * 100);
					minecraft.fontRenderer.drawString(String.valueOf(chance) + "%", j*40+26, i*36+18, Color.DARK_GRAY.getRGB());
				}
			}
		}

	}

}
