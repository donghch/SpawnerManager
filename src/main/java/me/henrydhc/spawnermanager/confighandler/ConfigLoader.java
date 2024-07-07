package me.henrydhc.spawnermanager.confighandler;

import java.io.File;
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

	public static final List<Material> eggList = new ArrayList<>();
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

	/**
	 * Save config files
	 * @param plugin plugin
	 */
	public static void saveConfig(Plugin plugin) {
		plugin.saveConfig();
	}

	/**
	 * Reload config
	 * @param plugin plugin
	 */
	public static void reloadConfig(Plugin plugin) {
		plugin.reloadConfig();
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

	/**
	 * Get egg's corresponding entity
	 * @param eggMaterial Egg material
	 * @return entity type or null if the input is invalid
	 */
	private static EntityType getEntity(Material eggMaterial) {

		if (!eggList.contains(eggMaterial)) {
			return null;
		}

		return EntityType.valueOf(eggMaterial.name().split("_SPAWN_EGG")[0]);
	}

}
