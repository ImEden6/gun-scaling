package net.mervyn.gunscaling.mixin;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.soulsweaponry.items.gun.GunItem;
import net.mervyn.gunscaling.GunScalingMod;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GunItem.class)
public class ShootSilverBulletMixin {

    @Inject(method = "createSilverBulletEntity", at = @At("RETURN"), cancellable = false)
    private void gunScaling$modifyProjectileDamage(World world, LivingEntity shooter, ItemStack gunStack,
            CallbackInfoReturnable<PersistentProjectileEntity> cir) {
        PersistentProjectileEntity projectile = cir.getReturnValue();

        if (projectile == null || shooter == null)
            return;

        // Safety check to ensure the attribute exists on the entity
        EntityAttributeInstance attributeInstance = shooter
                .getAttributeInstance(EntityAttributes_RangedWeapon.DAMAGE.attribute);
        if (attributeInstance == null)
            return;

        float baseDamage = (float) projectile.getDamage();

        float additive = GunScalingMod.configManager.value.damageAdditive;
        float scalingFactor = GunScalingMod.configManager.value.damageScalingFactor;
        double attrBonus = attributeInstance.getValue();

        // Formula: Final Damage = (Base + Additive) * (1 + Attribute Bonus / Scaling
        // Factor)
        if (scalingFactor == 0)
            scalingFactor = 1.0f; // Prevent division by zero

        float newDamage = (float) ((baseDamage + additive) * (1 + (attrBonus / scalingFactor)));

        projectile.setDamage(newDamage);
    }
}
