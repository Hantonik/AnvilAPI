package hantonik.anvilapi.mixins;

import hantonik.anvilapi.event.callback.RecipeUpdatedCallback;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener implements TickablePacketListener, ClientGamePacketListener {
    @Final
    @Shadow
    private RecipeManager recipeManager;

    @Inject(at = @At("RETURN"), method = "handleUpdateRecipes")
    public void handleUpdateRecipes(ClientboundUpdateRecipesPacket packet, CallbackInfo callback) {
        RecipeUpdatedCallback.EVENT.invoker().access(this.recipeManager);
    }
}
