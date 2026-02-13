package net.mervyn.gunscaling.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GunScalingConfig {
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("gun_scaling.json")
            .toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("gun-scaling");

    public static GunScalingConfig INSTANCE = new GunScalingConfig();

    public String _comment = "Formula: Final Damage = (Base * Multiplier) + Additive + Attribute Bonus";
    public String _usage = "Example: Set 'damageMultiplier' to 1.5 for 50% more damage. Set 'damageAdditive' to 5.0 for flat +5 damage.";
    public float damageMultiplier = 1.0f;
    public float damageAdditive = 0.0f;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, GunScalingConfig.class);
                // Ensure comments are always present and up-to-date
                INSTANCE._comment = "Formula: Final Damage = (Base * Multiplier) + Additive + Attribute Bonus";
                INSTANCE._usage = "Example: Set 'damageMultiplier' to 1.5 for 50% more damage. Set 'damageAdditive' to 5.0 for flat +5 damage.";
            } catch (IOException e) {
                LOGGER.error("Failed to load gun_scaling.json", e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save gun_scaling.json", e);
        }
    }
}
