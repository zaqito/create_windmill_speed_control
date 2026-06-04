package com.zaqito.create_visual_windmill_fixer;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockEntities {
    // We register the data controller for our custom bearing block
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CustomWindmillBearingBlockEntity>> VISUAL_WINDMILL_BEARING_ENTITY =
            CreateVisualWindmillFixer.BLOCK_ENTITIES.register(
                    "visual_windmill_bearing",
                    () -> BlockEntityType.Builder.of(
                            (pos, state) -> new CustomWindmillBearingBlockEntity(ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get(), pos, state),
                            ModBlocks.VISUAL_WINDMILL_BEARING.get()
                    ).build(null)
            );

    public static void register() {
        // Forces class loading sequence
    }
}
