package com.zaqito.create_visual_windmill_fixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

/**
 * Sibling slot to IBearingBlockEntity#getMovementModeSlot()'s
 * DirectionalExtenderScrollOptionSlot.
 *
 * Activates on the same 4 faces (the 2 side faces + top/bottom,
 * i.e. every face whose axis differs from the bearing's FACING axis),
 * but shifts the icon along FACING's normal in the OPPOSITE direction
 * and slightly further, so it lands next to (not on top of) the
 * vanilla rotation-direction icon on each of those faces.
 */
public class SideShiftedValueBoxTransform extends CenteredSideValueBoxTransform {

    public SideShiftedValueBoxTransform() {
        super((state, d) -> {
            Axis axis = d.getAxis();
            Axis bearingAxis = state.getValue(BlockStateProperties.FACING).getAxis();
            return bearingAxis != axis;
        });
    }

    @Override
    public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
        // Base centered point on the active face (from Sided/CenteredSideValueBoxTransform),
        // shifted +3/16 along FACING's normal — opposite side from the vanilla
        // direction icon's -2/16 shift, with extra clearance to avoid hitbox overlap.
        return super.getLocalOffset(level, pos, state)
                .add(Vec3.atLowerCornerOf(state.getValue(BlockStateProperties.FACING).getNormal())
                        .scale(3 / 16f));
    }

    @Override
    public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
        // Mirrors DirectionalExtenderScrollOptionSlot's top/bottom face correction,
        // so the label faces outward correctly on UP/DOWN as well as the side faces.
        if (!getSide().getAxis().isHorizontal())
            TransformStack.of(ms)
                    .rotateYDegrees(AngleHelper.horizontalAngle(state.getValue(BlockStateProperties.FACING)) + 180);
        super.rotate(level, pos, state, ms);
    }
}