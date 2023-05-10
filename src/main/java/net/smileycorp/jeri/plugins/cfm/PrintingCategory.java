package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocus.Mode;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.ModUtils;

public class PrintingCategory implements IRecipeCategory<PrintingCategory.Wrapper> {

	public static final String ID = ModDefinitions.getName("printing");

	private final IDrawable background;

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/cfm/printer.png");

	public PrintingCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEXTURE, 0, 0, 38, 62);
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
		return new TextComponentTranslation("jei.category.cfm.Printing").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, false, 10, 44);
		items.init(1, true, 10, 0);
		items.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		IFocus<?> focus = recipeLayout.getFocus();
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

	public static List<Wrapper> getRecipes(IGuiHelper guiHelper) {
		List<Wrapper> recipes = new ArrayList<>();
		for (RecipeData recipe : Recipes.getRecipes("printer")) {
			recipes.add(new Wrapper(guiHelper, recipe));
		}
		return recipes;
	}

	public static class Wrapper implements IRecipeWrapper {

		private final Map<ItemStack, ItemStack> recipes = Maps.newHashMap();

		protected final int inkCost;
		protected final int inkOffset;
		private final IDrawableAnimated progress;
		private final IDrawableAnimated ink;

		public Wrapper(IGuiHelper guiHelper, RecipeData recipe) {
			boolean isEnchantedBook = recipe.getInput().getItem() == Items.ENCHANTED_BOOK;
			if (isEnchantedBook) {
				for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
					if (enchantment.type != null) {
						for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
							ItemStack input = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i));
							ItemStack output = input.copy();
							output.setCount(2);
							recipes.put(input, output);
						}
					}
				}
			}
			else {
				ItemStack input = recipe.getInput();
				ItemStack output = input.copy();
				output.setCount(2);
				recipes.put(input, output);
			}
			inkCost = isEnchantedBook ? 10000 : 1000;
			inkOffset = Math.max(1,(int)Math.round((inkCost)*(16d/10000)));
			int ticks = (int)Math.round((inkCost)/2d);
			IDrawableStatic progressDrawable = guiHelper.createDrawable(TEXTURE, 3, 62, 16, 24);
			progress = guiHelper.createAnimatedDrawable(progressDrawable, ticks, IDrawableAnimated.StartDirection.TOP, false);
			IDrawableStatic inkDrawable = guiHelper.createDrawable(TEXTURE, 0, 78 - inkOffset, 3, inkOffset);
			ink = guiHelper.createAnimatedDrawable(inkDrawable, ticks, IDrawableAnimated.StartDirection.TOP, true);
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

		@Override
		public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			progress.draw(mc, 12, 19);
			ink.draw(mc, 7, 39 - inkOffset);
		}

		@Override
		public List<String> getTooltipStrings( int mouseX, int mouseY) {
			if (mouseX >= 7 && mouseX <= 10 && mouseY >= 23 && mouseY <= 39) {
				return Lists.newArrayList(new TextComponentTranslation("cfm.gui.ink_level")
						.getFormattedText().replace("/", String.valueOf(inkCost)));
			}
			return IRecipeWrapper.super.getTooltipStrings(mouseX, mouseY);
		}

		public ItemStack getInput(ItemStack output) {
			if (recipes.containsValue(output)) for (Entry<ItemStack, ItemStack> entry : recipes.entrySet())
				if (entry.getValue().equals(output)) return entry.getKey();
			if (ItemEnchantedBook.getEnchantments(output).hasNoTags()) return ItemStack.EMPTY;
			ItemStack input = output.copy();
			input.setCount(1);
			return input;
		}

		public ItemStack getOutput(ItemStack input) {
			if (recipes.containsKey(input)) return recipes.get(input);
			if (ItemEnchantedBook.getEnchantments(input).hasNoTags()) return ItemStack.EMPTY;
			ItemStack output = input.copy();
			output.setCount(2);
			return output;
		}

	}

}
