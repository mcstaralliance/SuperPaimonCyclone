package com.mcstaralliance.superpaimoncyclone;

import com.mcstaralliance.superpaimoncyclone.block.PaimonCycloneBlock;
import com.mcstaralliance.superpaimoncyclone.block.SuperPaimonCycloneBlock;
import com.mcstaralliance.superpaimoncyclone.block.entity.PaimonCycloneBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("superpaimoncyclone")
public class SuperPaimonCyclone {
    public static final String MOD_ID = "superpaimoncyclone";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SuperPaimonCyclone.MOD_ID);
    public static final RegistryObject<Block> PAIMON_CYCLONE_BLOCK = BLOCKS.register("paimon_cyclone_block", PaimonCycloneBlock::new);
    public static final RegistryObject<Block> SUPER_PAIMON_CYCLONE_BLOCK = BLOCKS.register("super_paimon_cyclone_block", SuperPaimonCycloneBlock::new);
    public static final RegistryObject<BlockItem> PAIMON_CYCLONE_BLOCK_ITEM = ITEMS.register("paimon_cyclone_block",
            () -> new BlockItem(PAIMON_CYCLONE_BLOCK.get(), new Item.Properties().rarity(Rarity.RARE))
    );

    public static final RegistryObject<BlockItem> SUPER_PAIMON_CYCLONE_BLOCK_ITEM = ITEMS.register("super_paimon_cyclone_block",
            () -> new BlockItem(SUPER_PAIMON_CYCLONE_BLOCK.get(), new Item.Properties().rarity(Rarity.RARE))
    );
    public static final RegistryObject<CreativeModeTab> PAIMON_TAB = CREATIVE_TABS.register("paimon_cyclone_creative_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + MOD_ID + ".paimon_cyclone_creative_tab"))
            .icon(() -> new ItemStack(PAIMON_CYCLONE_BLOCK_ITEM.get()))
            .displayItems((params, output) -> {
                output.accept(PAIMON_CYCLONE_BLOCK_ITEM.get());
                output.accept(SUPER_PAIMON_CYCLONE_BLOCK_ITEM.get());
            })
            .build()
    );
    public static final RegistryObject<BlockEntityType<PaimonCycloneBlockEntity>> PAIMON_CYCLONE_BLOCK_ENTITY = BLOCK_ENTITIES.register("paimon_cyclone_block_entity",
            () -> BlockEntityType.Builder.of(PaimonCycloneBlockEntity::new, SuperPaimonCyclone.PAIMON_CYCLONE_BLOCK.get()).build(null));
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);

    public static final RegistryObject<MenuType<FurnaceMenu>> FURNACE_MENU = CONTAINERS.register("furnace_menu",
            () -> new MenuType<>(FurnaceMenu::new));
    public SuperPaimonCyclone(FMLJavaModLoadingContext context) {
        BLOCKS.register(context.getModEventBus());
        ITEMS.register(context.getModEventBus());
        CREATIVE_TABS.register(context.getModEventBus());
        BLOCK_ENTITIES.register(context.getModEventBus());
        CONTAINERS.register(context.getModEventBus());
    }
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(PAIMON_CYCLONE_BLOCK_ITEM);

        }
    }
}
