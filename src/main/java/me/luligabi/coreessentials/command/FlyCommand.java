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

public class FlyCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();
    String prefix = cfg.getString("flyPrefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_FLY)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_FLY));
            return true;
        }
        if(args.length == 0) {
            if(!p.getAllowFlight()) {
                p.setAllowFlight(true);
                p.sendMessage(MessageUtils.successMessage(prefix, ""));
            } else {
                p.setAllowFlight(false);
                p.sendMessage(MessageUtils.successMessage(prefix, ""));
            }
        } else if(args.length == 1) {
            if(!p.hasPermission(Permissions.COMMAND_FLY_OTHERS)) {
                p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_FLY_OTHERS));
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target != null) {
                if(!p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    target.sendMessage(MessageUtils.successMessage(prefix, ""));
                    p.sendMessage(MessageUtils.successMessage(prefix, ""));
                } else {
                    p.setAllowFlight(false);
                    target.sendMessage(MessageUtils.successMessage(prefix, ""));
                    p.sendMessage(MessageUtils.successMessage(prefix, ""));
                }
            } else {
                p.sendMessage(MessageUtils.errorMessage(prefix, "not online")); //target not online
            }
        } else if(args.length >= 2) {
            p.sendMessage(MessageUtils.errorMessage(prefix, "too many arguments")); //too many arguments
            return true;
        }
        return false;
    }
    private void checkFlyStatus(Player p) {

    }
}
