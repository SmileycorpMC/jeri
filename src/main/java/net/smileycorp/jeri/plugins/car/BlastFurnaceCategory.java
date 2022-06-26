package net.smileycorp.jeri.plugins.car;

import mezz.jei.api.IGuiHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jeri.ModDefinitions;

public class BlastFurnaceCategory extends CarMachineCategory {

	public static final String ID = ModDefinitions.getName("blast_furnace");

	public BlastFurnaceCategory(IGuiHelper guiHelper) {
		super(guiHelper);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.car.BlastFurnace").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

}
