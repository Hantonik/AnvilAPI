package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.recipe.AnvilRecipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AARecipeSerializers {
    public static final RecipeSerializer<IAnvilRecipe> ANVIL = new AnvilRecipe.Serializer();

    public static void onInit() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("anvil"), ANVIL);
    }
}
