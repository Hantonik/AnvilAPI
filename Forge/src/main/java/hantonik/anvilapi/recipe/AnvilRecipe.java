package hantonik.anvilapi.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AAItemHelper;
import net.minecraft.Util;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnvilRecipe implements IAnvilRecipe {
    private final List<ICondition> conditions = Lists.newArrayList();
    private final Map<String, Criterion<?>> criteria = Maps.newLinkedHashMap();

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

    public AnvilRecipe(ItemStack result, List<ItemStack> leftInput, List<ItemStack> rightInput, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput.stream()), Ingredient.of(rightInput.stream())), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.get(0).hasTag() ? leftInput.get(0).getTag() : new CompoundTag(), rightInput.get(0).hasTag() ? rightInput.get(0).getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.get(0).getCount(), rightInput.get(0).getCount()), false, experience);
    }

    public AnvilRecipe(ItemStack result, ItemStack leftInput, ItemStack rightInput, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput), Ingredient.of(rightInput)), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.hasTag() ? leftInput.getTag() : new CompoundTag(), rightInput.hasTag() ? rightInput.getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.getCount(), rightInput.getCount()), false, experience);
    }

    public AnvilRecipe(ItemStack result, Ingredient leftInput, int leftInputCount, List<ItemStack> rightInput, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, leftInput, Ingredient.of(rightInput.stream())), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), rightInput.get(0).hasTag() ? rightInput.get(0).getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInputCount, rightInput.get(0).getCount()), false, experience);
    }

    public AnvilRecipe(ItemStack result, Ingredient leftInput, int leftInputCount, ItemStack rightInput, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, leftInput, Ingredient.of(rightInput)), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), rightInput.hasTag() ? rightInput.getTag() : new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInputCount, rightInput.getCount()), false, experience);
    }

    public AnvilRecipe(ItemStack result, List<ItemStack> leftInput, Ingredient rightInput, int rightInputCount, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput.stream()), rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.get(0).hasTag() ? leftInput.get(0).getTag() : new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.get(0).getCount(), rightInputCount), false, experience);
    }

    public AnvilRecipe(ItemStack result, ItemStack leftInput, Ingredient rightInput, int rightInputCount, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, Ingredient.of(leftInput), rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(leftInput.hasTag() ? leftInput.getTag() : new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInput.getCount(), rightInputCount), false, experience);
    }

    public AnvilRecipe(ItemStack result, Ingredient leftInput, int leftInputCount, Ingredient rightInput, int rightInputCount, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, leftInput, rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(leftInputCount, rightInputCount), false, experience);
    }

    public AnvilRecipe(ItemStack result, Ingredient leftInput, Ingredient rightInput, int experience) {
        this(result, NonNullList.of(Ingredient.EMPTY, leftInput, rightInput), Util.make(NonNullList.create(), returns -> returns.addAll(List.of(ItemStack.EMPTY, ItemStack.EMPTY))), Util.make(new ArrayList<>(), nbt -> nbt.addAll(List.of(new CompoundTag(), new CompoundTag()))), Util.make(new ArrayList<>(), strictNbt -> strictNbt.addAll(List.of(false, false))), Util.make(new ArrayList<>(), consumes -> consumes.addAll(List.of(true, true))), Util.make(new ArrayList<>(), useDurability -> useDurability.addAll(List.of(false, false))), List.of(1, 1), false, experience);
    }

    protected AnvilRecipe(ItemStack result, NonNullList<Ingredient> inputs, NonNullList<ItemStack> returns, List<CompoundTag> nbt, List<Boolean> strictNbt, List<Boolean> consumes, List<Boolean> useDurability, List<Integer> counts, boolean shapeless, int experience) {
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
    public AnvilRecipe addCriterion(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);

        return this;
    }

    public void build(RecipeOutput output, ResourceLocation id) {
        if (this.criteria.isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + id);

        var advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        output.accept(id, this, advancementBuilder.build(id.withPrefix("recipes/")));
    }

    public static class Serializer implements RecipeSerializer<AnvilRecipe> {
        @Override
        public Codec<AnvilRecipe> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    AAItemHelper.ITEMSTACK_WITH_NBT_CODEC.fieldOf("result").forGetter(recipe -> recipe.getResultItem(null)),
                    CompleteInput.CODEC.listOf().fieldOf("inputs").forGetter(recipe -> {
                        var inputs = new ArrayList<CompleteInput>();

                        for (var i = 0; i < recipe.getIngredients().size(); i++)
                            inputs.add(new CompleteInput(recipe.getInput(i), recipe.getInputCount(i), recipe.getInputNbt(i), recipe.isNbtStrict(i), recipe.isConsuming(i), recipe.isUsingDurability(i), recipe.getReturn(i)));

                        return inputs;
                    }),
                    ExtraCodecs.strictOptionalField(Codec.BOOL, "shapeless", false).forGetter(AnvilRecipe::isShapeless),
                    ExtraCodecs.strictOptionalField(Codec.INT, "experience", 0).forGetter(AnvilRecipe::getExperience)
            ).apply(instance, (result, inputs, shapeless, experience) -> new AnvilRecipe(result, inputs.stream().map(CompleteInput::input).collect(Collectors.toCollection(NonNullList::create)), inputs.stream().map(CompleteInput::returnStack).collect(Collectors.toCollection(NonNullList::create)), inputs.stream().map(CompleteInput::nbt).toList(), inputs.stream().map(CompleteInput::strictNbt).toList(), inputs.stream().map(CompleteInput::consume).toList(), inputs.stream().map(CompleteInput::useDurability).toList(), inputs.stream().map(CompleteInput::count).toList(), shapeless, experience)));
        }

        @Nullable
        @Override
        public AnvilRecipe fromNetwork(FriendlyByteBuf buffer) {
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

            return new AnvilRecipe(result, inputs, returns, nbt, strictNbt, consumes, useDurability, counts, shapeless, experience);
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

        private record CompleteInput(Ingredient input, int count, CompoundTag nbt, boolean strictNbt, boolean consume, boolean useDurability, ItemStack returnStack) {
            public static final Codec<CompleteInput> CODEC = Codec.pair(Ingredient.CODEC_NONEMPTY, IncompleteInput.CODEC).xmap(
                    codec -> new CompleteInput(codec.getFirst(), codec.getSecond().count, codec.getSecond().nbt, codec.getSecond().strictNbt, codec.getSecond().consume, codec.getSecond().useDurability, codec.getSecond().returnStack),
                    complete -> Pair.of(complete.input, new IncompleteInput(complete.count, complete.nbt, complete.strictNbt, complete.consume, complete.useDurability, complete.returnStack))
            );

            private record IncompleteInput(int count, CompoundTag nbt, boolean strictNbt, boolean consume, boolean useDurability, ItemStack returnStack) {
                private static final Codec<IncompleteInput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        ExtraCodecs.strictOptionalField(Codec.INT, "count", 1).forGetter(IncompleteInput::count),
                        TagParser.AS_CODEC.optionalFieldOf("nbt", new CompoundTag()).forGetter(IncompleteInput::nbt),
                        ExtraCodecs.strictOptionalField(Codec.BOOL, "strictNbt", false).forGetter(IncompleteInput::strictNbt),
                        ExtraCodecs.strictOptionalField(Codec.BOOL, "consume", true).forGetter(IncompleteInput::consume),
                        ExtraCodecs.strictOptionalField(Codec.BOOL, "useDurability", false).forGetter(IncompleteInput::useDurability),
                        AAItemHelper.ITEMSTACK_WITH_NBT_CODEC.optionalFieldOf("return", ItemStack.EMPTY).forGetter(IncompleteInput::returnStack)
                ).apply(instance, IncompleteInput::new));
            }
        }
    }
}
