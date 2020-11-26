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

    FileConfiguration cfg = CoreEssentials
            .plugin.getConfig();
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
                p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("flySuccessEnable")));
            } else {
                p.setAllowFlight(false);
                p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("flySuccessDisable")));
            }
        } else if(args.length == 1) {
            if(!p.hasPermission(Permissions.COMMAND_FLY_OTHERS)) {
                p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_FLY_OTHERS));
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target != null) {
                if(!target.getAllowFlight()) {
                    target.setAllowFlight(true);
                    target.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("flySucessOtherEnableTarget")
                            .replace("%player%", p.getDisplayName())));
                    p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("flySuccessOtherEnable")
                            .replace("%target%", target.getDisplayName())));

                } else {
                    target.setAllowFlight(false);
                    target.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("flySuccessOtherDisableTarget")
                            .replace("%player%", p.getDisplayName())));
                    p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("flySuccessOtherDisable")
                            .replace("%target%", target.getDisplayName())));
                }
            } else {
                p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("targetNotOnline")
                    .replace("%target%", args[0])));
            }
        } else if(args.length >= 2) {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("flyIncorrectUsage")));
            return true;
        }
        return false;
    }
}
