package com.laineypowell.cults;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.ladysnake.cca.api.v3.level.LevelComponentInitializer;

public final class Cults implements ModInitializer, LevelComponentInitializer {
    private static final String MOD_ID = "cults";

    @Override
    public void onInitialize() {
        CultsBlocks.register();
        CultsItems.register();
    }

    @Override
    public void registerLevelComponentFactories(org.ladysnake.cca.api.v3.level.LevelComponentFactoryRegistry levelComponentFactoryRegistry) {
    }

    public static ResourceLocation resourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath("cults", name);
    }
}
