package net.mervyn.gunscaling.config;

import net.tinyconfig.versioning.VersionableConfig;

public class GunScalingConfig extends VersionableConfig {

    public String _comment = "Formula: Final Damage = (Base * Multiplier) + Additive + Attribute Bonus";
    public String _usage = "Example: Set 'damageMultiplier' to 1.5 for 50% more damage. Set 'damageAdditive' to 5.0 for flat +5 damage.";

    public float damageMultiplier = 1.0f;
    public float damageAdditive = 0.0f;
}
