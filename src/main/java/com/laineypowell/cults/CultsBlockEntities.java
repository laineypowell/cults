package com.laineypowell.cults;

import com.laineypowell.cults.block.SquisherBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class CultsBlockEntities {
    public static final BlockEntityType<SquisherBlockEntity> SQUISHER = FabricBlockEntityTypeBuilder.create(SquisherBlockEntity::new, CultsBlocks.SQUISHER).build();

    public static void register() {
        register("squisher", SQUISHER);
    }

    public static void register(String name, BlockEntityType<?> type) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Cults.resourceLocation(name), type);
    }
}
