package com.laineypowell.cults.client;

import com.laineypowell.cults.Cults;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;

public final class CultsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(Cults.SQUISH, (minecraft, clientPacketListener, friendlyByteBuf, packetSender) -> {
            var blockPos = friendlyByteBuf.readBlockPos();
            minecraft.execute(() -> {
                var level = minecraft.level;
                if (level != null) {
                    var x = blockPos.getX() + 0.5f;
                    var y = blockPos.getY();
                    var z = blockPos.getZ() + 0.5f;
                    level.playLocalSound(x, y, z, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f, false);

                    var random = level.random;
                    for (var i = 0; i < 15; i++) {
                        var j = (random.nextFloat() - random.nextFloat()) * 0.75f;
                        var k = (random.nextFloat() - random.nextFloat()) * 0.75f;
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.RED_WOOL.defaultBlockState()), x + j, y + (random.nextFloat() * 0.75f), z + k, 0.0d, 0.5d, 0.0d);
                    }
                }

            });
        });
    }
}
