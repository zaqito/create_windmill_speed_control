package com.zaqito.create_visual_windmill_fixer;


import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.MethodsReturnNonnullByDefault;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CustomWindmillBearingBlockEntity extends WindmillBearingBlockEntity {
    private int visualSpeedPercentage = 100;
    private float visualAngle = 0.0f;
    private float visualPrevAngle = 0.0f;

    public CustomWindmillBearingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        behaviours.add(new UnifiedWindmillScrollBehaviour.VisualSpeedSlider(
                this,
                new SideShiftedValueBoxTransform(false)
        ));

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

        float speed = super.getGeneratedSpeed(); // Try using the parent's dynamic sail/packet speed first

        if (speed == 0.0F) {
            return 16.0F * this.getAngleSpeedDirection();
        }

        return speed;
    }

    public float getAngle()     { return this.visualAngle; }
    public float getPrevAngle() { return this.visualPrevAngle; }

    // -------------------------------------------------------------------------
    // Visual angle tracking — client side only
    // -------------------------------------------------------------------------
    @Override
    public void tick() {
        float angleBefore = this.angle; // real angle before super increments it
        super.tick();

        if (level != null && level.isClientSide) {
            visualPrevAngle = visualAngle;

            if (running) {
                float realDelta = angularDifference(this.angle, angleBefore);
                if (Math.abs(realDelta) > 100f) {
                    CreateVisualWindmillFixer.LOGGER.warn(
                            "[WRAP] before={} after={} delta={}",
                            angleBefore,
                            this.angle,
                            realDelta
                    );
                }
                visualAngle += realDelta * getVisualSpeedModifier();

                // Contraption uses our visual angle on the client only.
                if (movedContraption != null) {
                    float realContraptionAngle = movedContraption.getAngle(1.0f);
                    float error = Math.abs(angularDifference(realContraptionAngle, visualAngle));

                    if (error > 10.0f) {
                        CreateVisualWindmillFixer.LOGGER.warn(
                                "[ANGLE_DRIFT] visual={} real={} diff={}",
                                visualAngle,
                                realContraptionAngle,
                                error
                        );
                    }

                    movedContraption.setAngle(visualAngle);
                }
            }
            else {
                // Keep visual angle honest when stopped so restart is seamless
                visualAngle = this.angle;
                visualPrevAngle = this.angle;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Visual speed percentage — serialized and sent to client
    // -------------------------------------------------------------------------
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // Let Create displays the default Stress/RPM stats first
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        // Append our custom percentage slider row to the overlay text
        tooltip.add(Component.literal(" ")); // spacer line
        tooltip.add(Component.literal("Visual Speed Factor: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(this.visualSpeedPercentage + "%")
                        .withStyle(ChatFormatting.AQUA)));

        return true; // Tells the rendering engine to display the tooltip successfully
    }

    public void setVisualSpeedPercentage(int percentage) {
        this.visualSpeedPercentage = percentage;
        this.setChanged();
        this.sendData();
    }

    public float getVisualSpeedModifier() {
        return this.visualSpeedPercentage / 100.0f;
    }

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

    // -------------------------------------------------------------------------
    // Maths helper
    // -------------------------------------------------------------------------
    private static float angularDifference(float current, float previous) {
        float delta = current - previous;

        while (delta > 180f)
            delta -= 360f;

        while (delta < -180f)
            delta += 360f;

        return delta;
    }
}

