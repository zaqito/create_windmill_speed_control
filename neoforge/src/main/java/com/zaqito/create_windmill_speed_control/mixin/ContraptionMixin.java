package com.zaqito.create_windmill_speed_control.mixin;

import com.simibubi.create.content.contraptions.Contraption;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.zaqito.create_windmill_speed_control.Constants;
import com.zaqito.create_windmill_speed_control.block.CustomWindmillBearingBlock;

import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Contraption.class)
public abstract class ContraptionMixin {

    static {
        Constants.LOG.info("ContraptionMixin loaded.");
    }

    /**
     * Makes Create's Windmill Bearing disassembly logic also recognize our custom Windmill Bearing.
     *
     * In Contraption.moveBlock(), this is the first BlockEntry.has(BlockState) call for AllBlocks.WINDMILL_BEARING.
     */
    @Redirect(
            method = "moveBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 1
            )
    )
    private boolean createWindmillSpeedControl$acceptCustomBearing(
            BlockEntry<?> entry,
            BlockState state
    ) {
        boolean custom = state.getBlock() instanceof CustomWindmillBearingBlock;
        return entry.has(state) || custom;
    }

    /**
     * Makes Create's Windmill Bearing movement logic also recognize our custom Windmill Bearing.
     *
     * In Contraption.moveBlock(), this is the second BlockEntry.has(BlockState) call for AllBlocks.WINDMILL_BEARING.
     */
    @Redirect(
            method = "moveBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z",
                    ordinal = 6
            )
    )
    private boolean createWindmillSpeedControl$acceptCustomBearingMovement(
            BlockEntry<?> entry,
            BlockState state
    ) {
        return entry.has(state)
                || state.getBlock() instanceof CustomWindmillBearingBlock;
    }
}