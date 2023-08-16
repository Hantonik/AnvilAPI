package hantonik.anvilapi.utils;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AACraftingHelper {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Nullable
    public static Item getItem(String id, boolean disallowAirInRecipe) {
        var key = new ResourceLocation(id);

        if (!BuiltInRegistries.ITEM.containsKey(key))
            throw new JsonSyntaxException("Unknown item '" + id + "'");

        var item = BuiltInRegistries.ITEM.get(key);

        if (disallowAirInRecipe && item == Items.AIR)
            throw new JsonSyntaxException("Invalid item: " + id);

        return item;
    }

    public static CompoundTag getNBT(JsonElement json) {
        try {
            if (json.isJsonObject())
                return TagParser.parseTag(GSON.toJson(json));
            else
                return TagParser.parseTag(GsonHelper.convertToString(json, "nbt"));
        }
        catch (CommandSyntaxException e) {
            throw new JsonSyntaxException("Invalid NBT Entry: " + e);
        }
    }

    @Nullable
    public static ItemStack getItemStack(JsonObject json, boolean readNbt) {
        return getItemStack(json, readNbt, false);
    }

    public static ItemStack getItemStack(JsonObject json, boolean readNbt, boolean disallowAirInRecipe) {
        var id = GsonHelper.getAsString(json, "item");
        var item = getItem(id, disallowAirInRecipe);

        if (readNbt && json.has("nbt")) {
            var nbt = getNBT(json.get("nbt"));
            var tmp = new CompoundTag();

            if (nbt.contains("ForgeCaps")) {
                tmp.put("ForgeCaps", nbt.get("ForgeCaps"));

                nbt.remove("ForgeCaps");
            }

            tmp.put("tag", nbt);
            tmp.putString("id", id);
            tmp.putInt("Count", GsonHelper.getAsInt(json, "count", 1));

            return ItemStack.of(tmp);
        }

        return new ItemStack(item, GsonHelper.getAsInt(json, "count", 1));
    }
}
