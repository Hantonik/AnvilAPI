package hantonik.anvilapi.mixins;

import hantonik.anvilapi.init.Recipes;
import hantonik.atomiccore.utils.helpers.StackHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
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
    @Shadow
    public int repairItemCountCost;

    @Shadow
    public abstract void createResult();

    public MixinAnvilMenu(@Nullable MenuType<?> type, int containerId, Inventory container, ContainerLevelAccess access) {
        super(type, containerId, container, access);
    }

    @Inject(at = @At("HEAD"), method = "mayPickup", cancellable = true)
    protected void mayPickup(Player player, boolean b, CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(player.getAbilities().instabuild || player.experienceLevel >= this.cost.get());

        callback.cancel();
    }

    @Inject(at = @At("HEAD"), method = "onTake", cancellable = true)
    protected void onTake(Player player, ItemStack stack, CallbackInfo callback) {
        if (!player.getAbilities().instabuild)
            player.giveExperienceLevels(-this.cost.get());

        this.cost.set(0);

        var breakChance = ForgeHooks.onAnvilRepair(player, stack, this.inputSlots.getItem(0), this.inputSlots.getItem(1));

        var recipe = this.player.level.getRecipeManager().getRecipeFor(Recipes.ANVIL.get(), this.inputSlots, this.player.level).orElse(null);

        if (recipe != null) {
            var input1SlotId = recipe.getInput1().test(this.inputSlots.getItem(0)) ? 0 : 1;

            var input1 = this.inputSlots.getItem(input1SlotId);
            var input1Return = recipe.getInput1Return().copy();

            if (recipe.consumeInput1()) {
                if (recipe.ignoreInput1Durability()) {
                    input1.shrink(recipe.getInput1Amount());

                    this.inputSlots.setItem(input1SlotId, input1);
                } else {
                    if (input1.isDamageableItem()) {
                        var damage = input1.getDamageValue() + recipe.getInput1Amount();

                        input1.setDamageValue(damage);

                        if (damage < input1.getMaxDamage())
                            this.inputSlots.setItem(input1SlotId, input1);
                        else {
                            input1.shrink(1);
                            this.inputSlots.setItem(input1SlotId, input1);

                            this.access.execute((level, pos) -> level.levelEvent(1029, pos, 0));
                        }
                    } else {
                        input1.shrink(recipe.getInput1Amount());

                        this.inputSlots.setItem(input1SlotId, input1);
                    }
                }

                if (!input1Return.isEmpty()) {
                    if (input1.isEmpty())
                        this.inputSlots.setItem(input1SlotId, input1Return);
                    else if (StackHelper.canCombineStacks(input1Return, input1))
                        this.inputSlots.setItem(input1SlotId, StackHelper.combineStacks(input1Return, input1));
                    else
                        this.access.execute((level, pos) -> Containers.dropItemStack(level, pos.getX(), pos.getY() + 1.0F, pos.getZ(), input1Return));
                }
            } else {
                if (!input1Return.isEmpty()) {
                    if (StackHelper.canCombineStacks(input1Return, input1))
                        this.inputSlots.setItem(input1SlotId, StackHelper.combineStacks(input1Return, input1));
                    else
                        this.access.execute((level, pos) -> Containers.dropItemStack(level, pos.getX(), pos.getY() + 1.0F, pos.getZ(), input1Return));
                }

                this.createResult();
            }

            var input2SlotId = recipe.getInput2().test(this.inputSlots.getItem(1)) ? 1 : 0;

            var input2 = this.inputSlots.getItem(input2SlotId);
            var input2Return = recipe.getInput2Return().copy();

            if (recipe.consumeInput2()) {
                if (recipe.ignoreInput2Durability()) {
                    input2.shrink(recipe.getInput2Amount());

                    this.inputSlots.setItem(input2SlotId, input2);
                } else {
                    if (input2.isDamageableItem()) {
                        var damage = input2.getDamageValue() + recipe.getInput2Amount();

                        input2.setDamageValue(damage);

                        if (damage < input2.getMaxDamage())
                            this.inputSlots.setItem(input2SlotId, input2);
                        else {
                            input2.shrink(1);
                            this.inputSlots.setItem(input2SlotId, input2);

                            this.access.execute((level, pos) -> level.levelEvent(1029, pos, 0));
                        }
                    } else {
                        input2.shrink(recipe.getInput2Amount());

                        this.inputSlots.setItem(input2SlotId, input2);
                    }
                }

                if (!input2Return.isEmpty()) {
                    if (input2.isEmpty())
                        this.inputSlots.setItem(input2SlotId, input2Return);
                    else if (StackHelper.canCombineStacks(input2Return, input2))
                        this.inputSlots.setItem(input2SlotId, StackHelper.combineStacks(input2Return, input2));
                    else
                        this.access.execute((level, pos) -> Containers.dropItemStack(level, pos.getX(), pos.getY() + 1.0F, pos.getZ(), input2Return));
                }
            } else {
                if (!input2Return.isEmpty()) {
                    if (StackHelper.canCombineStacks(input2Return, input2))
                        this.inputSlots.setItem(input2SlotId, StackHelper.combineStacks(input2Return, input2));
                    else
                        this.access.execute((level, pos) -> Containers.dropItemStack(level, pos.getX(), pos.getY() + 1.0F, pos.getZ(), input2Return));
                }

                this.createResult();
            }

            this.broadcastChanges();
        } else {
            this.inputSlots.setItem(0, ItemStack.EMPTY);

            if (this.repairItemCountCost > 0) {
                ItemStack itemstack = this.inputSlots.getItem(1);

                if (!itemstack.isEmpty() && itemstack.getCount() > this.repairItemCountCost) {
                    itemstack.shrink(this.repairItemCountCost);

                    this.inputSlots.setItem(1, itemstack);
                } else
                    this.inputSlots.setItem(1, ItemStack.EMPTY);
            } else
                this.inputSlots.setItem(1, ItemStack.EMPTY);
        }
        
        this.access.execute((level, pos) -> {
            BlockState blockstate = level.getBlockState(pos);

            if (!player.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < breakChance) {
                BlockState blockstate1 = AnvilBlock.damage(blockstate);

                if (blockstate1 == null) {
                    level.removeBlock(pos, false);
                    level.levelEvent(1029, pos, 0);
                } else {
                    level.setBlock(pos, blockstate1, 2);
                    level.levelEvent(1030, pos, 0);
                }
            } else
                level.levelEvent(1030, pos, 0);
        });
        
        callback.cancel();
    }

    @Inject(at = @At("HEAD"), method = "createResult", cancellable = true)
    public void createResult(CallbackInfo callback) {
        var recipe = this.player.level.getRecipeManager().getRecipeFor(Recipes.ANVIL.get(), this.inputSlots, this.player.level).orElse(null);

        if (recipe != null) {
            ItemStack stack = recipe.getResultItem();
            ItemStack output = stack.copy();

            int exp = recipe.getExperience();

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
        }
    }
}
