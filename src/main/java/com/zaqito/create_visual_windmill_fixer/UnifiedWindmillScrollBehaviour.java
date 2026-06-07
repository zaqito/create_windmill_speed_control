package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.minecraft.network.chat.Component;

public class UnifiedWindmillScrollBehaviour {
    // Custom single-row behaviour for the Visual Speed percentage selection
    public static class VisualSpeedSlider extends ScrollValueBehaviour {
        private final CustomWindmillBearingBlockEntity customBE;

        public VisualSpeedSlider(SmartBlockEntity be, ValueBoxTransform slot) {
            super(Component.literal("Visual Speed Factor"), be, slot);
            this.customBE = (CustomWindmillBearingBlockEntity) be;
            // Configures the slider track to slide smoothly between 10% and 100%
            this.between(10, 100);
            this.withFormatter(val -> val + "%");
            super.setValue(100);
        }

        @Override
        public void setValue(int value) {
            super.setValue(value);
            if (customBE != null) {
                customBE.setVisualSpeedPercentage(value);
            }
        }
    }
}