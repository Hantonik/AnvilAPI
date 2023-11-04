package hantonik.anvilapi.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AARecipeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("mods." + AnvilAPI.MOD_ID + ".AnvilRepair")
@ZenRegister
public final class AnvilRepairRecipe {
    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack baseItem, IIngredient repairItem) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRepairRecipe(baseItem.getInternal().getItem(), repairItem.asVanillaIngredient())));
            }

            @Override
            public String describe() {
                return "Adding Anvil repair recipe for " + baseItem.getCommandString() + " with " + repairItem.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeRecipe(IItemStack baseItem, IIngredient repairItem) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL_REPAIR.get())
                        .values().stream()
                        .filter(r -> baseItem.getInternal().is(r.value().getBaseItem()))
                        .filter(r -> repairItem.asVanillaIngredient() == r.value().getRepairItem())
                        .map(RecipeHolder::id)
                        .forEach(r -> AARecipeHelper.getRecipes(AARecipeTypes.ANVIL_REPAIR.get()).remove(r));
            }

            @Override
            public String describe() {
                return "Removing Anvil repair recipe for " + baseItem.getCommandString() + " with " + repairItem.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeRecipe(IItemStack baseItem, IItemStack repairItem) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL_REPAIR.get())
                        .values().stream()
                        .filter(r -> baseItem.getInternal().is(r.value().getBaseItem()))
                        .filter(r -> r.value().getRepairItem().test(repairItem.getInternal()))
                        .map(RecipeHolder::id)
                        .forEach(r -> AARecipeHelper.getRecipes(AARecipeTypes.ANVIL_REPAIR.get()).remove(r));
            }

            @Override
            public String describe() {
                return "Removing Anvil repair recipe for " + baseItem.getCommandString() + " with " + repairItem.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeRecipe(String id) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.getRecipes().get(AARecipeTypes.ANVIL_REPAIR.get()).remove(new ResourceLocation(AnvilAPI.MOD_ID, id));
            }

            @Override
            public String describe() {
                return "Removing Anvil repair " + AnvilAPI.MOD_ID + ":" + id + " recipe...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeRecipe(String modId, String id) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.getRecipes().get(AARecipeTypes.ANVIL_REPAIR.get()).remove(new ResourceLocation(modId, id));
            }

            @Override
            public String describe() {
                return "Removing Anvil repair " + modId + ":" + id + " recipe...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }
}
