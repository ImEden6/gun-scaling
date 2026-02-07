package net.mervyn.gunscaling;

import net.fabricmc.api.ModInitializer;
import net.mervyn.gunscaling.config.GunScalingConfig;

public class GunScalingMod implements ModInitializer {
    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("gun-scaling");

    @Override
    public void onInitialize() {
        GunScalingConfig.load();
        LOGGER.info("Gun Scaling loaded. Multiplier: {}, Additive: {}", GunScalingConfig.INSTANCE.damageMultiplier,
                GunScalingConfig.INSTANCE.damageAdditive);
    }
}
