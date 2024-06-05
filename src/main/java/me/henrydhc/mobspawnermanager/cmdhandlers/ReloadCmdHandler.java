package me.henrydhc.mobspawnermanager.cmdhandlers;

import me.henrydhc.mobspawnermanager.confighandler.ConfigHandler;
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

		configHandler.reload();
		return true;

	}
}
