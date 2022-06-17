package hantonik.anvilapi.mixins;

import hantonik.anvilapi.init.Recipes;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
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

    public MixinAnvilMenu(@Nullable MenuType<?> p_39773_, int p_39774_, Inventory p_39775_, ContainerLevelAccess p_39776_) {
        super(p_39773_, p_39774_, p_39775_, p_39776_);
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

        float breakChance = net.minecraftforge.common.ForgeHooks.onAnvilRepair(player, stack, this.inputSlots.getItem(0), this.inputSlots.getItem(1));

        var recipe = this.player.level.getRecipeManager().getRecipeFor(Recipes.ANVIL.get(), this.inputSlots, this.player.level).orElse(null);

        if (recipe != null) {
            ItemStack input1 = this.inputSlots.getItem(0);

            if (recipe.getInput1().test(input1)) {
                input1.shrink(recipe.getInput1Amount());
                this.inputSlots.setItem(0, input1);
            } else {
                input1.shrink(recipe.getInput2Amount());
                this.inputSlots.setItem(1, input1);
            }

            ItemStack input2 = this.inputSlots.getItem(1);

            if (recipe.getInput2().test(input2)) {
                input2.shrink(recipe.getInput2Amount());
                this.inputSlots.setItem(1, input2);
            } else {
                input2.shrink(recipe.getInput1Amount());
                this.inputSlots.setItem(0, input2);
            }
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

        this.cost.set(0);
        
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
