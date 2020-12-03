package me.luligabi.coreessentials.command;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command
    Class Purpose:  Clear Chat.
    
*/

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.command.abstraction.CustomCommand;
import me.luligabi.coreessentials.utils.DefaultFontInfo;
import me.luligabi.coreessentials.utils.MessageUtils;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ClearChat extends CustomCommand<CoreEssentials> {

    /**
     * Constructor to initialise a Command instance.
     *
     * @param plugin - Plugin which is used to register the command.
     */
    public ClearChat(CoreEssentials plugin) {
        super(
                plugin,
                "clearchat",
                MessageUtils.permissionMessage(Permissions.COMMAND_CLEARCHAT),
                Permissions.COMMAND_CLEARCHAT,
                plugin.getConfig().getString("notAPlayer"),
                true
        );
    }

    /**
     * Abstract Method to handle Tab Completion given a sender the passed Arguments.
     *
     * @param sender - CommandSender who sent the TAB Completion request.
     * @param args   - Arguments provided.
     * @return - List of Strings based on given input.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return args.length == 0 ? Collections.singletonList("-s") : Collections.emptyList();
    }

    /**
     * Abstract Method to handle the 'Command' given a sender and the passed Arguments.
     *
     * @param sender - CommandSender object.
     * @param args   - Arguments provided with the command.
     * @return - true = Success, false = Failure.
     */
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        boolean silent = false;
        if (args.length >= 1) {
            silent = args[0].equalsIgnoreCase("-s");
        }
        String message = plugin.getConfig()
                .getString(silent ? "clearChatSuccessSilent" : "clearChatSuccess");
        if (message != null) {
            message = message.replaceAll("%player%", sender.getName());
            String finalMessage = ChatColor.translateAlternateColorCodes('&', message);
            Bukkit.getOnlinePlayers().forEach(player -> {
                for (int i = 0; i < 100; i++) {
                    player.sendMessage("");
                }
                sendCenteredMessage(player, finalMessage);
                player.sendMessage("");
            });
            return true;
        } else {
            System.out.println("Message hasn't been found!?");
            return false;
        }
    }

    /*
     * Originally from this Spigot forums resouce by SirSpoodles:
     * https://www.spigotmc.org/threads/free-code-sending-perfectly-centered-chat-message.95872/
     *
     * Edited by: Callum Johnson
     */

    /**
     * Send a Centered Message to a Player.
     *
     * @param player - Player to send the Centered message to.
     * @param message - Message to center & send.
     */
    private void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals("")) {
            player.sendMessage("");
            return;
        }
        message = ChatColor.translateAlternateColorCodes('&', message);
        int messagePxSize = 0, compensated = 0;
        boolean previousCode = false, isBold = false;
        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (compensated < 154 - messagePxSize / 2) {
            do {
                sb.append(" ");
                compensated += DefaultFontInfo.SPACE.getLength() + 1;
            } while (compensated < 154 - messagePxSize / 2);
        }
        player.sendMessage(sb.toString() + message);
    }

}
