package me.luligabi.coreessentials;

import me.luligabi.coreessentials.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoreEssentials extends JavaPlugin {

    public static CoreEssentials plugin;

    @Override
    public void onEnable() {
        plugin = this;
        registerCommands();
        saveDefaultConfig();
    }
    @Override
    public void onDisable() {

    }
    private void registerCommands() {
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("clock").setExecutor(new ClockCommand());
        getCommand("day").setExecutor(new DayCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("heal").setExecutor(new HealCommand());
        //getCommand("night").setExecutor(new NightCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("sun").setExecutor(new SunCommand());
    }
}