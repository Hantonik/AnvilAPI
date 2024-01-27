package hantonik.anvilapi;

import hantonik.anvilapi.utils.AADisabledRecipes;
import hantonik.anvilapi.utils.AARecipeHelper;
import net.fabricmc.api.ClientModInitializer;

public class AnvilAPIClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AnvilAPI.LOGGER.info(AnvilAPI.SETUP_MARKER, "Initializing client...");

        AARecipeHelper.registerClient();
        AADisabledRecipes.registerClient();
    }
}