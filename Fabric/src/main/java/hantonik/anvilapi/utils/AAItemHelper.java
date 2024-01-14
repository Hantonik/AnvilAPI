package hantonik.anvilapi.utils;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AAItemHelper {
    public static JsonObject serialize(Item item) {
        var json = new JsonObject();

        json.addProperty("item", BuiltInRegistries.ITEM.getKey(item).toString());

        return json;
    }

    public static JsonObject serialize(ItemStack stack) {
        var json = new JsonObject();

        json.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());

        if (stack.getCount() > 1)
            json.addProperty("count", stack.getCount());

        if (stack.hasTag())
            json.addProperty("nbt", stack.getTag().toString());

        return json;
    }

    public static ItemStack withSize(ItemStack stack, int size, boolean container) {
        if (size <= 0) {
            var item = stack.getItem();

            if (container && item.hasCraftingRemainingItem())
                return new ItemStack(item.getCraftingRemainingItem());
            else
                return ItemStack.EMPTY;
        }

        stack = stack.copy();
        stack.setCount(size);

        return stack;
    }

    public static ItemStack grow(ItemStack stack, int amount) {
        return withSize(stack, stack.getCount() + amount, false);
    }

    public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
        return !stack1.isEmpty() && !stack2.isEmpty() && stack1.is(stack2.getItem());
    }

    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return areItemsEqual(stack1, stack2) && ItemStack.isSameItemSameTags(stack1, stack2);
    }

    public static boolean canCombineStacks(ItemStack stack1, ItemStack stack2) {
        if (!stack1.isEmpty() && stack2.isEmpty())
            return true;

        return areStacksEqual(stack1, stack2) && (stack1.getCount() + stack2.getCount()) <= stack1.getMaxStackSize();
    }

    public static ItemStack combineStacks(ItemStack stack1, ItemStack stack2) {
        if (stack1.isEmpty())
            return stack2.copy();

        return grow(stack1, stack2.getCount());
    }
}
