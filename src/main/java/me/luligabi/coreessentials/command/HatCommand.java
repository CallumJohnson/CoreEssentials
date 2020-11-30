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

public class HatCommand implements CommandExecutor {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();
    //String prefix = cfg.getString("hatPrefix");
    String prefix = "Hat";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("notAPlayer")));
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission(Permissions.COMMAND_HAT)) {
            p.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_HAT));
        }
        if (p.getItemInHand() == null || !p.getItemInHand().getType().isBlock()) {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("hatInvalidBlock")));
        }
        if (p.getInventory().getHelmet() != null) {
            p.sendMessage(MessageUtils.errorMessage(prefix, cfg.getString("hatHelmet")));
        } else {
        p.getInventory().setHelmet(p.getItemInHand());
        p.getInventory().remove(p.getItemInHand());
        p.sendMessage(MessageUtils.successMessage(prefix, cfg.getString("hatSuccess")));
        }
        return false;
    }
}