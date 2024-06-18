package me.henrydhc.spawnermanager.confighandler;

import me.henrydhc.spawnermanager.listeners.SpawnerInteractionListener;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConfigLoader {

	public static final Map<String, Material> entityMapping = IntStream.range(0, SpawnerInteractionListener.eggList.size())
			.boxed()
			.collect(Collectors.toMap(i -> SpawnerInteractionListener.entityList.get(i),  i -> SpawnerInteractionListener.eggList.get(i)));
	private static final String DATA_FOLDER_PATH = "./plugins/SpawnerManager/";
	private static final String[] CONFIG_FIELDS = {
		"config-version",
		"lang",
		"allowed-mobs",
		"enable-mob-cost",
		"mob-cost"
	};
	private static Plugin parentPlugin;
	private static final List<Material> allowedMobs = new ArrayList<>();
	private static Boolean enableMobCost;
	private static final Map<Material, Double> mobCosts = new HashMap<>();
	private static FileConfiguration config;

	/**
	 * Initialize config loader
	 * @param plugin parent plugin
	 */
	public static void init(Plugin plugin) {
		parentPlugin = plugin;
		checkConfig();
		loadAllowanceData();
		loadMobCostData();
		parentPlugin.getLogger().info("Loaded " + allowedMobs.size() + " mob restriction rules");
		parentPlugin.getLogger().info("Loaded " + mobCosts.size() + " mob cost rules.");
	}

	/**
	 * Check if the input mob egg is allowed in the server. The input material
	 * should be a mob egg.
	 * @param mobEgg Mob egg material
	 * @return True if the egg is allowed, otherwise false.
	 */
	public static boolean isAllowedMobEgg(Material mobEgg) {
		return allowedMobs.contains(mobEgg);
	}

	/**
	 * Set whether the egg is allowed or not
	 * @param egg Target mob egg type
	 * @param isAllowed Whether allow that egg or not
	 * @return True on success, False on failure.
	 */
	public static boolean setValue(String egg, boolean isAllowed) {
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
	 * Get cost of placing a mob into the spawner
	 * @param mobEggMaterial Mob egg material
	 * @return Cost of placing a mob into the spawner
	 */
	public static double getMobCost(Material mobEggMaterial) {
		if (!enableMobCost) {
			return 0;
		}
		if (!mobCosts.containsKey(mobEggMaterial)) {
			return 0;
		}
		return mobCosts.get(mobEggMaterial);
	}

	/**
	 * Reload plugin configuration
	 */
	public static void reload() {
		checkConfig();
		loadAllowanceData();
	}

	/**
	 * Save config file
	 * @throws IOException
	 */
	public static void saveConfig() throws IOException {
		ConfigurationSection allowedMobsSection = config.getConfigurationSection("allowed-mobs");

		for (Map.Entry<String, Material> entry: entityMapping.entrySet()) {
			allowedMobsSection.set(entry.getKey(), allowedMobs.contains(entry.getValue()));
		}

		config.save(DATA_FOLDER_PATH + "config.yml");
	}

	/**
	 * Get language setting
	 */
	public static String getLang() {
		return config.getString("lang");
	}

	/**
	 * Check if config file exists or has valid fields. If it is
	 * missing or broken then create a new config file.
	 */
	private static void checkConfig() {

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
	private static void loadAllowanceData() {

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

	/**
	 * Load mob cost data
	 */
	private static void loadMobCostData() {

		if (!config.contains("enable-mob-cost")) {
			resetConfig();
		}
		enableMobCost = config.getBoolean("enable-mob-cost");

		if (enableMobCost) {
			ConfigurationSection section = config.getConfigurationSection("mob-cost");
			for (String mobStr: entityMapping.keySet()) {
				if (section.contains(mobStr)) {
					mobCosts.put(entityMapping.get(mobStr), section.getDouble(mobStr));
				}
			}
		}
	}

	private static void resetConfig() {
		parentPlugin.saveDefaultConfig();
		parentPlugin.getLogger().warning("Config file is malformed and it has been set to default value.");
		config = parentPlugin.getConfig();
	}



}
