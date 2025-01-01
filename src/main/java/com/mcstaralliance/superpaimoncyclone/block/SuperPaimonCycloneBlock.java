package com.mcstaralliance.superpaimoncyclone.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SuperPaimonCycloneBlock extends Block {
    public SuperPaimonCycloneBlock() {
        super(BlockBehaviour.Properties
                .copy(Blocks.STONE)
                // 为矿机设置防爆 3600000.0f
                .strength(3.0f, 3600000.0f)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
    }
}
