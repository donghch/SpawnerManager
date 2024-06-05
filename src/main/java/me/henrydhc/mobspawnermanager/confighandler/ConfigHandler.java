package me.henrydhc.mobspawnermanager.confighandler;

import me.henrydhc.mobspawnermanager.listeners.SpawnerInteractionListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConfigHandler {

	public static Map<String, Material> entityMapping = IntStream.range(0, SpawnerInteractionListener.eggList.size())
			.boxed()
			.collect(Collectors.toMap(i -> SpawnerInteractionListener.entityList.get(i),  i -> SpawnerInteractionListener.eggList.get(i)));
	private static String DATA_FOLDER_PATH = "./plugins/MobSpawnerManager/";
	private static String[] CONFIG_FIELDS = {
		"config-version",
		"enable-permission",
		"lang",
		"allowed-mobs"
	};
	private Plugin parentPlugin;
	private List<Material> allowedMobs;
	private FileConfiguration config;

	public ConfigHandler() {
		parentPlugin = Bukkit.getPluginManager().getPlugin("MobSpawnerManager");
		allowedMobs = new ArrayList<>();
		checkConfig();
		loadAllowanceData();
	}

	/**
	 * Check if the input mob egg is allowed in the server. The input material
	 * should be a mob egg.
	 * @param mobEgg Mob egg material
	 * @return True if the egg is allowed, otherwise false.
	 */
	public boolean isAllowedMobEgg(Material mobEgg) {
		return allowedMobs.contains(mobEgg);
	}

	/**
	 * Reload plugin configuration
	 */
	public void reload() {
		checkConfig();
		loadAllowanceData();
	}

	private void checkConfig() {

		config = YamlConfiguration.loadConfiguration(new File(DATA_FOLDER_PATH + "config.yml"));

		// Use plugin's version number to check if it is malformed
		for (String configKey: CONFIG_FIELDS) {
			if (!config.contains(configKey)) {
				parentPlugin.saveDefaultConfig();
				parentPlugin.getLogger().warning("Config file is malformed and it has been set to default value.");
				config = YamlConfiguration.loadConfiguration(new File(DATA_FOLDER_PATH + "config.yml"));
				return;
			}
		}


	}

	private void loadAllowanceData() {

		if (!config.contains("allowed-mobs")) {
			parentPlugin.saveDefaultConfig();
			parentPlugin.getLogger().warning("Config file is malformed and it has been set to default value.");
			config = parentPlugin.getConfig();
		}

		List<String> allowedMobsStrings = config.getStringList("allowed-mobs");
		for (String mobStr: allowedMobsStrings) {
			if (SpawnerInteractionListener.entityList.contains(mobStr)) {
				allowedMobs.add(SpawnerInteractionListener.eggList.get(SpawnerInteractionListener.entityList.indexOf(mobStr)));
			}
		}

	}



}
