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
import sun.plugin2.message.Message;

import java.lang.reflect.InvocationTargetException;

public class PingCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();
    String prefix = cfg.getString("pingPrefix");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission(Permissions.COMMAND_PING)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_PING));
            return true;
        }
        if(args.length == 0) {
            p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("pingSuccess")
                    .replace("%ping%", Integer.toString(getPing(p)))));
        }
        if(args.length == 1) {
            if(!p.hasPermission(Permissions.COMMAND_PING_OTHERS)) {
                p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_PING_OTHERS));
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target != null) {
                p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("healSuccessOther")
                        .replace("%target%", target.getDisplayName()
                        .replace("%ping%", Integer.toString(getPing(target))))));
            } else {
                p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("targetNotOnline")));
            }
        }
        if(args.length >= 2) {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("pingIncorrectUsage")));
        }
        return false;
    }
    public int getPing(Player player) {
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
