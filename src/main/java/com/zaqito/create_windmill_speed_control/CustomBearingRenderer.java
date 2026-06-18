package com.zaqito.create_windmill_speed_control;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CustomBearingRenderer extends KineticBlockEntityRenderer<CustomWindmillBearingBlockEntity> {

    public CustomBearingRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CustomWindmillBearingBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        // Render the kinetic shaft via the standard path
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        final Direction facing = be.getBlockState().getValue(BlockStateProperties.FACING);
        PartialModel top = be.isWoodenTop() ? AllPartialModels.BEARING_TOP_WOODEN : AllPartialModels.BEARING_TOP;
        SuperByteBuffer superBuffer = CachedBuffers.partial(top, be.getBlockState());

        // Uses our custom angle tracker that handles the custom visual speed modifier!
        float interpolatedAngle = be.getPrevAngle() + (be.getAngle() - be.getPrevAngle()) * partialTicks;
        kineticRotationTransform(superBuffer, be, facing.getAxis(), (float) (interpolatedAngle / 180 * Math.PI), light);

        if (facing.getAxis().isHorizontal()) {
            superBuffer.rotateCentered(AngleHelper.rad(AngleHelper.horizontalAngle(facing.getOpposite())), Direction.UP);
        }
        superBuffer.rotateCentered(AngleHelper.rad(-90 - AngleHelper.verticalAngle(facing)), Direction.EAST);
        superBuffer.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}