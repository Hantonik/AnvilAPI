package hantonik.anvilapi;

import com.mojang.logging.LogUtils;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AADisabledRecipes;
import hantonik.anvilapi.utils.AARecipeHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public final class AnvilAPI implements ModInitializer {
    public static final String MOD_ID = "anvilapi";
    public static final String MOD_NAME = "AnvilAPI";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Marker SETUP_MARKER = MarkerFactory.getMarker("SETUP");

    @Override
    public void onInitialize() {
        LOGGER.info(SETUP_MARKER, "Initializing...");

        AARecipeTypes.onInit();
        AARecipeSerializers.onInit();

        this.serverEvents();
    }

    private void serverEvents() {
        ServerLifecycleEvents.SERVER_STARTED.register(new AADisabledRecipes());
        ServerLifecycleEvents.SERVER_STARTED.register(new AARecipeHelper());

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(new AADisabledRecipes());
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(new AARecipeHelper());
    }
}
