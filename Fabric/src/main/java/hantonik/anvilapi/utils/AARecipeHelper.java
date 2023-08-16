package hantonik.anvilapi.utils;

import com.google.common.collect.Maps;
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
    public static Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipes(RecipeManager manager) {
        return manager.recipes;
    }

    public static <C extends Container> Map<ResourceLocation, Recipe<C>> getRecipes(RecipeManager manager, RecipeType<Recipe<C>> type) {
        return manager.byType(type);
    }

    public static void addRecipe(RecipeManager manager, Recipe<?> recipe) {
        manager.recipes.computeIfAbsent(recipe.getType(), type -> Maps.newHashMap()).put(recipe.getId(), recipe);
    }

    public static void addRecipe(RecipeManager manager, RecipeType<?> recipeType, Recipe<?> recipe) {
        manager.recipes.computeIfAbsent(recipeType, type -> Maps.newHashMap()).put(recipe.getId(), recipe);
    }
}
