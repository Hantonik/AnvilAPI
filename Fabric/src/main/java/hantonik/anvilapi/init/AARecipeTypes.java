package hantonik.anvilapi.init;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AARecipeTypes {
    public static final RecipeType<IAnvilRecipe> ANVIL = new RecipeType<>(){
        public String toString() {
            return "anvil";
        }
    };

    public static void onInit() {
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation("anvil"), ANVIL);
    }
}
