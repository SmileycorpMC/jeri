package net.smileycorp.jeri.plugins.cfm;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

import com.mrcrayfish.furniture.init.FurnitureItems;

public class FreezerCoolantCategory extends CFMFuelCategory {

	public static final String ID = ModDefinitions.getName("freezer_coolants");

	public FreezerCoolantCategory(IGuiHelper guiHelper) {
		super(guiHelper, new ItemStack(FurnitureItems.COOL_PACK));
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.cfm.FreezerCoolants").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

	public static List<Wrapper> getRecipes(IGuiHelper guiHelper) {
		List<Wrapper> recipes = new ArrayList<Wrapper>();
		recipes.add(new Wrapper(guiHelper, new ItemStack(Blocks.PACKED_ICE), 3000));
		recipes.add(new Wrapper(guiHelper, new ItemStack(Blocks.ICE), 2000));
		recipes.add(new Wrapper(guiHelper, new ItemStack(FurnitureItems.COOL_PACK), 400));
		return recipes;
	}

	public static class Wrapper extends CFMFuelWrapper {

		public Wrapper(IGuiHelper guiHelper, ItemStack input, int iceOutput) {
			super(guiHelper, input, iceOutput, 3000d);
		}

		@Override
		public String getUnlocalizedTooltipString() {
			return "cfm.gui.ice_level";
		}

		@Override
		public IDrawable getFuelDrawable(IGuiHelper guiHelper) {
			return guiHelper.createDrawable(TEXTURE, 0, 44-fuelOffset, 16, fuelOffset);
		}

	}

}
