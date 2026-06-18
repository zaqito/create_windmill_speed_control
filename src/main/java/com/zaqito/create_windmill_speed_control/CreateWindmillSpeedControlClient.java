package com.zaqito.create_windmill_speed_control;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = CreateWindmillSpeedControl.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = CreateWindmillSpeedControl.MODID, value = Dist.CLIENT)
public class CreateWindmillSpeedControlClient {
    public CreateWindmillSpeedControlClient() {

    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        CreateWindmillSpeedControl.LOGGER.info("HELLO FROM CLIENT SETUP");
        CreateWindmillSpeedControl.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
