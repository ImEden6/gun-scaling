package net.mervyn.gunscaling.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import net.soulsweaponry.items.gun.GunItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class GunTooltipMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void gunScaling$modifyGunTooltip(@Nullable PlayerEntity player, TooltipContext context,
            CallbackInfoReturnable<List<Text>> cir) {
        if (((ItemStack) (Object) this).getItem() instanceof GunItem) {
            List<Text> tooltip = cir.getReturnValue();
            for (int i = 0; i < tooltip.size(); i++) {
                Text line = tooltip.get(i);

                // Check if the text content is a TranslatableTextContent
                if (line.getContent() instanceof TranslatableTextContent) {
                    TranslatableTextContent content = (TranslatableTextContent) line.getContent();
                    if ("enchantment.minecraft.infinity".equals(content.getKey())) {
                        // Create replacement text "Bullet Infinity"
                        // We preserve the style of the original line
                        MutableText replacement = Text.literal("Bullet Infinity").setStyle(line.getStyle());
                        tooltip.set(i, replacement);
                    }
                }
            }
        }
    }
}
