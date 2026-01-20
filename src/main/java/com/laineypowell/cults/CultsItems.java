package com.laineypowell.cults;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class CultsItems {
    public static final Item BRAIN = new Item(new Item.Properties());
    public static final Item HEART = new Item(new Item.Properties());

    public static void register() {
        register("brain", BRAIN);
        register("heart", HEART);
    }

    public static void register(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, Cults.resourceLocation(name), item);
    }
}
