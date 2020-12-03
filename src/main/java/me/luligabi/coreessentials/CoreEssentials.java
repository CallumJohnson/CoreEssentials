package me.luligabi.coreessentials;

import me.luligabi.coreessentials.command.*;
import me.luligabi.coreessentials.command.abstraction.CustomCommand;
import me.luligabi.coreessentials.perk.ColoredSignPerk;
import me.luligabi.coreessentials.perk.FullServerLoginPerk;
import me.luligabi.coreessentials.perk.JoinMessagePerk;
import me.luligabi.coreessentials.perk.QuitMessagePerk;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

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
        // getCommand("clearchat").setExecutor(new ClearChatCommand());
        registerCommand(ClearChat.class);
        // getCommand("clock").setExecutor(new ClockCommand());
        registerCommand(Clock.class);
        // getCommand("day").setExecutor(new DayCommand());
        registerCommand(Day.class);
        // getCommand("feed").setExecutor(new FeedCommand());
        registerCommand(Feed.class);
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("night").setExecutor(new NightCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("rain").setExecutor(new RainCommand());
        getCommand("sun").setExecutor(new SunCommand());
        getCommand("thunder").setExecutor(new ThunderCommand());
        getCommand("tpall").setExecutor(new TpAllCommand());
        getCommand("tphere").setExecutor(new TpHereCommand());
    }
    private void registerPerks() {
        getServer().getPluginManager().registerEvents(new JoinMessagePerk(), this);
        getServer().getPluginManager().registerEvents(new QuitMessagePerk(), this);
        getServer().getPluginManager().registerEvents(new FullServerLoginPerk(), this);
        getServer().getPluginManager().registerEvents(new ColoredSignPerk(), this);
    }

    /**
     * Method to register a command to a specific plugin.
     *
     * @param commandClass - CustomCommand class to be registered.
     */
    public void registerCommand(Class<? extends CustomCommand<?>> commandClass) {
        try {
            Constructor<? extends CustomCommand<?>> constructor = commandClass.getDeclaredConstructor(getClass());
            CustomCommand<?> customCommand = constructor.newInstance(this);
            customCommand.register();
        } catch (Exception ex) {
            System.err.println("Failed to Register CustomCommand.");
        }
    }

}