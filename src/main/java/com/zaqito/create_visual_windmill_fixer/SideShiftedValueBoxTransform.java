package com.zaqito.create_visual_windmill_fixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SideShiftedValueBoxTransform extends ValueBoxTransform {

    private final boolean clockwiseFace;

    public SideShiftedValueBoxTransform(boolean clockwiseFace) {
        this.clockwiseFace = clockwiseFace;
    }

    private Direction getTargetFace(BlockState state) {
        Direction facing = state.hasProperty(WindmillBearingBlock.FACING)
                ? state.getValue(WindmillBearingBlock.FACING)
                : Direction.NORTH;
        if (facing.getAxis() == Direction.Axis.Y) {
            return clockwiseFace ? Direction.EAST : Direction.WEST;
        }
        return clockwiseFace ? facing.getClockWise() : facing.getCounterClockWise();
    }

    @Override
    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        Direction face = getTargetFace(state);
        return new Vec3(
                0.5 + face.getStepX() * 0.5,
                0.5,
                0.5 + face.getStepZ() * 0.5
        );
    }

    @Override
    public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
        Direction face = getTargetFace(state);
        // +180 so the label faces outward from the block, matching ValueBoxTransform.Sided convention
        ms.mulPose(com.mojang.math.Axis.YP.rotationDegrees(AngleHelper.horizontalAngle(face) + 180));
    }
}