package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.AllBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlocks {
    // We declare our custom windmill bearing block.
    // We copy the properties of Create's original Windmill Bearing so it acts exactly the same way to tools/explosions.
    public static final DeferredBlock<Block> VISUAL_WINDMILL_BEARING = CreateVisualWindmillFixer.BLOCKS.register(
            "visual_windmill_bearing",
            () -> new CustomWindmillBearingBlock(BlockBehaviour.Properties.ofLegacyCopy(AllBlocks.WINDMILL_BEARING.get()))
    );

    public static void register() {
        // This helper method simply forces the class loading sequence during mod construction
    }
}
