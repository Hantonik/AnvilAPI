package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.api.recipe.IAnvilRepairRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AARecipeTypes {
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "minecraft");

    public static final RegistryObject<RecipeType<IAnvilRecipe>> ANVIL = register("anvil");
    public static final RegistryObject<RecipeType<IAnvilRepairRecipe>> ANVIL_REPAIR = register("anvil_repair");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(final String name) {
        return TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
