package com.lightning.northstar;

import org.slf4j.Logger;

import com.lightning.northstar.block.NorthstarBlocks;
import com.lightning.northstar.block.NorthstarTechBlocks;
import com.lightning.northstar.block.entity.NorthstarBlockEntityTypes;
import com.lightning.northstar.block.tech.NorthstarPartialModels;
import com.lightning.northstar.client.renderer.armor.BrokenIronSpaceSuitLayerRenderer;
import com.lightning.northstar.client.renderer.armor.BrokenIronSpaceSuitModelRenderer;
import com.lightning.northstar.client.renderer.armor.IronSpaceSuitLayerRenderer;
import com.lightning.northstar.client.renderer.armor.IronSpaceSuitModelRenderer;
import com.lightning.northstar.fluids.NorthstarFluids;
import com.lightning.northstar.item.NorthstarItems;
import com.lightning.northstar.item.NorthstarRecipeTypes;
import com.lightning.northstar.item.armor.BrokenIronSpaceSuitArmorItem;
import com.lightning.northstar.item.armor.IronSpaceSuitArmorItem;
import com.lightning.northstar.particle.NorthstarParticles;
import com.lightning.northstar.sound.NorthstarSounds;
import com.lightning.northstar.world.OxygenStuff;
import com.lightning.northstar.world.TemperatureStuff;
import com.lightning.northstar.world.dimension.NorthstarPlanets;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.GeckoLib;
//import software.bernie.geckolib.renderers.geo.GeoArmorRenderer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Northstar.MOD_ID)
public class Northstar
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "northstar";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);
	public static final NorthstarRegistrate REGISTRATE_CUSTOM = NorthstarRegistrate.create(MOD_ID);
	

	static {
		REGISTRATE.setTooltipModifierFactory(item -> {
			return new ItemDescription.Modifier(item, new Palette(TooltipHelper.styleFromColor(0x9ba4ae), TooltipHelper.styleFromColor(0x80afd2)))
				.andThen(TooltipModifier.mapNull(KineticStats.create(item)));
		});
	}

    public Northstar() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        REGISTRATE.registerEventListeners(modEventBus);
        NorthstarItems.register(modEventBus);
        NorthstarBlocks.register(modEventBus);
        NorthstarTechBlocks.register();
        //      NorthstarContraptionTypes.register();
        //      NorthstarTechBlocks.register(modEventBus);
        NorthstarBlockEntityTypes.register(modEventBus);
        NorthstarRecipeTypes.register(modEventBus);
        NorthstarParticles.register(modEventBus);
        NorthstarSounds.register(modEventBus);
        NorthstarPlanets.register();
        //      NorthstarDimensions.register();
        //      NorthstarEntityTypes.register();
        //      NorthstarEntityTypes.ENTITIES.register(modEventBus);
        NorthstarFluids.register();


        NorthstarPartialModels.init();

        OxygenStuff.register();
        TemperatureStuff.register();

        //      RocketHandler.register();

        GeckoLib.initialize();
        // Register the commonSetup method for modloading
        modEventBus.addListener(Northstar::init);
        modEventBus.addListener(this::commonSetup);
        //      modEventBus.addListener(this::registerSpawnPlacements);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NorthstarClient.onCtorClient(modEventBus, forgeEventBus));


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

        public static void init(final FMLCommonSetupEvent event) {
        NorthstarPackets.registerPackets();

//        event.enqueueWork(() -> {
//            NorthstarAdvancements.register();
//            NorthstarTriggers.register();
//        });
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void addEntityRendererLayers(EntityRenderersEvent.AddLayers event) {
            EntityRenderDispatcher dispatcher = Minecraft.getInstance()
                    .getEntityRenderDispatcher();
            IronSpaceSuitLayerRenderer.registerOnAll(dispatcher);
            BrokenIronSpaceSuitLayerRenderer.registerOnAll(dispatcher);
        }
    	@SuppressWarnings("removal")
    	@SubscribeEvent
    	public static void registerRenderers(final FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(NorthstarBlocks.IRON_GRATE.get(), RenderType.cutout());

    		ItemBlockRenderTypes.setRenderLayer(NorthstarFluids.LIQUID_OXYGEN.get().getSource(), RenderType.translucent());
    		ItemBlockRenderTypes.setRenderLayer(NorthstarFluids.LIQUID_OXYGEN.get(), RenderType.translucent());
    	}
    }
	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(Northstar.MOD_ID, path);
	}
}
