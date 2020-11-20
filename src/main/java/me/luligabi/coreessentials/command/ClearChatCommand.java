package me.luligabi.coreessentials.command;

import me.luligabi.coreessentials.utils.MessageUtils;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_CLOCK)) {
            p.sendMessage("");
            return true;
        }
        for(int i=0;i<100;i++) {
            Bukkit.broadcastMessage(" ");
        }
        MessageUtils.sendCenteredMessage(p, ChatColor.GOLD + "Chat cleared!");
        return false;
    }

}