package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.INamedIconOptions;
import com.simibubi.create.foundation.gui.AllIcons;
import net.minecraft.network.chat.Component;

public class UnifiedWindmillScrollBehaviour {

    // 1. Direct replication of Create's clean rotation direction toggle menu
    public enum WindmillDirection implements INamedIconOptions {
        CLOCKWISE("Clockwise", AllIcons.I_REFRESH),
        COUNTER_CLOCKWISE("Counter-Clockwise", AllIcons.I_ROTATE_CCW);

        private final String text;
        private final AllIcons icon;

        WindmillDirection(String text, AllIcons icon) {
            this.text = text;
            this.icon = icon;
        }

        @Override public AllIcons getIcon() { return this.icon; }
        @Override public String getTranslationKey() { return "create.generic." + this.text.toLowerCase().replace(" ", "_"); }
    }

    // Custom single-row behaviour for the Visual Speed percentage selection
    public static class VisualSpeedSlider extends ScrollValueBehaviour {
        private final CustomWindmillBearingBlockEntity customBE;

        public VisualSpeedSlider(SmartBlockEntity be, ValueBoxTransform slot) {
            super(Component.literal("Visual Speed Factor"), be, slot);
            this.customBE = (CustomWindmillBearingBlockEntity) be;
            // Configures the slider track to slide smoothly between 10% and 100%
            this.between(10, 100);
            this.withFormatter(val -> val + "%");
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