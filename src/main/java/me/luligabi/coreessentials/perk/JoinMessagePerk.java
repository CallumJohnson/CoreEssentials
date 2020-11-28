package me.luligabi.coreessentials.perk;

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinMessagePerk implements Listener {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission(Permissions.PERK_JOIN_MESSAGE)) {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&',cfg.getString("joinMessagePerk")
                .replace("%player%", p.getPlayerListName())));
        } else {
            e.setJoinMessage(null);
        }
    }
}
