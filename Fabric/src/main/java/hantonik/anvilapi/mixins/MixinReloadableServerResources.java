package hantonik.anvilapi.mixins;

import hantonik.anvilapi.event.callback.AddServerReloadListenerCallback;
import hantonik.anvilapi.utils.AADisabledRecipes;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerResources.class)
public abstract class MixinReloadableServerResources {
    @Inject(at = @At(value = "RETURN"), method = "listeners", cancellable = true)
    public void listeners(CallbackInfoReturnable<List<PreparableReloadListener>> callback) {
        var listeners = new ArrayList<>(callback.getReturnValue());

        listeners.add(new AADisabledRecipes());

        callback.setReturnValue(listeners);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.BEFORE), method = "loadResources", locals = LocalCapture.CAPTURE_FAILHARD)
    private static void loadResources(ResourceManager resourceManager, RegistryAccess.Frozen frozen, FeatureFlagSet featureFlagSet, Commands.CommandSelection commandSelection, int i, Executor executor, Executor executor2, CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> callback, ReloadableServerResources reloadableServerResources) {
        AddServerReloadListenerCallback.EVENT.invoker().access(reloadableServerResources, frozen);
    }
}
