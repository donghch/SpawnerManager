package me.henrydhc.spawnermanager.cmdhandlers;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.lang.LangLoader;
import me.henrydhc.spawnermanager.msghandler.MsgHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(ChatColor.RED + "只有玩家可以使用此指令!");
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
					return setMobUsage(commandSender, strings);
				}
			} else if (strings[0].equalsIgnoreCase("moblist")) {
				MsgHandler.showAvailableMobs((Player) commandSender);
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

		ConfigLoader.reload();
		LangLoader.loadLang(ConfigLoader.getLang(), Bukkit.getPluginManager().getPlugin(
			"SpawnerManager"
		));
		sender.sendMessage(LangLoader.MSG_RELOAD_COMPLETE);
		return true;

	}

	private boolean setMobUsage(CommandSender sender, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "只有玩家可以使用这个指令!");
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

		if (!ConfigLoader.setValue(args[1], targetValue)) {
			MsgHandler.showHeader((Player)sender);
			MsgHandler.showAvailableMobs((Player)sender);
			MsgHandler.showFooter((Player)sender);
		}

		sender.sendMessage(LangLoader.MSG_MOB_RULE_SETTLED);
		return true;

	}
}
