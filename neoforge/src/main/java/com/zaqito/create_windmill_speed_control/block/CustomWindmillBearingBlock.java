package com.zaqito.create_windmill_speed_control.block;

import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlock;
import com.zaqito.create_windmill_speed_control.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class CustomWindmillBearingBlock extends WindmillBearingBlock {
    public CustomWindmillBearingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get().create(pos, state);
    }

    @Override
    public BlockEntityType<? extends WindmillBearingBlockEntity> getBlockEntityType() {
        return ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get();
    }
}
