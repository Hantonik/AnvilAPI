package hantonik.anvilapi.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.google.common.collect.Maps;
import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.api.recipe.IAnvilRecipe;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.RecipeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name("mods." + AnvilAPI.MOD_ID + ".Anvil")
@ZenRegister
public final class AnvilRecipe {
    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IIngredient rightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IIngredient rightInput, int rightInputCount, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IIngredient rightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IIngredient rightInput, int rightInputCount, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, boolean consumeLeftInput, IIngredient rightInput, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, boolean consumeLeftInput, IIngredient rightInput, int rightInputCount, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, boolean consumeLeftInput, IIngredient rightInput, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, boolean consumeLeftInput, IIngredient rightInput, int rightInputCount, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, int rightInputCount, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, int rightInputCount, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, IIngredient rightInput, IItemStack rightReturnItem, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, int leftInputCount, IIngredient rightInput, IItemStack rightReturnItem, int rightInputCount, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, IIngredient rightInput, IItemStack rightReturnItem, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, int leftInputCount, IIngredient rightInput, IItemStack rightReturnItem, int rightInputCount, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, IIngredient rightInput, IItemStack rightReturnItem, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, int leftInputCount, boolean consumeLeftInput, IIngredient rightInput, IItemStack rightReturnItem, int rightInputCount, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, IIngredient rightInput, IItemStack rightReturnItem, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, int leftInputCount, boolean consumeLeftInput, IIngredient rightInput, IItemStack rightReturnItem, int rightInputCount, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, IItemStack rightReturnItem, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, int leftInputCount, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, IItemStack rightReturnItem, int rightInputCount, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, IItemStack rightReturnItem, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, IItemStack leftReturnItem, int leftInputCount, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, IItemStack rightReturnItem, int rightInputCount, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new hantonik.anvilapi.recipe.AnvilRecipe(new ResourceLocation(AnvilAPI.MOD_ID, id), result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless));
            }

            @Override
            public String describe() {
                return "Adding Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeAllRecipes(IItemStack result) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.getRecipes()
                        .getOrDefault(AARecipeTypes.ANVIL.get(), Maps.newHashMap())
                        .values().stream()
                        .filter(r -> r.getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .map(Recipe::getId)
                        .forEach(r -> RecipeHelper.getRecipes().get(AARecipeTypes.ANVIL.get()).remove(r));
            }

            @Override
            public String describe() {
                return "Removing all Anvil recipes for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeRecipe(IItemStack result, IIngredient leftInput, IIngredient rightInput) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.getRecipes()
                        .getOrDefault(AARecipeTypes.ANVIL.get(), Maps.newHashMap())
                        .values().stream()
                        .filter(r -> r.getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .filter(r -> r.getIngredients().get(0) == leftInput.asVanillaIngredient() && r.getIngredients().get(1) == rightInput.asVanillaIngredient())
                        .map(Recipe::getId)
                        .forEach(r -> RecipeHelper.getRecipes().get(AARecipeTypes.ANVIL.get()).remove(r));
            }

            @Override
            public String describe() {
                return "Removing Anvil recipe for " + result.getCommandString() + "...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }

    @ZenCodeType.Method
    public static void removeRecipe(IItemStack result, IIngredient leftInput, int leftInputCount, IIngredient rightInput, int rightInputCount) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.getRecipes()
                        .getOrDefault(AARecipeTypes.ANVIL.get(), Maps.newHashMap())
                        .values().stream()
                        .filter(r -> r.getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .filter(r -> r.getIngredients().get(0) == leftInput.asVanillaIngredient() && r.getIngredients().get(1) == rightInput.asVanillaIngredient())
                        .filter(r -> ((IAnvilRecipe) r).getCounts().get(0) == leftInputCount && ((IAnvilRecipe) r).getCounts().get(1) == rightInputCount)
                        .map(Recipe::getId)
                        .forEach(r -> RecipeHelper.getRecipes().get(AARecipeTypes.ANVIL.get()).remove(r));
            }

            @Override
            public String describe() {
                return "Removing Anvil recipe for " + result.getCommandString() + "...";
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
                RecipeHelper.getRecipes().get(AARecipeTypes.ANVIL.get()).remove(new ResourceLocation(AnvilAPI.MOD_ID, id));
            }

            @Override
            public String describe() {
                return "Removing Anvil " + AnvilAPI.MOD_ID + ":" + id + " recipe...";
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
                RecipeHelper.getRecipes().get(AARecipeTypes.ANVIL.get()).remove(new ResourceLocation(modId, id));
            }

            @Override
            public String describe() {
                return "Removing Anvil " + modId + ":" + id + " recipe...";
            }

            @Override
            public String systemName() {
                return AnvilAPI.MOD_ID;
            }
        });
    }
}
