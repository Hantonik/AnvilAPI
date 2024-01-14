package hantonik.anvilapi.event.callback;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.crafting.RecipeManager;

@Environment(EnvType.CLIENT)
public interface AARecipesUpdatedCallback {
    Event<AARecipesUpdatedCallback> EVENT = EventFactory.createArrayBacked(AARecipesUpdatedCallback.class, listeners -> manager -> {
        for (var listener : listeners)
            listener.onRecipesUpdated(manager);
    });

    void onRecipesUpdated(RecipeManager manager);
}
