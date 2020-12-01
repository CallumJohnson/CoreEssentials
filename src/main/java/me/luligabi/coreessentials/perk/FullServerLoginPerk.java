package me.luligabi.coreessentials.perk;

import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class FullServerLoginPerk implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(!(e.getResult() == PlayerLoginEvent.Result.KICK_FULL)) return;
        if(e.getPlayer().hasPermission(Permissions.PERK_FULL_JOIN)) {
            e.allow();
        }
    }
}
