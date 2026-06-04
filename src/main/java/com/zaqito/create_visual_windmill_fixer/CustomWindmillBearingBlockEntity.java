package com.zaqito.create_visual_windmill_fixer;

import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CustomWindmillBearingBlockEntity extends WindmillBearingBlockEntity {
    // Our new internal state variable tracking the visual RPM reduction factor
    private int visualSpeedPercentage = 100; // Defaults to full 100% processing speed

    public CustomWindmillBearingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        // 1. Locate and remove Create's default single-direction behaviour loop first
        // so it doesn't double-register a value box position!
        behaviours.removeIf(behaviour -> behaviour instanceof ScrollOptionBehaviour);

        // 2. Run the rest of the standard Create setup sequence
        super.addBehaviours(behaviours);

        // 3. Instantiate our custom unified configuration UI block
        // We reuse Create's default options text strings array and value box transform slot positioning rules
        String[] directionOptions = new String[]{"Clockwise", "Counter-Clockwise"};

        // We pass 'this.sloting' which represents the inherited ValueBoxTransform of the base bearing face
        UnifiedWindmillScrollBehaviour unifiedScroll = new UnifiedWindmillScrollBehaviour(
                this,
                this.getMovementModeSlot() // Inherited ValueBoxTransform from WindmillBearingBlockEntity
        );

        behaviours.add(unifiedScroll);
    }

    public void setVisualSpeedPercentage(int percentage) {
        this.visualSpeedPercentage = percentage;
        this.setChanged(); // Flag Minecraft that the block entity data updated and needs to save to disk
        this.sendData();   // Synchronize this modified state value instantly down to client-side renders
    }

    public float getVisualSpeedModifier() {
        return this.visualSpeedPercentage / 100.0f;
    }

    // --- CRUCIAL: Saving and Loading from NBT ---
    // If we don't override these, your customized percentage resets to 100% every time a player relogs.

    @Override
    public void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("VisualSpeedPercentage", this.visualSpeedPercentage);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("VisualSpeedPercentage")) {
            this.visualSpeedPercentage = tag.getInt("VisualSpeedPercentage");
        }
    }
}
