package net.mervyn.gunscaling;

import net.fabricmc.api.ModInitializer;
import net.mervyn.gunscaling.config.GunScalingConfig;
import net.tinyconfig.ConfigManager;

public class GunScalingMod implements ModInitializer {
    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("gun-scaling");
    public static ConfigManager<GunScalingConfig> configManager;

    @Override
    public void onInitialize() {
        configManager = new ConfigManager<>("gun_scaling", new GunScalingConfig())
                .builder()
                .setDirectory("gun-scaling")
                .sanitize(true)
                .build();
        configManager.refresh();

        LOGGER.info("Gun Scaling loaded. Multiplier: {}, Additive: {}",
                configManager.value.damageMultiplier,
                configManager.value.damageAdditive);
    }
}
