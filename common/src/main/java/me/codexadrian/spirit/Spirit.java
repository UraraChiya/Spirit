package me.codexadrian.spirit;

import me.codexadrian.spirit.network.NetworkHandler;
import me.codexadrian.spirit.platform.fabric.Services;
import me.codexadrian.spirit.registry.SpiritBlocks;
import me.codexadrian.spirit.registry.SpiritItems;
import me.codexadrian.spirit.registry.SpiritMisc;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Spirit {

    public static final String MODID = "spirit";
    public static final String MOD_NAME = "Spirit";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final CreativeModeTab SPIRIT = Services.REGISTRY.registerCreativeTab(
            new ResourceLocation(MODID, "itemgroup"), () -> new ItemStack(SpiritItems.SOUL_CRYSTAL.get()));

    public static final TagKey<EntityType<?>> BLACKLISTED_TAG = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(MODID, "soul_cage_blacklisted"));
    public static final TagKey<EntityType<?>> REVITALIZER_TAG = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation("vitalize", "revitalizer_blacklist"));
    public static final TagKey<EntityType<?>> COLLECT_BLACKLISTED_TAG = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(MODID, "collect_blacklisted"));

    public static final TagKey<EntityType<?>> UNCOMMON = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Spirit.MODID, "rarity/uncommon"));
    public static final TagKey<EntityType<?>> RARE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Spirit.MODID, "rarity/rare"));
    public static final TagKey<EntityType<?>> EPIC = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Spirit.MODID, "rarity/epic"));
    public static final TagKey<EntityType<?>> LEGENDARY = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Spirit.MODID, "rarity/legendary"));

    public static final TagKey<Item> SOUL_STEEL_MAINHAND = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MODID, "soul_steel_mainhand"));
    public static final TagKey<Item> SOUL_FIRE_IMMUNE = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MODID, "soul_fire_immune"));
    public static final TagKey<Item> SOUL_STEEL_OFFHAND = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(MODID, "soul_steel_offhand"));

    public static final int SOUL_COLOR = 0xFF00fffb;

    public static void onInitialize() {
        SpiritMisc.registerAll();
        SpiritBlocks.registerAll();
        SpiritItems.registerAll();
        NetworkHandler.register();
    }
}
