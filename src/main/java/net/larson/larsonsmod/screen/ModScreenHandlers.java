package net.larson.larsonsmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.larson.larsonsmod.LarsonsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<GemPolishingScreenHandler> GEM_POLISHING_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(LarsonsMod.MOD_ID, "gem_polishing"),
                    new ExtendedScreenHandlerType<>(GemPolishingScreenHandler::new));

    public static void registerScreenHandlers() {
        LarsonsMod.LOGGER.info("Registering Screen Handlers for " + LarsonsMod.MOD_ID);
    }
}
