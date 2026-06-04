package com.zaqito.create_visual_windmill_fixer;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateVisualWindmillFixer.MODID);

    // Automatically maps an Item container wrapper around your physical visual windmill bearing block
    public static final DeferredItem<Item> VISUAL_WINDMILL_BEARING_ITEM = ITEMS.registerItem(
            "visual_windmill_bearing",
            properties -> new BlockItem(ModBlocks.VISUAL_WINDMILL_BEARING.get(), properties)
    );

    // This helper method simply forces the class loading sequence during mod construction
    public static void register() {}
}