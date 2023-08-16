package hantonik.anvilapi.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.crafting.RecipeManager;

@FunctionalInterface
public interface RecipeUpdatedCallback {
    Event<RecipeUpdatedCallback> EVENT = EventFactory.createArrayBacked(RecipeUpdatedCallback.class, listeners -> manager -> {
        for (var listener : listeners)
            listener.access(manager);
    });

    void access(RecipeManager manager);
}
