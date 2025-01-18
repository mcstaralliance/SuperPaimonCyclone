package com.mcstaralliance.superpaimoncyclone.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.level.block.Blocks;
import com.mcstaralliance.superpaimoncyclone.SuperPaimonCyclone;

public class PaimonCycloneBlockEntity extends BlockEntity {

    private final EnergyStorage energyStorage = new EnergyStorage(10000, 100, 100); // FE 能量存储
    private final ItemStackHandler inventory = new ItemStackHandler(2); // 0: 输入, 1: 输出
    private int workTime = 0; // 当前工作时间
    private final int MAX_WORK_TIME = 20 * 20; // 20秒周期 (20 tick * 20秒)

    public PaimonCycloneBlockEntity(BlockPos pos, BlockState state) {
        super(SuperPaimonCyclone.PAIMON_CYCLONE_BLOCK_ENTITY.get(), pos, state);
    }

    /**
     * 方块实体的 tick 方法，由 Minecraft 自动调用
     */
    public static void tick(Level level, BlockPos pos, BlockState state, PaimonCycloneBlockEntity blockEntity) {
        if (level == null || level.isClientSide) return;

        // 检查能量和输入物品
        if (blockEntity.energyStorage.getEnergyStored() >= 100 && blockEntity.isPolishedAndesiteInInput()) {
            // 仅当能量足够时，增加工作时间
            blockEntity.workTime++;
            blockEntity.energyStorage.extractEnergy(100, false);

            if (blockEntity.workTime >= blockEntity.MAX_WORK_TIME) {
                blockEntity.workTime = 0;
                blockEntity.processCycle();
            }
        } else {
            // 如果能量不足或输入不满足，重置工作时间
            blockEntity.workTime = 0;
        }
    }

    /**
     * 检查输入槽中是否有抛光安山岩
     */
    private boolean isPolishedAndesiteInInput() {
        ItemStack stack = inventory.getStackInSlot(0);
        return stack.getItem() == Blocks.POLISHED_ANDESITE.asItem();
    }

    /**
     * 处理一个完整的生产周期
     */
    private void processCycle() {
        // TODO 修改为配置文件自定义矿石列表
        NonNullList<ItemStack> oreItems = NonNullList.create();
        oreItems.add(new ItemStack(Blocks.COAL_ORE));
        oreItems.add(new ItemStack(Blocks.IRON_ORE));
        oreItems.add(new ItemStack(Blocks.GOLD_ORE));
        oreItems.add(new ItemStack(Blocks.DIAMOND_ORE));
        oreItems.add(new ItemStack(Blocks.LAPIS_ORE));
        oreItems.add(new ItemStack(Blocks.REDSTONE_ORE));
        oreItems.add(new ItemStack(Blocks.EMERALD_ORE));

        // 检查输出槽是否有足够空间
        for (ItemStack ore : oreItems) {
            if (!inventory.insertItem(1, ore, true).isEmpty()) {
                // 如果无法完全插入矿石，终止生产，避免丢失物品
                return;
            }
        }

        // 将矿石实际插入到输出槽
        for (ItemStack ore : oreItems) {
            inventory.insertItem(1, ore, false);
        }

        // 消耗一个输入物品
        inventory.extractItem(0, 1, false);
    }
}