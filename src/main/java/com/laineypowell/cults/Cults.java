package com.laineypowell.cults;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class Cults implements ModInitializer {
    private static final String MOD_ID = "cults";

    @Override
    public void onInitialize() {
        CultsBlocks.register();
        CultsItems.register();
        CultsBlockEntities.register();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation(MOD_ID), FabricItemGroup.builder()
                .title(Component.translatable("itemGroup." + MOD_ID))
                .icon(CultsItems.BRAIN::getDefaultInstance)
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(CultsItems.BRAIN);
                    output.accept(CultsItems.HEART);
                    output.accept(CultsBlocks.SQUISHER);
                })
                .build());
    }

    public static ResourceLocation resourceLocation(String name) {
        return new ResourceLocation("cults", name);
    }
}
