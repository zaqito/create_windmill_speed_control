package com.zaqito.create_visual_windmill_fixer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateVisualWindmillFixer.MODID)
public class CreateVisualWindmillFixer {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "createvisualwindmillfixer";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public CreateVisualWindmillFixer(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Hook the localized registry buses up to NeoForge
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        // Force class loading sequence to bind holders safely
        ModBlocks.register();
        ModItems.register();

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (createvisualwindmillfixer) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register the mod's ModConfigSpec so that FML can create and load the config file
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Safely register the custom renderer config directly to the MOD event bus
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientModEvents::registerRenderers);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // 1. Grab the custom windmill block instance
            net.minecraft.world.level.block.Block customBlock = ModBlocks.VISUAL_WINDMILL_BEARING.get();
            net.minecraft.world.level.block.Block vanillaBlock = com.simibubi.create.AllBlocks.WINDMILL_BEARING.get();

            // 2. Register the capacity supplier using the exact fields from the vanilla windmill BlockStressValues class
            com.simibubi.create.api.stress.BlockStressValues.CAPACITIES.register(
                    customBlock,
                    () -> com.simibubi.create.api.stress.BlockStressValues.getCapacity(vanillaBlock)
            );

            // 3. Register the RPM display configuration so the tooltips/goggles show correct statistics
            com.simibubi.create.api.stress.BlockStressValues.RPM.register(
                    customBlock,
                    com.simibubi.create.api.stress.BlockStressValues.RPM.get(vanillaBlock)
            );
        });

        LOGGER.info("Visual Windmill Fixer Addon Initialized!");
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
        LOGGER.info("Server side registry validation effective");
    }
}
