package com.zaqito.create_windmill_speed_control.mixin;

import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ControlledContraptionEntity.class, remap = false)
public interface ControlledContraptionEntityAccessor {

    @Accessor("angle")
    float getAngleField();

    @Accessor("prevAngle")
    float getPrevAngleField();

    @Accessor("controllerPos")
    BlockPos getControllerPos();
}