package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

import com.mrcrayfish.furniture.init.FurnitureItems;

public class PrinterInkCategory extends CFMFuelCategory {

	public static final String ID = ModDefinitions.getName("printer_ink");

	public PrinterInkCategory(IGuiHelper guiHelper) {
		super(guiHelper, new ItemStack(FurnitureItems.INK_CARTRIDGE));
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.PrinterInk").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<Wrapper> getRecipes(IGuiHelper guiHelper) {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		recipes.add(new Wrapper(guiHelper, new ItemStack(Items.DYE, 1, 0), 1000));
		recipes.add(new Wrapper(guiHelper, new ItemStack(FurnitureItems.INK_CARTRIDGE), 5000));
		return recipes;
	}

	public static class Wrapper extends CFMFuelWrapper {

		public Wrapper(IGuiHelper guiHelper, ItemStack input, int iceOutput) {
			super(guiHelper, input, iceOutput, 5000d);
		}

		@Override
		public String getUnlocalizedTooltipString() {
			return "cfm.gui.ink_level";
		}

		@Override
		public IDrawable getFuelDrawable(IGuiHelper guiHelper) {
			return guiHelper.createDrawable(TEXTURE, 16, 44-fuelOffset, 16, fuelOffset);
		}

	}

}
