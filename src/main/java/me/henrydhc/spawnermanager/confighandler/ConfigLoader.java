package me.henrydhc.spawnermanager.confighandler;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class ConfigLoader {

	public static final Map<String, EntityType> entityMapping = new HashMap<>();
	public static final Map<EntityType, MobConfig> mobConfigMap = new HashMap<>();
	private static FileConfiguration config;

	public static void loadConfig(Plugin plugin) {

		File configFile = new File("plugins/SpawnerManager/config.yml");
		if (!configFile.isFile()) {
			plugin.saveDefaultConfig();
		}
		config = plugin.getConfig();

		buildEntityMapping();
		ConfigurationSection mobs = config.getConfigurationSection("mobs");
		for (String entityName: entityMapping.keySet()) {
			if (mobs.contains(entityName)) {
				mobConfigMap.put(entityMapping.get(entityName), 
					new MobConfig(entityMapping.get(entityName), mobs.getDouble(entityName)));
			}
		}

	}

	/**
	 * Get mob config data
	 * @return Mob config if such config exists. Otherwise return null.
	 */
	public static MobConfig getMobConfig(EntityType type) {
		return mobConfigMap.get(type);
	}

	/**
	 * Get language
	 * @return Language
	 */
	public static String getLang() {
		return config.getString("lang");
	}

	public static void saveConfig(Plugin plugin) {
		plugin.saveConfig();
	}

	/**
	 * Build entity mapping.
	 * This has to be done dynamically as higher versions have more
	 * mobs
	 */
	private static void buildEntityMapping() {
		Class<EntityType> entityTypeClass = EntityType.class;
		for (Field field: entityTypeClass.getFields()) {
			entityMapping.put(field.getName(), EntityType.valueOf(field.getName()));
		}
	}

}
