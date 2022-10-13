package hantonik.anvilapi.api.recipe;

import hantonik.atomic.core.api.recipe.ISpecialRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface IAnvilRecipe extends ISpecialRecipe {
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
}
