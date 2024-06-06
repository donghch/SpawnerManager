package me.henrydhc.spawnermanager.cmdhandlers;

import me.henrydhc.spawnermanager.confighandler.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCmdHandler implements CommandExecutor {

	private ConfigHandler configHandler;

	public ReloadCmdHandler(ConfigHandler handler) {
		configHandler = handler;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

		switch (strings.length) {
			case 1:
				if (strings[0].equalsIgnoreCase("reload"))
					return reloadConfig(commandSender);
				break;
			default:
				return false;
		}

		return false;

	}

	private boolean reloadConfig(CommandSender sender) {

		if (!sender.hasPermission("spawnermanager.admin")) {
			sender.sendMessage(ChatColor.RED + "你没有权限使用这个指令！");
			return false;
		}

		sender.sendMessage("正在重载配置文件...");
		configHandler.reload();
		sender.sendMessage("配置文件重载完毕");
		return true;

	}

}
