package me.henrydhc.spawnermanager.confighandler;

import me.henrydhc.spawnermanager.listeners.SpawnerInteractionListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConfigHandler {

	public static final Map<String, Material> entityMapping = IntStream.range(0, SpawnerInteractionListener.eggList.size())
			.boxed()
			.collect(Collectors.toMap(i -> SpawnerInteractionListener.entityList.get(i),  i -> SpawnerInteractionListener.eggList.get(i)));
	private static final String DATA_FOLDER_PATH = "./plugins/SpawnerManager/";
	private static final String[] CONFIG_FIELDS = {
		"config-version",
		"lang",
		"allowed-mobs"
	};
	private final Plugin parentPlugin;
	private final List<Material> allowedMobs;
	private FileConfiguration config;

	public ConfigHandler() {
		parentPlugin = Bukkit.getPluginManager().getPlugin("SpawnerManager");
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
	 * Set whether the egg is allowed or not
	 * @param egg Target mob egg type
	 * @param isAllowed Whether allow that egg or not
	 * @return True on success, False on failure.
	 */
	public boolean setValue(String egg, boolean isAllowed) {
		if (!entityMapping.containsKey(egg.toLowerCase())) {
			return false;
		} else {
			Material eggType = entityMapping.get(egg);
			if (allowedMobs.contains(eggType) && !isAllowed) {
				allowedMobs.remove(eggType);
			} else if (!allowedMobs.contains(eggType) && isAllowed) {
				allowedMobs.add(eggType);
			}
		}
		return true;
	}

	/**
	 * Reload plugin configuration
	 */
	public void reload() {
		checkConfig();
		loadAllowanceData();
	}

	public void saveConfig() throws IOException {
		ConfigurationSection allowedMobsSection = config.getConfigurationSection("allowed-mobs");

		for (Map.Entry<String, Material> entry: entityMapping.entrySet()) {
			allowedMobsSection.set(entry.getKey(), allowedMobs.contains(entry.getValue()));
		}

		config.save(DATA_FOLDER_PATH + "config.yml");
	}

	/**
	 * Check if config file exists or has valid fields. If it is
	 * missing or broken then create a new config file.
	 */
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

	/**
	 * Load config from config file.
	 */
	private void loadAllowanceData() {

		if (!config.contains("allowed-mobs")) {
			parentPlugin.saveDefaultConfig();
			parentPlugin.getLogger().warning("Config file is malformed and it has been set to default value.");
			config = parentPlugin.getConfig();
		}

		allowedMobs.clear();
		for (String mobStr: SpawnerInteractionListener.entityList) {
			if (config.getConfigurationSection("allowed-mobs").contains(mobStr) && config.getConfigurationSection("allowed-mobs").getBoolean(mobStr)) {
				allowedMobs.add(SpawnerInteractionListener.eggList.get(SpawnerInteractionListener.entityList.indexOf(mobStr)));
			}
		}

	}



}
