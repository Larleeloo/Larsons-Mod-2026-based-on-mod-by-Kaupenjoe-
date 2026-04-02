# Larson's Mod - Migration from 1.21.1 to 1.21.7

This document lists every expected code change needed to update this Fabric mod from Minecraft 1.21.1 to 1.21.7. Changes span breaking API changes introduced in 1.21.2, 1.21.4, 1.21.5, 1.21.6, and 1.21.7.

---

## 1. Build Files (DONE)

### `gradle.properties`
- `minecraft_version` -> `1.21.7`
- `yarn_mappings` -> `1.21.7+build.1`
- `loader_version` -> `0.16.14`
- `mod_version` -> `0.3-1.21.7`
- `fabric_version` -> `0.129.0+1.21.7`

### `build.gradle`
- Loom plugin -> `1.10-SNAPSHOT`
- Updated all dependency versions for 1.21.7 compatibility

---

## 2. `fabric.mod.json`

| Change | Detail |
|--------|--------|
| Minecraft version | `"minecraft": "~1.21.1"` -> `"~1.21.7"` |

---

## 3. `ModToolMaterial.java` - Enum to Record

**Old (1.21.1):** `ToolMaterial` was an interface implemented by an enum with getter methods and a `Supplier<Ingredient>` for repair.

**New (1.21.2+):** `ToolMaterial` is a record. Instantiate directly:
```java
// Old
public enum ModToolMaterial implements ToolMaterial {
    RUBY(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 650, 4.5f, 3.5f, 26,
         () -> Ingredient.ofItems(ModItems.RUBY));
    // ... interface method overrides
}

// New
public class ModToolMaterial {
    public static final ToolMaterial RUBY = new ToolMaterial(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 650, 4.5f, 3.5f, 26,
        ModTags.Items.RUBY_REPAIR);  // TagKey<Item> instead of Supplier<Ingredient>
}
```

**Key difference:** Last parameter changed from `Supplier<Ingredient>` to `TagKey<Item>`.

---

## 4. `ModArmorMaterials.java` - Complete Rewrite

**Old (1.21.1):** `ArmorMaterial` registered in `Registries.ARMOR_MATERIAL` with defense map, layers, `Supplier<Ingredient>`.

**New (1.21.5+):** `ArmorMaterial` is gone as a registry. Armor properties are now set via `Item.Settings` using equipment-related methods. Texture identification uses `RegistryKey<EquipmentAsset>`.

```java
// Old
public static final RegistryEntry<ArmorMaterial> RUBY = registerMaterial("ruby",
    Map.of(ArmorItem.Type.BOOTS, 3, ...), 19, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
    2.0f, 0.1f, () -> Ingredient.ofItems(ModItems.RUBY), 25);

// New - define an ArmorMaterial record or use Item.Settings directly
// ArmorMaterial is now a simple record with defense, enchantability, equipSound, repairTag, toughness, knockbackResistance
public static final ArmorMaterial RUBY = new ArmorMaterial(
    19, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 2.0f, 0.1f,
    ModTags.Items.RUBY_REPAIR, ModEquipmentAssetKeys.RUBY);
```

---

## 5. `ModTags.java` - Add Repair Item Tag

Add a `RUBY_REPAIR` tag to `ModTags.Items` for use by both tool and armor materials:
```java
public static final TagKey<Item> RUBY_REPAIR = createTag("ruby_repair");
```

Also need a corresponding JSON tag file at `data/larsonsmod/tags/item/ruby_repair.json`.

---

## 6. `ModItems.java` - Major Rewrite

### 6a. Registration Pattern
**Old:** `Registry.register(Registries.ITEM, Identifier.of(...), new Item(...))`
**New:** Items require `RegistryKey<Item>` in settings via `.registryKey(key)`:
```java
// Old
private static Item registerItem(String name, Item item) {
    return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
}

// New
private static RegistryKey<Item> keyOf(String name) {
    return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(LarsonsMod.MOD_ID, name));
}
private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
    RegistryKey<Item> key = keyOf(name);
    Item item = factory.apply(settings.registryKey(key));
    return Registry.register(Registries.ITEM, key, item);
}
// Simple items:
private static Item registerItem(String name, Item.Settings settings) {
    return registerItem(name, Item::new, settings);
}
```

### 6b. Tool Items (SwordItem, PickaxeItem, AxeItem, ShovelItem, HoeItem REMOVED)
**Old:** `new SwordItem(material, settings.attributeModifiers(SwordItem.createAttributeModifiers(...)))`
**New:** Use `Item.Settings` methods - `.sword()`, `.pickaxe()`, `.axe()`, `.shovel()`, `.hoe()`:
```java
// Old
public static final Item RUBY_SWORD = registerItem("ruby_sword",
    new SwordItem(ModToolMaterial.RUBY, new Item.Settings()
        .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterial.RUBY, 5, 3f))));

// New
public static final Item RUBY_SWORD = registerItem("ruby_sword",
    new Item.Settings().sword(ModToolMaterial.RUBY, 5f, -2.4f));
```

### 6c. Armor Items (ArmorItem REMOVED)
**Old:** `new ArmorItem(material, ArmorItem.Type.HELMET, settings)`
**New:** Use `Item.Settings.humanoidArmor(material, EquipmentType)`:
```java
// Old
public static final Item RUBY_HELMET = registerItem("ruby_helmet",
    new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.HELMET,
        new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(25))));

// New
public static final Item RUBY_HELMET = registerItem("ruby_helmet",
    new Item.Settings().humanoidArmor(ModArmorMaterials.RUBY, EquipmentType.HELMET));
```

### 6d. SpawnEggItem - Color Parameters Removed
**Old:** `new SpawnEggItem(entityType, primaryColor, secondaryColor, settings)`
**New:** `new SpawnEggItem(entityType, settings)` - colors are now data-driven via textures.

### 6e. All Other Items
Every item registration must include `.registryKey(key)` in their `Item.Settings`.

---

## 7. `ModArmorItem.java` - ArmorItem Base Class Removed

**Old:** Extends `ArmorItem`, uses `ArmorItem.getMaterial()` to check armor material.
**New:** Must extend `Item`. Use data components to detect armor material instead of `instanceof ArmorItem` + `getMaterial()`.

```java
// Old
public class ModArmorItem extends ArmorItem { ... }
ArmorItem boots = ((ArmorItem) stack.getItem());
boots.getMaterial() == material;

// New
public class ModArmorItem extends Item { ... }
// Check via equipment data components on the ItemStack
// Use stack.get(DataComponentTypes.EQUIPPABLE) to identify armor material
```

---

## 8. `ModBlocks.java` - RegistryKey-Based Registration

### 8a. Block Registration
**Old:** `Registry.register(Registries.BLOCK, Identifier.of(...), block)`
**New:** Blocks also need `RegistryKey<Block>` in `AbstractBlock.Settings`:
```java
// Old
private static Block registerBlock(String name, Block block) {
    registerBlockItem(name, block);
    return Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, name), block);
}

// New
private static Block registerBlock(String name,
        Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
    RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, name));
    Block block = factory.apply(settings.registryKey(key));
    Block registered = Registry.register(Registries.BLOCK, key, block);
    registerBlockItem(name, registered);
    return registered;
}
```

### 8b. BlockItem Registration
Block items also need registry keys set in their settings.

### 8c. ExperienceDroppingBlock Renamed
`ExperienceDroppingBlock` may be renamed to `ExperienceOreBlock` or similar.

### 8d. FlowerBlock Constructor Change
**Old:** `new FlowerBlock(StatusEffect, duration, settings)`
**New:** `new FlowerBlock(StatusEffectInstance, settings)` or effect specified via settings/component.

### 8e. SaplingBlock Constructor
May require `SaplingGenerator` parameter changes.

### 8f. AbstractBlock.Settings.copy()
`AbstractBlock.Settings.copy(Blocks.X)` should still work but the settings object needs `.registryKey()` applied.

---

## 9. `ModEntities.java` - EntityType.Builder.build()

**Old:** `EntityType.Builder.create(...).dimensions(...).build()` (no-arg)
**New:** `build()` requires a `RegistryKey<EntityType<?>>`:
```java
// Old
EntityType.Builder.create(PorcupineEntity::new, SpawnGroup.CREATURE)
    .dimensions(1f, 1f).build()

// New
EntityType.Builder.create(PorcupineEntity::new, SpawnGroup.CREATURE)
    .dimensions(1f, 1f).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "porcupine")))
```

---

## 10. `LarsonsMod.java` - FuelRegistry API

**Old:** `FuelRegistry.INSTANCE.add(item, burnTime)`
**New:** `FuelRegistryEvents.BUILD` event:
```java
// Old
FuelRegistry.INSTANCE.add(ModItems.COAL_BRIQUETTE, 200);

// New
FuelRegistryEvents.BUILD.register((builder, context) -> {
    builder.add(ModItems.COAL_BRIQUETTE, 200);
});
```

---

## 11. `DiceItem.java` - TypedActionResult Removed

**Old:** `TypedActionResult<ItemStack> use(...)` returning `TypedActionResult.success(stack, isClient)`
**New:** `ActionResult use(...)` returning `ActionResult.SUCCESS`:
```java
// Old
public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
    ...
    return TypedActionResult.success(itemStack, world.isClient());
}

// New
public ActionResult use(World world, PlayerEntity user, Hand hand) {
    ...
    return ActionResult.SUCCESS;
}
```

---

## 12. `PorcupineEntity.java` - EntityAttributes Prefix Removed

**Old:** `EntityAttributes.GENERIC_MAX_HEALTH`, `GENERIC_MOVEMENT_SPEED`, etc.
**New:** `EntityAttributes.MAX_HEALTH`, `MOVEMENT_SPEED`, etc.

```java
// Old
.add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
.add(EntityAttributes.GENERIC_ARMOR, 0.5f)
.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)

// New
.add(EntityAttributes.MAX_HEALTH, 15)
.add(EntityAttributes.MOVEMENT_SPEED, 0.2f)
.add(EntityAttributes.ARMOR, 0.5f)
.add(EntityAttributes.ATTACK_DAMAGE, 2)
```

Also: `EntityType.create()` in `createChild()` may need a `ServerWorld` parameter.

---

## 13. `ModCustomTrades.java` - TradeOfferHelper API

**Old:** `TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, level, ...)`
**New:** Takes `RegistryKey<VillagerProfession>` instead of `VillagerProfession` instance:
```java
// Old
TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, ...);

// New
TradeOfferHelper.registerVillagerOffers(VillagerProfessions.FARMER, 1, ...);
// or RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, Identifier.ofVanilla("farmer"))
```

Custom professions (SOUND_MASTER) need a RegistryKey reference.

---

## 14. `GemPolishingStationBlockEntity.java` - NbtCompound Changes

**Old:** `nbt.getInt("key")` returns `int` directly.
**New (1.21.5+):** `nbt.getInt("key")` returns `OptionalInt` or requires a default value parameter:
```java
// Old
progress = nbt.getInt("gem_polishing_station.progress");

// New
progress = nbt.getInt("gem_polishing_station.progress").orElse(0);
// or: nbt.getInt("gem_polishing_station.progress", 0);
```

---

## 15. `GemPolishingStationBlock.java` - onStateReplaced Timing

**Old (1.21.1):** `onStateReplaced` runs BEFORE block entity is removed - safe to access `world.getBlockEntity(pos)`.
**New (1.21.5+):** `onStateReplaced` runs AFTER block entities are removed. Must use `onRemoved` or `afterBreak`, or override the new `getDroppedStacks` method instead.

```java
// Old
@Override
public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    if (state.getBlock() != newState.getBlock()) {
        BlockEntity blockEntity = world.getBlockEntity(pos); // still exists
        ...
    }
}

// New - use BlockState parameter or override afterBreak
// The old state is passed as parameter, but block entity is already gone
// Solution: override getDroppedStacks or use onRemoved callback
```

---

## 16. `GemPolishingBlockEntityRenderer.java` - ModelTransformationMode

**Old:** `ModelTransformationMode.GUI`
**New (1.21.5+):** May be renamed to `ItemDisplayContext.GUI` or `ItemTransform.GUI`:
```java
// Old
itemRenderer.renderItem(stack, ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, world, 1);

// New
itemRenderer.renderItem(stack, ItemDisplayContext.GUI, light, overlay, matrices, vertexConsumers, world, 1);
```

---

## 17. `GemPolishingScreen.java` - DrawContext.drawTexture

The `drawTexture` method signature may have changed in 1.21.2+. Verify parameter order and types at compile time.

---

## 18. `DiceProjectileEntity.java` - ThrownItemEntity Constructor

The `ThrownItemEntity(EntityType, LivingEntity, World)` constructor may have changed to include `ItemStack` parameter or the world may come from the entity. Verify at compile time.

---

## 19. `ModFoodComponents.java` - FoodComponent API

**Old:** `new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).statusEffect(...).build()`
**New:** The `statusEffect` method may have been removed from FoodComponent. Effects are now applied via `ConsumableComponent` instead. The food component only handles nutrition/saturation:
```java
// Old
public static final FoodComponent TOMATO = new FoodComponent.Builder()
    .nutrition(3).saturationModifier(0.25f)
    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 200), 0.25f)
    .build();

// New
public static final FoodComponent TOMATO = new FoodComponent.Builder()
    .nutrition(3).saturationModifier(0.25f).build();
// Status effect applied separately via ConsumableComponent on the Item.Settings
```

---

## 20. `ModItemGroups.java`

Minor changes - `Registry.register` for item groups may also need RegistryKey-based approach. Verify at compile time.

---

## 21. `ModBlockEntities.java`

`FabricBlockEntityTypeBuilder` should still work. May need RegistryKey in registration.

---

## 22. `ModScreenHandlers.java`

`ExtendedScreenHandlerType` may have changed constructor. Verify at compile time.

---

## 23. `ModRecipes.java` / `GemPolishingRecipe.java`

- Recipe registration may need RegistryKey-based approach
- `RecipeEntry` identification may use RegistryKey instead of Identifier
- `Ingredient.DISALLOW_EMPTY_CODEC` may be renamed
- `SingleStackRecipeInput` should still work
- `fits()` method may have been removed from Recipe interface

---

## 24. `ModVillagers.java`

- `VillagerProfession` constructor may have changed
- Registration pattern may need updating for RegistryKey-based approach

---

## 25. `LarsonsModClient.java`

- `HandledScreens.register()` may be replaced with a different API
- `EntityRendererRegistry` / `EntityModelLayerRegistry` should still work
- `BlockRenderLayerMap` should still work

---

## 26. `PorcupineModel.java` - SinglePartEntityModel

`SinglePartEntityModel` may be renamed or API methods changed. The `animateMovement` / `updateAnimation` methods should still work.

---

## 27. World Generation Files

- `ModBiomes.java`: `MusicType.createIngameMusic()` may have changed
- `ModConfiguredFeatures.java` / `ModPlacedFeatures.java`: `Registerable` may be renamed to `RegistrationHelper` or similar
- `ModDimensions.java`: `DimensionType` constructor may have new parameters
- `ModSaplingGenerators.java`: `SaplingGenerator` constructor may have changed

---

## 28. Datagen Providers

All datagen classes may need minor updates:
- `ModModelProvider.java` - model generation APIs
- `ModBlockTagProvider.java` / `ModItemTagProvider.java` - tag provider superclass changes
- `ModRecipeProvider.java` - recipe API changes
- `ModLootTableProvider.java` - loot table API changes
- `ModWorldGenerator.java` - datagen entry point

---

## 29. Mixin Files

- `FoliagePlacerTypeInvoker.java` / `TrunkPlacerTypeInvoker.java` - target method signatures may have changed
- `ExampleMixin.java` - verify target still exists

---

## 30. Data Files

- Add `data/larsonsmod/tags/item/ruby_repair.json` with `ModItems.RUBY` for tool/armor repair
- SpawnEgg textures need to be added as data-driven assets (replacing color constructor params)
- Equipment asset definitions may be needed for custom armor textures

---

## Summary of Breaking Change Categories

| Category | Affected Files |
|----------|---------------|
| RegistryKey-based registration | ModItems, ModBlocks, ModEntities, ModBlockEntities, ModRecipes, ModScreenHandlers, ModItemGroups |
| Tool/Armor class removal | ModItems, ModArmorItem, ModArmorMaterials, ModToolMaterial |
| TypedActionResult removal | DiceItem |
| EntityAttributes rename | PorcupineEntity |
| FuelRegistry API change | LarsonsMod |
| NbtCompound Optional returns | GemPolishingStationBlockEntity |
| onStateReplaced timing | GemPolishingStationBlock |
| TradeOfferHelper API | ModCustomTrades |
| FoodComponent changes | ModFoodComponents |
| Rendering API changes | GemPolishingBlockEntityRenderer, GemPolishingScreen |
| ToolMaterial record | ModToolMaterial |
