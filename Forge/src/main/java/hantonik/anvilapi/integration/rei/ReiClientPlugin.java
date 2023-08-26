package hantonik.anvilapi.integration.rei;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.integration.rei.category.AnvilCategory;
import hantonik.anvilapi.integration.rei.category.display.AnvilDisplay;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.tags.ItemTags;

@REIPluginClient
public final class ReiClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new AnvilCategory());

        registry.addWorkstations(AnvilCategory.ID, EntryIngredients.ofItemTag(ItemTags.ANVIL));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(IAnvilRecipe.class, AARecipeTypes.ANVIL.get(), AnvilDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(102, 48, 22, 15), AnvilScreen.class, AnvilCategory.ID);
    }
}
