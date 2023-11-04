package hantonik.anvilapi.integration.jei;

import com.mojang.datafixers.util.Pair;
import hantonik.anvilapi.AnvilAPI;
import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.integration.jei.category.AnvilRecipeCategory;
import hantonik.anvilapi.mixins.accessors.AccessorJEIRecipeManager;
import hantonik.anvilapi.utils.AADisabledRecipes;
import hantonik.anvilapi.utils.AARecipeHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.registration.*;
import mezz.jei.library.plugins.vanilla.anvil.AnvilRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JeiPlugin
public final class JeiIntegration implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(AnvilAPI.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AnvilRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AnvilRecipeCategory.RECIPE_TYPE, AARecipeHelper.getRecipeManager().getAllRecipesFor(AARecipeTypes.ANVIL).stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.ANVIL), AnvilRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Blocks.CHIPPED_ANVIL), AnvilRecipeCategory.RECIPE_TYPE, RecipeTypes.ANVIL);
        registration.addRecipeCatalyst(new ItemStack(Blocks.DAMAGED_ANVIL), AnvilRecipeCategory.RECIPE_TYPE, RecipeTypes.ANVIL);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(AnvilMenu.class, MenuType.ANVIL, AnvilRecipeCategory.RECIPE_TYPE, 0, 2, 3, 36);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AnvilScreen.class, 102, 48, 22, 15, AnvilRecipeCategory.RECIPE_TYPE, RecipeTypes.ANVIL);
    }

    @Override
    public void registerRuntime(IRuntimeRegistration registration) {
        var recipesToAdd = new ArrayList<IJeiAnvilRecipe>();
        var ingredientsToAdd = new ArrayList<ItemStack>();

        ((AccessorJEIRecipeManager) registration.getRecipeManager()).anvilapi$getInternal().getRecipesStream(RecipeTypes.ANVIL, registration.getJeiHelpers().getFocusFactory().getEmptyFocusGroup(), false).forEach(recipe -> {
            if (recipe.getRightInputs().stream().anyMatch(rightInput -> AADisabledRecipes.isRepairItemDisabled(rightInput) || recipe.getLeftInputs().stream().anyMatch(leftInput -> AADisabledRecipes.isRepairDisabled(leftInput, rightInput) || (rightInput.getItem() instanceof EnchantedBookItem && EnchantmentHelper.getEnchantments(rightInput).entrySet().stream().anyMatch(entry -> AADisabledRecipes.isEnchantmentDisabled(leftInput, entry.getKey(), entry.getValue())))))) {
                registration.getRecipeManager().hideRecipes(RecipeTypes.ANVIL, Collections.singletonList(recipe));

                var leftInputs = new ArrayList<>(recipe.getLeftInputs());
                var rightInputs = new ArrayList<>(recipe.getRightInputs());

                ingredientsToAdd.addAll(leftInputs.stream().filter(stack -> registration.getIngredientVisibility().isIngredientVisible(VanillaTypes.ITEM_STACK, stack)).map(stack -> new ItemStack(stack.getItem())).toList());
                ingredientsToAdd.addAll(rightInputs.stream().filter(stack -> registration.getIngredientVisibility().isIngredientVisible(VanillaTypes.ITEM_STACK, stack)).map(stack -> new ItemStack(stack.getItem())).toList());

                var outputs = new ArrayList<>(recipe.getOutputs());

                if (leftInputs.stream().allMatch(input -> input.is(leftInputs.get(0).getItem()))) {
                    var leftInput = leftInputs.get(0);

                    if (rightInputs.stream().anyMatch(input -> !(input.getItem() instanceof EnchantedBookItem))) {
                        rightInputs.removeIf(AADisabledRecipes::isRepairItemDisabled);

                        if (AADisabledRecipes.isRepairDisabled(leftInput, null))
                            return;

                        rightInputs.removeIf(input -> AADisabledRecipes.isRepairDisabled(leftInput, input));
                    } else {
                        var done = 0;

                        for (var index = 0; index - done < rightInputs.size(); index++) {
                            if (EnchantmentHelper.getEnchantments(rightInputs.get(index - done)).entrySet().stream().anyMatch(enchantment -> AADisabledRecipes.isEnchantmentDisabled(leftInput, enchantment.getKey(), enchantment.getValue()))) {
                                if (rightInputs.size() == outputs.size())
                                    outputs.remove(index - done);

                                rightInputs.remove(index - done);

                                done++;
                            }
                        }
                    }

                    if (!rightInputs.isEmpty())
                        recipesToAdd.add(new AnvilRecipe(leftInputs, rightInputs, outputs));
                } else {
                    var recipes = new ArrayList<IJeiAnvilRecipe>();

                    if (rightInputs.stream().anyMatch(input -> !(input.getItem() instanceof EnchantedBookItem))) {
                        leftInputs.removeIf(input -> AADisabledRecipes.isRepairDisabled(input, null));

                        leftInputs.removeIf(leftInput -> {
                            var filter = false;

                            var newRightInputs = new ArrayList<>(rightInputs);

                            for (var rightInput : rightInputs) {
                                if (AADisabledRecipes.isRepairDisabled(leftInput, rightInput)) {
                                    newRightInputs.remove(rightInput);

                                    filter = true;
                                }
                            }

                            if (!filter)
                                return false;

                            recipes.add(new AnvilRecipe(Collections.singletonList(leftInput), newRightInputs, outputs));

                            return true;
                        });
                    } else {
                        leftInputs.removeIf(leftInput -> {
                            var newRightInputs = new ArrayList<>(rightInputs);
                            var newOutputs = new ArrayList<>(outputs);

                            var filter = false;
                            var done = 0;

                            for (var index = 0; index - done < newRightInputs.size(); index++) {
                                if (EnchantmentHelper.getEnchantments(newRightInputs.get(index - done)).entrySet().stream().anyMatch(enchantment -> AADisabledRecipes.isEnchantmentDisabled(leftInput, enchantment.getKey(), enchantment.getValue()))) {
                                    if (newRightInputs.size() == newOutputs.size())
                                        newOutputs.remove(index - done);

                                    newRightInputs.remove(index - done);

                                    done++;
                                    filter = true;
                                }
                            }

                            if (!filter)
                                return false;

                            recipes.add(new AnvilRecipe(Collections.singletonList(leftInput), newRightInputs, newOutputs));

                            return true;
                        });
                    }

                    if (!recipes.isEmpty())
                        recipesToAdd.addAll(recipes.stream().collect(Collectors.toMap(r -> Pair.of(r.getRightInputs(), r.getOutputs()), r -> r.getLeftInputs().stream(), Stream::concat)).entrySet().stream().map(entry -> new AnvilRecipe(entry.getValue().toList(), entry.getKey().getFirst(), entry.getKey().getSecond())).toList());

                    if (!leftInputs.isEmpty())
                        recipesToAdd.add(new AnvilRecipe(leftInputs, rightInputs, outputs));
                }
            }
        });

        var recipes = new ArrayList<IJeiAnvilRecipe>();

        recipes.addAll(recipesToAdd);
        recipes.addAll(AARecipeHelper.getRecipes(AARecipeTypes.ANVIL_REPAIR).values().stream().map(RecipeHolder::value).map(recipe -> (IJeiAnvilRecipe) new AnvilRecipe(Stream.of(new ItemStack(recipe.getBaseItem())).peek(input -> input.setDamageValue(input.getMaxDamage())).toList(), Arrays.asList(recipe.getRepairItem().getItems()), Stream.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())).peek(input -> input.setDamageValue(input.getMaxDamage() * 3 / 4)).toList())).toList());

        if (!recipes.isEmpty())
            registration.getRecipeManager().addRecipes(RecipeTypes.ANVIL, recipes.stream().distinct().toList());

        if (!ingredientsToAdd.isEmpty())
            registration.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, ingredientsToAdd);
    }
}
