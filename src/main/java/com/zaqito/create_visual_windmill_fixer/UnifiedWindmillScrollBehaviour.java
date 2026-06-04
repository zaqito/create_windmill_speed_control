package com.zaqito.create_visual_windmill_fixer;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.gui.AllIcons; // Import Create's icon library
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

public class UnifiedWindmillScrollBehaviour extends ScrollOptionBehaviour<UnifiedWindmillScrollBehaviour.WindmillDirection> {

    // Store a reference to our parent block entity so we can inject the speed value
    private final CustomWindmillBearingBlockEntity customBE;

    // 1. Define a modern compliant option Enum wrapping the direction data attributes
    public enum WindmillDirection implements INamedIconOptions {
        CLOCKWISE("Clockwise", AllIcons.I_REFRESH),
        COUNTER_CLOCKWISE("Counter-Clockwise", AllIcons.I_ROTATE_CCW);

        private final String text;
        private final AllIcons icon;

        WindmillDirection(String text, AllIcons icon) {
            this.text = text;
            this.icon = icon;
        }

        @Override
        public String getTranslationKey() { return "create.generic." + this.text.toLowerCase().replace(" ", "_"); }

        @Override
        public AllIcons getIcon() { return icon; }
    }

    public UnifiedWindmillScrollBehaviour(SmartBlockEntity be, ValueBoxTransform slot) {
        // Adjusted constructor blueprint order for modern Create API configurations:
        // Enum class, Text Component Label, Block Entity Brain, Target Interaction Slot
        super(WindmillDirection.class, Component.literal("Windmill Configuration"), be, slot);
        this.customBE = (CustomWindmillBearingBlockEntity) be;
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        // Define the labels for our 2 separate rows in the unified 2D overlay UI
        ImmutableList<Component> rows = ImmutableList.of(
                Component.literal("Rotation Direction"),
                Component.literal("Visual RPM Factor")
        );

        // Define a custom formatter that maps the values nicely in the 2D UI board display rows
        ValueSettingsFormatter formatter = new ValueSettingsFormatter(context -> {
            if (context.row() == 0) {
                // Row 0 maps the direction configuration
                return Component.literal(context.value() == 0 ? "Clockwise" : "Counter-Clockwise");
            }
            // Row 1 maps your custom speed reduction factor percentage
            return Component.literal(context.value() + "%");
        });
        // Arguments: Board Title, Max Value, Step/Tick Increments, Row Labels, Formatter
        // We set the max integer capacity boundary to 100 to easily capture percentages.
        return new ValueSettingsBoard(
                Component.literal("Windmill Configuration"),
                100,
                10,
                rows,
                formatter
        );
    }

    @Override
    public void setValueSettings(Player player, ValueSettingsBehaviour.ValueSettings valueSetting, boolean ctrlDown) {
        // 1. Extract the encoded values sent back from the client's 2D UI panel selection matrix
        int row = valueSetting.row();
        int value = valueSetting.value();

        if (row == 0) {
            // Row 0 is the original rotation direction parameter. Keep value within 0 or 1 bounds.
            int clampedDir = value > 1 ? 1 : (value < 0 ? 0 : value);
            this.setValue(clampedDir);
        } else if (row == 1) {
            // Row 1 is our custom visual speed constraint factor. Clamp between 10% and 100%.
            int clampedSpeed = value > 100 ? 100 : (value < 10 ? 10 : value);
            customBE.setVisualSpeedPercentage(clampedSpeed);
        }

        this.playFeedbackSound(this);
    }

    @Override
    public ValueSettingsBehaviour.ValueSettings getValueSettings() {
        // Return the active state so the UI draws sliders accurately matching current data records.
        // We use row 0 here as the baseline initialization token.
        return new ValueSettingsBehaviour.ValueSettings(0, this.value);
    }
}