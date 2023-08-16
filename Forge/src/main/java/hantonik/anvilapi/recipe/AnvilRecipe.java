package hantonik.anvilapi.recipe;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.ItemHelper;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AnvilRecipe implements IAnvilRecipe {
    private final ResourceLocation serializerName;

    private final List<ICondition> conditions = Lists.newArrayList();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    private final ResourceLocation id;
    private final ItemStack result;
    private final NonNullList<Ingredient> inputs;
    private final NonNullList<ItemStack> returns;
    private final List<Boolean> consumes;
    private final List<Boolean> useDurability;
    private final List<Integer> counts;
    private final int experience;

    private boolean shapeless;

    public AnvilRecipe(ResourceLocation id, ItemStack result, Ingredient leftInput, int leftInputCount, Ingredient rightInput, int rightInputCount, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, leftInput, rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(false, false))), List.of(leftInputCount, rightInputCount), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, Ingredient leftInput, Ingredient rightInput, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, leftInput, rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(false, false))), List.of(1, 1), false, experience);
    }

    protected AnvilRecipe(ResourceLocation id, ItemStack result, NonNullList<Ingredient> inputs, NonNullList<ItemStack> returns, List<Boolean> consumes, List<Boolean> useDurability, List<Integer> counts, boolean shapeless, int experience) {
        this.serializerName = new ResourceLocation("anvil");

        this.id = id;
        this.result = result;
        this.inputs = inputs;
        this.returns = returns;
        this.consumes = consumes;
        this.useDurability = useDurability;
        this.counts = counts;
        this.shapeless = shapeless;
        this.experience = experience;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe shapeless() {
        this.shapeless = true;

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe shapeless(boolean shapeless) {
        this.shapeless = shapeless;

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe consume(int inputId, boolean consume) {
        this.consumes.set(inputId, this.useDurability.get(inputId) || consume);

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe setUseDurability(int inputId, boolean useDurability) {
        this.useDurability.set(inputId, useDurability);
        this.consumes.set(inputId, useDurability || this.consumes.get(inputId));

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe setUseDurability(int inputId) {
        this.useDurability.set(inputId, true);
        this.consumes.set(inputId, true);

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe setReturnItem(int inputId, ItemStack returnItem) {
        this.returns.set(inputId, returnItem);

        return this;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public Ingredient getInput(int inputId) {
        return this.inputs.get(inputId);
    }

    @Override
    public NonNullList<ItemStack> getReturns() {
        return this.returns;
    }

    @Override
    public ItemStack getReturn(int inputId) {
        return this.returns.get(inputId);
    }

    @Override
    public List<Integer> getCounts() {
        return this.counts;
    }

    @Override
    public int getInputCount(int inputId) {
        return this.counts.get(inputId);
    }

    @Override
    public boolean isConsuming(int inputId) {
        return this.consumes.get(inputId);
    }

    @Override
    public List<Boolean> getUseDurability() {
        return this.useDurability;
    }

    @Override
    public boolean isUsingDurability(int inputId) {
        return this.useDurability.get(inputId);
    }

    @Override
    public List<Boolean> getConsumes() {
        return this.consumes;
    }

    @Override
    public boolean isShapeless() {
        return this.shapeless;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess access) {
        return this.getResultItem(access).copy();
    }

    @Override
    public boolean matches(Container container, Level level) {
        NonNullList<ItemStack> inputs = NonNullList.create();

        inputs.add(container.getItem(0));
        inputs.add(container.getItem(1));

        if (this.shapeless) {
            List<Integer> checked = Lists.newArrayList();

            inputs.removeIf(stack -> stack == null || stack == ItemStack.EMPTY);

            if (inputs.size() != this.inputs.size())
                return false;

            for (var ingredientId = 0; ingredientId < this.inputs.size(); ingredientId++) {
                var flag = false;

                var ingredient = this.inputs.get(ingredientId);
                var ingredientCount = this.counts.get(ingredientId);

                for (var inputId = 0; inputId < inputs.size(); inputId++) {
                    var input = inputs.get(inputId);

                    if (this.useDurability.get(ingredientId)) {
                        if (!input.isDamageableItem())
                            continue;
                        else
                            if (input.getMaxDamage() - input.getDamageValue() + 1 < this.getInputCount(ingredientId))
                                continue;
                    }

                    if (checked.contains(inputId))
                        continue;

                    if (input.getCount() < ingredientCount)
                        continue;

                    if (ingredient.test(input)) {
                        flag = true;

                        checked.add(inputId);

                        break;
                    }
                }

                if (!flag)
                    return false;
            }

            return true;
        } else
            return this.getInput(0).test(inputs.get(0)) && this.getInputCount(0) <= inputs.get(0).getCount() && (!this.isUsingDurability(0) || inputs.get(0).isDamageableItem()) && this.getInput(1).test(inputs.get(1)) && this.getInputCount(1) <= inputs.get(1).getCount() && (!this.isUsingDurability(1) || inputs.get(1).isDamageableItem());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return AARecipeTypes.ANVIL.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AARecipeSerializers.ANVIL.get();
    }

    public AnvilRecipe addCriterion(String name, CriterionTriggerInstance criterion) {
        this.advancementBuilder.addCriterion(name, criterion);

        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        if (this.advancementBuilder.getCriteria().isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + id);

        this.advancementBuilder.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", new RecipeUnlockedTrigger.TriggerInstance(ContextAwarePredicate.ANY, id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumer.accept(new Result(id));
    }

    public static class Serializer implements RecipeSerializer<IAnvilRecipe> {
        @Override
        public IAnvilRecipe fromJson(ResourceLocation id, JsonObject json) {
            var result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, false);

            NonNullList<Ingredient> inputs = NonNullList.create();
            NonNullList<ItemStack> returns = NonNullList.create();
            List<Boolean> consumes = Lists.newArrayList();
            List<Boolean> useDurability = Lists.newArrayList();
            List<Integer> counts = Lists.newArrayList();

            for (var inputElement : GsonHelper.getAsJsonArray(json, "inputs")) {
                inputs.add(Ingredient.fromJson(inputElement));

                returns.add(inputElement.getAsJsonObject().has("return") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(inputElement.getAsJsonObject(), "return"), true, false) : ItemStack.EMPTY);
                counts.add(GsonHelper.getAsInt(inputElement.getAsJsonObject(), "count", 1));
                consumes.add(GsonHelper.getAsBoolean(inputElement.getAsJsonObject(), "consume", true));
                useDurability.add(GsonHelper.getAsBoolean(inputElement.getAsJsonObject(), "useDurability", false));
            }

            var shapeless = false;

            if (json.has("shapeless"))
                shapeless = GsonHelper.getAsBoolean(json, "shapeless");

            var experience = GsonHelper.getAsInt(json, "experience", 0);

            return new AnvilRecipe(id, result, inputs, returns, consumes, useDurability, counts, shapeless, experience);
        }

        @Nullable
        @Override
        public IAnvilRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            var result = buffer.readItem();

            var inputsSize = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (var inputId = 0; inputId < inputsSize; inputId++)
                inputs.add(Ingredient.fromNetwork(buffer));

            var returnsSize = buffer.readInt();
            NonNullList<ItemStack> returns = NonNullList.create();

            for (var returnId = 0; returnId < returnsSize; returnId++)
                returns.add(buffer.readItem());

            var consumesSize = buffer.readInt();
            List<Boolean> consumes = Lists.newArrayList();

            for (var consumeId = 0; consumeId < consumesSize; consumeId++)
                consumes.add(buffer.readBoolean());

            var useDurabilitySize = buffer.readInt();
            List<Boolean> useDurability = Lists.newArrayList();

            for (var useDurabilityId = 0; useDurabilityId < useDurabilitySize; useDurabilityId++)
                useDurability.add(buffer.readBoolean());

            var countsSize = buffer.readInt();
            List<Integer> counts = Lists.newArrayList();

            for (var countId = 0; countId < countsSize; countId++)
                counts.add(buffer.readInt());

            var shapeless = buffer.readBoolean();
            var experience = buffer.readInt();

            return new AnvilRecipe(id, result, inputs, returns, consumes, useDurability, counts, shapeless, experience);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, IAnvilRecipe recipe) {
            buffer.writeItem(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));

            buffer.writeInt(recipe.getIngredients().size());

            for (var input : recipe.getIngredients())
                input.toNetwork(buffer);

            buffer.writeInt(recipe.getReturns().size());

            for (var ret : recipe.getReturns())
                buffer.writeItem(ret);

            buffer.writeInt(recipe.getCounts().size());

            for (var consume : recipe.getConsumes())
                buffer.writeBoolean(consume);

            buffer.writeInt(recipe.getUseDurability().size());

            for (var useDurability : recipe.getUseDurability())
                buffer.writeBoolean(useDurability);

            for (var count : recipe.getCounts())
                buffer.writeInt(count);

            buffer.writeInt(recipe.getConsumes().size());

            buffer.writeBoolean(recipe.isShapeless());
            buffer.writeInt(recipe.getExperience());
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
        public JsonObject serializeRecipe() {
            var jsonObject = new JsonObject();

            jsonObject.addProperty("type", serializerName.toString());

            if (!conditions.isEmpty()) {
                var conditionsArray = new JsonArray();

                for (var condition : conditions)
                    conditionsArray.add(CraftingHelper.serialize(condition));

                jsonObject.add("conditions", conditionsArray);
            }

            this.serializeRecipeData(jsonObject);

            return jsonObject;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            var inputsJson = new JsonArray();

            for (var inputId = 0; inputId < inputs.size(); inputId++) {
                var inputJson = inputs.get(inputId).toJson().getAsJsonObject();

                if (counts.get(inputId) != 1)
                    inputJson.addProperty("count", counts.get(inputId));
                if (!consumes.get(inputId))
                    inputJson.addProperty("consume", consumes.get(inputId));
                if (useDurability.get(inputId))
                    inputJson.addProperty("useDurability", useDurability.get(inputId));
                if (!returns.get(inputId).isEmpty())
                    inputJson.add("return", ItemHelper.serialize(returns.get(inputId)));

                inputsJson.add(inputJson);
            }

            json.add("inputs", inputsJson);
            json.add("result", ItemHelper.serialize(result));

            if (shapeless)
                json.addProperty("shapeless", true);

            if (experience != 0)
                json.addProperty("experience", experience);
        }

        @Nonnull
        @Override
        public RecipeSerializer<?> getType() {
            return ForgeRegistries.RECIPE_SERIALIZERS.getValue(serializerName);
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancementBuilder.serializeToJson();
        }
    }
}