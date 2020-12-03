package me.luligabi.coreessentials.command.abstraction;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command.abstraction
    Class Purpose:  An Abstract Class which Represents a Single Command.
    
*/

import me.luligabi.coreessentials.command.abstraction.exceptions.CommandRegistrationException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CustomCommand<Plugin extends JavaPlugin> implements TabExecutor {

    private final String commandName, permission, noPerms, playerRequiredMessage;
    private final boolean playerRequired;
    protected final Plugin plugin;

    private final Set<Command> subCommands = new HashSet<>();

    /**
     * Constructor to initialise a Command instance.
     *
     * @param plugin - Plugin which is used to register the command.
     * @param commandName - Name of the Command.
     * @param noPermissionMessage - Permission Message which is sent if the CommandSender fails a Permission-Check.
     * @param permission - Permission to test. Can be 'null'.
     * @param playerRequiredMessage - Message which is sent if the User isn't a Player.
     * @param playerRequired - Is Player Required?
     */
    public CustomCommand(
            Plugin plugin,
            String commandName,
            String noPermissionMessage,
            String permission,
            String playerRequiredMessage,
            boolean playerRequired
    ) {
        this.commandName = commandName;
        this.plugin = plugin;
        this.noPerms = noPermissionMessage;
        this.permission = permission;
        this.playerRequired = playerRequired;
        this.playerRequiredMessage = playerRequiredMessage;
    }

    public String getPermission() {
        return permission;
    }

    public String getCommandName() {
        return commandName;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getPlayerRequiredMessage() {
        return playerRequiredMessage;
    }

    public boolean isPlayerRequired() {
        return playerRequired;
    }

    public String getNoPermissionMessage() {
        return noPerms;
    }

    /**
     * register() is a class-specific method, which uses reflection
     * to collect the methods and assigns them to different
     *
     * @see SubCommand - for method creation.
     *
     * @throws CommandRegistrationException - Failed to Register Command.
     */
    public void register() throws CommandRegistrationException {
        try {
            PluginCommand command = this.getPlugin().getCommand(this.getCommandName());
            if (command == null) {
                throw new InvalidPluginException("Plugin.yml doesn't contain the command '" + getCommandName() + "'!");
            }
            CommandExecutor executor = command.getExecutor();
            if (!executor.equals(this)) {
                command.setExecutor(this);
            }
            TabCompleter completer = command.getTabCompleter();
            if (completer == null || !completer.equals(this)) {
                command.setTabCompleter(this);
            }
            if (this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) this, getPlugin());
            }
            this.registerSubCommands();
        } catch (Exception exception) {
            if (exception.getMessage() != null) System.out.println(exception.getMessage());
            throw new CommandRegistrationException("Failed to register the command '" + getCommandName() + "'!");
        }
    }

    /**
     * registerSubCommands() creates entries in Map's to create a completely modular
     * Command API.
     *
     * @see SubCommand to define the subcommands within the command file.
     *
     * @throws CommandRegistrationException - Failed to Register Command.
     */
    private void registerSubCommands() throws CommandRegistrationException {
        try {
            Class<?> commandClass = this.getClass();
            Method[] declaredMethods = commandClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                SubCommand subCommand = declaredMethod.getDeclaredAnnotation(SubCommand.class);
                if (subCommand != null) {
                    Class<?> returnType = declaredMethod.getReturnType();
                    if (Boolean.TYPE.equals(returnType)) {
                        Class<?>[] parameters = declaredMethod.getParameterTypes();
                        if (parameters.length == 2) {
                            if (parameters[0].equals(CommandSender.class) && parameters[1].equals(String[].class)) {
                                String commandName = subCommand.name();
                                String description = subCommand.description();
                                String usage = subCommand.usage();
                                String permissionRequired = subCommand.perm();
                                boolean playerRequired = subCommand.playerCommand();
                                Command command = new Command(
                                        commandName,
                                        usage,
                                        description,
                                        permissionRequired,
                                        playerRequired,
                                        declaredMethod
                                );
                                subCommands.add(command);
                            }
                        }
                    }
                }
            }
        } catch (Exception exception) {
            throw new CommandRegistrationException("Failed to register the command '" + getCommandName() + "'!");
        }
    }

    /**
     * Method to return a Command's Usage from the Sub-Command's Name.
     *
     * @param subCommand - The specified command.
     * @return - String Usage.
     * @see SubCommand -> Usage.
     */
    public String getCommandUsage(String subCommand) {
        return subCommands.stream().filter(command -> command.getName().equalsIgnoreCase(subCommand))
                .findFirst().map(Command::getUsage).orElse(null);
    }

    /**
     * Method to return a Command's Description from the Sub-Command's Name.
     *
     * @param subCommand - The specified command.
     * @return - String Description.
     * @see SubCommand -> Description.
     */
    public String getCommandDescription(String subCommand) {
        return subCommands.stream().filter(command -> command.getName().equalsIgnoreCase(subCommand))
                .findFirst().map(Command::getDescription).orElse(null);
    }

    /**
     * Method to return a Command's Permission from the Sub-Command's Name.
     *
     * @param subCommand - The specified command.
     * @return - String Permission.
     * @see SubCommand -> Permission.
     */
    public String getCommandPermission(String subCommand) {
        return subCommands.stream().filter(command -> command.getName().equalsIgnoreCase(subCommand))
                .findFirst().map(Command::getPermissionRequired).orElse(null);
    }

    /**
     * Method to return a Command's Method from the Sub-Command's Name.
     *
     * @param subCommand - The specified command.
     * @return - Method.
     */
    public Method getCommandMethod(String subCommand) {
        return subCommands.stream().filter(command -> command.getName().equalsIgnoreCase(subCommand))
                .findFirst().map(Command::getMethod).orElse(null);
    }

    /**
     * Method to determine if a Command is player exclusive.
     *
     * @param subCommand - The specified Command.
     * @return - true = Yes, false = No.
     */
    public boolean isCommandPlayerExclusive(String subCommand) {
        for (Command command : subCommands) {
            if (command.getName().equalsIgnoreCase(subCommand)) {
                return command.isPlayerRequired();
            }
        }
        return false;
    }

    /**
     * Method to handle all commands within the class using Reflection
     * methodology and the previously registered command Maps.
     *
     * @param sender - CommandSender object.
     * @param command - Command instance.
     * @param s - The label used to run the command.
     * @param args - Arguments provided with the command.
     * @return - true = Success, false = Failure.
     */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getNoPermissionMessage()));
            return false;
        }
        if (isPlayerRequired()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getPlayerRequiredMessage()));
                return false;
            }
        }
        if (args.length < 1) {
            return this.onCommand(sender, args);
        } else {
            String subCommand = args[0].toLowerCase();
            String[] newArgs = (args.length < 2) ? new String[0] : Arrays.copyOfRange(args, 1, args.length);
            Method method = getCommandMethod(subCommand);
            if (method == null) {
                return this.onCommand(sender, args);
            }
            try {
                String perm = getCommandPermission(subCommand);
                if (perm != null) {
                    if (!sender.hasPermission(perm)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerms));
                        return false;
                    }
                }
                if (isCommandPlayerExclusive(subCommand)) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getPlayerRequiredMessage()));
                        return false;
                    }
                }
                Object[] parameters = {sender, newArgs};
                return (boolean) method.invoke(this, parameters);
            } catch (Exception exception) {
                return false;
            }
        }
    }


    /**
     * Method to redirect Tab Completion functionality to the CustomCommand implementation.
     *
     * @param sender - CommandSender object.
     * @param command - Command instance.
     * @param s - The label used to run the command.
     * @param args - Arguments provided with the command.
     * @return - List of Strings based on given input.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        return onTabComplete(sender, args);
    }

    /**
     * Convert a String into a Long.
     * If this method fails, the provided failure message will be sent to the Sender.
     *
     * @param sender - CommandSender who sent the command.
     * @param potential - The potential Long.
     * @param failureMessage - The message to be sent to the CommandSender upon Failure.
     * @return - Converted Long.
     */
    public long parseLong(CommandSender sender, String potential, String failureMessage) {
        try {
            return Long.parseLong(potential);
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', failureMessage));
            return Long.MIN_VALUE;
        }
    }

    /**
     * Convert a String into a Integer.
     * If this method fails, the provided failure message will be sent to the Sender.
     *
     * @param sender - CommandSender who sent the command.
     * @param potential - The potential Integer.
     * @param failureMessage - The message to be sent to the CommandSender upon Failure.
     * @return - Converted Integer.
     */
    public int parseInteger(CommandSender sender, String potential, String failureMessage) {
        try {
            return Integer.parseInt(potential);
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', failureMessage));
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Convert a String into a Double.
     * If this method fails, the provided failure message will be sent to the Sender.
     *
     * @param sender - CommandSender who sent the command.
     * @param potential - The potential Double.
     * @param failureMessage - The message to be sent to the CommandSender upon Failure.
     * @return - Converted Double.
     */
    public double parseDouble(CommandSender sender, String potential, String failureMessage) {
        try {
            return Double.parseDouble(potential);
        } catch (NumberFormatException exception) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', failureMessage));
            return Double.MIN_VALUE;
        }
    }

    /**
     * Abstract Method to handle Tab Completion given a sender the passed Arguments.
     *
     * @param sender - CommandSender who sent the TAB Completion request.
     * @param args - Arguments provided.
     * @return - List of Strings based on given input.
     */
    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    /**
     * Abstract Method to handle the 'Command' given a sender and the passed Arguments.
     *
     * @param sender - CommandSender object.
     * @param args - Arguments provided with the command.
     * @return - true = Success, false = Failure.
     */
    public abstract boolean onCommand(CommandSender sender, String[] args);

}
