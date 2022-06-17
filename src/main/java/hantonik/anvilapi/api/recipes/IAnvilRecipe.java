package hantonik.anvilapi.api.recipes;

import hantonik.atomiccore.crafting.ISpecialRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public interface IAnvilRecipe extends Recipe<Container>, ISpecialRecipe {
    Ingredient getInput1();
    Ingredient getInput2();

    int getInput1Amount();
    int getInput2Amount();

    Component getOutputName();

    boolean isShapeless();

    int getExperience();
}
