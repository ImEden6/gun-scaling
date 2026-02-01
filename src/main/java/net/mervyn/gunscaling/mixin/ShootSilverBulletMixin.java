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

        double attrValue = attributeInstance.getValue(); // This includes base + modifiers
        // Note: Ranged Weapon API base is 0.0, so this usually just represents the
        // bonus.

        float baseDamage = (float) projectile.getDamage();
        float newDamage = baseDamage;
        float multiplier = GunScalingConfig.INSTANCE.damageMultiplier;

        if (GunScalingConfig.INSTANCE.scalingMode == GunScalingConfig.ScalingMode.ADDITIVE) {
            // mode: ADDITIVE
            newDamage = baseDamage + (float) (attrValue * multiplier);
        } else {
            // mode: MULTIPLICATIVE
            // Assuming attrValue is flat bonus to be used as percentage (e.g. 5.0 = 500%?
            // No, usually 0.5 = 50%)
            // If the attribute is generally small (like 1.0, 2.0), treating it as a flat
            // multiplier to 1 might be huge.
            // But per user request: base * (1 + attr * mult)
            // Example: Base 10, Attr 2.0 (from API), Mult 1.0 -> 10 * (1 + 2.0) = 30.
            newDamage = baseDamage * (1.0f + (float) (attrValue * multiplier));
        }

        projectile.setDamage(newDamage);
    }
}
