package me.luligabi.coreessentials.perk;

import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class ColoredSignPerk implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if(!e.getPlayer().hasPermission(Permissions.PERK_COLORED_SIGNS)) return;
        for(int i = 0; i < 4; i++) {
            String line = e.getLine(i);
            if(line == null || line.equals("")) return;
            e.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
        }
    }
}
