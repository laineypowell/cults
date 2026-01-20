package com.laineypowell.cults.block;

import com.laineypowell.cults.CultsBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class SquisherBlockEntity extends BlockEntity {
    public SquisherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CultsBlockEntities.SQUISHER, blockPos, blockState);
    }

}
