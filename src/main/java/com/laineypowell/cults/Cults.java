package com.laineypowell.cults;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.level.LevelComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.level.LevelComponentInitializer;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.List;

public final class Cults implements ModInitializer, LevelComponentInitializer {
    private static final String MOD_ID = "cults";

    public static final ResourceLocation SQUISH = resourceLocation("squish");

    public static final Object2IntMap<Item> ITEM_TO_BLOOD = new Object2IntArrayMap<>();

    public static final ComponentKey<CultComponent> CULT = ComponentRegistryV3.INSTANCE.getOrCreate(resourceLocation("cult"), CultComponent.class);

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
                    output.accept(CultsItems.CULTIST_ROBE);
                    output.accept(CultsItems.CULTIST_ROBE_BOTTOMS);
                    output.accept(CultsBlocks.FLESH);
                    output.accept(CultsItems.SQUISHER);
                })
                .build());

        ITEM_TO_BLOOD.put(CultsItems.BRAIN, 120);
        ITEM_TO_BLOOD.put(CultsItems.HEART, 120);
        ITEM_TO_BLOOD.put(CultsItems.FLESH, 120 * 9);

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> {
            var cults = Commands.literal("cults").requires(commandSourceStack -> commandSourceStack.hasPermission(2));

            commandDispatcher.register(cults.then(Commands.literal("create").then(Commands.argument("name", StringArgumentType.string()).then(Commands.argument("title", StringArgumentType.string()).executes(context -> {
                var name = context.getArgument("name", String.class);
                var source = context.getSource();
                if (Cult.invalidateName(name)) {
                    source.sendFailure(Component.translatable("command.cults.create.invalid_name", name));
                    return -1;
                }

                var component = context.getSource().getLevel().getLevelData().getComponent(CULT);
                if (component.has(name)) {
                    source.sendFailure(Component.translatable("command.cults.create.duplicate_name", name));
                    return -1;
                }

                var cult = new Cult();
                cult.name = name;
                cult.displayName = context.getArgument("title", String.class);
                component.add(name, cult);

                source.sendSuccess(() -> Component.translatable("command.cults.create.success", name), true);
                return 1;
            })))));

            commandDispatcher.register(cults.then(Commands.literal("destroy").then(Commands.argument("name", StringArgumentType.string()).executes(context -> {
                var name = context.getArgument("name", String.class);
                var source = context.getSource();

                var component = context.getSource().getLevel().getLevelData().getComponent(CULT);
                if (component.has(name)) {

                    source.sendSuccess(() -> Component.translatable("command.cults.destroy.success", name), true);
                    component.remove(name);
                    return 1;
                }

                source.sendFailure(Component.translatable("command.cults.destroy.unknown", name));
                return -1;
            }))));
            commandDispatcher.register(cults.then(Commands.literal("view")));
        });
    }

    @Override
    public void registerLevelComponentFactories(LevelComponentFactoryRegistry levelComponentFactoryRegistry) {
        levelComponentFactoryRegistry.register(CULT, levelData -> new CultComponent());
    }

    public static ResourceLocation resourceLocation(String name) {
        return new ResourceLocation("cults", name);
    }
}
