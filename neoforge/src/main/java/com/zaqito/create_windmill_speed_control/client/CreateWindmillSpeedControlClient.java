package com.zaqito.create_windmill_speed_control.client;

import com.zaqito.create_windmill_speed_control.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class CreateWindmillSpeedControlClient {
    public CreateWindmillSpeedControlClient() {}

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        Constants.LOG.info("Initialized client side.");
    }
}
