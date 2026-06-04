package com.zaqito.create_visual_windmill_fixer;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateVisualWindmillFixer.MODID);

    // We declare our custom windmill bearing block.
    // We copy the properties of Create's original Windmill Bearing so it acts exactly the same way to tools/explosions.
    public static final DeferredBlock<Block> VISUAL_WINDMILL_BEARING = BLOCKS.register(
            "visual_windmill_bearing",
            CustomWindmillBearingBlock::new
    );

    // This helper method simply forces the class loading sequence during mod construction
    public static void register() {}
}
