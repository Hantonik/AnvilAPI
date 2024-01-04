package hantonik.anvilapi.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import hantonik.anvilapi.event.callback.AARecipeManagerLoadedCallback;
import hantonik.anvilapi.event.callback.AARecipesUpdatedCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Map;

public final class AARecipeHelper {
    private static RecipeManager MANAGER;

    public static void register() {
        AARecipeManagerLoadedCallback.EVENT.register(manager -> MANAGER = manager);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        AARecipesUpdatedCallback.EVENT.register(manager -> MANAGER = manager);
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

    public static <C extends Container, T extends Recipe<C>> Map<ResourceLocation, T> getRecipes(RecipeType<T> type) {
        return getRecipeManager().byType(type);
    }

    public static <C extends Container, T extends Recipe<C>> void addRecipe(T recipe) {
        getRecipeManager().recipes.computeIfAbsent(recipe.getType(), type -> Maps.newHashMap()).put(recipe.getId(), recipe);
    }

    public static <C extends Container, T extends Recipe<C>> void addRecipe(RecipeType<T> recipeType, T recipe) {
        getRecipeManager().recipes.computeIfAbsent(recipeType, type -> Maps.newHashMap()).put(recipe.getId(), recipe);
    }
}
