package me.luligabi.coreessentials.utils;

import me.luligabi.coreessentials.CoreEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static String successMessage(String prefix, String message) {
        return ChatColor.translateAlternateColorCodes('&', CoreEssentials.plugin.getConfig().getString("sucessMessage"))
                .replace("%prefix%", prefix)
                .replace("%message%", message);
    }
    public static String errorMessage(String prefix, String message) {
        return ChatColor.translateAlternateColorCodes('&', CoreEssentials.plugin.getConfig().getString("errorMessage"))
                .replace("%prefix%", prefix)
                .replace("%message%", message);
    }
    public static String permissionMessage(String permission) {
        return ChatColor.translateAlternateColorCodes('&', CoreEssentials.plugin.getConfig().getString("permissionMessage"))
                .replace("%permission%", permission);
    }

    /*
     * Originally from this Spigot forums resouce by SirSpoodles:
     * https://www.spigotmc.org/threads/free-code-sending-perfectly-centered-chat-message.95872/
     */
    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(Player player, String message){
        if(message == null || message.equals("")) player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }else if(previousCode == true){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;
                    continue;
                }else isBold = false;
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }
}
