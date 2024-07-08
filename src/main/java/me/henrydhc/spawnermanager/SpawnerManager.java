package me.henrydhc.spawnermanager;

import me.henrydhc.spawnermanager.cmdhandlers.CmdHandler;
import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.hook.HookManager;
import me.henrydhc.spawnermanager.lang.LangLoader;
import me.henrydhc.spawnermanager.listeners.SpawnerInteractionListener;
import me.henrydhc.spawnermanager.permissionhandler.PermissionHandler;
import me.henrydhc.spawnermanager.tabcompleter.TabHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SpawnerManager extends JavaPlugin {

    Logger log;

    @Override
    public void onEnable() {
        PermissionHandler.registerPermissions();
        getServer().getPluginManager().registerEvents(new SpawnerInteractionListener(), this);

        // Register commands and tab completion
        getCommand("spawnermanager").setExecutor(new CmdHandler(this));
        getCommand("spawnermanager").setTabCompleter(new TabHelper());

        // Get logger instance
        log = getLogger();
        log.info("The current server version is " + Bukkit.getBukkitVersion());

        // Load config file
        ConfigLoader.loadConfig(this);

        // Hook to VaultUnlocked/Vault Service
        if (ConfigLoader.isEconEnabled()) {
            econHook();
        }


        // Load language file
        if (!LangLoader.loadLang(ConfigLoader.getLang(), this)) {
            log.severe("Failed to load language file!. Use English as default language!");
            LangLoader.loadLang("en", this);
        }

    }

    @Override
    public void onDisable() {
        ConfigLoader.saveConfig(this);
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
