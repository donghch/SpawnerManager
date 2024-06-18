package me.henrydhc.spawnermanager;

import me.henrydhc.spawnermanager.cmdhandlers.CmdHandler;
import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.hook.HookManager;
import me.henrydhc.spawnermanager.listeners.SpawnerInteractionListener;
import me.henrydhc.spawnermanager.permissionhandler.PermissionHandler;
import me.henrydhc.spawnermanager.tabcompleter.TabHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class SpawnerManager extends JavaPlugin {

    Logger log;

    @Override
    public void onEnable() {
        PermissionHandler.registerPermissions();
        getServer().getPluginManager().registerEvents(new SpawnerInteractionListener(), this);

        // Register commands and tab completion
        getCommand("spawnermanager").setExecutor(new CmdHandler());
        getCommand("spawnermanager").setTabCompleter(new TabHelper());

        // Get logger instance
        log = getLogger();

        // Hook to VaultUnlocked/Vault Service
        econHook();

    }

    @Override
    public void onDisable() {
        try {
            ConfigLoader.saveConfig();
        } catch (IOException e) {
            getLogger().severe("保存配置文件失败!");
        }
    }

    /**
     * Hook to economy dependencies
     */
    private void econHook() {
        if (HookManager.vaultUnlockedHook()) {
            log.info("Hooked to VualtUnlocked!");
        } else if (HookManager.vaultHook()) {
            log.info("Hooked to Vault!");
        } else {
            log.warning("Can't hook to Vault/VaultUnlocked and any " +
                    "economy plugin. Economy feature will not be available.");
        }
    }

}
