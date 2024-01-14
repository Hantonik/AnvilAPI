package hantonik.anvilapi.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

public interface AARecipeManagerLoadedCallback {
    Event<AARecipeManagerLoadedCallback> EVENT = EventFactory.createArrayBacked(AARecipeManagerLoadedCallback.class, listeners -> manager -> {
        for (var listener : listeners)
            listener.onRecipeManagerLoaded(manager);
    });

    void onRecipeManagerLoaded(@NotNull RecipeManager manager);
}
