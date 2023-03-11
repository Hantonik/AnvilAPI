package hantonik.anvilapi.integration.category;

import com.mojang.blaze3d.vertex.PoseStack;
import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.atomic.core.utils.helpers.ItemHelper;
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

    public AnvilRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 152, 77);
        this.shapeless = helper.createDrawable(TEXTURE, 153, 0, 18, 16);
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
    public void draw(IAnvilRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        var font = Minecraft.getInstance().font;
        var player = Minecraft.getInstance().player;

        font.drawShadow(stack, Component.literal(recipe.getResultItem().getHoverName().getString()), 46, 17, 0xFFFFFFFF);

        if (recipe.isShapeless())
            this.shapeless.draw(stack, 135, 62);

        if (recipe.getExperience() > 0)
            font.drawShadow(stack, Component.translatable("container.repair.cost", recipe.getExperience() < 0 ? "err" : String.valueOf(recipe.getExperience())).getString(), 9, 66, (player == null || player.isCreative()) || (recipe.getExperience() < 40 && recipe.getExperience() <= player.experienceLevel) ? 0xFF80FF20 : 0xFFFF6060);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IAnvilRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 40).addItemStacks(Arrays.stream(recipe.getInput(0).getItems()).map(stack -> ItemHelper.withSize(stack, recipe.getInputCount(0), false)).toList());
        builder.addSlot(RecipeIngredientRole.INPUT, 59, 40).addItemStacks(Arrays.stream(recipe.getInput(1).getItems()).map(stack -> ItemHelper.withSize(stack, recipe.getInputCount(1), false)).toList());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 117, 40).addItemStack(recipe.getResultItem());
    }
}