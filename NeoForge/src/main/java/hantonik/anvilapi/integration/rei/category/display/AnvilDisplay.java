package hantonik.anvilapi.integration.rei.category.display;

import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.integration.rei.category.AnvilCategory;
import hantonik.anvilapi.utils.AAItemHelper;
import hantonik.anvilapi.utils.AARecipeHelper;
import lombok.RequiredArgsConstructor;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class AnvilDisplay extends BasicDisplay {
    public final RecipeHolder<IAnvilRecipe> recipe;

    public AnvilDisplay(RecipeHolder<IAnvilRecipe> recipe) {
        this(List.of(
                EntryIngredients.ofItemStacks(Arrays.stream(recipe.value().getInput(0).getItems()).map(stack -> AAItemHelper.withSize(stack, recipe.value().getInputCount(0), false)).peek(stack -> stack.setTag(recipe.value().getInputNbt(0))).toList()),
                EntryIngredients.ofItemStacks(Arrays.stream(recipe.value().getInput(1).getItems()).map(stack -> AAItemHelper.withSize(stack, recipe.value().getInputCount(1), false)).peek(stack -> stack.setTag(recipe.value().getInputNbt(1))).toList())
        ), Util.make(new ArrayList<>(), outputs -> {
            outputs.add(EntryIngredients.of(recipe.value().getResultItem(Minecraft.getInstance().level.registryAccess())));

            outputs.add(recipe.value().getReturn(0).isEmpty() ? EntryIngredient.empty() : EntryIngredients.of(recipe.value().getReturn(0)));
            outputs.add(recipe.value().getReturn(1).isEmpty() ? EntryIngredient.empty() : EntryIngredients.of(recipe.value().getReturn(1)));
        }), Optional.of(recipe.id()));
    }

    public AnvilDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location) {
        super(inputs, outputs, location);

        this.recipe = AARecipeHelper.getRecipeManager().byType(AARecipeTypes.ANVIL.get()).get(location.orElse(null));
    }

    @Override
    public CategoryIdentifier<? extends AnvilDisplay> getCategoryIdentifier() {
        return AnvilCategory.ID;
    }

    public static Serializer<AnvilDisplay> serializer() {
        return Serializer.ofSimple(AnvilDisplay::new);
    }

    @RequiredArgsConstructor
    public static class AnvilMenuInfo implements SimplePlayerInventoryMenuInfo<AnvilMenu, AnvilDisplay> {
        private final AnvilDisplay display;

        @Override
        public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<AnvilMenu, ?, AnvilDisplay> context) {
            return List.of(SlotAccessor.fromSlot(context.getMenu().getSlot(0)), SlotAccessor.fromSlot(context.getMenu().getSlot(1)));
        }

        @Override
        public AnvilDisplay getDisplay() {
            return this.display;
        }
    }
}
