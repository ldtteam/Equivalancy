package com.ldtteam.equivalency.biome.placements;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.stream.Stream;

public class Height0to3 extends SimplePlacement<NoPlacementConfig>
{
    public Height0to3(final Codec<NoPlacementConfig> config)
    {
        super(config);
    }

    @Override
    protected Stream<BlockPos> getPositions(final Random random, final NoPlacementConfig config, final BlockPos pos)
    {
        final boolean shouldSpawn = random.nextInt(1000) == 0;
        if (!shouldSpawn)
            return Stream.empty();

        final BlockPos spawnPos = new BlockPos(
          pos.getX() + random.nextInt(16),
          random.nextInt(4),
          pos.getZ() + random.nextInt(16)
        );

        System.out.println("Determined spawn pos:" + spawnPos.toString());

        return Stream.of(spawnPos);
    }
}
