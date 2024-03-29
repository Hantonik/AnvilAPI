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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IItemStack leftReturnItem, IIngredient rightInput, int rightInputCount, IItemStack rightReturnItem, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IItemStack leftReturnItem, IIngredient rightInput, int rightInputCount, IItemStack rightReturnItem, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IItemStack leftReturnItem, boolean consumeLeftInput, IIngredient rightInput, int rightInputCount, IItemStack rightReturnItem, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IItemStack leftReturnItem, boolean consumeLeftInput, IIngredient rightInput, int rightInputCount, IItemStack rightReturnItem, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IItemStack leftReturnItem, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, int rightInputCount, IItemStack rightReturnItem, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), rightInput.asVanillaIngredient(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IIngredient leftInput, int leftInputCount, IItemStack leftReturnItem, boolean consumeLeftInput, boolean useLeftItemDurability, IIngredient rightInput, int rightInputCount, IItemStack rightReturnItem, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.asVanillaIngredient(), leftInputCount, rightInput.asVanillaIngredient(), rightInputCount, experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack rightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack rightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, boolean consumeLeftInput, IItemStack rightInput, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, boolean consumeLeftInput, IItemStack rightInput, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, boolean consumeLeftInput, boolean useLeftItemDurability, IItemStack rightInput, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, boolean consumeLeftInput, boolean useLeftItemDurability, IItemStack rightInput, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack leftReturnItem, IItemStack rightInput, IItemStack rightReturnItem, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack leftReturnItem, IItemStack rightInput, IItemStack rightReturnItem, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, IItemStack rightInput, IItemStack rightReturnItem, boolean consumeRightInput, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, IItemStack rightInput, IItemStack rightReturnItem, boolean consumeRightInput, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, boolean useLeftItemDurability, IItemStack rightInput, IItemStack rightReturnItem, boolean consumeRightInput, boolean useRightItemDurability, int experience) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal())));
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
    public static void addRecipe(String id, IItemStack result, IItemStack leftInput, IItemStack leftReturnItem, boolean consumeLeftInput, boolean useLeftItemDurability, IItemStack rightInput, IItemStack rightReturnItem, boolean consumeRightInput, boolean useRightItemDurability, int experience, boolean shapeless) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.addRecipe(new RecipeHolder<>(new ResourceLocation(AnvilAPI.MOD_ID, id), new hantonik.anvilapi.recipe.AnvilRecipe(result.getInternal(), leftInput.getInternal(), rightInput.getInternal(), experience).consume(0, consumeLeftInput).consume(1, consumeRightInput).setUseDurability(0, useLeftItemDurability).setUseDurability(1, useRightItemDurability).setReturnItem(0, leftReturnItem.getInternal()).setReturnItem(1, rightReturnItem.getInternal()).shapeless(shapeless)));
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
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get())
                        .values().stream()
                        .filter(r -> r.value().getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .map(RecipeHolder::id)
                        .forEach(r -> AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get()).remove(r));
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
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get())
                        .values().stream()
                        .filter(r -> r.value().getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .filter(r -> r.value().getIngredients().get(0) == leftInput.asVanillaIngredient() && r.value().getIngredients().get(1) == rightInput.asVanillaIngredient())
                        .map(RecipeHolder::id)
                        .forEach(r -> AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get()).remove(r));
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
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get())
                        .values().stream()
                        .filter(r -> r.value().getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .filter(r -> r.value().getIngredients().get(0) == leftInput.asVanillaIngredient() && r.value().getIngredients().get(1) == rightInput.asVanillaIngredient())
                        .filter(r -> r.value().getCounts().get(0) == leftInputCount && r.value().getCounts().get(1) == rightInputCount)
                        .map(RecipeHolder::id)
                        .forEach(r -> AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get()).remove(r));
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
    public static void removeRecipe(IItemStack result, IItemStack leftInput, IItemStack rightInput) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get())
                        .values().stream()
                        .filter(r -> r.value().getResultItem(ServerLifecycleHooks.getCurrentServer().registryAccess()).is(result.getInternal().getItem()))
                        .filter(r -> r.value().getIngredients().get(0).test(leftInput.getInternal()) && r.value().getIngredients().get(1).test(rightInput.getInternal()))
                        .filter(r -> r.value().getInputNbt(0).equals(leftInput.getInternal().getOrCreateTag()) && r.value().getInputNbt(1).equals(rightInput.getInternal().getOrCreateTag()))
                        .map(RecipeHolder::id)
                        .forEach(r -> AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get()).remove(r));
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
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get()).remove(new ResourceLocation(AnvilAPI.MOD_ID, id));
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
                AARecipeHelper.getRecipes(AARecipeTypes.ANVIL.get()).remove(new ResourceLocation(modId, id));
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
