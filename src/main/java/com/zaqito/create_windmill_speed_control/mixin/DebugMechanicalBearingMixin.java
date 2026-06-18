package com.zaqito.create_windmill_speed_control.mixin;

import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import com.zaqito.create_windmill_speed_control.CreateWindmillSpeedControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MechanicalBearingBlockEntity.class, remap = false)
public class DebugMechanicalBearingMixin {
    @Inject(method = "applyRotation", at = @At("HEAD"))
    private void debugApplyRotation(CallbackInfo ci) {
        MechanicalBearingBlockEntity self = (MechanicalBearingBlockEntity)(Object)this;

        if (self.getLevel().isClientSide()) {
            CreateWindmillSpeedControl.LOGGER.debug(
                    "[APPLY_ROTATION] pos={} angle={}",
                    self.getBlockPos(),
                    self.getInterpolatedAngle(1.0f)
            );
        }
    }
}