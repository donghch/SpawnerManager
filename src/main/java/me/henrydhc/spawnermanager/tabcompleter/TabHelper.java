package me.henrydhc.spawnermanager.tabcompleter;

import me.henrydhc.spawnermanager.confighandler.ConfigHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabHelper implements TabCompleter {

	private final List<String> subCommands = List.of("moblist", "reload", "set");

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

		List<String> result = new ArrayList<>();

		if (!(commandSender instanceof Player)) {
			return result;
		}

		switch (strings.length) {
			case 1:
				result.addAll(subCommands);
				break;
			case 2:
				if (strings[0].equalsIgnoreCase("set")) {
					result.addAll(ConfigHandler.entityMapping.keySet());
				}
				break;
			case 3:
				if (strings[0].equalsIgnoreCase("set")) {
					result.add("true");
					result.add("false");
				}
				break;
			default:
		}
		return result;

	}
}
