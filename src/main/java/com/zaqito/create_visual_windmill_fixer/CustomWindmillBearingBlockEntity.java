package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component; // Vanilla Minecraft translation components
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CustomWindmillBearingBlockEntity extends WindmillBearingBlockEntity {
    // The scroll control that allows modifying the visual factor via the wrench UI
    public ScrollValueBehaviour visualSpeedScroll;

    public CustomWindmillBearingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        // Run Create's default logic first
        super.addBehaviours(behaviours);

        // Add our custom UI configuration element
        visualSpeedScroll = new ScrollValueBehaviour(
                Component.literal("Visual RPM Speed Factor"),
                this,
                null
//                new VisualWindmillScrollSlot()
        );

        // Define limits: 10% speed scaling up to 100% (default)
        visualSpeedScroll.between(10, 100);
        visualSpeedScroll.setValue(100); // Default to 100% scale
        visualSpeedScroll.withFormatter(value -> value + "%");

        behaviours.add(visualSpeedScroll);
    }

    // A getter utility to easily fetch the multiplier float (e.g., 0.5 for 50%)
    public float getVisualSpeedModifier() {
        if (visualSpeedScroll == null) return 1.0f;
        return visualSpeedScroll.getValue() / 100.0f;
    }
}
