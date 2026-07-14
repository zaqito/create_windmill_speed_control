package com.zaqito.create_windmill_speed_control.registry;

import com.zaqito.create_windmill_speed_control.Constants;
import com.zaqito.create_windmill_speed_control.blockentity.CustomWindmillBearingBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    // We register the data controller for our custom bearing block
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CustomWindmillBearingBlockEntity>> VISUAL_WINDMILL_BEARING_ENTITY =
            BLOCK_ENTITIES.register(
                    "visual_windmill_bearing",
                    () -> BlockEntityType.Builder.of(
                            (pos, state) -> new CustomWindmillBearingBlockEntity(ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get(), pos, state),
                            ModBlocks.VISUAL_WINDMILL_BEARING.get()
                    ).build(null)
            );

}
