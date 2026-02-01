# Gun Scaling Mod

A Fabric mod that bridges **Soulslike Weaponry** and **Ranged Weapon API** to allow gun damage to scale with player attributes.

## Features
- **Attribute Scaling**: Gun damage (Silver Bullets) scales based on the `ranged_weapon:damage` attribute.
- **Configurable**: Choose between Additive or Multiplicative scaling modes.
- **Custom Multiplier**: Fine-tune damage values via configuration.

## Dependencies
- [Soulslike Weaponry](https://www.curseforge.com/minecraft/mc-mods/soulslike-weaponry-fabric)
- [Ranged Weapon API](https://github.com/Fabric-Extras/Ranged-Weapon-API) (or local version)

## Configuration
The config file is located at `config/gun_scaling.json`.

```json
{
  "scalingMode": "ADDITIVE",
  "damageMultiplier": 1.0
}
```

## Building
1. Clone the repository.
2. Ensure you have the dependency Jars in the `libs/` folder.
3. Run:
   ```bash
   ./gradlew build
   ```
4. The output jar will be in `build/libs/`.

## License
MIT
