package me.henrydhc.spawnermanager.msghandler;

import me.henrydhc.spawnermanager.confighandler.ConfigLoader;
import me.henrydhc.spawnermanager.lang.LangLoader;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class MsgHandler {

	private static final List<String> availableEggs = ConfigLoader.eggList.stream().map(Material::name).collect(Collectors.toList());
	private static final String header = "=".repeat(10) + "SpawnerManager" + "=".repeat(10);
	private static final String footer = "=".repeat(34);

	public MsgHandler() {
	}

	/* Public Methods */

	public static void showHeader(Player player) {
		player.sendMessage(header);
	}

	public static void showFooter(Player player) {
		player .sendMessage(footer);
	}

	public static void showAvailableEggs(Player player) {
		StringBuilder builder = new StringBuilder()
			.append(LangLoader.MSG_AVAILABLE_MOBS)
			.append(": ")
			.append(ChatColor.GRAY);
		for (String mob : availableEggs) {
			builder.append(String.format("%s ", mob));
		}
		builder.append("\n");
		player.sendMessage(builder.toString());
	}

	public static void showGeneralUsageMsg(Player player) {
		String result = ChatColor.GOLD +
				LangLoader.MSG_USAGE +
				": " +
				ChatColor.WHITE +
				"/sm <set|reload>";
		player.sendMessage(result);
	}

	public static void showSetUsageMsg(Player player) {
		String result = ChatColor.GOLD +
				LangLoader.MSG_USAGE +
				": " +
				ChatColor.WHITE +
				"/sm set <name> <true|false>\n";
		player.sendMessage(result);
	}


}
