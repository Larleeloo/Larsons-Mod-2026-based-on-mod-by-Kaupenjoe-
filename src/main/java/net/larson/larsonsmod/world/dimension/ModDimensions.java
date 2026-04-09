package net.larson.larsonsmod.world.dimension;

import net.larson.larsonsmod.LarsonsMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> KAUPENDIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(LarsonsMod.MOD_ID, "kaupendim"));
    public static final RegistryKey<World> KAUPENDIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(LarsonsMod.MOD_ID, "kaupendim"));
    public static final RegistryKey<DimensionType> KAUPEN_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(LarsonsMod.MOD_ID, "kaupendim_type"));

    // Neon Dimension
    public static final RegistryKey<DimensionOptions> NEONDIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(LarsonsMod.MOD_ID, "neondim"));
    public static final RegistryKey<World> NEONDIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(LarsonsMod.MOD_ID, "neondim"));
    public static final RegistryKey<DimensionType> NEON_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(LarsonsMod.MOD_ID, "neondim_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(KAUPEN_DIM_TYPE, new DimensionType(
                true, // hasFixedTime (was OptionalLong.of(12000))
                false, // hasSkylight
                false, // hasCeiling
                1.0, // coordinateScale
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(UniformIntProvider.create(0, 0), 0),
                DimensionType.Skybox.OVERWORLD, // skybox
                DimensionType.CardinalLightType.DEFAULT, // cardinalLightType
                EnvironmentAttributeMap.EMPTY, // attributes
                RegistryEntryList.empty())); // timelines

        // Neon Dimension - perpetual twilight, skylight, tall world for floating islands
        context.register(NEON_DIM_TYPE, new DimensionType(
                true, // hasFixedTime (was OptionalLong.of(18000))
                true, // hasSkylight
                false, // hasCeiling
                1.0, // coordinateScale
                -64, // minY - deeper underground
                384, // height - tall world for floating islands
                384, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                0.0f, // ambientLight - completely dark so neon blocks glow brightly
                new DimensionType.MonsterSettings(UniformIntProvider.create(0, 7), 0),
                DimensionType.Skybox.OVERWORLD, // skybox
                DimensionType.CardinalLightType.DEFAULT, // cardinalLightType
                EnvironmentAttributeMap.EMPTY, // attributes
                RegistryEntryList.empty())); // timelines
    }
}
