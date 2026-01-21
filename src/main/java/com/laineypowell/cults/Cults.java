package com.laineypowell.cults;

import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.List;

public final class Cults implements ModInitializer, LevelComponentInitializer {
    private static final String MOD_ID = "cults";

    public static final ResourceLocation SQUISH = resourceLocation("squish");

    @Override
    public void onInitialize() {
        CultsBlocks.register();
        CultsItems.register();
        CultsBlockEntities.register();

        LootTableEvents.MODIFY.register((resourceManager, lootDataManager, resourceLocation, builder, lootTableSource) -> {
            var paths = resourceLocation.getPath().split("/");

            var key = resourceLocation.withPath(paths[paths.length - 1]);
            if (BuiltInRegistries.ENTITY_TYPE.containsKey(key)) {
                var entityType = BuiltInRegistries.ENTITY_TYPE.get(key);

                var brains = List.of(EntityType.CHICKEN, EntityType.COW, EntityType.SHEEP, EntityType.PIG, EntityType.ZOMBIE);
                var hearts = List.of(EntityType.CHICKEN, EntityType.COW, EntityType.SHEEP, EntityType.PIG);

                if (brains.contains(entityType)) {
                    builder.withPool(LootPool.lootPool().with(LootItem.lootTableItem(CultsItems.BRAIN)
                            .setQuality(1)
                            .setWeight(50)
                            .build()));
                }

                if (hearts.contains(entityType)) {
                    builder.withPool(LootPool.lootPool().with(LootItem.lootTableItem(CultsItems.HEART)
                            .setQuality(1)
                            .setWeight(50)
                            .build()));
                }
            }
        });

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, resourceLocation(MOD_ID), FabricItemGroup.builder()
                .title(Component.translatable("itemGroup." + MOD_ID))
                .icon(CultsItems.BRAIN::getDefaultInstance)
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(CultsItems.BRAIN);
                    output.accept(CultsItems.HEART);
                    output.accept(CultsItems.CULTIST_CREED);
                    output.accept(CultsBlocks.FLESH);
                    output.accept(CultsItems.SQUISHER);
                })
                .build());
    }

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry levelComponentFactoryRegistry) {

    }

    public static ResourceLocation resourceLocation(String name) {
        return new ResourceLocation("cults", name);
    }
}
