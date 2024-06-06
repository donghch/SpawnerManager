package me.henrydhc.spawnermanager.permissionhandler;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PermissionHandler {

	private static Map<String, PermissionDefault> permissions = Map.of(
			"spawnermanager.use",
			PermissionDefault.FALSE,
			"spawnermanager.admin",
			PermissionDefault.OP,
			"spawnermanager.use.bypass",
			PermissionDefault.OP
	);

	/**
	 * Register plugin permissions
	 */
	public static void registerPermissions() {

		for (Map.Entry<String, PermissionDefault> entry : permissions.entrySet()) {
			Bukkit.getPluginManager().addPermission(new Permission(entry.getKey(), entry.getValue()));
		}

	}

}
