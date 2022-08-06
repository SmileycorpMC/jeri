package net.smileycorp.jeri.plugins.car;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.smileycorp.jeri.JEIPluginJERI;
import net.smileycorp.jeri.JERIPlugin;

import com.google.common.collect.Lists;

import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.items.ModItems;

@JERIPlugin(modid = "car")
public class CarPlugin implements JEIPluginJERI {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new OilMillCategory(guiHelper));
		registry.addRecipeCategories(new BlastFurnaceCategory(guiHelper));
		registry.addRecipeCategories(new BackmixReactorCategory(guiHelper));
		registry.addRecipeCategories(new SplitTankCategory(guiHelper));
	}

	@Override
	public void register(IModRegistry registry) {
		//oil mill
		registry.handleRecipes(CarMachineRecipeWrapper.class, (r) -> r, OilMillCategory.ID);
		registry.addRecipes(Lists.newArrayList(new CarMachineRecipeWrapper(new ItemStack(ModItems.CANOLA), new ItemStack(ModItems.RAPECAKE), new FluidStack(ModFluids.CANOLA_OIL, 100))), OilMillCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(de.maxhenkel.car.blocks.ModBlocks.OIL_MILL), OilMillCategory.ID);

		//blast furnace
		registry.handleRecipes(CarMachineRecipeWrapper.class, (r) -> r, BlastFurnaceCategory.ID);
		registry.addRecipes(Lists.newArrayList(new CarMachineRecipeWrapper(OreDictionary.getOres("logWood"), new ItemStack(Items.COAL, 1, 1), new FluidStack(ModFluids.METHANOL, 100))), BlastFurnaceCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(de.maxhenkel.car.blocks.ModBlocks.BLAST_FURNACE), BlastFurnaceCategory.ID);

		//backmix reactor
		registry.handleRecipes(BackmixReactorCategory.Wrapper.class, (r) -> r, BackmixReactorCategory.ID);
		registry.addRecipes(Lists.newArrayList(new BackmixReactorCategory.Wrapper()), BackmixReactorCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(de.maxhenkel.car.blocks.ModBlocks.BACKMIX_REACTOR), BackmixReactorCategory.ID);

		//split tank
		registry.handleRecipes(SplitTankCategory.Wrapper.class, (r) -> r, SplitTankCategory.ID);
		registry.addRecipes(Lists.newArrayList(new SplitTankCategory.Wrapper()), SplitTankCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(de.maxhenkel.car.blocks.ModBlocks.SPLIT_TANK), SplitTankCategory.ID);
	}

}
