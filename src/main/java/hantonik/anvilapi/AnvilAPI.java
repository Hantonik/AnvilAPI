package hantonik.anvilapi;

import hantonik.anvilapi.init.AARecipeSerializers;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.atomic.core.utils.references.AtomicReferences;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AnvilAPI.MOD_ID)
public final class AnvilAPI {
    public static final String MOD_ID = "anvilapi";
    public static final String MOD_NAME = "AnvilAPI";

    public static final Logger LOGGER = LogManager.getLogger(AnvilAPI.MOD_NAME);

    public AnvilAPI() {
        AnvilAPI.LOGGER.info(AtomicReferences.SETUP_MARKER, "Initializing...");

        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.register(this);

        AARecipeTypes.TYPES.register(bus);
        AARecipeSerializers.SERIALIZERS.register(bus);
    }

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
        AnvilAPI.LOGGER.info(AtomicReferences.SETUP_MARKER, "Starting common setup...");

        MinecraftForge.EVENT_BUS.register(this);

        AnvilAPI.LOGGER.info(AtomicReferences.SETUP_MARKER, "Finished common setup!");
    }
}
