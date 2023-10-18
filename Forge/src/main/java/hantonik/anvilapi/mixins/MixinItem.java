package hantonik.anvilapi.mixins;

import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AADisabledRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem implements FeatureElement, ItemLike, IForgeItem {
    @Inject(at = @At("RETURN"), method = "isValidRepairItem", cancellable = true)
    public void isValdRepairItem(ItemStack stack, ItemStack repairCandidate, CallbackInfoReturnable<Boolean> callback) {
        if (AADisabledRecipes.isRepairItemDisabled(repairCandidate) || AADisabledRecipes.isRepairDisabled(stack, repairCandidate))
            callback.setReturnValue(false);

        var level = Minecraft.getInstance().level;

        if (level != null) {
            var container = new SimpleContainer(2);
            container.addItem(stack);
            container.addItem(repairCandidate);

            if (level.getRecipeManager().getRecipeFor(AARecipeTypes.ANVIL_REPAIR.get(), container, level).isPresent())
                callback.setReturnValue(true);
        }
    }
}
