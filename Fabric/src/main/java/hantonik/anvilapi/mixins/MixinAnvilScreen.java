package hantonik.anvilapi.mixins;

import hantonik.anvilapi.init.AARecipeTypes;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public abstract class MixinAnvilScreen extends ItemCombinerScreen<AnvilMenu> {
    @Shadow
    private EditBox name;
    @Final
    @Shadow
    private Player player;

    public MixinAnvilScreen(AnvilMenu menu, Inventory inventory, Component component, ResourceLocation location) {
        super(menu, inventory, component, location);
    }

    @Inject(at = @At("HEAD"), method = "slotChanged", cancellable = true)
    public void slotChanged(AbstractContainerMenu menu, int slot, ItemStack stack, CallbackInfo callback) {
        callback.cancel();

        var level = this.player.level();
        var recipe = level.getRecipeManager().getRecipeFor(AARecipeTypes.ANVIL, new SimpleContainer(this.getMenu().getItems().toArray(ItemStack[]::new)), level).map(RecipeHolder::value).orElse(null);

        if (recipe != null) {
            if (slot == AnvilMenu.RESULT_SLOT) {
                if (!stack.isEmpty() && !this.name.getValue().isEmpty()) {
                    this.name.setValue(stack.isEmpty() ? "" : stack.getHoverName().getString());
                    this.name.setEditable(!stack.isEmpty());

                    this.setFocused(this.name);
                }
            } else if (slot == AnvilMenu.INPUT_SLOT || slot == AnvilMenu.ADDITIONAL_SLOT) {
                stack = recipe.getResultItem(level.registryAccess());

                this.name.setValue(stack.isEmpty() ? "" : stack.getHoverName().getString());
                this.name.setEditable(!stack.isEmpty());

                this.setFocused(this.name);
            }
        } else {
            if (slot == AnvilMenu.INPUT_SLOT || slot == AnvilMenu.ADDITIONAL_SLOT) {
                stack = menu.getSlot(AnvilMenu.INPUT_SLOT).getItem();

                this.name.setValue(stack.isEmpty() ? "" : stack.getHoverName().getString());
                this.name.setEditable(!stack.isEmpty());

                this.setFocused(this.name);
            }
        }
    }
}
