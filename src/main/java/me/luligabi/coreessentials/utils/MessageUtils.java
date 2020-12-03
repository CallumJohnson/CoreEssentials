package me.luligabi.coreessentials.utils;

import me.luligabi.coreessentials.CoreEssentials;
import org.bukkit.ChatColor;

/**
 * Edited by: Callum Johnson
 */
public class MessageUtils {

    public static String successMessage(String prefix, String message) {
        return ChatColor.translateAlternateColorCodes('&', CoreEssentials.plugin.getConfig().getString("successMessage").replace("%prefix%", prefix).replace("%message%", message));
    }

    public static String errorMessage(String prefix, String message) {
        return ChatColor.translateAlternateColorCodes('&', CoreEssentials.plugin.getConfig().getString("errorMessage").replace("%prefix%", prefix).replace("%message%", message));
    }

    public static String permissionMessage(String permission) {
        return ChatColor.translateAlternateColorCodes('&', CoreEssentials.plugin.getConfig().getString("permissionMessage").replace("%permission%", permission));
    }

}
