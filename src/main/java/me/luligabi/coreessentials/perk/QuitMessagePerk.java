package me.luligabi.coreessentials.perk;

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitMessagePerk implements Listener {

    FileConfiguration cfg = CoreEssentials.plugin.getConfig();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(p.hasPermission(Permissions.PERK_QUIT_MESSAGE)) {
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("quitMessagePerk")
                    .replace("%player%", p.getPlayerListName())));
        } else {
            e.setQuitMessage(null);
        }
    }
}

