package hantonik.anvilapi.integration.jei;

import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.integration.jei.category.AnvilRecipeCategory;
import hantonik.anvilapi.utils.RecipeHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.*;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

@JeiPlugin
public final class JeiIntegration implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(AnvilAPI.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AnvilRecipeCategory.RECIPE_TYPE, RecipeHelper.getRecipeManager().getAllRecipesFor(AARecipeTypes.ANVIL.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.ANVIL), AnvilRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.CHIPPED_ANVIL), AnvilRecipeCategory.RECIPE_TYPE, RecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(Blocks.DAMAGED_ANVIL), AnvilRecipeCategory.RECIPE_TYPE, RecipeTypes.ANVIL);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(AnvilMenu.class, MenuType.ANVIL, AnvilRecipeCategory.RECIPE_TYPE, 0, 2, 3, 36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AnvilScreen.class, 102, 48, 22, 15, AnvilRecipeCategory.RECIPE_TYPE, RecipeTypes.ANVIL);
    }
}
