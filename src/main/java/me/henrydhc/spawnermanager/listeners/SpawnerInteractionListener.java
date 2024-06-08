package me.henrydhc.spawnermanager.listeners;

import me.henrydhc.spawnermanager.confighandler.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;


public class SpawnerInteractionListener implements Listener {

    public static List<Material> eggList = Arrays.asList(
            Material.ALLAY_SPAWN_EGG,
            Material.AXOLOTL_SPAWN_EGG,
            Material.BAT_SPAWN_EGG,
            Material.BEE_SPAWN_EGG,
            Material.BLAZE_SPAWN_EGG,
            Material.CAT_SPAWN_EGG,
            Material.CAMEL_SPAWN_EGG,
            Material.CAVE_SPIDER_SPAWN_EGG,
            Material.CHICKEN_SPAWN_EGG,
            Material.COD_SPAWN_EGG,
            Material.COW_SPAWN_EGG,
            Material.CREEPER_SPAWN_EGG,
            Material.DOLPHIN_SPAWN_EGG,
            Material.DONKEY_SPAWN_EGG,
            Material.DROWNED_SPAWN_EGG,
            Material.ELDER_GUARDIAN_SPAWN_EGG,
            Material.ENDER_DRAGON_SPAWN_EGG,
            Material.ENDERMAN_SPAWN_EGG,
            Material.ENDERMITE_SPAWN_EGG,
            Material.EVOKER_SPAWN_EGG,
            Material.FOX_SPAWN_EGG,
            Material.FROG_SPAWN_EGG,
            Material.GHAST_SPAWN_EGG,
            Material.GLOW_SQUID_SPAWN_EGG,
            Material.GOAT_SPAWN_EGG,
            Material.GUARDIAN_SPAWN_EGG,
            Material.HOGLIN_SPAWN_EGG,
            Material.HORSE_SPAWN_EGG,
            Material.HUSK_SPAWN_EGG,
            Material.IRON_GOLEM_SPAWN_EGG,
            Material.LLAMA_SPAWN_EGG,
            Material.MAGMA_CUBE_SPAWN_EGG,
            Material.MOOSHROOM_SPAWN_EGG,
            Material.MULE_SPAWN_EGG,
            Material.OCELOT_SPAWN_EGG,
            Material.PANDA_SPAWN_EGG,
            Material.PARROT_SPAWN_EGG,
            Material.PHANTOM_SPAWN_EGG,
            Material.PIG_SPAWN_EGG,
            Material.PIGLIN_SPAWN_EGG,
            Material.PIGLIN_BRUTE_SPAWN_EGG,
            Material.PILLAGER_SPAWN_EGG,
            Material.POLAR_BEAR_SPAWN_EGG,
            Material.PUFFERFISH_SPAWN_EGG,
            Material.RABBIT_SPAWN_EGG,
            Material.RAVAGER_SPAWN_EGG,
            Material.SALMON_SPAWN_EGG,
            Material.SHEEP_SPAWN_EGG,
            Material.SHULKER_SPAWN_EGG,
            Material.SILVERFISH_SPAWN_EGG,
            Material.SKELETON_SPAWN_EGG,
            Material.SKELETON_HORSE_SPAWN_EGG,
            Material.SLIME_SPAWN_EGG,
            Material.SNIFFER_SPAWN_EGG,
            Material.SNOW_GOLEM_SPAWN_EGG,
            Material.SPIDER_SPAWN_EGG,
            Material.SQUID_SPAWN_EGG,
            Material.STRAY_SPAWN_EGG,
            Material.STRIDER_SPAWN_EGG,
            Material.TADPOLE_SPAWN_EGG,
            Material.TRADER_LLAMA_SPAWN_EGG,
            Material.TROPICAL_FISH_SPAWN_EGG,
            Material.TURTLE_SPAWN_EGG,
            Material.VEX_SPAWN_EGG,
            Material.VILLAGER_SPAWN_EGG,
            Material.VINDICATOR_SPAWN_EGG,
            Material.WANDERING_TRADER_SPAWN_EGG,
            Material.WARDEN_SPAWN_EGG,
            Material.WITCH_SPAWN_EGG,
            Material.WITHER_SPAWN_EGG,
            Material.WITHER_SKELETON_SPAWN_EGG,
            Material.WOLF_SPAWN_EGG,
            Material.ZOGLIN_SPAWN_EGG,
            Material.ZOMBIE_SPAWN_EGG,
            Material.ZOMBIE_HORSE_SPAWN_EGG,
            Material.ZOMBIE_VILLAGER_SPAWN_EGG,
            Material.ZOMBIFIED_PIGLIN_SPAWN_EGG
    );

    public static List<String> entityList = List.of(
            "allay",
            "axolotl",
            "bat",
            "bee",
            "blaze",
            "cat",
            "camel",
            "cave-spider",
            "chicken",
            "cod",
            "cow",
            "creeper",
            "dolphin",
            "donkey",
            "drowned",
            "elder-guardian",
            "ender-dragon",
            "enderman",
            "endermite",
            "evoker",
            "fox",
            "frog",
            "ghast",
            "glow-squid",
            "goat",
            "guardian",
            "hoglins",
            "horse",
            "husk",
            "iron-golem",
            "llama",
            "magma-cube",
            "mooshroom",
            "mule",
            "ocelot",
            "panda",
            "parrot",
            "phantom",
            "pig",
            "piglin",
            "piglin-brute",
            "pillager",
            "polar-bear",
            "pufferfish",
            "rabbit",
            "ravager",
            "salmon",
            "sheep",
            "shulker",
            "silverfish",
            "skeleton",
            "skeleton-horse",
            "slime",
            "sniffer",
            "snow-golem",
            "spider",
            "squid",
            "stray",
            "strider",
            "tadpole",
            "trader-llama",
            "tropical-fish",
            "turtle",
            "vex",
            "villager",
            "vindicator",
            "wandering-trader",
            "warden",
            "witch",
            "wither",
            "wither-skeleton",
            "wolf",
            "zoglin",
            "zombie",
            "zombie-horse",
            "zombie-villager",
            "zombified-piglin"
    );

    private final ConfigHandler configHandler;

    public SpawnerInteractionListener(ConfigHandler handler) {
        configHandler = handler;
    }

    @EventHandler
    public void onPlayerSpawnerInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block;
        ItemStack handItem;

        if (!event.hasItem()) {
            return;
        }
        if (!event.hasBlock()) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        block = event.getClickedBlock();
        handItem = event.getItem();


        if (block.getType() != Material.SPAWNER) {
            return;
        }

        if (!eggList.contains(handItem.getType())) {
            return;
        }

        if (!player.hasPermission("spawnermanager.use")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "你不能对刷怪笼使用这个怪物蛋!");
            return;
        }

        if (configHandler.isAllowedMobEgg(handItem.getType())) {
            return;
        }


        if (player.hasPermission("spawnermanager.bypass")) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "你不能对刷怪笼使用这个怪物蛋!");

    }

}
