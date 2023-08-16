package hantonik.anvilapi;

import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AARecipeHelper;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AnvilAPI implements ModInitializer {
    public static final String MOD_ID = "anvilapi";
    public static final String MOD_NAME = "AnvilAPI";

    public static final Logger LOGGER = LogManager.getLogger(AnvilAPI.MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing...");

        AARecipeTypes.onInit();
        AARecipeSerializers.onInit();

        AARecipeHelper.init();
    }
}
