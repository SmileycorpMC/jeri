package net.smileycorp.jemi.plugins.car;

import mezz.jei.api.IGuiHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.smileycorp.jemi.ModDefinitions;

public class OilMillCategory extends CarMachineCategory {

	public static final String ID = ModDefinitions.getName("oil_mill");

	public OilMillCategory(IGuiHelper guiHelper) {
		super(guiHelper);
	}

	@Override
	public String getTitle() {
		return new TextComponentTranslation("jei.category.car.OilMill").getFormattedText();
	}

	@Override
	public String getUid() {
		return ID;
	}

}
