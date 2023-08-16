package hantonik.anvilapi.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import hantonik.anvilapi.event.callback.AddServerReloadListenerCallback;
import hantonik.anvilapi.event.callback.RecipeUpdatedCallback;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AARecipeHelper {
    private static RecipeManager MANAGER;

    public static void init() {
        AddServerReloadListenerCallback.EVENT.register((resources, access) -> MANAGER = resources.getRecipeManager());
        RecipeUpdatedCallback.EVENT.register(manager -> MANAGER = manager);
    }

    public static RecipeManager getRecipeManager() {
        if (MANAGER.recipes instanceof ImmutableMap) {
            MANAGER.recipes = Maps.newHashMap(MANAGER.recipes);
            MANAGER.recipes.replaceAll((type, recipes) -> Maps.newHashMap(MANAGER.recipes.get(type)));
        }

        return MANAGER;
    }

    public static Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipes() {
        return getRecipeManager().recipes;
    }

    public static <C extends Container> Map<ResourceLocation, Recipe<C>> getRecipes(RecipeType<Recipe<C>> type) {
        return getRecipeManager().byType(type);
    }

    public static void addRecipe(Recipe<?> recipe) {
        getRecipeManager().recipes.computeIfAbsent(recipe.getType(), type -> Maps.newHashMap()).put(recipe.getId(), recipe);
    }

    public static void addRecipe(RecipeType<?> recipeType, Recipe<?> recipe) {
        getRecipeManager().recipes.computeIfAbsent(recipeType, type -> Maps.newHashMap()).put(recipe.getId(), recipe);
    }
}
