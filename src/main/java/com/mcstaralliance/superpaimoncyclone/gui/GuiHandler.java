package com.mcstaralliance.superpaimoncyclone.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceMenu;

public class GuiHandler {
    public static void openFurnaceGui(Inventory playerInventory) {
        Minecraft.getInstance().setScreen(new FurnaceScreen(new FurnaceMenu(0, playerInventory, playerInventory)));
    }
}
