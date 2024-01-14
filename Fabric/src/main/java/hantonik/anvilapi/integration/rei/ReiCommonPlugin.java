package hantonik.anvilapi.integration.rei;

import hantonik.anvilapi.integration.rei.category.AnvilCategory;
import hantonik.anvilapi.integration.rei.category.display.AnvilDisplay;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import net.minecraft.world.inventory.AnvilMenu;

public final class ReiCommonPlugin implements REIServerPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(AnvilCategory.ID, AnvilDisplay.serializer());
    }

    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(AnvilCategory.ID, AnvilMenu.class, SimpleMenuInfoProvider.of(AnvilDisplay.AnvilMenuInfo::new));
    }
}
