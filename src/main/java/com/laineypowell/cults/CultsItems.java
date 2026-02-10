package com.laineypowell.cults;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class CultsItems {

    public static void register() {
    }

    public static void register(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, Cults.resourceLocation(name), item);
    }

    public static Item blockItem(Block block) {
        return new BlockItem(block, new Item.Properties());
    }

}
