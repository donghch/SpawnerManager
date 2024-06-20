package me.henrydhc.spawnermanager.lang;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class LangLoader {

    private static FileConfiguration langFile;
    private static final List<String> supportedLangs = List.of(
        "zh-cn",
        "en"
    );
    private static final List<String> langFields = List.of(
        "header",
        "failedMsg",
        "noPermissionMsg",
        "reloadComplete",
        "lackMoney",
        "mobRuleSettled",
        "playerOnly",
        "usage",
        "availableMobs"
    );
    private static final String JAR_LANG_PATH = "lang/";
    private static final String PLUGIN_LANG_PATH = "plugins/SpawnerManager/lang/";

    // List of languages
    public static String MSG_HEADER;
    public static String MSG_FAILED_MSG;
    public static String MSG_NO_PERMISSION_MSG;
    public static String MSG_RELOAD_COMPLETE;
    public static String MSG_LACK_MONEY;
    public static String MSG_MOB_RULE_SETTLED;
    public static String MSG_PLAYER_ONLY;
    public static String MSG_USAGE;
    public static String MSG_AVAILABLE_MOBS;

    public static boolean loadLang(String type, Plugin plugin) {
        if (supportedLangs.contains(type)) {
            YamlConfiguration langFile = YamlConfiguration.loadConfiguration(
                new File(PLUGIN_LANG_PATH + type + ".yml")
            );
            if (!checkLangFile(langFile)) {
                extractLang(type, plugin);
                langFile = YamlConfiguration.loadConfiguration(new File(PLUGIN_LANG_PATH + type + ".yml"));
            }
            loadMsg(langFile);
        } else {
            // Load english as default language
            return false;
        }
        return true;
    }

    public static void extractLang(String type, Plugin plugin) {
        if (supportedLangs.contains(type)) {
            plugin.saveResource(JAR_LANG_PATH + type + ".yml", true);
        } else {
            plugin.saveResource(JAR_LANG_PATH + "en.yml", true);
        }
    }

    private static boolean checkLangFile(FileConfiguration file) {
        for (String field: langFields) {
            if (!file.contains(field)) {
                return false;
            }
        }
        return true;
    }

    private static void loadMsg(FileConfiguration langFile) {
        MSG_HEADER = ChatColor.translateAlternateColorCodes('&', langFile.getString("header")) + " ";
        MSG_FAILED_MSG = MSG_HEADER + ChatColor.translateAlternateColorCodes('&', langFile.getString("failedMsg"));
        MSG_NO_PERMISSION_MSG = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("noPermissionMsg"));
        MSG_RELOAD_COMPLETE = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("reloadComplete"));
        MSG_LACK_MONEY = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("lackMoney"));
        MSG_MOB_RULE_SETTLED = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("mobRuleSettled"));
        MSG_PLAYER_ONLY = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("playerOnly"));
        MSG_USAGE = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("usage"));
        MSG_AVAILABLE_MOBS = MSG_HEADER + ChatColor.translateAlternateColorCodes('&',
            langFile.getString("availableMobs"));
    }

}
