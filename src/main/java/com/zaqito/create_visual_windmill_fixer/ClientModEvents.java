package com.zaqito.create_visual_windmill_fixer;

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