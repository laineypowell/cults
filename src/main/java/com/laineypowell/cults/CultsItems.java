package com.laineypowell.cults;

import com.laineypowell.cults.item.CultistRobe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class CultsItems {
    public static final Item BRAIN = new Item(new Item.Properties());
    public static final Item HEART = new Item(new Item.Properties());
    public static final Item CULTIST_CREED = new Item(new Item.Properties());
    public static final Item CULTIST_ROBE = new CultistRobe(new Item.Properties().stacksTo(1));
    public static final Item CULTIST_ROBE_BOTTOMS = new CultistRobe(new Item.Properties().stacksTo(1));
    public static final Item FLESH = blockItem(CultsBlocks.FLESH);
    public static final Item SQUISHER = blockItem(CultsBlocks.SQUISHER);

    public static void register() {
        register("brain", BRAIN);
        register("heart", HEART);
        register("cultist_creed", CULTIST_CREED);
        register("cultist_robe", CULTIST_ROBE);
        register("cultist_robe_bottoms", CULTIST_ROBE_BOTTOMS);
        register("flesh", FLESH);
        register("squisher", SQUISHER);
    }

    public static void register(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, Cults.resourceLocation(name), item);
    }

    public static Item blockItem(Block block) {
        return new BlockItem(block, new Item.Properties());
    }

}
