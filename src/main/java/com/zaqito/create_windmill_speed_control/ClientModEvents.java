package com.zaqito.create_windmill_speed_control;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientModEvents {

    // Explicitly invoked via our main mod class initialization phase
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.VISUAL_WINDMILL_BEARING_ENTITY.get(),
                CustomBearingRenderer::new
        );
    }
}