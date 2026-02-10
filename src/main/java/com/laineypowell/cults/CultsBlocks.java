package com.laineypowell.cults;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public final class CultsBlocks {

    public static void register() {
    }

    public static void register(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, Cults.resourceLocation(name), block);
    }
}
