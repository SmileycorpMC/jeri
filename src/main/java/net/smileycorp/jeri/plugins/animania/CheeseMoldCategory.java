package net.smileycorp.jeri.plugins.animania;

import java.util.List;

import com.animania.addons.farm.common.handler.FarmAddonBlockHandler;
import com.animania.addons.farm.common.handler.FarmAddonItemHandler;
import com.animania.addons.farm.config.FarmConfig;
import com.google.common.collect.Lists;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.smileycorp.jeri.ModDefinitions;
import net.smileycorp.jeri.api.SimpleRecipeCategory;

public class CheeseMoldCategory extends SimpleRecipeCategory<CheeseMoldCategory.Wrapper> {

	public static final String ID = ModDefinitions.getName("cheese_mold");

	public static final ResourceLocation TEXTURE = ModDefinitions.getResource("textures/gui/simple_recipe.png");

	public CheeseMoldCategory(IGuiHelper guiHelper) {
		super(guiHelper, true);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.animania.CheeseMold").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, Wrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup items = recipeLayout.getItemStacks();
		items.init(0, true, 1, 5);
		items.init(1, false, 48, 5);
		items.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		items.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	public static List<Wrapper> getRecipes() {
		return Lists.newArrayList(new Wrapper(FarmAddonBlockHandler.fluidMilkFriesian, FarmAddonItemHandler.cheeseWheelFriesian),
				new Wrapper(FarmAddonBlockHandler.fluidMilkHolstein, FarmAddonItemHandler.cheeseWheelHolstein),
				new Wrapper(FarmAddonBlockHandler.fluidMilkGoat, FarmAddonItemHandler.cheeseWheelGoat),
				new Wrapper(FarmAddonBlockHandler.fluidMilkSheep, FarmAddonItemHandler.cheeseWheelSheep),
				new Wrapper(FarmAddonBlockHandler.fluidMilkJersey, FarmAddonItemHandler.cheeseWheelJersey),
				new Wrapper(FluidRegistry.WATER, new ItemStack(FarmAddonItemHandler.salt, FarmConfig.settings.saltCreationAmount)));
	}

	public static class Wrapper implements IRecipeWrapper {

		protected final Fluid input;
		protected final ItemStack output;

		public Wrapper(Fluid input, Item output) {
			this(input, new ItemStack(output));
		}

		public Wrapper(Fluid input, ItemStack output) {
			this.input = input;
			this.output = output;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(FluidUtil.getFilledBucket(new FluidStack(input, 1000))));
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}

		@Override
		public void drawInfo(Minecraft minecraft, int width, int height, int mouseX, int mouseY) {
			String text = new TextComponentTranslation("jei.tooltip.jeri.Ticks", 1000).getFormattedText();
			minecraft.fontRenderer.drawString(text, (width-minecraft.fontRenderer.getStringWidth(text))/2, -2, 0x404040);
		}

	}

}
