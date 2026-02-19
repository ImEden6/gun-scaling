package net.mervyn.gunscaling.config;

import net.tinyconfig.versioning.VersionableConfig;

public class GunScalingConfig extends VersionableConfig {

    public String _comment = "Formula: Final Damage = (Base + Additive) * (1 + Attribute Bonus / Scaling Factor)";
    public String _usage = "Example: Set 'damageAdditive' to 5.0 for flat +5 base damage. Set 'damageScalingFactor' to control the attribute strength (higher factor = less bonus).";

    public float damageAdditive = 0.0f;
    public float damageScalingFactor = 1.0f;
}
