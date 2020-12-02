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

public class TpHereCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();
    String prefix = cfg.getString("tpHerePrefix");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_TPHERE)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_TPHERE));
            return true;
        }
        if(args.length == 0 || args.length >= 2) {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("tpHereIncorrectUsage")));
            return true;
        }
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target != null) {
            target.teleport(p.getLocation());
            p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("tpHereSuccess")
                    .replace("%target%", target.getName())));
            target.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("tpHereSuccessTarget")
                    .replace("%player%", p.getName())));
        } else {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("targetNotOnline")
                    .replace("%target%", args[0])));
        }
        return false;
    }
}
