package me.henrydhc.spawnermanager.hook;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class HookManager {

    private static Object econ;
    private static HookType type = HookType.NONE;

    /**
     * Try to hook to Vault
     * @return Economy provider
     */
    public static boolean vaultHook() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
                .getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        type = HookType.VAULT;
        return true;
    }

    /**
     * Try to hook to VaultUnlocked
     * @return Economy provider
     */
    public static boolean vaultUnlockedHook() {
        if (Bukkit.getPluginManager().getPlugin("VaultUnlocked") == null) {
            return false;
        }
        RegisteredServiceProvider<net.milkbowl.vault2.economy.Economy> rsp =
                Bukkit.getServer().getServicesManager().getRegistration(
                        net.milkbowl.vault2.economy.Economy.class
                );
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        type = HookType.VAULT_UNLOCKED;
        return true;
    }

    /**
     * Get hook type
     * @return Hook type
     */
    public static HookType getHookType() {
        return type;
    }

    /**
     * Get economy service provider
     * @return Economy service provider
     */
    public static Object getEconProvider() {
        return econ;
    }
}
