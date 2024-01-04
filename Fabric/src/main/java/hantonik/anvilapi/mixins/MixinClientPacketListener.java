package hantonik.anvilapi.mixins;

import hantonik.anvilapi.event.callback.AARecipesUpdatedCallback;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener implements TickablePacketListener, ClientGamePacketListener {
    @Shadow
    public abstract RecipeManager getRecipeManager();

    @Inject(at = @At("RETURN"), method = "handleUpdateRecipes")
    public void handleUpdateRecipes(ClientboundUpdateRecipesPacket packet, CallbackInfo callback) {
        AARecipesUpdatedCallback.EVENT.invoker().onRecipesUpdated(this.getRecipeManager());
    }
}
