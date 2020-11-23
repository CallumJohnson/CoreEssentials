package me.luligabi.coreessentials.command;

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.utils.MessageUtils;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {


    FileConfiguration cfg = CoreEssentials.plugin.getConfig();
    String prefix = cfg.getString("dayPrefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_DAY)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_DAY));
        }
        p.getWorld().setTime(1000);
        p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("daySuccess")
                .replace("%world%", p.getWorld().getName())));
        return false;
    }
}
