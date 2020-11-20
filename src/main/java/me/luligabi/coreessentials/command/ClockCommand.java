package me.luligabi.coreessentials.command;

import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClockCommand implements CommandExecutor {
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
        p.sendMessage(parseTime(p.getWorld().getTime(), false));
        return false;
    }
    public static String parseTime(long time, boolean ampm) {
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
