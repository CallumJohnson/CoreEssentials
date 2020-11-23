package me.luligabi.coreessentials;

import me.luligabi.coreessentials.command.ClearChatCommand;
import me.luligabi.coreessentials.command.ClockCommand;
import me.luligabi.coreessentials.command.FlyCommand;
import me.luligabi.coreessentials.command.HealCommand;
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
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("heal").setExecutor(new HealCommand());
    }
}
