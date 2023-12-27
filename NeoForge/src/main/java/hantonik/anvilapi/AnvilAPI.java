package hantonik.anvilapi;

import com.mojang.logging.LogUtils;
import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AADisabledRecipes;
import hantonik.anvilapi.utils.AARecipeHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

@Mod(AnvilAPI.MOD_ID)
public final class AnvilAPI {
    public static final String MOD_ID = "anvilapi";
    public static final String MOD_NAME = "AnvilAPI";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Marker SETUP_MARKER = MarkerFactory.getMarker("SETUP");

    public AnvilAPI(IEventBus bus) {
        LOGGER.info(SETUP_MARKER, "Initializing...");

        bus.register(this);

        AARecipeTypes.TYPES.register(bus);
        AARecipeSerializers.SERIALIZERS.register(bus);
    }

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info(SETUP_MARKER, "Starting common setup...");

        NeoForge.EVENT_BUS.register(new AARecipeHelper());
        NeoForge.EVENT_BUS.register(new AADisabledRecipes());

        LOGGER.info(SETUP_MARKER, "Finished common setup!");
    }
}
