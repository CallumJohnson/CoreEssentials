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

public class ClockCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_CLOCK)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_CLOCK));
            return true;
        }
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', MessageUtils.successMessage(cfg.getString("clockPrefix"), cfg.getString("clockSuccess")
                .replace("%clock%", parseTime(p.getWorld().getTime(), cfg.getBoolean("ampm"))))));
        return false;
    }
    private static String parseTime(long time, boolean ampm) {
        long hours = time / 1000 + 6;
        long minutes = (time % 1000) * 60 / 1000;
        String mm = "0" + minutes;
        mm = mm.substring(mm.length() - 2, mm.length());
        if (!ampm) {
            return hours + ":" + mm;
        } else {
            String ampmsys = "AM";
            if (hours >= 12) {
                hours -= 12;
                ampmsys = "PM";
            }
            if (hours >= 12) {
                hours -= 12;
                ampmsys = "AM";
            }
            if (hours == 0) hours = 12;
            return hours + ":" + mm + ampmsys;
        }
    }
}
