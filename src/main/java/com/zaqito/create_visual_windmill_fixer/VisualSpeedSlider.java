package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

// Custom single-row behaviour for the Visual Speed percentage selection
public class VisualSpeedSlider extends ScrollValueBehaviour {
    public static final BehaviourType<VisualSpeedSlider> TYPE = new BehaviourType<>();
    private static final String NBT_KEY = "VisualSpeedScrollValue";
    private final CustomWindmillBearingBlockEntity customBE;
    private static final int VISUAL_SPEED_NET_ID = 1;

    public VisualSpeedSlider(SmartBlockEntity be, ValueBoxTransform slot) {
        super(Component.literal("Visual Speed Factor"), be, slot);
        this.customBE = (CustomWindmillBearingBlockEntity) be;
        // Configures the slider track to slide smoothly between 10% and 100%
        this.between(10, 100);
        this.withFormatter(val -> val + "%");
        this.withCallback(val -> {
            if (customBE != null) customBE.setVisualSpeedPercentage(val);
        });
        this.withClientCallback(val -> {
            if (customBE != null) customBE.setVisualSpeedPercentage(val);
        });
        super.setValue(100);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
    }

    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
        nbt.putInt(NBT_KEY, value);
        // deliberately NOT calling super.write — avoids colliding "ScrollValue" key
    }

    @Override
    public void read(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
        if (nbt.contains(NBT_KEY)) {
            value = nbt.getInt(NBT_KEY);
        }
        // deliberately NOT calling super.read
    }

    @Override
    public boolean testHit(Vec3 hit) {
        return super.testHit(hit);
    }

    @Override
    public int netId() {
        return VISUAL_SPEED_NET_ID;
    }
}