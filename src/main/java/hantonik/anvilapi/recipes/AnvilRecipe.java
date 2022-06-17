package hantonik.anvilapi.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hantonik.anvilapi.api.recipes.IAnvilRecipe;
import hantonik.anvilapi.init.RecipeSerializers;
import hantonik.anvilapi.init.Recipes;
import hantonik.atomiccore.crafting.recipe.RecipeBuilder;
import hantonik.atomiccore.utils.helpers.ItemHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class AnvilRecipe extends RecipeBuilder<AnvilRecipe> implements IAnvilRecipe {
    private final ResourceLocation id;
    private final Ingredient input1;
    private final Ingredient input2;
    private final int input1Amount;
    private final int input2Amount;
    private final ItemStack output;
    private final Component outputName;
    private final boolean isShapeless;
    private final int experience;

    public AnvilRecipe(ResourceLocation id, ItemStack output, Ingredient input1, int input1Amount, Ingredient input2, int input2Amount, int experience, boolean isShapeless) {
        this(id, output, input1, input1Amount, input2, input2Amount, experience, isShapeless, output.getHoverName());
    }

    public AnvilRecipe(ResourceLocation id, ItemStack output, Ingredient input1, int input1Amount, Ingredient input2, int input2Amount, int experience, boolean isShapeless, Component outputName) {
        super(new ResourceLocation("anvil"));

        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.input1Amount = input1Amount;
        this.input2Amount = input2Amount;
        this.output = output;
        this.isShapeless = isShapeless;
        this.outputName = outputName;
        this.experience = experience;
    }

    @Override
    public boolean matches(Container inventory, Level level) {
        return this.isShapeless() ? ((this.input1.test(inventory.getItem(0)) && inventory.getItem(0).getCount() >= this.input1Amount) && (this.input2.test(inventory.getItem(1)) && inventory.getItem(1).getCount() >= this.input2Amount)) || ((this.input1.test(inventory.getItem(1)) && inventory.getItem(1).getCount() >= this.input1Amount) && (this.input2.test(inventory.getItem(0))) && inventory.getItem(0).getCount() >= this.input2Amount) : (this.input1.test(inventory.getItem(0)) && inventory.getItem(0).getCount() >= this.input1Amount) && (this.input2.test(inventory.getItem(1)) && inventory.getItem(1).getCount() >= this.input2Amount);
    }

    @Override
    public Ingredient getInput1() {
        return this.input1;
    }

    @Override
    public Ingredient getInput2() {
        return this.input2;
    }

    @Override
    public int getInput1Amount() {
        return this.input1Amount;
    }

    @Override
    public int getInput2Amount() {
        return this.input2Amount;
    }

    @Override
    public Component getOutputName() {
        return this.outputName;
    }

    @Override
    public boolean isShapeless() {
        return this.isShapeless;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack assemble(IItemHandler inventory) {
        return this.output.copy();
    }

    @Override
    protected RecipeBuilder<AnvilRecipe>.RecipeResult getResult(ResourceLocation id) {
        return new AnvilRecipeResult(id);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.ANVIL.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.ANVIL.get();
    }

    public static class Serializer implements RecipeSerializer<AnvilRecipe> {
        @Override
        public AnvilRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ItemHelper.deserializeStack(GsonHelper.getAsJsonObject(json, "output"));

            JsonObject inputs = json.getAsJsonObject("inputs");

            JsonObject input1Json = inputs.getAsJsonObject("input1");
            JsonObject input2Json = inputs.getAsJsonObject("input2");

            Ingredient input1 = Ingredient.fromJson(GsonHelper.isArrayNode(input1Json, "items") ? GsonHelper.getAsJsonArray(input1Json, "items") : GsonHelper.getAsJsonObject(input1Json, "items"));
            int input1Amount = input1Json.has("amount") ? GsonHelper.getAsInt(input1Json, "amount") : 1;

            Ingredient input2 = Ingredient.fromJson(GsonHelper.isArrayNode(input2Json, "items") ? GsonHelper.getAsJsonArray(input2Json, "items") : GsonHelper.getAsJsonObject(input2Json, "items"));
            int input2Amount = input2Json.has("amount") ? GsonHelper.getAsInt(input2Json, "amount") : 1;

            int experience = GsonHelper.getAsInt(json, "experience");

            boolean isShapeless = json.has("shapeless") && GsonHelper.getAsBoolean(json, "shapeless");

            return new AnvilRecipe(id, output, input1, input1Amount, input2, input2Amount, experience, isShapeless);
        }

        @Override
        @Nullable
        public AnvilRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            ItemStack output = buffer.readItem();

            Ingredient input1 = Ingredient.fromNetwork(buffer);
            Ingredient input2 = Ingredient.fromNetwork(buffer);

            int input1Amount = buffer.readInt();
            int input2Amount = buffer.readInt();

            int experience = buffer.readInt();

            boolean isShapeless = buffer.readBoolean();

            return new AnvilRecipe(id, output, input1, input1Amount, input2, input2Amount, experience, isShapeless);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AnvilRecipe recipe) {
            buffer.writeItem(recipe.output);

            recipe.input1.toNetwork(buffer);
            recipe.input2.toNetwork(buffer);

            buffer.writeInt(recipe.input1Amount);
            buffer.writeInt(recipe.input2Amount);

            buffer.writeInt(recipe.experience);

            buffer.writeBoolean(recipe.isShapeless);
        }
    }

    public class AnvilRecipeResult extends RecipeResult {
        AnvilRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject inputs = new JsonObject();

            JsonObject input1Group = new JsonObject();
            JsonObject input2Group = new JsonObject();

            JsonElement input1Json = input1.toJson();
            JsonElement input2Json = input2.toJson();

            input1Group.add("items", input1Json);

            if (input1Amount != 1)
                input1Group.addProperty("amount", input1Amount);

            input2Group.add("items", input2Json);

            if (input2Amount != 1)
                input2Group.addProperty("amount", input2Amount);

            inputs.add("input1", input1Group);
            inputs.add("input2", input2Group);

            json.add("inputs", inputs);

            json.add("output", ItemHelper.serialize(output));

            if (isShapeless)
                json.addProperty("shapeless", isShapeless);

            json.addProperty("experience", experience);
        }
    }
}
