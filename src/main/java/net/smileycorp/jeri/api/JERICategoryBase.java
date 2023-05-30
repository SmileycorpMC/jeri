package net.smileycorp.jeri.api;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.smileycorp.jeri.ModDefinitions;

public abstract class JERICategoryBase<T extends IRecipeWrapper> implements IRecipeCategory<T>{

	private final IDrawable background;
	protected IDrawableAnimated progress;
	protected final int arrowX, arrowY;

	public JERICategoryBase(IGuiHelper guiHelper, boolean animateArrow, int width, int height, int arrowX, int arrowY) {
		ResourceLocation texture = getTexture();
		background = guiHelper.createDrawable(texture, 0, 0, width, height);
		if (animateArrow) {
			IDrawableStatic progressDrawable = guiHelper.createDrawable(texture, 0, height, 24, 16);
			progress = guiHelper.createAnimatedDrawable(progressDrawable, 300, IDrawableAnimated.StartDirection.LEFT, false);
		}
		this.arrowX = arrowX;
		this.arrowY = arrowY;
	}

	public abstract ResourceLocation getTexture();

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(@Nonnull Minecraft minecraft) {
		if (progress != null) progress.draw(minecraft, arrowX, arrowY);
	}

	@Override
	public String getModName() {
		return ModDefinitions.MODID;
	}

}
