package hantonik.anvilapi.api.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public interface IAnvilRecipe extends Recipe<Container> {
    @Override
    NonNullList<Ingredient> getIngredients();

    Ingredient getInput(int inputId);

    NonNullList<ItemStack> getReturns();

    ItemStack getReturn(int inputId);

    List<Integer> getCounts();

    int getInputCount(int inputId);

    List<Boolean> getConsumes();

    boolean isConsuming(int inputId);

    List<Boolean> getUseDurability();

    boolean isUsingDurability(int inputId);

    boolean isShapeless();

    int getExperience();

    ItemStack getResultItem();

    @Override
    default ItemStack getResultItem(RegistryAccess access) {
        return this.getResultItem();
    }
}
