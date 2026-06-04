package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomWindmillBearingBlock extends WindmillBearingBlock {
    public CustomWindmillBearingBlock(Properties properties) {
        super(properties);
    }

    // Hijack the BlockEntity creation method to return our custom data entity
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get().create(pos, state);
    }

    // Ensure Create's engine assigns the proper structural type hierarchy
    @Override
    public BlockEntityType<? extends WindmillBearingBlockEntity> getBlockEntityType() {
        return ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get();
    }
}
