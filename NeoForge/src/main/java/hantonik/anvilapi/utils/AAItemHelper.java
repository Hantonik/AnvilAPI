package hantonik.anvilapi.utils;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AAItemHelper {
    private static final Codec<CompoundTag> NBT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.strictOptionalField(TagParser.AS_CODEC, "nbt").forGetter(Optional::ofNullable)
    ).apply(instance, nbt -> nbt.orElse(null)));

    public static final Codec<ItemStack> ITEMSTACK_WITH_NBT_CODEC = Codec.pair(ItemStack.ITEM_WITH_COUNT_CODEC, NBT_CODEC).xmap(codec -> {
        var stack = codec.getFirst().copy();
        stack.setTag(codec.getSecond());

        return stack;
    }, stack -> Pair.of(stack, stack.getTag()));

    public static JsonObject serialize(Item item) {
        return Util.getOrThrow(BuiltInRegistries.ITEM.byNameCodec().encodeStart(JsonOps.INSTANCE, item), IllegalStateException::new).getAsJsonObject();
    }

    public static JsonObject serialize(ItemStack stack) {
        return Util.getOrThrow(ITEMSTACK_WITH_NBT_CODEC.encodeStart(JsonOps.INSTANCE, stack), IllegalStateException::new).getAsJsonObject();
    }

    public static ItemStack withSize(ItemStack stack, int size, boolean container) {
        if (size <= 0) {
            if (container && stack.hasCraftingRemainingItem())
                return stack.getCraftingRemainingItem();
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
