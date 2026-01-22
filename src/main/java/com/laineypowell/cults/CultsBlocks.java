package com.laineypowell.cults;

import com.laineypowell.cults.block.Flesh;
import com.laineypowell.cults.block.Squisher;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public final class CultsBlocks {
    public static final Block FLESH = new Flesh(Properties.of().strength(0.25f, 0.125f).randomTicks().sound(SoundType.SLIME_BLOCK));
    public static final Block SQUISHER = new Squisher(Properties.of());

    public static void register() {
        register("flesh", FLESH);
        register("squisher", SQUISHER);
    }

    public static void register(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, Cults.resourceLocation(name), block);
    }
}
