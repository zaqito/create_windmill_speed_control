package com.zaqito.create_visual_windmill_fixer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CustomWindmillBearingBlockEntity extends WindmillBearingBlockEntity {
    private int visualSpeedPercentage = 100;
    private float customAngleTracker = 0.0f;

    public CustomWindmillBearingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        // 1. Initialize the vanilla behaviour (adds the Movement Mode icon in the dead centre)
        super.addBehaviours(behaviours);

        // 2. Instantiate our custom slider, shifted 3.0 voxels to the right of the centre
        ValueBoxTransform customSideSlot = new SideShiftedValueBox(getMovementModeSlot(), 3.0f);

        UnifiedWindmillScrollBehaviour.VisualSpeedSlider speedSliderBehaviour =
                new UnifiedWindmillScrollBehaviour.VisualSpeedSlider(this, customSideSlot);

        behaviours.add(speedSliderBehaviour);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get();
    }

    @Override
    public boolean isSource() {
        return true;
    }

    @Override
    public float getGeneratedSpeed() {
        if (!this.running) {
            return 0.0F;
        }

        // Try using the parent's dynamic sail/packet speed first
        float speed = super.getGeneratedSpeed();

        // If Create's engine fails to fetch sail counts due to registry type checks,
        // we step in and force a valid base speed multiplied by the directional sign.
        if (speed == 0.0F) {
            // 16.0F matches Create's standard default wind rotation velocity limit
            return 16.0F * this.getAngleSpeedDirection();
        }

        return speed;
    }

    // This returns the actual calculated capacity setting from the vanilla sail calculations
    private float getSpeedSetting() {
        // Pulls the configuration-defined speed per sail block directly from the base class logic
        return super.getGeneratedSpeed() != 0 ? Math.abs(super.getGeneratedSpeed()) : 16.0f;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // Let Create displays the default Stress/RPM stats first
        boolean hasTooltip = super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        // Append our custom percentage slider row to the overlay text
        tooltip.add(Component.literal(" ")); // spacer line
        tooltip.add(Component.literal("Visual Speed Factor: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(this.visualSpeedPercentage + "%")
                        .withStyle(ChatFormatting.AQUA)));

        return true; // Tells the rendering engine to display the tooltip successfully
    }

    /**
     * Spatial wrapper that takes any existing Create ValueBoxTransform
     * and offsets it horizontally relative to the block's current orientation.
     */
    private static class SideShiftedValueBox extends ValueBoxTransform {
        private final ValueBoxTransform parent;
        private final float horizontalShift; // Positive = right, Negative = left

        public SideShiftedValueBox(ValueBoxTransform parent, float horizontalShift) {
            this.parent = parent;
            this.horizontalShift = horizontalShift / 16f; // Convert voxels to blocks
        }

        @Override
        public Vec3 getLocalOffset(LevelAccessor level, BlockPos pos, BlockState state) {
            // Call the parent delegate to locate the core centre coordinate
            Vec3 baseVec = parent.getLocalOffset(level, pos, state);

            // Resolve the orientation of the bearing
            Direction facing = state.getOptionalValue(com.simibubi.create.content.contraptions.bearing.BearingBlock.FACING)
                    .orElse(Direction.UP);

            // Derive the local orthogonal "right" vector relative to the block's face orientation
            Vec3 right;
            if (facing.getAxis() == Direction.Axis.Y) {
                right = new Vec3(1, 0, 0); // Default East shift if facing straight up/down
            } else {
                net.minecraft.core.Vec3i normal = facing.getClockWise().getOpposite().getNormal();
                right = new Vec3(normal.getX(), normal.getY(), normal.getZ());
            }

            // Return the base position translated horizontally
            return baseVec.add(right.scale(horizontalShift));
        }

        @Override
        public void rotate(LevelAccessor level, BlockPos pos, BlockState state, PoseStack ms) {
            // Fully delegate rotation matrix operations directly to the parent layout engine
            parent.rotate(level, pos, state, ms);
        }
    }

    // --- KINETIC TICK OVERRIDE ENGINE ---

//    @Override
//    public void tick() {
//        float angleBefore = this.angle;
//
//        super.tick();
//
//        // If the bearing is currently running and processing an assembled contraption
//        if (this.running && this.movedContraption != null) {
//            float nativeDelta = this.angle - angleBefore;
//
//            // Normalize angular delta boundary thresholds across 360-degree wrap-arounds
//            if (nativeDelta < -180f) nativeDelta += 360f;
//            if (nativeDelta > 180f) nativeDelta -= 360f;
//
//            // Apply our visual percentage scale down to the real positional change step
//            float modifiedDelta = nativeDelta * getVisualSpeedModifier();
//
//            // Accumulate our scaled track positions safely
//            this.customAngleTracker = (this.customAngleTracker + modifiedDelta) % 360f;
//            if (this.customAngleTracker < 0) this.customAngleTracker += 360f;
//
//            // Hot-swap the underlying angle tracker read by the Flywheel renderer
//            this.angle = this.customAngleTracker;
//
//            // Keep the physical/server-side sub-world entity collision bounds fully synced
//            if (this.movedContraption.getControllingEntity() != null) {
//                this.movedContraption.getControllingEntity().setAngle(this.angle);
//            }
//        }
//    }

    public void setVisualSpeedPercentage(int percentage) {
        this.visualSpeedPercentage = percentage;
        this.setChanged();
        this.sendData();
    }

    public float getVisualSpeedModifier() {
        return this.visualSpeedPercentage / 100.0f;
    }

    @Override
    public void write(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("VisualSpeedPercentage", this.visualSpeedPercentage);
    }

    @Override
    protected void read(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        if (tag.contains("VisualSpeedPercentage")) {
            this.visualSpeedPercentage = tag.getInt("VisualSpeedPercentage");
        }
    }
}