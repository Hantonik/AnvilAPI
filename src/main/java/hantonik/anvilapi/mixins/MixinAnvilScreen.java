package hantonik.anvilapi.mixins;

import hantonik.anvilapi.init.Recipes;
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
        if (slot != 2) {
            this.name.setValue(menu.getSlot(0).hasItem() ? menu.getSlot(0).getItem().getHoverName().getString() : "");
            this.name.setEditable(menu.getSlot(0).hasItem());

            this.setFocused(this.name);
        }

        var recipe = this.player.level.getRecipeManager().getRecipeFor(Recipes.ANVIL.get(), new SimpleContainer(this.getMenu().getItems().toArray(ItemStack[]::new)), this.player.level).orElse(null);

        if (recipe != null) {
            this.name.setValue(menu.getSlot(2).hasItem() ? recipe.getOutputName().getString() : "");
            this.name.setEditable(menu.getSlot(2).hasItem());

            this.setFocused(this.name);
        }

        callback.cancel();
    }
}
