package net.kaupenjoe.tutorialmod.world.dimension;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> KAUPENDIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(TutorialMod.MOD_ID, "kaupendim"));
    public static final RegistryKey<World> KAUPENDIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(TutorialMod.MOD_ID, "kaupendim"));
    public static final RegistryKey<DimensionType> KAUPEN_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(TutorialMod.MOD_ID, "kaupendim_type"));

    // Neon Dimension
    public static final RegistryKey<DimensionOptions> NEONDIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(TutorialMod.MOD_ID, "neondim"));
    public static final RegistryKey<World> NEONDIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(TutorialMod.MOD_ID, "neondim"));
    public static final RegistryKey<DimensionType> NEON_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(TutorialMod.MOD_ID, "neondim_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(KAUPEN_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));

        // Neon Dimension - perpetual twilight, skylight, tall world for floating islands
        context.register(NEON_DIM_TYPE, new DimensionType(
                OptionalLong.of(18000), // fixedTime - perpetual twilight/night for neon glow
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY - deeper underground
                384, // height - tall world for floating islands
                384, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                0.1f, // ambientLight - dim so neon blocks glow brightly
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 7), 0)));
    }
}
