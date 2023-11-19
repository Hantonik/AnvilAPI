package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.api.recipe.IAnvilRepairRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class AARecipeTypes {
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, "minecraft");

    public static final Supplier<RecipeType<IAnvilRecipe>> ANVIL = register("anvil");
    public static final Supplier<RecipeType<IAnvilRepairRecipe>> ANVIL_REPAIR = register("anvil_repair");

    private static <T extends Recipe<?>> Supplier<RecipeType<T>> register(final String name) {
        return TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
