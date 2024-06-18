package me.henrydhc.spawnermanager.tabcompleter;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TabHelper implements TabCompleter {

	private final List<String> subCommands = List.of("moblist", "reload", "set");

	@Override
	public List<String> onTabComplete(@NonNull CommandSender commandSender,
									 @NonNull Command command, @NonNull String s, String[] strings) {

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
					result.addAll(ConfigLoader.entityMapping.keySet());
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
