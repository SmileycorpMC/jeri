package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.init.FurnitureItems;

public class SoapCategory extends CFMFuelCategory {

	public static final String ID = ModDefinitions.getName("soap");

	public SoapCategory(IGuiHelper guiHelper) {
		super(guiHelper, new ItemStack(FurnitureItems.SOAPY_WATER));
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.Soap").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<Wrapper> getRecipes(IGuiHelper guiHelper) {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		recipes.add(new Wrapper(guiHelper, new ItemStack(FurnitureItems.SOAPY_WATER), false));
		recipes.add(new Wrapper(guiHelper, new ItemStack(FurnitureItems.SUPER_SOAPY_WATER), true));
		return recipes;
	}

	public static class Wrapper extends CFMFuelWrapper {

		protected final boolean isSuper;

		public Wrapper(IGuiHelper guiHelper, ItemStack input, boolean isSuper) {
			super(guiHelper, input, 5000, 5000d);
			this.isSuper = isSuper;
		}

		@Override
		public List<String> getTooltipStrings( int mouseX, int mouseY) {
			if (mouseX >= 48 && mouseX <= 64 && mouseY >= 6 && mouseY <= 22) {
				return Lists.newArrayList(new TextComponentTranslation(getUnlocalizedTooltipString())
				.getFormattedText().replace("/", ""),
				new TextComponentTranslation("jei.tooltip.jeri.TPO", isSuper ? 20 : 50).getFormattedText());
			}
			return super.getTooltipStrings(mouseX, mouseY);
		}

		@Override
		public String getUnlocalizedTooltipString() {
			return "cfm.gui.soap_level";
		}

		@Override
		public IDrawable getFuelDrawable(IGuiHelper guiHelper) {
			return guiHelper.createDrawable(TEXTURE, isSuper ? 48 : 32, 44-fuelOffset, 16, fuelOffset);
		}

	}

}
