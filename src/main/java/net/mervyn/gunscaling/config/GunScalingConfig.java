package net.mervyn.gunscaling.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class GunScalingConfig {
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("gun_scaling.properties")
            .toFile();
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("gun-scaling");

    public static GunScalingConfig INSTANCE = new GunScalingConfig();

    public float damageMultiplier = 1.0f;
    public float damageAdditive = 0.0f;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (var reader = new java.io.FileReader(CONFIG_FILE)) {
                var props = new java.util.Properties();
                props.load(reader);
                try {
                    INSTANCE.damageMultiplier = Float.parseFloat(props.getProperty("damageMultiplier", "1.0"));
                    INSTANCE.damageAdditive = Float.parseFloat(props.getProperty("damageAdditive", "0.0"));
                } catch (NumberFormatException e) {
                    LOGGER.error("Failed to parse config values, using defaults", e);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to load gun_scaling.properties", e);
            }
        }
        save();
    }

    public static void save() {
        try (var writer = new java.io.FileWriter(CONFIG_FILE)) {
            var props = new java.util.Properties();
            props.setProperty("damageMultiplier", String.valueOf(INSTANCE.damageMultiplier));
            props.setProperty("damageAdditive", String.valueOf(INSTANCE.damageAdditive));
            props.store(writer, "Gun Scaling Configuration\n" +
                    "Formula: final_damage = (base_damage * damageMultiplier) + damageAdditive + attribute_bonus\n" +
                    "Default damageMultiplier: 1.0\n" +
                    "Default damageAdditive: 0.0");
        } catch (IOException e) {
            LOGGER.error("Failed to save gun_scaling.properties", e);
        }
    }
}
