package com.zaqito.create_windmill_speed_control;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.simibubi.create.AllBlocks.WINDMILL_BEARING;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateWindmillSpeedControl.MODID);

    // can be improved (cf Create REGISTRATE)
    public static final DeferredBlock<Block> VISUAL_WINDMILL_BEARING =
            BLOCKS.register(
                    "visual_windmill_bearing",
                    () -> new CustomWindmillBearingBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.PODZOL)
                                    .noOcclusion()
                                    .strength(1.5F)
                    )
            );

    // This helper method simply forces the class loading sequence during mod construction
    public static void register() {}
}
