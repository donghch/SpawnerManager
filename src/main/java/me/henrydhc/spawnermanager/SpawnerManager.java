package me.henrydhc.spawnermanager;

import me.henrydhc.spawnermanager.cmdhandlers.ReloadCmdHandler;
import me.henrydhc.spawnermanager.confighandler.ConfigHandler;
import me.henrydhc.spawnermanager.listeners.SpawnerInteractionListener;
import me.henrydhc.spawnermanager.permissionhandler.PermissionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnerManager extends JavaPlugin {

    ConfigHandler configHandler;

    @Override
    public void onEnable() {
        configHandler = new ConfigHandler();
        PermissionHandler.registerPermissions();
        getServer().getPluginManager().registerEvents(new SpawnerInteractionListener(configHandler), this);
        getCommand("spawnermanager").setExecutor(new ReloadCmdHandler(configHandler));
    }

    @Override
    public void onDisable() {

    }

}
