package hantonik.anvilapi.recipe;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.anvilapi.api.recipe.IAnvilRepairRecipe;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AAItemHelper;
import lombok.Getter;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class AnvilRepairRecipe implements IAnvilRepairRecipe {
    private final List<ICondition> conditions = Lists.newArrayList();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    private final ResourceLocation id;
    private final Item baseItem;
    private final Ingredient repairItem;

    public AnvilRepairRecipe(ResourceLocation id, Item baseItem, Ingredient repairItem) {
        this.id = id;
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
    public ResourceLocation getId() {
        return this.id;
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
    public RecipeSerializer<AnvilRepairRecipe> getSerializer() {
        return AARecipeSerializers.ANVIL_REPAIR.get();
    }

    @CanIgnoreReturnValue
    public AnvilRepairRecipe addCondition(ICondition condition) {
        this.conditions.add(condition);

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRepairRecipe addCriterion(String name, CriterionTriggerInstance criterion) {
        this.advancementBuilder.addCriterion(name, criterion);

        return this;
    }

    public void build(Consumer<FinishedRecipe> output, ResourceLocation id) {
        if (this.advancementBuilder.getCriteria().isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + id);

        this.advancementBuilder.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", new RecipeUnlockedTrigger.TriggerInstance(EntityPredicate.Composite.ANY, id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        output.accept(new Result(id));
    }

    public static class Serializer implements RecipeSerializer<AnvilRepairRecipe> {
        @Override
        public AnvilRepairRecipe fromJson(ResourceLocation id, JsonObject json) {
            var baseItem = GsonHelper.getAsItem(json, "baseItem");

            var repairItemJson = json.get("repairItem");
            var repairItem = repairItemJson.isJsonPrimitive() ? Ingredient.of(GsonHelper.convertToItem(repairItemJson, "repairItem")) : Ingredient.fromJson(repairItemJson);

            return new AnvilRepairRecipe(id, baseItem, repairItem);
        }

        @Nullable
        @Override
        public AnvilRepairRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            var baseItem = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
            var repairItem = Ingredient.fromNetwork(buffer);

            return new AnvilRepairRecipe(id, baseItem, repairItem);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AnvilRepairRecipe recipe) {
            buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(recipe.getBaseItem()));
            recipe.getRepairItem().toNetwork(buffer);
        }
    }

    @Getter
    private class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ResourceLocation advancementId;

        Result(ResourceLocation id) {
            this.id = id;
            this.advancementId = new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath());
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            if (!conditions.isEmpty()) {
                var conditionsArray = new JsonArray();

                for (var condition : conditions)
                    conditionsArray.add(CraftingHelper.serialize(condition));

                json.add("conditions", conditionsArray);
            }

            json.add("baseItem", AAItemHelper.serialize(baseItem));
            json.add("repairItem", repairItem.toJson());
        }

        @Override
        public RecipeSerializer<AnvilRepairRecipe> getType() {
            return getSerializer();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancementBuilder.serializeToJson();
        }
    }
}
