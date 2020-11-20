package me.luligabi.coreessentials;

import me.luligabi.coreessentials.command.ClockCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoreEssentials extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
    }

    @Override
    public void onDisable() {

    }
    private void registerCommands() {
        getCommand("clock").setExecutor(new ClockCommand());
    }
}
