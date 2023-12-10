package hantonik.anvilapi.init;

import hantonik.anvilapi.recipe.AnvilRecipe;
import hantonik.anvilapi.recipe.AnvilRepairRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class AARecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, "minecraft");

    public static final Supplier<RecipeSerializer<AnvilRecipe>> ANVIL = SERIALIZERS.register("anvil", AnvilRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<AnvilRepairRecipe>> ANVIL_REPAIR = SERIALIZERS.register("anvil_repair", AnvilRepairRecipe.Serializer::new);
}
