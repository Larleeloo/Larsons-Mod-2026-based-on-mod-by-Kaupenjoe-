package net.larson.larsonsmod.item;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent TOMATO = new FoodComponent.Builder()
            .nutrition(3)
            .saturationModifier(0.25f)
            .build();
}
