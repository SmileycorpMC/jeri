package net.smileycorp.jeri.plugins.cfm;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import com.google.common.collect.Lists;

public abstract class CFMFuelWrapper implements IRecipeWrapper {

	protected final ItemStack input;
	protected final int fuelOutput;
	protected final int fuelOffset;
	protected IGuiHelper guiHelper;
	protected IDrawable fuel;

	public CFMFuelWrapper(IGuiHelper guiHelper, ItemStack input, int fuelOutput, double max) {
		this.input = input;
		this.fuelOutput = fuelOutput;
		fuelOffset = Math.max(1,(int)Math.round((fuelOutput)*(16d/max)));
		this.guiHelper = guiHelper;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, Lists.newArrayList(input));
	}

	@Override
	public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (guiHelper != null) {
			fuel = getFuelDrawable(guiHelper);
			guiHelper = null;
		}
		if (fuel != null) fuel.draw(mc, 49, 22 - fuelOffset);
	}

	@Override
	public List<String> getTooltipStrings( int mouseX, int mouseY) {
		if (mouseX >= 48 && mouseX <= 64 && mouseY >= 6 && mouseY <= 22) {
			return Lists.newArrayList(new TextComponentTranslation(getUnlocalizedTooltipString())
			.getFormattedText().replace("/", String.valueOf(fuelOutput)));
		}
		return IRecipeWrapper.super.getTooltipStrings(mouseX, mouseY);
	}

	public abstract String getUnlocalizedTooltipString();

	public abstract IDrawable getFuelDrawable(IGuiHelper guiHelper);

}