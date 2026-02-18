package net.mervyn.gunscaling.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.soulsweaponry.items.gun.GunItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class GunInfinityMixin {

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void gunScaling$allowInfinityOnGuns(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == Enchantments.INFINITY && stack.getItem() instanceof GunItem) {
            cir.setReturnValue(true);
        }
    }
}
