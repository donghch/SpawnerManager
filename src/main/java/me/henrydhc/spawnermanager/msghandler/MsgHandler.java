package me.henrydhc.spawnermanager.msghandler;

import me.henrydhc.spawnermanager.confighandler.ConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class MsgHandler {

	private static final String[] availableMobs = ConfigHandler.entityMapping.keySet().stream().sorted().toList().toArray(new String[0]);
	private static final String header = "=".repeat(10) + "SpawnerManager" + "=".repeat(10);
	private static final String footer = "=".repeat(34);

	// Language options
	public enum LangType {
		ENGLISH,
		CHINESE_SIMPLIFIED
	}

	private final LangType langType;

	public MsgHandler(LangType langType) {
		this.langType = langType;
	}

	/* Public Methods */

	public static void showHeader(Player player) {
		player.sendMessage(header);
	}

	public static void showFooter(Player player) {
		player .sendMessage(footer);
	}

	public static void showAvailableMobs(Player player) {
		StringBuilder builder = new StringBuilder()
				.append(ChatColor.GOLD)
				.append("可用生物列表: ")
				.append(ChatColor.GRAY);
		for (String mob : availableMobs) {
			builder.append(String.format("%s ", mob));
		}
		builder.append("\n");
		player.sendMessage(builder.toString());
	}

	public static void showGeneralUsageMsg(Player player) {
		String result = ChatColor.GOLD +
				"使用方法" +
				": " +
				ChatColor.WHITE +
				"/sm <set|reload>";
		player.sendMessage(result);
	}

	public static void showSetUsageMsg(Player player) {
		String result = ChatColor.GOLD +
				"使用方法" +
				": " +
				ChatColor.WHITE +
				"/sm set <动物名字> <true|false>\n";
		player.sendMessage(result);
	}

	/* Helper Methods */

	private void loadLang() {

	}



}
