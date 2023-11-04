package hantonik.anvilapi.recipe;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hantonik.anvilapi.api.recipe.IAnvilRepairRecipe;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AAItemHelper;
import lombok.Getter;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AnvilRepairRecipe implements IAnvilRepairRecipe {
    private final ResourceLocation serializerName;

    private final Map<String, Criterion<?>> criteria = Maps.newLinkedHashMap();

    private final Item baseItem;
    private final Ingredient repairItem;

    public AnvilRepairRecipe(Item baseItem, Ingredient repairItem) {
        this.serializerName = new ResourceLocation("anvil_repair");

        this.baseItem = baseItem;
        this.repairItem = repairItem;
    }

    @Override
    public Item getBaseItem() {
        return this.baseItem;
    }

    @Override
    public Ingredient getRepairItem() {
        return this.repairItem;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return new ItemStack(this.baseItem);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        return this.getResultItem(access).copy();
    }

    @Override
    public boolean matches(Container container, Level level) {
        return container.getItem(0).is(this.baseItem) && this.repairItem.test(container.getItem(1));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<IAnvilRepairRecipe> getType() {
        return AARecipeTypes.ANVIL_REPAIR.get();
    }

    @Override
    public RecipeSerializer<IAnvilRepairRecipe> getSerializer() {
        return AARecipeSerializers.ANVIL_REPAIR.get();
    }

    public AnvilRepairRecipe addCriterion(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);

        return this;
    }

    public void build(RecipeOutput output, ResourceLocation id) {
        if (this.criteria.isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + id);

        var advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        output.accept(new Result(id, advancementBuilder.build(id.withPrefix("recipes/"))));
    }

    public static class Serializer implements RecipeSerializer<IAnvilRepairRecipe> {
        @Override
        public Codec<IAnvilRepairRecipe> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    ForgeRegistries.ITEMS.getCodec().fieldOf("baseItem").forGetter(IAnvilRepairRecipe::getBaseItem),
                    ExtraCodecs.either(ForgeRegistries.ITEMS.getCodec(), Ingredient.CODEC_NONEMPTY).fieldOf("repairItem").forGetter(recipe -> Either.right(recipe.getRepairItem()))
            ).apply(instance, (baseItem, resultItem) -> new AnvilRepairRecipe(baseItem, resultItem.right().isPresent() ? resultItem.right().orElseThrow() : Ingredient.of(resultItem.orThrow()))));
        }

        @Nullable
        @Override
        public IAnvilRepairRecipe fromNetwork(FriendlyByteBuf buffer) {
            var baseItem = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
            var repairItem = Ingredient.fromNetwork(buffer);

            return new AnvilRepairRecipe(baseItem, repairItem);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, IAnvilRepairRecipe recipe) {
            buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(recipe.getBaseItem()));
            recipe.getRepairItem().toNetwork(buffer);
        }
    }

    @Getter
    private class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final AdvancementHolder advancement;

        Result(ResourceLocation id, AdvancementHolder advancement) {
            this.id = id;
            this.advancement = advancement;
        }

        @Override
        public ResourceLocation id() {
            return this.id;
        }

        @Nullable
        @Override
        public AdvancementHolder advancement() {
            return this.advancement;
        }

        @Override
        public RecipeSerializer<IAnvilRepairRecipe> type() {
            return AARecipeSerializers.ANVIL_REPAIR.get();
        }

        @Override
        public JsonObject serializeRecipe() {
            var json = new JsonObject();

            json.addProperty("type", serializerName.toString());

            this.serializeRecipeData(json);

            return json;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("baseItem", AAItemHelper.serialize(baseItem));
            json.add("repairItem", repairItem.toJson(false));
        }
    }
}
