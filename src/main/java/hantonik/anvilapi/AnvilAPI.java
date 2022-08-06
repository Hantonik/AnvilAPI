package hantonik.anvilapi;

import hantonik.anvilapi.init.RecipeSerializers;
import hantonik.anvilapi.init.Recipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AnvilAPI.MOD_ID)
public final class AnvilAPI {
    public static final String MOD_ID = "anvilapi";

    public AnvilAPI() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.register(this);

        Recipes.RECIPES.register(bus);
        RecipeSerializers.SERIALIZERS.register(bus);
    }

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
