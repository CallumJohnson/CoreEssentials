package me.luligabi.coreessentials;

import me.luligabi.coreessentials.command.*;
import me.luligabi.coreessentials.perk.JoinMessagePerk;
import me.luligabi.coreessentials.perk.QuitMessagePerk;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoreEssentials extends JavaPlugin {

    public static CoreEssentials plugin;

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();
        registerPerks();
        saveDefaultConfig();
    }
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
    private void registerCommands() {
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("clock").setExecutor(new ClockCommand());
        getCommand("day").setExecutor(new DayCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("night").setExecutor(new NightCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("rain").setExecutor(new RainCommand());
        getCommand("sun").setExecutor(new SunCommand());
        getCommand("thunder").setExecutor(new ThunderCommand());
    }
    private void registerPerks() {
        getServer().getPluginManager().registerEvents(new JoinMessagePerk(), this);
        getServer().getPluginManager().registerEvents(new QuitMessagePerk(), this);
    }
}