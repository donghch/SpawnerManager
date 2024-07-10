package me.henrydhc.spawnermanager.listeners;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.confighandler.MobConfig;
import me.henrydhc.spawnermanager.hook.HookManager;
import me.henrydhc.spawnermanager.hook.HookType;
import me.henrydhc.spawnermanager.lang.LangLoader;
import net.milkbowl.vault2.economy.Economy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.math.BigDecimal;


public class SpawnerInteractionListener implements Listener {
    
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

        if (!(handItem.getItemMeta() instanceof SpawnEggMeta)) {
            return;
        }

        if (!player.hasPermission("spawnermanager.use")) {
            event.setCancelled(true);
            player.sendMessage(LangLoader.MSG_NO_PERMISSION_MSG);
            return;
        }

        Material material = handItem.getType();
        if (!ConfigLoader.eggList.contains(material)) {
            return;
        }

        MobConfig config = ConfigLoader.mobConfigMap.get(material);

        if (config == null) {
            if (player.hasPermission("spawnermanager.bypass")) {
                return;
            } else {
                event.setCancelled(true);
                player.sendMessage(LangLoader.MSG_NO_PERMISSION_MSG);
                return;
            }
        }

        if (ConfigLoader.isEconEnabled()) {
            boolean deducted = doMoneyDeduction(player, config.getCost());
            if (deducted) {
                return;
            }
        } else {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(LangLoader.MSG_LACK_MONEY);
        return;

    }

    /**
     * Deduct money from player who can place paid mob eggs
     * @param player Target player
     * @return `True` if the transaction is success, otherwise `False`.
     */
    private boolean doMoneyDeduction(Player player, double cost) {
        if (cost <= 0) {
            return true;
        }

        // Use hooked plugin to do transaction
        if (HookManager.getHookType() == HookType.VAULT_UNLOCKED) {
            Economy econ = (Economy) HookManager.getEconProvider();
            return econ.withdraw("SpawnerManager", player.getUniqueId(), BigDecimal.valueOf(cost))
                .transactionSuccess();
        } else if (HookManager.getHookType() == HookType.VAULT) {
            net.milkbowl.vault.economy.Economy econ =
                (net.milkbowl.vault.economy.Economy) HookManager.getEconProvider();
            return econ.withdrawPlayer(player, cost).transactionSuccess();
        } else {
            return false;
        }
    }

}
