package hantonik.anvilapi.integration.jei;

import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.integration.jei.category.AnvilRecipeCategory;
import hantonik.anvilapi.utils.AARecipeHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.registration.*;
import mezz.jei.library.plugins.vanilla.anvil.AnvilRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.stream.Stream;

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
        registration.addRecipes(AnvilRecipeCategory.RECIPE_TYPE, AARecipeHelper.getRecipeManager().getAllRecipesFor(AARecipeTypes.ANVIL.get()));
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

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        registration.getRecipeManager().addRecipes(RecipeTypes.ANVIL, AARecipeHelper.getRecipes(AARecipeTypes.ANVIL_REPAIR.get()).values().stream().map(recipe -> (IJeiAnvilRecipe) new AnvilRecipe(Stream.of(new ItemStack(recipe.getBaseItem())).peek(input -> input.setDamageValue(input.getMaxDamage())).toList(), Arrays.asList(recipe.getRepairItem().getItems()), Stream.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())).peek(input -> input.setDamageValue(input.getMaxDamage() * 3 / 4)).toList())).toList());
    }
}
