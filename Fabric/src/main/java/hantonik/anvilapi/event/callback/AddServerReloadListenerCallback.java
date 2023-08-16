package hantonik.anvilapi.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;

@FunctionalInterface
public interface AddServerReloadListenerCallback {
    Event<AddServerReloadListenerCallback> EVENT = EventFactory.createArrayBacked(AddServerReloadListenerCallback.class, listeners -> (resources, access) -> {
        for (var listener : listeners)
            listener.access(resources, access);
    });

    void access(ReloadableServerResources resources, RegistryAccess access);
}
