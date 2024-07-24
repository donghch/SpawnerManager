package me.henrydhc.spawnermanager.confighandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class ConfigLoader {

	/** This is a list of eggs we build from bukkit api */
	public static final List<Material> eggList = new ArrayList<>();
	/** Stores allowed mobs and their costs. If a mob egg does not exist in this map
	 * then it should not be allowed in the server & vice versa.
	 */
	public static final Map<Material, MobConfig> mobConfigMap = new HashMap<>();
	private static FileConfiguration config;

	public static void loadConfig(Plugin plugin) {

		File configFile = new File("plugins/SpawnerManager/config.yml");
		if (!configFile.isFile()) {
			plugin.saveDefaultConfig();
		}
		config = plugin.getConfig();

		/* Since we can't use Bukkit's api to get mob type, we 
		 * have to build a mapping from mob egg to mob.
		*/
		buildEggList();
		ConfigurationSection mobs = config.getConfigurationSection("mobs");
		for (Material egg: eggList) {
			String mobName = getEntity(egg).name();
			if (mobs.contains(mobName)) {
				mobConfigMap.put(egg, 
				new MobConfig(EntityType.valueOf(mobName), mobs.getDouble(mobName)));
			}
		}

	}

	/**
	 * Get language
	 * @return Language
	 */
	public static String getLang() {
		return config.getString("lang");
	}

	/**
	 * Save config files
	 * @param plugin plugin
	 */
	public static void saveConfig(Plugin plugin) {
		ConfigurationSection mobSection = config.getConfigurationSection("mobs");

		// Put all entries in mobConfigMap into the config file
		for (Map.Entry<Material, MobConfig> configEntry: mobConfigMap.entrySet()) {
			mobSection.set(getEntity(configEntry.getKey()).name(), 
				configEntry.getValue().getEconCost());
		}
		
		try {
			config.save("plugins/SpawnerManager/config.yml");
		} catch (IOException e) {
			plugin.getLogger().severe("Failed to save config");
		}
	}

	/**
	 * Reload config
	 * @param plugin plugin
	 */
	public static void reloadConfig(Plugin plugin) {
		plugin.reloadConfig();
	}

	/**
	 * Get egg's corresponding entity
	 * @param eggMaterial Egg material
	 * @return entity type or null if the input is invalid
	 */
	public static EntityType getEntity(Material eggMaterial) {

		if (!eggList.contains(eggMaterial)) {
			return null;
		}

		EntityType result;

		try {
			result = EntityType.valueOf(eggMaterial.name().split("_SPAWN_EGG")[0]);
		} catch (IllegalArgumentException e) {
			switch (eggMaterial) {
				case MOOSHROOM_SPAWN_EGG:
					result = EntityType.MUSHROOM_COW;
					break;
				case ZOMBIE_PIGMAN_SPAWN_EGG:
					result = EntityType.PIG_ZOMBIE;
					break;
				default:
					result = EntityType.UNKNOWN;
					break;
			}
		}
		return result;
	}

	/**
	 * Check if economy feature is enabled
	 * @return True if yes, otherwise no
	 */
	public static boolean isEconEnabled() {
		return config.getBoolean("enable-economy");
	}

	/**
	 * Build entity mapping.
	 * This has to be done dynamically as higher versions have more
	 * mobs
	 */
	private static void buildEggList() {
		Class<Material> materialClass = Material.class;
		for (Field field: materialClass.getFields()) {
			String name = field.getName();
			if (name.contains("SPAWN_EGG")) {
				eggList.add(Material.valueOf(name));
			}
		}
	}

}
