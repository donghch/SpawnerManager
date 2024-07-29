package me.henrydhc.spawnermanager.tabcompleter;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TabHelper implements TabCompleter {

	private final List<String> subCommands = List.of("reload");

	@Override
	public List<String> onTabComplete(CommandSender commandSender,
									 Command command, String s, String[] strings) {

		List<String> result = new ArrayList<>();

		if (!(commandSender instanceof Player)) {
			return result;
		}

		switch (strings.length) {
			case 1:
				result.addAll(subCommands);
				break;
			default:
		}
		return result;

	}
}
