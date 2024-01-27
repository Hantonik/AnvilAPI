package hantonik.anvilapi.mixins;

import dev.architectury.extensions.ItemExtension;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AARecipeHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem implements FeatureElement, ItemLike, ItemExtension {
    @Inject(at = @At("HEAD"), method = "isValidRepairItem", cancellable = true)
    public void isValidRepairItem(ItemStack stack, ItemStack repairCandidate, CallbackInfoReturnable<Boolean> callback) {
        if (AARecipeHelper.getRecipeManager().getRecipeFor(AARecipeTypes.ANVIL_REPAIR.get(), new SimpleContainer(stack, repairCandidate), null).isPresent())
            callback.setReturnValue(true);
    }
}
