package hantonik.anvilapi.mixins;

import hantonik.anvilapi.init.AARecipeTypes;
import hantonik.anvilapi.utils.AADisabledRecipes;
import hantonik.anvilapi.utils.AAItemHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public abstract class MixinAnvilMenu extends ItemCombinerMenu {
    @Final
    @Shadow
    private DataSlot cost;
    @Shadow
    private String itemName;

    public MixinAnvilMenu(@Nullable MenuType<?> type, int containerId, Inventory container, ContainerLevelAccess access) {
        super(type, containerId, container, access);
    }

    @Shadow
    public abstract void createResult();

    @Inject(at = @At("HEAD"), method = "mayPickup", cancellable = true)
    protected void mayPickup(Player player, boolean hasStack, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(player.getAbilities().instabuild || player.experienceLevel >= this.cost.get());

        callback.cancel();
    }

    @Inject(at = @At("HEAD"), method = "onTake", cancellable = true)
    protected void onTake(Player player, ItemStack stack, CallbackInfo callback) {
        var recipe = this.player.level().getRecipeManager().getRecipeFor(AARecipeTypes.ANVIL.get(), this.inputSlots, this.player.level()).map(RecipeHolder::value).orElse(null);

        if (recipe != null) {
            if (!player.getAbilities().instabuild)
                player.giveExperienceLevels(-this.cost.get());

            this.cost.set(0);

            var breakChance = CommonHooks.onAnvilRepair(player, stack, this.inputSlots.getItem(0), this.inputSlots.getItem(1));

            var input1 = this.inputSlots.getItem(0);
            var input2 = this.inputSlots.getItem(1);

            var input1Slot = 0;
            var input2Slot = 1;

            if (!(recipe.getInput(0).test(this.inputSlots.getItem(0)) && recipe.getInput(1).test(this.inputSlots.getItem(1)))) {
                input1 = this.inputSlots.getItem(1);
                input2 = this.inputSlots.getItem(0);

                input1Slot = 1;
                input2Slot = 0;
            }

            if (recipe.isConsuming(0)) {
                if (recipe.isUsingDurability(0) && input1.isDamageableItem())
                    input1.hurtAndBreak(recipe.getInputCount(0), this.player, p -> this.access.execute(((level, pos) -> level.levelEvent(1029, pos, 0))));
                else
                    input1.shrink(recipe.getInputCount(0));

                if (input1.isEmpty())
                    this.inputSlots.setItem(input1Slot, ItemStack.EMPTY);
            }

            if (!recipe.getReturn(0).isEmpty()) {
                var returnItem = recipe.getReturn(0).copy();

                if (input1.isEmpty())
                    this.inputSlots.setItem(input1Slot, returnItem);
                else {
                    if (!recipe.isUsingDurability(0)) {
                        if (AAItemHelper.canCombineStacks(input1, returnItem))
                            this.inputSlots.setItem(input1Slot, AAItemHelper.combineStacks(input1, returnItem));

                        else
                            this.access.execute(((level, pos) -> Containers.dropItemStack(level, pos.getX(), pos.getY() + 1, pos.getZ(), returnItem)));
                    }
                }
            }

            if (recipe.isConsuming(1)) {
                if (recipe.isUsingDurability(1) && input2.isDamageableItem())
                    input2.hurtAndBreak(recipe.getInputCount(1), this.player, p -> this.access.execute(((level, pos) -> level.levelEvent(1029, pos, 0))));
                else
                    input2.shrink(recipe.getInputCount(1));

                if (input2.isEmpty())
                    this.inputSlots.setItem(input2Slot, ItemStack.EMPTY);
            }

            if (!recipe.getReturn(1).isEmpty()) {
                var returnItem = recipe.getReturn(1).copy();

                if (input2.isEmpty())
                    this.inputSlots.setItem(input2Slot, returnItem);
                else {
                    if (!recipe.isUsingDurability(1)) {
                        if (AAItemHelper.canCombineStacks(input2, returnItem))
                            this.inputSlots.setItem(input2Slot, AAItemHelper.combineStacks(input2, returnItem));
                        else
                            this.access.execute(((level, pos) -> Containers.dropItemStack(level, pos.getX(), pos.getY() + 1, pos.getZ(), returnItem)));
                    }
                }
            }

            this.createResult();

            this.access.execute((level, pos) -> {
                BlockState blockstate = level.getBlockState(pos);

                if (!player.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < breakChance) {
                    BlockState state1 = AnvilBlock.damage(blockstate);

                    if (state1 == null) {
                        level.removeBlock(pos, false);
                        level.levelEvent(1029, pos, 0);
                    } else {
                        level.setBlock(pos, state1, 2);
                        level.levelEvent(1030, pos, 0);
                    }
                } else
                    level.levelEvent(1030, pos, 0);
            });

            this.inputSlots.setChanged();

            callback.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "createResult", cancellable = true)
    public void createResult(CallbackInfo callback) {
        var level = this.player.level();
        var recipe = level.getRecipeManager().getRecipeFor(AARecipeTypes.ANVIL.get(), this.inputSlots, level).map(RecipeHolder::value).orElse(null);

        if (recipe != null) {
            var stack = recipe.getResultItem(level.registryAccess());
            var output = recipe.assemble(this.inputSlots, level.registryAccess());
            var exp = recipe.getExperience();

            if (StringUtils.isBlank(this.itemName)) {
                if (stack.hasCustomHoverName()) {
                    exp++;

                    output.resetHoverName();
                }
            } else if (!this.itemName.equals(stack.getHoverName().getString())) {
                exp++;

                output.setHoverName(Component.literal(this.itemName));
            }

            this.cost.set(exp);

            this.resultSlots.setItem(0, output);
            this.broadcastChanges();

            callback.cancel();
        } else {
            var baseItem = this.inputSlots.getItem(0);
            var secondItem = this.inputSlots.getItem(1);

            if (baseItem.isDamageableItem() && !(secondItem.getItem() instanceof EnchantedBookItem) && !AADisabledRecipes.isValidRepairItem(baseItem, secondItem, level)) {
                callback.cancel();

                this.resultSlots.setItem(0, ItemStack.EMPTY);
                this.broadcastChanges();
            }

            if (baseItem.getItem() instanceof EnchantedBookItem) {
                if (secondItem.getItem() instanceof EnchantedBookItem) {
                    if (EnchantmentHelper.getEnchantments(baseItem).entrySet().stream().anyMatch(entry1 -> EnchantmentHelper.getEnchantments(secondItem).entrySet().stream().anyMatch(entry2 -> AADisabledRecipes.isEnchantmentCombiningDisabled(entry1.getKey(), entry1.getValue(), entry2.getKey(), entry2.getValue())))) {
                        callback.cancel();

                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.broadcastChanges();
                    }
                } else if (EnchantmentHelper.getEnchantments(baseItem).entrySet().stream().anyMatch(entry -> AADisabledRecipes.isEnchantmentDisabled(null, entry.getKey(), entry.getValue()))) {
                    callback.cancel();

                    this.resultSlots.setItem(0, ItemStack.EMPTY);
                    this.broadcastChanges();
                }
            } else {
                if (secondItem.getItem() instanceof EnchantedBookItem) {
                    if (EnchantmentHelper.getEnchantments(secondItem).entrySet().stream().anyMatch(entry -> AADisabledRecipes.isEnchantmentDisabled(baseItem, entry.getKey(), entry.getValue()))) {
                        callback.cancel();

                        this.resultSlots.setItem(0, ItemStack.EMPTY);
                        this.broadcastChanges();
                    }
                }
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        if (slotIndex == AnvilMenu.RESULT_SLOT) {
            if (this.slots.get(slotIndex).hasItem()) {
                if (!this.mayPickup(player, true)) {
                    this.resetQuickCraft();

                    return ItemStack.EMPTY;
                }
            }
        }

        return super.quickMoveStack(player, slotIndex);
    }
}
