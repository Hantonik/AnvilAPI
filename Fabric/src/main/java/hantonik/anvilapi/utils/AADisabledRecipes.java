package hantonik.anvilapi.utils;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.init.AARecipeTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class AADisabledRecipes implements ServerLifecycleEvents.ServerStarted, ServerLifecycleEvents.EndDataPackReload {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().toAbsolutePath();

    private static final List<Pair<Ingredient, Ingredient>> REPAIR = Lists.newArrayList();
    private static final List<Ingredient> REPAIR_ITEMS = Lists.newArrayList();
    private static final List<Pair<Pair<Enchantment, Integer>, Ingredient>> ENCHANTMENTS = Lists.newArrayList();
    private static final List<Pair<Pair<Enchantment, Integer>, Pair<Enchantment, Integer>>> ENCHANTMENT_COMBINING = Lists.newArrayList();

    private static final List<Pair<Ingredient, Ingredient>> REPAIR_INTERNAL = Lists.newArrayList();
    private static final List<Ingredient> REPAIR_ITEMS_INTERNAL = Lists.newArrayList();
    private static final List<Pair<Pair<Enchantment, Integer>, Ingredient>> ENCHANTMENTS_INTERNAL = Lists.newArrayList();
    private static final List<Pair<Pair<Enchantment, Integer>, Pair<Enchantment, Integer>>> ENCHANTMENT_COMBINING_INTERNAL = Lists.newArrayList();

    public AADisabledRecipes() {
        try {
            Files.createDirectory(Paths.get(PATH.toString(), AnvilAPI.MOD_ID));
        } catch (FileAlreadyExistsException e) {
            AnvilAPI.LOGGER.debug("{} config directory already exists.", AnvilAPI.MOD_NAME);
        } catch (IOException e) {
            AnvilAPI.LOGGER.error("Failed to create {} config directory.", AnvilAPI.MOD_NAME);
        }
    }

    public static void disableRepairRecipe(Ingredient baseItem, Ingredient repairItem) {
        REPAIR_INTERNAL.add(Pair.of(baseItem, repairItem));
    }

    public static void disableRepairItem(Ingredient repairItem) {
        REPAIR_ITEMS_INTERNAL.add(repairItem);
    }

    public static void disableEnchantmentRecipe(Ingredient baseItem, Enchantment enchantment) {
        disableEnchantmentRecipe(baseItem, enchantment, -1);
    }

    public static void disableEnchantmentRecipe(Ingredient baseItem, Enchantment enchantment, int enchantmentLevel) {
        ENCHANTMENTS_INTERNAL.add(Pair.of(Pair.of(enchantment, enchantmentLevel), baseItem));
    }

    public static void disableEnchantmentCombiningRecipe(Enchantment enchantment1, Enchantment enchantment2) {
        disableEnchantmentCombiningRecipe(enchantment1, -1, enchantment2, -1);
    }

    public static void disableEnchantmentCombiningRecipe(Enchantment enchantment1, int enchantment1Level, Enchantment enchantment2, int enchantment2Level) {
        ENCHANTMENT_COMBINING_INTERNAL.add(Pair.of(Pair.of(enchantment1, enchantment1Level), Pair.of(enchantment2, enchantment2Level)));
    }

    public static boolean isRepairDisabled(ItemStack baseItem, @Nullable ItemStack repairItem) {
        return REPAIR.stream().anyMatch(entry -> entry.getFirst().test(baseItem) && (entry.getSecond().isEmpty() || entry.getSecond().test(repairItem)));
    }

    public static boolean isRepairItemDisabled(ItemStack repairItem) {
        return REPAIR_ITEMS.stream().anyMatch(item -> item.test(repairItem));
    }

    public static boolean isEnchantmentDisabled(@Nullable ItemStack baseItem, Enchantment enchantment, int enchantmentLevel) {
        return ENCHANTMENTS.stream().anyMatch(entry -> entry.getFirst().getFirst() == enchantment && (entry.getFirst().getSecond() == -1 || entry.getFirst().getSecond() == enchantmentLevel) && (entry.getSecond().isEmpty() || entry.getSecond().test(baseItem)));
    }

    public static boolean isEnchantmentCombiningDisabled(Enchantment enchantment1, int enchantment1Level, Enchantment enchantment2, int enchantment2Level) {
        return ENCHANTMENT_COMBINING.stream().anyMatch(entry -> (entry.getFirst().getFirst() == enchantment1 && (entry.getFirst().getSecond() == -1 || entry.getFirst().getSecond() == enchantment1Level) && entry.getSecond().getFirst() == enchantment2 && (entry.getSecond().getSecond() == -1 || entry.getSecond().getSecond() == enchantment2Level)) || (entry.getFirst().getFirst() == enchantment2 && (entry.getFirst().getSecond() == -1 || entry.getFirst().getSecond() == enchantment2Level) && entry.getSecond().getFirst() == enchantment1 && (entry.getSecond().getSecond() == -1 || entry.getSecond().getSecond() == enchantment1Level)));
    }

    public static boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        if (isRepairItemDisabled(repairCandidate) || isRepairDisabled(stack, repairCandidate))
            return false;

        var level = Minecraft.getInstance().level;

        if (level != null) {
            var container = new SimpleContainer(2);
            container.addItem(stack);
            container.addItem(repairCandidate);

            if (level.getRecipeManager().getRecipeFor(AARecipeTypes.ANVIL_REPAIR, container, level).isPresent())
                return true;
        }

        return stack.getItem().isValidRepairItem(stack, repairCandidate);
    }

    public void reload() {
        REPAIR.clear();
        REPAIR_ITEMS.clear();
        ENCHANTMENTS.clear();
        ENCHANTMENT_COMBINING.clear();

        REPAIR.addAll(REPAIR_INTERNAL);
        REPAIR_ITEMS.addAll(REPAIR_ITEMS_INTERNAL);
        ENCHANTMENTS.addAll(ENCHANTMENTS_INTERNAL);
        ENCHANTMENT_COMBINING.addAll(ENCHANTMENT_COMBINING_INTERNAL);

        var dir = Paths.get(PATH.toString(), AnvilAPI.MOD_ID);

        try {
            var file = new File(dir.toString(), "disabled_vanilla_recipes.json");

            if (!file.exists()) {
                file.createNewFile();

                var writer = new FileWriter(file);

                var template = new JsonObject();
                template.addProperty("enable", true);

                template.add("repair", new JsonArray());
                template.add("repairItems", new JsonArray());
                template.add("enchantments", new JsonArray());

                GSON.toJson(template, writer);
                writer.close();

                return;
            }

            var json = JsonParser.parseReader(new InputStreamReader(new FileInputStream(file))).getAsJsonObject();

            if (!GsonHelper.getAsBoolean(json, "enable", true))
                return;

            for (var repairJson : json.get("repair").getAsJsonArray()) {
                Ingredient baseItem;
                var repairItem = Ingredient.EMPTY;

                if (repairJson.isJsonObject()) {
                    var baseItemJson = repairJson.getAsJsonObject().get("baseItem");

                    if (baseItemJson.isJsonPrimitive())
                        baseItem = Ingredient.of(new ItemStack(GsonHelper.convertToItem(baseItemJson, "baseItem")));
                    else
                        baseItem = Util.getOrThrow(Ingredient.CODEC_NONEMPTY.parse(JsonOps.INSTANCE, baseItemJson), IllegalStateException::new);

                    if (repairJson.getAsJsonObject().has("repairItem")) {
                        var repairItemJson = repairJson.getAsJsonObject().get("repairItem");

                        if (repairItemJson.isJsonPrimitive())
                            repairItem = Ingredient.of(new ItemStack(GsonHelper.convertToItem(repairItemJson, "repairItem")));
                        else
                            repairItem = Util.getOrThrow(Ingredient.CODEC.parse(JsonOps.INSTANCE, repairJson.getAsJsonObject().get("repairItem")), IllegalStateException::new);
                    }
                } else
                    baseItem = Ingredient.of(new ItemStack(GsonHelper.convertToItem(repairJson, "baseItem")));

                for (var baseStack : baseItem.getItems()) {
                    if (repairItem == Ingredient.EMPTY)
                        AnvilAPI.LOGGER.debug("Disabling repair recipe for {} with any", BuiltInRegistries.ITEM.getKey(baseStack.getItem()));
                    else
                        for (var repairStack : repairItem.getItems())
                            AnvilAPI.LOGGER.debug("Disabling repair recipe for {} with {}", BuiltInRegistries.ITEM.getKey(baseStack.getItem()), BuiltInRegistries.ITEM.getKey(repairStack.getItem()));
                }

                REPAIR.add(Pair.of(baseItem, repairItem));
            }

            for (var repairItemJson : json.get("repairItems").getAsJsonArray()) {
                Ingredient repairItem;

                if (repairItemJson.isJsonPrimitive())
                    repairItem = Ingredient.of(new ItemStack(GsonHelper.convertToItem(repairItemJson, "repairItem")));
                else
                    repairItem = Util.getOrThrow(Ingredient.CODEC_NONEMPTY.parse(JsonOps.INSTANCE, repairItemJson), IllegalStateException::new);

                for (var repairStack : repairItem.getItems())
                    AnvilAPI.LOGGER.debug("Disabling repair recipes with {}", BuiltInRegistries.ITEM.getKey(repairStack.getItem()));

                REPAIR_ITEMS.add(repairItem);
            }

            for (var enchantmentJson : json.get("enchantments").getAsJsonArray()) {
                var baseItem = Ingredient.EMPTY;

                Enchantment enchantment1;
                var enchantment1Level = -1;

                Enchantment enchantment2 = null;
                var enchantment2Level = -1;

                if (enchantmentJson.isJsonObject()) {
                    if (enchantmentJson.getAsJsonObject().has("combining")) {
                        var combiningJson = enchantmentJson.getAsJsonObject().get("combining").getAsJsonArray();

                        enchantment1 = BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(GsonHelper.getAsString(combiningJson.get(0).getAsJsonObject(), "enchantment")));
                        enchantment1Level = GsonHelper.getAsInt(combiningJson.get(0).getAsJsonObject(), "level", -1);

                        if (combiningJson.size() >= 2) {
                            enchantment2 = BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(GsonHelper.getAsString(combiningJson.get(1).getAsJsonObject(), "enchantment")));
                            enchantment2Level = GsonHelper.getAsInt(combiningJson.get(1).getAsJsonObject(), "level", -1);
                        } else {
                            enchantment2 = enchantment1;
                            enchantment2Level = enchantment1Level;
                        }
                    } else {
                        if (enchantmentJson.getAsJsonObject().has("baseItem")) {
                            var baseItemJson = enchantmentJson.getAsJsonObject().get("baseItem");

                            if (baseItemJson.isJsonPrimitive())
                                baseItem = Ingredient.of(new ItemStack(GsonHelper.convertToItem(baseItemJson, "baseItem")));
                            else
                                baseItem = Util.getOrThrow(Ingredient.CODEC_NONEMPTY.parse(JsonOps.INSTANCE, baseItemJson), IllegalStateException::new);
                        }

                        enchantment1 = BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(GsonHelper.getAsString(enchantmentJson.getAsJsonObject(), "enchantment")));
                        enchantment1Level = GsonHelper.getAsInt(enchantmentJson.getAsJsonObject(), "level", -1);
                    }
                } else
                    enchantment1 = BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(enchantmentJson.getAsString()));

                if (enchantment2 == null) {
                    if (baseItem == Ingredient.EMPTY)
                        AnvilAPI.LOGGER.debug("Disabling enchantment recipe with {}", BuiltInRegistries.ENCHANTMENT.getKey(enchantment1));
                    else
                        for (var baseStack : baseItem.getItems())
                            AnvilAPI.LOGGER.debug("Disabling enchantment recipe for {} with {}", BuiltInRegistries.ITEM.getKey(baseStack.getItem()), BuiltInRegistries.ENCHANTMENT.getKey(enchantment1));

                    ENCHANTMENTS.add(Pair.of(Pair.of(enchantment1, enchantment1Level), baseItem));
                } else {
                    AnvilAPI.LOGGER.debug("Disabling enchantment combining recipe for {} with {}", BuiltInRegistries.ENCHANTMENT.getKey(enchantment1), BuiltInRegistries.ENCHANTMENT.getKey(enchantment2));

                    ENCHANTMENT_COMBINING.add(Pair.of(Pair.of(enchantment1, enchantment1Level), Pair.of(enchantment2, enchantment2Level)));
                }
            }
        } catch (IOException e) {
            AnvilAPI.LOGGER.error("Could no load disabled repair recipes.", e);
        }
    }

    @Override
    public void onServerStarted(MinecraftServer server) {
        this.reload();
    }

    @Override
    public void endDataPackReload(MinecraftServer server, CloseableResourceManager manager, boolean success) {
        if (success)
            this.reload();
    }
}
