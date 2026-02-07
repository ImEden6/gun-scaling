package net.mervyn.gunscaling.mixin;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.soulsweaponry.items.gun.GunItem;
import net.mervyn.gunscaling.config.GunScalingConfig;
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

        float multiplier = GunScalingConfig.INSTANCE.damageMultiplier;
        float additive = GunScalingConfig.INSTANCE.damageAdditive;
        double attrBonus = attributeInstance.getValue();

        // Formula: final = (base * multiplier) + additive + the attribute bonus
        // Note: attributeInstance.getValue() includes base + modifiers. Ranged Weapon
        // API base is 0.0.
        // Assuming attrBonus IS the attribute bonus the user wants added at the end.

        float newDamage = (float) ((baseDamage * multiplier) + additive + attrBonus);

        projectile.setDamage(newDamage);
    }
}
