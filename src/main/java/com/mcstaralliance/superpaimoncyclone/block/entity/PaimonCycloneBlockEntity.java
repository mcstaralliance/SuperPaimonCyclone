package com.mcstaralliance.superpaimoncyclone.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
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

    public static void tick(Level level, BlockPos pos, BlockState state, PaimonCycloneBlockEntity blockEntity) {
        if (level == null || level.isClientSide) return;

        // 检查能量和输入物品
        if (blockEntity.getEnergyStorage().getEnergyStored() >= 100 && blockEntity.isPolishedAndesiteInInput()) {
            blockEntity.setWorkTime(blockEntity.getWorkTime() + 1); // workTime++
            blockEntity.getEnergyStorage().extractEnergy(100, false);

            if (blockEntity.getWorkTime() >= blockEntity.MAX_WORK_TIME) {
                blockEntity.setWorkTime(0);
                blockEntity.processCycle();
            }
        }
        level.scheduleTick(pos, state.getBlock(), 20); // 每 20 ticks 调度一次

    }

    private boolean isPolishedAndesiteInInput() {
        ItemStack stack = inventory.getStackInSlot(0);
        return stack.getItem() == Blocks.POLISHED_ANDESITE.asItem();
    }
    private void processCycle() {
        // 一次性输出所有矿石
        NonNullList<ItemStack> oreItems = NonNullList.create();
        oreItems.add(new ItemStack(Blocks.COAL_ORE));
        oreItems.add(new ItemStack(Blocks.IRON_ORE));
        oreItems.add(new ItemStack(Blocks.GOLD_ORE));
        oreItems.add(new ItemStack(Blocks.DIAMOND_ORE));
        oreItems.add(new ItemStack(Blocks.LAPIS_ORE));
        oreItems.add(new ItemStack(Blocks.REDSTONE_ORE));
        oreItems.add(new ItemStack(Blocks.EMERALD_ORE));

        // 放入输出槽
        for (ItemStack ore : oreItems) {
            inventory.insertItem(1, ore, false);
        }

        // 消耗输入物品
        inventory.extractItem(0, 1, false);
    }
    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public EnergyStorage getEnergyStorage() {
        return energyStorage;
    }

}
