package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.api.recipe.IAnvilRepairRecipe;
import hantonik.anvilapi.recipe.AnvilRecipe;
import hantonik.anvilapi.recipe.AnvilRepairRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public final class AARecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "minecraft");

    public static final RegistryObject<RecipeSerializer<IAnvilRecipe>> ANVIL = SERIALIZERS.register("anvil", AnvilRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<IAnvilRepairRecipe>> ANVIL_REPAIR = SERIALIZERS.register("anvil_repair", AnvilRepairRecipe.Serializer::new);
}
