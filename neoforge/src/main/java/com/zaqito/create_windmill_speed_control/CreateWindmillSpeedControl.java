package com.zaqito.create_windmill_speed_control;


import com.simibubi.create.AllBlocks;
import com.zaqito.create_windmill_speed_control.client.ClientModEvents;
import com.zaqito.create_windmill_speed_control.registry.ModBlockEntities;
import com.zaqito.create_windmill_speed_control.registry.ModBlocks;
import com.zaqito.create_windmill_speed_control.registry.ModItems;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Constants.MOD_ID)
public class CreateWindmillSpeedControl {

    public CreateWindmillSpeedControl(IEventBus eventBus) {
        // Register NeoForge registries
        ModBlocks.BLOCKS.register(eventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(eventBus);
        ModItems.ITEMS.register(eventBus);

        // Common initialization
        CreateWindmillSpeedControlCommon.init();

        // Create integration setup
        eventBus.addListener(this::commonSetup);

        // Register the item to a creative tab
        eventBus.addListener(this::addCreative);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);

        // Force class loading sequence to bind holders safely
        ModBlocks.register();
        ModItems.register();

        // Safely register the custom renderer config directly to the MOD event bus
        if (FMLEnvironment.dist == Dist.CLIENT) {
            eventBus.addListener(ClientModEvents::registerRenderers);
        }

        Constants.LOG.info("Loaded NeoForge mod version.");
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {

            // Create stress integration
            Block customBlock = ModBlocks.VISUAL_WINDMILL_BEARING.get();
            Block vanillaBlock = AllBlocks.WINDMILL_BEARING.get();

            com.simibubi.create.api.stress.BlockStressValues.CAPACITIES.register(
                    customBlock,
                    () -> com.simibubi.create.api.stress.BlockStressValues.getCapacity(vanillaBlock)
            );

            com.simibubi.create.api.stress.BlockStressValues.RPM.register(
                    customBlock,
                    com.simibubi.create.api.stress.BlockStressValues.RPM.get(vanillaBlock)
            );
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().location().getNamespace().equals("create")) {
            event.accept(ModItems.VISUAL_WINDMILL_BEARING_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        Constants.LOG.info("Server side registry validation effective.");
    }
}