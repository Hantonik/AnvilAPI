package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.recipe.AnvilRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AARecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "minecraft");

    public static final RegistryObject<RecipeSerializer<IAnvilRecipe>> ANVIL = SERIALIZERS.register("anvil", AnvilRecipe.Serializer::new);
}
