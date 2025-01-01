package com.mcstaralliance.superpaimoncyclone.block;

import com.mcstaralliance.superpaimoncyclone.SuperPaimonCyclone;
import com.mcstaralliance.superpaimoncyclone.block.entity.PaimonCycloneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaimonCycloneBlock extends Block implements EntityBlock {
    public PaimonCycloneBlock() {
        super(BlockBehaviour.Properties
                .copy(Blocks.STONE)
                // 为矿机设置防爆 3600000.0f
                .strength(3.0f, 3600000.0f)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PaimonCycloneBlockEntity(pos, state);
    }
    // 重写 getTicker 方法，注册 BlockEntityTicker
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        // 检查是否为您自定义的 BlockEntityType
        // return type == SuperPaimonCyclone.PAIMON_CYCLONE_BLOCK_ENTITY.get() ? PaimonCycloneBlockEntity::tick : null;
        return checkType(type, SuperPaimonCyclone.PAIMON_CYCLONE_BLOCK_ENTITY.get(), PaimonCycloneBlockEntity::tick);
    }
    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }


}