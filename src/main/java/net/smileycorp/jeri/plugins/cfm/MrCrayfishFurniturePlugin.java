package net.smileycorp.jeri.plugins.cfm;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.smileycorp.jeri.JEIPluginJERI;
import net.smileycorp.jeri.JERIPlugin;
import net.smileycorp.jeri.JustEnoughRecipeIntegrations;

@JERIPlugin(modid = "cfm")
public class MrCrayfishFurniturePlugin implements JEIPluginJERI {

	private static Map<IRecipeWrapper, String> recipeMap = Maps.newHashMap();

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new MineBayCategory(guiHelper));
		registry.addRecipeCategories(new OvenCategory(guiHelper));
		registry.addRecipeCategories(new FreezingCategory(guiHelper));
		registry.addRecipeCategories(new FreezerCoolantCategory(guiHelper));
		registry.addRecipeCategories(new PrintingCategory(guiHelper));
		registry.addRecipeCategories(new PrinterInkCategory(guiHelper));
		registry.addRecipeCategories(new ToastingCategory(guiHelper));
		registry.addRecipeCategories(new CuttingCategory(guiHelper));
		registry.addRecipeCategories(new BlendingCategory(guiHelper));
		registry.addRecipeCategories(new DishwashingCategory(guiHelper));
		registry.addRecipeCategories(new ClothesWashingCategory(guiHelper));
		registry.addRecipeCategories(new SoapCategory(guiHelper));
		registry.addRecipeCategories(new MicrowavingCategory(guiHelper));
		registry.addRecipeCategories(new GrillingCategory(guiHelper));
	}

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		//computer
		registry.handleRecipes(MineBayCategory.Wrapper.class, r->r, MineBayCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.COMPUTER), MineBayCategory.ID);
		//oven
		registry.handleRecipes(CFMRecipeWrapper.class, r->r, OvenCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.OVEN), OvenCategory.ID);
		//freezer
		registry.handleRecipes(CFMRecipeWrapper.class, r->r, FreezingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.FREEZER), FreezingCategory.ID);
		//freezer coolants
		registry.handleRecipes(FreezerCoolantCategory.Wrapper.class, r->r, FreezerCoolantCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.FREEZER), FreezerCoolantCategory.ID);
		registry.addRecipes(FreezerCoolantCategory.getRecipes(guiHelper), FreezerCoolantCategory.ID);
		//printer
		registry.handleRecipes(PrintingCategory.Wrapper.class, r->r, PrintingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.PRINTER), PrintingCategory.ID);
		//printer ink
		registry.handleRecipes(PrinterInkCategory.Wrapper.class, r->r, PrinterInkCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.PRINTER), PrinterInkCategory.ID);
		registry.addRecipes(PrinterInkCategory.getRecipes(guiHelper), PrinterInkCategory.ID);
		//toaster
		registry.handleRecipes(CFMRecipeWrapper.class, r->r, ToastingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.TOASTER), ToastingCategory.ID);
		//cutting board
		registry.handleRecipes(CFMRecipeWrapper.class, r->r, CuttingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureItems.KNIFE), CuttingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.CHOPPING_BOARD), CuttingCategory.ID);
		//blender
		registry.handleRecipes(BlendingCategory.Wrapper.class, r->r, BlendingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.BLENDER), BlendingCategory.ID);
		//dishwasher
		registry.handleRecipes(CFMWashingWrapper.class, r->r, DishwashingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.DISHWASHER), DishwashingCategory.ID);
		//washing machine
		registry.handleRecipes(CFMWashingWrapper.class, r->r, ClothesWashingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.WASHING_MACHINE), ClothesWashingCategory.ID);
		//soap
		registry.handleRecipes(SoapCategory.Wrapper.class, r->r, SoapCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.DISHWASHER), SoapCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.WASHING_MACHINE), SoapCategory.ID);
		registry.addRecipes(SoapCategory.getRecipes(guiHelper), SoapCategory.ID);
		//microwave
		registry.handleRecipes(CFMRecipeWrapper.class, r->r, MicrowavingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.MICROWAVE), MicrowavingCategory.ID);
		//grill
		registry.handleRecipes(CFMRecipeWrapper.class, r->r, GrillingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureBlocks.GRILL), GrillingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(FurnitureItems.SPATULA), GrillingCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(Items.FLINT_AND_STEEL), GrillingCategory.ID);
	}

	//register recipes when player logs in to catch IMC and config recipes
	@Override
	@SuppressWarnings("deprecation")
	public void onPlayerJoinWorld(IRecipeRegistry registry) {
		IGuiHelper guiHelper = JustEnoughRecipeIntegrations.getHelpers().getGuiHelper();
		for (Entry<IRecipeWrapper, String> entry : recipeMap.entrySet()) registry.removeRecipe(entry.getKey(), entry.getValue());
		recipeMap.clear();
		addRecipes(registry, MineBayCategory.ID, MineBayCategory.getRecipes());
		addRecipes(registry, OvenCategory.ID, OvenCategory.getRecipes());
		addRecipes(registry, FreezingCategory.ID, FreezingCategory.getRecipes());
		addRecipes(registry, PrintingCategory.ID, PrintingCategory.getRecipes(guiHelper));
		addRecipes(registry, ToastingCategory.ID, ToastingCategory.getRecipes());
		addRecipes(registry, CuttingCategory.ID, CuttingCategory.getRecipes());
		addRecipes(registry, BlendingCategory.ID, BlendingCategory.getRecipes());
		addRecipes(registry, DishwashingCategory.ID, DishwashingCategory.getRecipes());
		addRecipes(registry, ClothesWashingCategory.ID, ClothesWashingCategory.getRecipes());
		addRecipes(registry, MicrowavingCategory.ID, MicrowavingCategory.getRecipes());
		addRecipes(registry, GrillingCategory.ID, GrillingCategory.getRecipes());
	}

	@SuppressWarnings("deprecation")
	private void addRecipes(IRecipeRegistry registry, String category, Collection<? extends IRecipeWrapper> recipes) {
		for (IRecipeWrapper recipe : recipes) {
			registry.addRecipe(recipe, category);
			recipeMap.put(recipe, category);
		}
	}

}
