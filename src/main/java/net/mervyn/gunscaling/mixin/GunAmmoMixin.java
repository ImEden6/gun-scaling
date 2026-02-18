package net.mervyn.gunscaling.mixin;

import net.minecraft.item.ItemStack;
import net.soulsweaponry.items.gun.GunItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GunItem.class)
public class GunAmmoMixin {

    @Inject(method = "getBulletsNeededWithInfinity", at = @At("HEAD"), cancellable = true)
    private void gunScaling$forceZeroAmmoWithInfinity(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        // Force return 0 so that GunItem.canShoot treats it as infinite ammo
        cir.setReturnValue(0);
    }
}
