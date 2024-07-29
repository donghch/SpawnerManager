package me.henrydhc.spawnermanager.confighandler;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.henrydhc.spawnermanager.lang.LangLoader;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

public class ConfigLoader {

	/** This is a list of eggs we build from bukkit api */
	public static final List<Material> eggList = new ArrayList<>();
	/** Stores allowed mobs and their costs. If a mob egg does not exist in this map
	 * then it should not be allowed in the server & vice versa.
	 */
	public static final Map<Material, MobConfig> mobConfigMap = new HashMap<>();
	private static final FileConfiguration config = new YamlConfiguration();

	public static void loadConfig(Plugin plugin) {

		File configFile = new File("plugins/SpawnerManager/config.yml");
		if (!configFile.isFile()) {
			plugin.saveDefaultConfig();
		}

		try {
			config.load(configFile);
		} catch (Exception e) {
			// We don't handle that error as it's not our fault
			e.printStackTrace();
		}

		// Load language file
		if (!LangLoader.loadLang(ConfigLoader.getLang(), plugin)) {
			//log.severe("Failed to load language file!. Use English as default language!");
			LangLoader.loadLang("en", plugin);
		}

		/* Since we can't use Bukkit's api to get mob type, we 
		 * have to build a mapping from mob egg to mob.
		*/
		buildEggList();
		ConfigurationSection mobs = config.getConfigurationSection("mobs");
		for (Material egg: eggList) {
			String mobName = getEntity(egg).name();
			if (mobs.contains(mobName)) {
				ConfigurationSection mob = mobs.getConfigurationSection(mobName);
				Double econCost;
				List<String> itemCosts;

				// Load economic & item cost
				econCost = loadEconCost(mob);
				itemCosts = loadItemCost(mob);
				mobConfigMap.put(egg, 
					new MobConfig(EntityType.valueOf(mobName), 
						econCost,
						itemCosts));
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

	}

	/**
	 * Reload config
	 * @param plugin plugin
	 */
	public static void reloadConfig(Plugin plugin) {
		eggList.clear();
		mobConfigMap.clear();
		loadConfig(plugin);
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

	private static Double loadEconCost(ConfigurationSection section) {
		if (!section.contains("econ-cost")) {
			return .0;
		}

		Double result = section.getDouble("econ-cost");
		if (result.isInfinite() || result.isNaN() || result < 0) {
			return .0;
		}
		return result;
	}

	private static List<String> loadItemCost(ConfigurationSection section) {
		return section.getStringList("item-costs");
	}

}
