package com.laineypowell.cults.block;

import com.laineypowell.cults.Cults;
import com.laineypowell.cults.CultsBlockEntities;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class SquisherBlockEntity extends BlockEntity {
    public SquisherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(CultsBlockEntities.SQUISHER, blockPos, blockState);
    }

    public void squish() {
        if (!level.isClientSide) {
            for (var player : PlayerLookup.tracking(this)) {
                var buf = PacketByteBufs.create();
                buf.writeBlockPos(worldPosition);
                ServerPlayNetworking.send(player, Cults.SQUISH, buf);
            }
        }
    }

}
