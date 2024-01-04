package hantonik.anvilapi.recipe;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AAItemHelper;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
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
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AnvilRecipe implements IAnvilRecipe {
    private final List<ICondition> conditions = Lists.newArrayList();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    private final ResourceLocation id;
    private final ItemStack result;
    private final NonNullList<Ingredient> inputs;
    private final NonNullList<ItemStack> returns;
    private final List<CompoundTag> nbt;
    private final List<Boolean> strictNbt;
    private final List<Boolean> consumes;
    private final List<Boolean> useDurability;
    private final List<Integer> counts;
    private final int experience;

    private boolean shapeless;

    public AnvilRecipe(ResourceLocation id, ItemStack result, List<ItemStack> leftInput, List<ItemStack> rightInput, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput.stream()), Ingredient.of(rightInput.stream())), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.get(0).hasTag() ? leftInput.get(0).getTag() : new CompoundTag(), rightInput.get(0).hasTag() ? rightInput.get(0).getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.get(0).getCount(), rightInput.get(0).getCount()), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, ItemStack leftInput, ItemStack rightInput, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput), Ingredient.of(rightInput)), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.hasTag() ? leftInput.getTag() : new CompoundTag(), rightInput.hasTag() ? rightInput.getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.getCount(), rightInput.getCount()), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, Ingredient leftInput, int leftInputCount, List<ItemStack> rightInput, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, leftInput, Ingredient.of(rightInput.stream())), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), rightInput.get(0).hasTag() ? rightInput.get(0).getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInputCount, rightInput.get(0).getCount()), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, Ingredient leftInput, int leftInputCount, ItemStack rightInput, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, leftInput, Ingredient.of(rightInput)), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), rightInput.hasTag() ? rightInput.getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInputCount, rightInput.getCount()), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, List<ItemStack> leftInput, Ingredient rightInput, int rightInputCount, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput.stream()), rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.get(0).hasTag() ? leftInput.get(0).getTag() : new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.get(0).getCount(), rightInputCount), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, ItemStack leftInput, Ingredient rightInput, int rightInputCount, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput), rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.hasTag() ? leftInput.getTag() : new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.getCount(), rightInputCount), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, Ingredient leftInput, int leftInputCount, Ingredient rightInput, int rightInputCount, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, leftInput, rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInputCount, rightInputCount), false, experience);
    }

    public AnvilRecipe(ResourceLocation id, ItemStack result, Ingredient leftInput, Ingredient rightInput, int experience) {
        this(id, result, NonNullList.of(Ingredient.EMPTY, leftInput, rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(1, 1), false, experience);
    }

    protected AnvilRecipe(ResourceLocation id, ItemStack result, NonNullList<Ingredient> inputs, NonNullList<ItemStack> returns, List<CompoundTag> nbt, List<Boolean> strictNbt, List<Boolean> consumes, List<Boolean> useDurability, List<Integer> counts, boolean shapeless, int experience) {
        this.id = id;
        this.result = result;
        this.inputs = inputs;
        this.returns = returns;
        this.nbt = nbt;
        this.strictNbt = strictNbt;
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

    @CanIgnoreReturnValue
    public AnvilRecipe strictNbt(int inputId) {
        this.strictNbt.set(inputId, this.hasNbt(inputId));

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
    public List<CompoundTag> getAllNbt() {
        return this.nbt;
    }

    @Override
    public CompoundTag getInputNbt(int inputId) {
        return this.nbt.get(inputId);
    }

    @Override
    public boolean hasNbt(int inputId) {
        return !this.nbt.get(inputId).isEmpty();
    }

    @Override
    public List<Boolean> getStrictNbt() {
        return this.strictNbt;
    }

    @Override
    public boolean isNbtStrict(int inputId) {
        return this.hasNbt(inputId) && this.strictNbt.get(inputId);
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
    public ItemStack assemble(Container container, RegistryAccess access) {
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
                var ingredientNbt = this.nbt.get(ingredientId);

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

                    if (this.isNbtStrict(ingredientId)) {
                        if (!ingredientNbt.equals(input.hasTag() ? input.getTag() : new CompoundTag()))
                            continue;
                    } else
                        if (!NbtUtils.compareNbt(ingredientNbt, input.hasTag() ? input.getTag() : new CompoundTag(), true))
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
            return this.getInput(0).test(inputs.get(0)) && this.getInputCount(0) <= inputs.get(0).getCount() && (this.isNbtStrict(0) ? this.getInputNbt(0).equals(inputs.get(0).hasTag() ? inputs.get(0).getTag() : new CompoundTag()) : NbtUtils.compareNbt(this.getInputNbt(0), inputs.get(0).hasTag() ? inputs.get(0).getTag() : new CompoundTag(), true)) && (!this.isUsingDurability(0) || inputs.get(0).isDamageableItem()) && this.getInput(1).test(inputs.get(1)) && this.getInputCount(1) <= inputs.get(1).getCount() && (this.isNbtStrict(1) ? this.getInputNbt(1).equals(inputs.get(1).hasTag() ? inputs.get(1).getTag() : new CompoundTag()) : NbtUtils.compareNbt(this.getInputNbt(1), inputs.get(1).hasTag() ? inputs.get(1).getTag() : new CompoundTag(), true)) && (!this.isUsingDurability(1) || inputs.get(1).isDamageableItem());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<IAnvilRecipe> getType() {
        return AARecipeTypes.ANVIL.get();
    }

    @Override
    public RecipeSerializer<AnvilRecipe> getSerializer() {
        return AARecipeSerializers.ANVIL.get();
    }

    @CanIgnoreReturnValue
    public AnvilRecipe addCondition(ICondition condition) {
        this.conditions.add(condition);

        return this;
    }

    @CanIgnoreReturnValue
    public AnvilRecipe addCriterion(String name, CriterionTriggerInstance criterion) {
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

    public static class Serializer implements RecipeSerializer<AnvilRecipe> {
        @Override
        public AnvilRecipe fromJson(ResourceLocation id, JsonObject json) {
            var result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);

            NonNullList<Ingredient> ingredients = NonNullList.create();
            NonNullList<ItemStack> returnItems = NonNullList.create();
            NonNullList<CompoundTag> nbt = NonNullList.create();
            NonNullList<Boolean> strictNbt = NonNullList.create();
            NonNullList<Boolean> consume = NonNullList.create();
            NonNullList<Boolean> useDurability = NonNullList.create();
            NonNullList<Integer> count = NonNullList.create();

            for (var input : GsonHelper.getAsJsonArray(json, "inputs")) {
                ingredients.add(Ingredient.fromJson(input));
                returnItems.add(input.getAsJsonObject().has("return") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(input.getAsJsonObject(), "return"), true, false) : ItemStack.EMPTY);
                nbt.add(input.getAsJsonObject().has("nbt") ? CraftingHelper.getNBT(input.getAsJsonObject().get("nbt")) : new CompoundTag());
                strictNbt.add(GsonHelper.getAsBoolean(input.getAsJsonObject(), "strictNbt", false));
                consume.add(GsonHelper.getAsBoolean(input.getAsJsonObject(), "consume", true));
                useDurability.add(GsonHelper.getAsBoolean(input.getAsJsonObject(), "useDurability", false));
                count.add(GsonHelper.getAsInt(input.getAsJsonObject(), "count", 1));
            }

            var shapeless = GsonHelper.getAsBoolean(json, "shapeless", false);
            var experience = GsonHelper.getAsInt(json, "experience", 0);

            return new AnvilRecipe(id, result, ingredients, returnItems, nbt, strictNbt, consume, useDurability, count, shapeless, experience);
        }

        @Nullable
        @Override
        public AnvilRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            var result = buffer.readItem();

            var inputsSize = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (var inputId = 0; inputId < inputsSize; inputId++)
                inputs.add(Ingredient.fromNetwork(buffer));

            var returnsSize = buffer.readInt();
            NonNullList<ItemStack> returns = NonNullList.create();

            for (var returnId = 0; returnId < returnsSize; returnId++)
                returns.add(buffer.readItem());

            var nbtSize = buffer.readInt();
            List<CompoundTag> nbt = Lists.newArrayList();

            for (var nbtId = 0; nbtId < nbtSize; nbtId++)
                nbt.add(buffer.readNbt());

            var strictNbtSize = buffer.readInt();
            List<Boolean> strictNbt = Lists.newArrayList();

            for (var strictNbtId = 0; strictNbtId < strictNbtSize; strictNbtId++)
                strictNbt.add(buffer.readBoolean());

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

            return new AnvilRecipe(id, result, inputs, returns, nbt, strictNbt, consumes, useDurability, counts, shapeless, experience);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AnvilRecipe recipe) {
            buffer.writeItem(recipe.result);

            buffer.writeInt(recipe.inputs.size());

            for (var input : recipe.inputs)
                input.toNetwork(buffer);

            buffer.writeInt(recipe.returns.size());

            for (var ret : recipe.returns)
                buffer.writeItem(ret);

            buffer.writeInt(recipe.nbt.size());

            for (var nbt : recipe.nbt)
                buffer.writeNbt(nbt);

            buffer.writeInt(recipe.strictNbt.size());

            for (var strictNbt : recipe.strictNbt)
                buffer.writeBoolean(strictNbt);

            buffer.writeInt(recipe.consumes.size());

            for (var consume : recipe.consumes)
                buffer.writeBoolean(consume);

            buffer.writeInt(recipe.useDurability.size());

            for (var useDurability : recipe.useDurability)
                buffer.writeBoolean(useDurability);

            buffer.writeInt(recipe.counts.size());

            for (var count : recipe.counts)
                buffer.writeInt(count);

            buffer.writeBoolean(recipe.shapeless);
            buffer.writeInt(recipe.experience);
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

            json.add("result", AAItemHelper.serialize(result));

            var inputArray = new JsonArray();

            for (var inputId = 0; inputId < inputs.size(); inputId++) {
                var inputJson = inputs.get(inputId).toJson().getAsJsonObject();

                if (!returns.get(inputId).isEmpty())
                    inputJson.add("return", AAItemHelper.serialize(returns.get(inputId)));
                if (!nbt.get(inputId).isEmpty())
                    inputJson.addProperty("nbt", nbt.get(inputId).toString());
                if (strictNbt.get(inputId))
                    inputJson.addProperty("strictNbt", strictNbt.get(inputId));
                if (!consumes.get(inputId))
                    inputJson.addProperty("consume", consumes.get(inputId));
                if (useDurability.get(inputId))
                    inputJson.addProperty("useDurability", useDurability.get(inputId));
                if (counts.get(inputId) != 1)
                    inputJson.addProperty("count", counts.get(inputId));

                inputArray.add(inputJson);
            }

            json.add("inputs", inputArray);

            if (shapeless)
                json.addProperty("shapeless", true);

            if (experience != 0)
                json.addProperty("experience", experience);
        }

        @Override
        public RecipeSerializer<AnvilRecipe> getType() {
            return getSerializer();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return advancementBuilder.serializeToJson();
        }
    }
}
