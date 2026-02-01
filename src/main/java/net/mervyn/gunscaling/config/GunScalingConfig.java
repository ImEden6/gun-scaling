package net.mervyn.gunscaling.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GunScalingConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("gun_scaling.json")
            .toFile();
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("gun-scaling");

    public static GunScalingConfig INSTANCE = new GunScalingConfig();

    public enum ScalingMode {
        ADDITIVE,
        MULTIPLICATIVE
    }

    public ScalingMode scalingMode = ScalingMode.ADDITIVE;
    public float damageMultiplier = 1.0f;

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (var reader = java.nio.file.Files.newBufferedReader(CONFIG_FILE.toPath(),
                    java.nio.charset.StandardCharsets.UTF_8)) {
                INSTANCE = GSON.fromJson(reader, GunScalingConfig.class);
            } catch (IOException e) {
                LOGGER.error("Failed to load gun_scaling.json", e);
            }
        }
        save();
    }

    public static void save() {
        try (var writer = java.nio.file.Files.newBufferedWriter(CONFIG_FILE.toPath(),
                java.nio.charset.StandardCharsets.UTF_8)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save gun_scaling.json", e);
        }
    }
}
