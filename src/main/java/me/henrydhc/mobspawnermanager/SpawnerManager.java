package me.henrydhc.mobspawnermanager;

import me.henrydhc.mobspawnermanager.cmdhandlers.ReloadCmdHandler;
import me.henrydhc.mobspawnermanager.confighandler.ConfigHandler;
import me.henrydhc.mobspawnermanager.listeners.SpawnerInteractionListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnerManager extends JavaPlugin {

    ConfigHandler configHandler;

    @Override
    public void onEnable() {
        configHandler = new ConfigHandler();
        getServer().getPluginManager().registerEvents(new SpawnerInteractionListener(configHandler), this);
        getServer().getPluginManager().addPermission(new Permission("mobspawnermanager.use"));
        getCommand("mobspawnermanager").setExecutor(new ReloadCmdHandler(configHandler));
    }

    @Override
    public void onDisable() {

    }

}
