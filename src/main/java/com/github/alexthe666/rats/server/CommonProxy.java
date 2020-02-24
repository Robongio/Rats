package com.github.alexthe666.rats.server;

import com.github.alexthe666.rats.ConfigHolder;
import com.github.alexthe666.rats.RatConfig;
import com.github.alexthe666.rats.RatsMod;
import com.github.alexthe666.rats.server.entity.EntityRat;
import com.github.alexthe666.rats.server.entity.RatsEntityRegistry;
import com.github.alexthe666.rats.server.inventory.RatsContainerRegistry;
import com.github.alexthe666.rats.server.misc.RatsSoundRegistry;
import com.github.alexthe666.rats.server.recipes.RatsRecipeRegistry;
import com.github.alexthe666.rats.server.world.BiomeRatlantis;
import com.github.alexthe666.rats.server.world.RatsWorldRegistry;
import com.github.alexthe666.rats.server.world.structure.RatlantisStructureRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.lang.reflect.Field;
import java.util.List;

import static com.github.alexthe666.rats.server.world.RatsWorldRegistry.RATLANTIS_DIM;

@Mod.EventBusSubscriber(modid = RatsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Effect> event) {
        event.getRegistry().registerAll(RatsMod.CONFIT_BYALDI_POTION, RatsMod.PLAGUE_POTION);
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        try {
            for (Field f : RatsSoundRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent sound : (SoundEvent[]) obj) {
                        event.getRegistry().register(sound);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerVillagers(RegistryEvent.Register<VillagerProfession> event) {
        // event.getRegistry().register(RatsVillageRegistry.PET_SHOP_OWNER);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        try {
            for (Field f : RatsEntityRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof EntityType) {
                    event.getRegistry().register((EntityType) obj);
                } else if (obj instanceof EntityType[]) {
                    for (EntityType type : (EntityType[]) obj) {
                        event.getRegistry().register(type);

                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        RatsRecipeRegistry.register();
    }


    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.RAT, 0X30333E, 0XDAABA1, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_rat"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.PIED_PIPER, 0XCABC42, 0X3B6063, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_piper"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.RATLANTEAN_SPIRIT, 0XEDBD00, 0XFFE8AF, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_ratlantean_spirit"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.RATLANTEAN_AUTOMATON, 0XE8E4D7, 0X72E955, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_ratlantean_automaton"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.FERAL_RATLANTEAN, 0X30333E, 0XECECEC, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_feral_ratlantean"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.NEO_RATLANTEAN, 0X30333E, 0X00EFEF, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_neo_ratlantean"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.PIRAT, 0X30333E, 0XAF363A, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_pirat"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.PLAGUE_DOCTOR, 0X2A292A, 0X515359, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_plague_doctor"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.BLACK_DEATH, 0X000000, 0X000000, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_black_death"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.PLAGUE_CLOUD, 0X000000, 0X52574D, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_plague_cloud"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.PLAGUE_BEAST, 0X000000, 0XECECEC, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_plague_beast"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.GHOST_PIRAT, 0X54AA55, 0X7BD77D, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_ghost_pirat"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.DUTCHRAT, 0XBBF9BB, 0XD0E2B5, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_dutchrat"));
        event.getRegistry().register(new SpawnEggItem(RatsEntityRegistry.RATFISH, 0X30333E, 0X7EA8BD, new Item.Properties().group(RatsMod.TAB)).setRegistryName("rats:spawn_egg_ratfish"));
        //TODO: Forge needs to update loom code so that these don't crash the game
       // event.getRegistry().register(new BannerPatternItem(RatsRecipeRegistry.RAT_PATTERN, (new Item.Properties()).maxStackSize(1).group(RatsMod.TAB)).setRegistryName("rats:rat_banner_pattern"));
       // event.getRegistry().register(new BannerPatternItem(RatsRecipeRegistry.CHEESE_PATTERN, (new Item.Properties()).maxStackSize(1).group(RatsMod.TAB)).setRegistryName("rats:cheese_banner_pattern"));
       // event.getRegistry().register(new BannerPatternItem(RatsRecipeRegistry.RAT_AND_CROSSBONES_PATTERN, (new Item.Properties()).maxStackSize(1).group(RatsMod.TAB)).setRegistryName("rats:rat_and_crossbones_banner_pattern"));
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        // Rebake the configs when they change
        if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
            RatConfig.bakeClient(config);
        } else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
            RatConfig.bakeServer(config);
        }
    }

    @SubscribeEvent
    public static void registerWorldGenFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(RatsWorldRegistry.RAT_RUINS, RatsWorldRegistry.FLYING_DUTCHRAT, RatsWorldRegistry.RATLANTIS_AQUADUCTS);
    }


    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        try {
            for (Field f : RatsContainerRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof ContainerType) {
                    event.getRegistry().register((ContainerType) obj);
                } else if (obj instanceof ContainerType[]) {
                    for (ContainerType container : (ContainerType[]) obj) {
                        event.getRegistry().register(container);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerSurfaces(final RegistryEvent.Register<SurfaceBuilder<?>> event) {
        // event.getRegistry().register(RatsWorldRegistry.RATLANTIS_SURFACE);
    }

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(RatsWorldRegistry.RATLANTIS_BIOME = new BiomeRatlantis());
        if (RatConfig.spawnRats) {
            for (Biome biome : Biome.BIOMES) {
                if (biome != null && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
                    List<Biome.SpawnListEntry> spawnList = RatConfig.ratsSpawnLikeMonsters ? biome.getSpawns(EntityClassification.MONSTER) : biome.getSpawns(EntityClassification.CREATURE);
                    spawnList.add(new Biome.SpawnListEntry(RatsEntityRegistry.RAT, RatConfig.ratSpawnRate, 1, 3));
                }
            }
        }
        if (RatConfig.spawnPiper) {
            for (Biome biome : Biome.BIOMES) {
                if (biome != null && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.END) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
                    List<Biome.SpawnListEntry> spawnList = biome.getSpawns(EntityClassification.MONSTER);
                    if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MAGICAL) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SPOOKY)) {
                        //3 times as likely to spawn in dark forests
                        spawnList.add(new Biome.SpawnListEntry(RatsEntityRegistry.PIED_PIPER, RatConfig.piperSpawnRate * 3, 1, 1));
                    } else {
                        spawnList.add(new Biome.SpawnListEntry(RatsEntityRegistry.PIED_PIPER, RatConfig.piperSpawnRate, 1, 1));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerDimensionTypes(RegisterDimensionsEvent event) {
        RatsWorldRegistry.RATLANTIS_DIMENSION_TYPE = DimensionManager.registerOrGetDimension(new ResourceLocation("rats:ratlantis"), RATLANTIS_DIM, null, true);

    }

    @SubscribeEvent
    public static void registerModDimensions(final RegistryEvent.Register<ModDimension> event) {
        event.getRegistry().register(RATLANTIS_DIM);
        RatlantisStructureRegistry.register();
    }

    public void preInit() {
    }

    public void init() {
        //RatsWorldRegistry.register();
    }

    public void postInit() {
    }

    public Object getArmorModel(int index) {
        return null;
    }

    public boolean shouldRenderNameplates() {
        return true;
    }

    public void openCheeseStaffGui() {
    }

    public EntityRat getRefrencedRat() {
        return null;
    }

    public void setRefrencedRat(EntityRat rat) {
    }

    public TileEntity getRefrencedTE() {
        return null;
    }

    public void setRefrencedTE(TileEntity te) {
    }

    public void setCheeseStaffContext(BlockPos pos, Direction facing) {
    }

    public void addParticle(String name, double x, double y, double z, double motX, double motY, double motZ) {
    }

    public ItemStack getRefrencedItem() {
        return ItemStack.EMPTY;
    }

    public void setRefrencedItem(ItemStack stack) {
    }

    public World getWorld(){ return null; }

    public void handlePacketAutoCurdlerFluid(long blockPos, FluidStack fluid){}
    public void handlePacketCheeseStaffRat(int entityId, boolean clear){}
    public void handlePacketUpdateTileSlots(long blockPos, CompoundNBT tag){}

    public void setupTEISR(Item.Properties props) {
    }

    public void openRadiusStaffGui() {
    }
}
