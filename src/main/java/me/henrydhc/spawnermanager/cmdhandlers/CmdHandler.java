package me.henrydhc.spawnermanager.cmdhandlers;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.lang.LangLoader;
import me.henrydhc.spawnermanager.msghandler.MsgHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CmdHandler implements CommandExecutor {

	private final Plugin plugin;

	public CmdHandler(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
							 @NotNull String s, String[] strings) {

		if (strings.length == 0) {
			MsgHandler.showGeneralUsageMsg((Player)commandSender);
			return true;
		} else {
			if (strings[0].equalsIgnoreCase("reload")) {
				return reloadConfig(commandSender);
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
}
