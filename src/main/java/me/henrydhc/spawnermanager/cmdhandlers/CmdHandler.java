package me.henrydhc.spawnermanager.cmdhandlers;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.confighandler.MobConfig;
import me.henrydhc.spawnermanager.lang.LangLoader;
import me.henrydhc.spawnermanager.msghandler.MsgHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CmdHandler implements CommandExecutor {

	private final Plugin plugin;

	public CmdHandler(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
			
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(LangLoader.MSG_PLAYER_ONLY);
			return true;
		}

		if (strings.length == 0) {
			MsgHandler.showGeneralUsageMsg((Player)commandSender);
			return true;
		} else {
			if (strings[0].equalsIgnoreCase("reload")) {
				return reloadConfig(commandSender);
			} else if (strings[0].equalsIgnoreCase("set")) {
				if (strings.length != 3) {
					MsgHandler.showSetUsageMsg((Player)commandSender);
					return true;
				} else {
					return setEggUsage(commandSender, strings);
				}
			} else if (strings[0].equalsIgnoreCase("egglist")) {
				MsgHandler.showAvailableEggs((Player) commandSender);
				return true;
			} else {
				MsgHandler.showGeneralUsageMsg((Player) commandSender);
				return true;
			}
		}
	}

	private boolean reloadConfig(CommandSender sender) {

		if (!sender.hasPermission("spawnermanager.reload")) {
			sender.sendMessage(LangLoader.MSG_NO_PERMISSION_MSG);
			return false;
		}

		ConfigLoader.reloadConfig(plugin);
		LangLoader.loadLang(ConfigLoader.getLang(), Bukkit.getPluginManager().getPlugin(
			"SpawnerManager"
		));
		sender.sendMessage(LangLoader.MSG_RELOAD_COMPLETE);
		return true;

	}

	private boolean setEggUsage(CommandSender sender, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(LangLoader.MSG_PLAYER_ONLY);
			return true;
		}

		if (!sender.hasPermission("spawnermanager.set")) {
			sender.sendMessage(LangLoader.MSG_NO_PERMISSION_MSG);
			return true;
		}

		boolean targetValue;
		if (args[2].equalsIgnoreCase("true")) {
			targetValue = true;
		} else if (args[2].equalsIgnoreCase("false")) {
			targetValue = false;
		} else {
			MsgHandler.showSetUsageMsg((Player)sender);
			return true;
		}

		Material eggMaterial;
		try {
			eggMaterial = Material.valueOf(args[1]);
			if (!ConfigLoader.eggList.contains(eggMaterial)) {
				MsgHandler.showSetUsageMsg((Player) sender);
				return true;
			}
		} catch (Exception e) {
			// prepare for illegal input
			MsgHandler.showSetUsageMsg((Player) sender);
			return true;
		}

		if (targetValue) {
			ConfigLoader.mobConfigMap.putIfAbsent(eggMaterial, 
				new MobConfig(ConfigLoader.getEntity(eggMaterial), 0));
		} else {
			ConfigLoader.mobConfigMap.remove(eggMaterial);
		}

		sender.sendMessage(LangLoader.MSG_MOB_RULE_SETTLED);
		return true;
	}
}
