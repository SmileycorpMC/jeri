package net.smileycorp.jemi;

import net.minecraft.util.ResourceLocation;

public class ModDefinitions {

	//mod constants
	public static final String MODID = "jemi";
	public static final String NAME = "Just Enough Mod Integrations";
	public static final String VERSIONS = "1.1.0";
	public static final String DEPENDENCIES = "required-after:jei";

	//helper methods
	public static String getName(String name) {
		return MODID + "." + name.replace("_", "");
	}

	public static ResourceLocation getResource(String name) {
		return new ResourceLocation(MODID, name.toLowerCase());
	}

}
