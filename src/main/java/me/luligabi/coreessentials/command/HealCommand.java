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

public class HealCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();
    String prefix = cfg.getString("healPrefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_HEAL)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_HEAL));
            return true;
        }
        if(args.length == 0) {
            p.setHealth(20.0);
            p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("healSuccess")));
        } else if(args.length == 1) {
            if(!p.hasPermission(Permissions.COMMAND_HEAL_OTHERS)) {
                p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_HEAL_OTHERS));
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target != null) {
                target.setHealth(20.0);
                target.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("healSuccessOtherTarget")
                        .replace("%player%", p.getDisplayName())));
                p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("healSuccessOther")
                        .replace("%target%", target.getDisplayName())));
            } else {
                p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("targetNotOnline")
                        .replace("%target%", args[0])));
            }
        } else if(args.length >= 2) {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("healIncorrectUsage")));
            return true;
        }
        return false;
    }
}

