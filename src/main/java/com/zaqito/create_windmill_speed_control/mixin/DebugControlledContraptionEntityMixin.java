package com.zaqito.create_windmill_speed_control.mixin;

import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import com.zaqito.create_windmill_speed_control.CreateWindmillSpeedControl;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ControlledContraptionEntity.class, remap = false)
public class DebugControlledContraptionEntityMixin {
    @Inject(method = "setAngle", at = @At("HEAD"))
    private void debugSetAngle(float incomingAngle, CallbackInfo ci) {
        ControlledContraptionEntity self = (ControlledContraptionEntity)(Object)this;

        if (!self.level().isClientSide())
            return;

        float currentAngle = ((ControlledContraptionEntityAccessor)this).getAngleField();
        float diff = Math.abs(incomingAngle - currentAngle);
        if (diff > 5f) {
            CreateWindmillSpeedControl.LOGGER.warn(
                    "[SET_ANGLE] controller={} current={} incoming={} diff={}",
                    ((ControlledContraptionEntityAccessor)this).getControllerPos(),
                    currentAngle,
                    incomingAngle,
                    diff
            );
//            Thread.dumpStack();
        }
    }
}