package com.laineypowell.cults.block;

import com.laineypowell.cults.CultsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class Squisher extends Block implements EntityBlock {
    public Squisher(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return CultsBlockEntities.SQUISHER.create(blockPos, blockState);
    }
}
