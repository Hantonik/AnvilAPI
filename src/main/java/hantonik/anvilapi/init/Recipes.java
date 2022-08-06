package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipes.IAnvilRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class Recipes {
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "minecraft");

    public static final RegistryObject<RecipeType<IAnvilRecipe>> ANVIL = register("anvil");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(final String name) {
        return RECIPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
