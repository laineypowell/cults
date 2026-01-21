package com.laineypowell.cults.block;

import com.laineypowell.cults.Cults;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class Flesh extends Block {
    public Flesh(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevel.isEmptyBlock(blockPos.below()) && randomSource.nextInt(5) == 0) {
            serverLevel.scheduleTick(blockPos, this, 5);
        }

    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        FallingBlockEntity.fall(serverLevel, blockPos, blockState);

        for (var player : PlayerLookup.tracking(serverLevel, blockPos)) {
            ServerPlayNetworking.send(player, Cults.SQUISH, SquisherBlockEntity.blockPosBuf(blockPos));
        }
    }
}
