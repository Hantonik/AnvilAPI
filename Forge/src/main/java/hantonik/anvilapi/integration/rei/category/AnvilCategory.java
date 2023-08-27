package hantonik.anvilapi.integration.rei.category;

import com.google.common.collect.Lists;
import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.integration.rei.category.display.AnvilDisplay;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public final class AnvilCategory implements DisplayCategory<AnvilDisplay> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AnvilAPI.MOD_ID, "textures/gui/jei/anvil.png");
    public static final CategoryIdentifier<AnvilDisplay> ID = CategoryIdentifier.of(new ResourceLocation(AnvilAPI.MOD_ID, "anvil"));

    @Override
    public CategoryIdentifier<? extends AnvilDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.minecraft.anvil");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Blocks.ANVIL);
    }

    @Override
    public int getDisplayWidth(AnvilDisplay display) {
        return 162;
    }

    @Override
    public int getDisplayHeight() {
        return 89;
    }

    @Override
    public List<Widget> setupDisplay(AnvilDisplay display, Rectangle bounds) {
        List<Widget> widgets = Lists.newArrayList();
        var recipe = display.recipe;

        var startPoint = new Point(bounds.getMinX() + 5, bounds.getMinY() + 5);

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createDrawableWidget(((graphics, mouseX, mouseY, delta) -> {
            var font = Minecraft.getInstance().font;
            var player = Minecraft.getInstance().player;

            graphics.blit(TEXTURE, startPoint.x, startPoint.y, 0, 0, 152, 79);

            if (recipe.isShapeless()) {
                graphics.blit(TEXTURE, startPoint.x + 135, startPoint.y + 64, 153, 0, 18, 16);

                if (mouseX > startPoint.x + 135 && mouseX < startPoint.x + 152 && mouseY > startPoint.y + 64 && mouseY < startPoint.y + 79)
                    graphics.renderTooltip(font, Component.translatable("text.rei.shapeless"), mouseX, mouseY);
            }

            graphics.drawString(font, Component.literal(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()).getHoverName().getString()), startPoint.x + 46, startPoint.y + 17, 0xFFFFFFFF);

            if (recipe.getExperience() > 0)
                graphics.drawString(font, Component.translatable("container.repair.cost", recipe.getExperience() < 0 ? "err" : String.valueOf(recipe.getExperience())).getString(), startPoint.x + 9, startPoint.y + 65, (player == null || player.isCreative()) || (recipe.getExperience() < 40 && recipe.getExperience() <= player.experienceLevel) ? 0xFF80FF20 : 0xFFFF6060);

            if (!recipe.getReturns().stream().allMatch(ItemStack::isEmpty)) {
                graphics.pose().pushPose();
                graphics.pose().translate(0, 0, 400);

                if (!recipe.getReturn(0).isEmpty()) {
                    if (mouseX > startPoint.x + 9 && mouseX < startPoint.x + 27 && mouseY > startPoint.y + 39 && mouseY < startPoint.y + 57) {
                        var item = recipe.getReturn(0);

                        graphics.renderItem(item, mouseX + 12, mouseY + 30);
                        graphics.renderItemDecorations(font, item, mouseX + 12, mouseY + 30);
                    }
                }

                if (!recipe.getReturn(1).isEmpty()) {
                    if (mouseX > startPoint.x + 58 && mouseX < startPoint.x + 76 && mouseY > startPoint.y + 39 && mouseY < startPoint.y + 57) {
                        var item = recipe.getReturn(1);

                        graphics.renderItem(item, mouseX + 12, mouseY + 30);
                        graphics.renderItemDecorations(font, item, mouseX + 12, mouseY + 30);
                    }
                }

                graphics.pose().popPose();
            }
        })));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 10, startPoint.y + 40)).entries(display.getInputEntries().get(0).stream().map(stack -> {
            List<Component> components = Lists.newArrayList(Component.translatable("tooltip.anvilapi.consumes").append(": ").withStyle(ChatFormatting.GRAY).append(recipe.isConsuming(0) ? Component.translatable("tooltip.anvilapi.yes").withStyle(ChatFormatting.RED) : Component.translatable("tooltip.anvilapi.no").withStyle(ChatFormatting.GREEN)));

            if (recipe.isUsingDurability(0))
                components.add(Component.translatable("tooltip.anvilapi.uses_durability").append(": ").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip.anvilapi.yes").withStyle(ChatFormatting.RED)));

            if (recipe.hasNbt(0))
                components.add(Component.translatable("tooltip.anvilapi.strict_nbt").append(": ").withStyle(ChatFormatting.GRAY).append(recipe.isNbtStrict(0) ? Component.translatable("tooltip.anvilapi.yes").withStyle(ChatFormatting.RED) : Component.translatable("tooltip.anvilapi.no").withStyle(ChatFormatting.GREEN)));

            if (!recipe.getReturn(0).isEmpty()) {
                components.add(Component.translatable("tooltip.anvilapi.returns").append(": ").withStyle(ChatFormatting.GRAY));
                components.add(Component.empty());
                components.add(Component.empty());
            }

            return stack.tooltip(components);
        }).toList()).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 59, startPoint.y + 40)).entries(display.getInputEntries().get(1).stream().map(stack -> {
            List<Component> components = Lists.newArrayList(Component.translatable("tooltip.anvilapi.consumes").append(": ").withStyle(ChatFormatting.GRAY).append(recipe.isConsuming(1) ? Component.translatable("tooltip.anvilapi.yes").withStyle(ChatFormatting.RED) : Component.translatable("tooltip.anvilapi.no").withStyle(ChatFormatting.GREEN)));

            if (recipe.isUsingDurability(1))
                components.add(Component.translatable("tooltip.anvilapi.uses_durability").append(": ").withStyle(ChatFormatting.GRAY).append(Component.translatable("tooltip.anvilapi.yes").withStyle(ChatFormatting.RED)));

            if (recipe.hasNbt(1))
                components.add(Component.translatable("tooltip.anvilapi.strict_nbt").append(": ").withStyle(ChatFormatting.GRAY).append(recipe.isNbtStrict(1) ? Component.translatable("tooltip.anvilapi.yes").withStyle(ChatFormatting.RED) : Component.translatable("tooltip.anvilapi.no").withStyle(ChatFormatting.GREEN)));

            if (!recipe.getReturn(1).isEmpty()) {
                components.add(Component.translatable("tooltip.anvilapi.returns").append(": ").withStyle(ChatFormatting.GRAY));
                components.add(Component.empty());
                components.add(Component.empty());
            }

            return stack.tooltip(components);
        }).toList()).markInput());

        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 117, startPoint.y + 40)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 117, startPoint.y + 40)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());

        if (!recipe.getReturn(0).isEmpty())
            widgets.add(Widgets.createSlot(new Rectangle(0, 0, -1, -1)).entries(display.getOutputEntries().get(1)).markOutput());

        if (!recipe.getReturn(1).isEmpty())
            widgets.add(Widgets.createSlot(new Rectangle(0, 0, -1, -1)).entries(display.getOutputEntries().get(2)).markOutput());

        return widgets;
    }
}
