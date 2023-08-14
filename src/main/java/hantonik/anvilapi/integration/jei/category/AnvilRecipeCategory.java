package hantonik.anvilapi.integration.jei.category;

import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.utils.ItemHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public final class AnvilRecipeCategory implements IRecipeCategory<IAnvilRecipe> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AnvilAPI.MOD_ID, "textures/gui/jei/anvil.png");
    public static final RecipeType<IAnvilRecipe> RECIPE_TYPE = RecipeType.create(AnvilAPI.MOD_ID, "anvil", IAnvilRecipe.class);

    private final IDrawable background;
    private final IDrawable shapeless;
    private final IDrawable icon;
    private final IDrawable returnSlot;

    public AnvilRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 152, 113);
        this.shapeless = helper.createDrawable(TEXTURE, 153, 0, 18, 16);

        this.returnSlot = helper.createDrawable(TEXTURE, 0, 115, 18, 32);

        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.ANVIL));
    }

    @Override
    public RecipeType<IAnvilRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.minecraft.anvil");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(IAnvilRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        var font = Minecraft.getInstance().font;
        var player = Minecraft.getInstance().player;

        graphics.drawString(font, Component.literal(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()).getHoverName().getString()), 46, 17, 0xFFFFFFFF);

        if (recipe.isShapeless())
            this.shapeless.draw(graphics, 135, 62);

        if (!recipe.getReturns().stream().allMatch(ItemStack::isEmpty)) {
            if (!recipe.getReturn(0).isEmpty())
                this.returnSlot.draw(graphics, 9, 60);

            if (!recipe.getReturn(1).isEmpty())
                this.returnSlot.draw(graphics, 58, 60);
        }

        if (recipe.getExperience() > 0)
            graphics.drawString(font, Component.translatable("container.repair.cost", recipe.getExperience() < 0 ? "err" : String.valueOf(recipe.getExperience())).getString(), 9, (!recipe.getReturns().stream().allMatch(ItemStack::isEmpty) ? 95 : 66), (player == null || player.isCreative()) || (recipe.getExperience() < 40 && recipe.getExperience() <= player.experienceLevel) ? 0xFF80FF20 : 0xFFFF6060);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IAnvilRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 40).addItemStacks(Arrays.stream(recipe.getInput(0).getItems()).map(stack -> ItemHelper.withSize(stack, recipe.getInputCount(0), false)).toList());
        builder.addSlot(RecipeIngredientRole.INPUT, 59, 40).addItemStacks(Arrays.stream(recipe.getInput(1).getItems()).map(stack -> ItemHelper.withSize(stack, recipe.getInputCount(1), false)).toList());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 117, 40).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));

        if (!recipe.getReturns().stream().allMatch(ItemStack::isEmpty)) {
            if (!recipe.getReturn(0).isEmpty())
                builder.addSlot(RecipeIngredientRole.OUTPUT, 10, 75).addItemStack(recipe.getReturn(0));

            if (!recipe.getReturn(1).isEmpty())
                builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 75).addItemStack(recipe.getReturn(1));
        }
    }
}
