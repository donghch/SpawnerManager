package me.henrydhc.spawnermanager;

import me.henrydhc.spawnermanager.cmdhandlers.CmdHandler;
import me.henrydhc.spawnermanager.confighandler.ConfigHandler;
import me.henrydhc.spawnermanager.listeners.SpawnerInteractionListener;
import me.henrydhc.spawnermanager.permissionhandler.PermissionHandler;
import me.henrydhc.spawnermanager.tabcompleter.TabHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SpawnerManager extends JavaPlugin {

    ConfigHandler configHandler;

    @Override
    public void onEnable() {
        configHandler = new ConfigHandler();
        PermissionHandler.registerPermissions();
        getServer().getPluginManager().registerEvents(new SpawnerInteractionListener(configHandler), this);
        getCommand("spawnermanager").setExecutor(new CmdHandler(configHandler));
        getCommand("spawnermanager").setTabCompleter(new TabHelper());
    }

    @Override
    public void onDisable() {
        try {
            configHandler.saveConfig();
        } catch (IOException e) {
            getLogger().severe("保存配置文件失败!");
        }
    }

}
