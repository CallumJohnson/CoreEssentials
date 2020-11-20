package me.luligabi.coreessentials.command;

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.utils.MessageUtils;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ClearChatCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_CLEARCHAT)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_CLEARCHAT));
            return true;
        }
        String silentMessage = args.length == 1 && args[0].equalsIgnoreCase("-s") ?
                cfg.getString("clearChatSuccessSilent")
                : cfg.getString("clearChatSuccess").replace("%player%", p.getDisplayName());
        for (Player all : Bukkit.getOnlinePlayers()) {
            for(int i = 0;i<100;i++) {
                Bukkit.broadcastMessage("");
            }
            MessageUtils.sendCenteredMessage(all, ChatColor.translateAlternateColorCodes('&', silentMessage));
            Bukkit.broadcastMessage("");
        }
        return false;
    }

}