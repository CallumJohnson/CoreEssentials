package me.luligabi.coreessentials.command.abstraction;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command.abstraction
    Class Purpose:  Command Object.
    
*/

import java.lang.reflect.Method;

public class Command {

    private final String name, usage, description, permissionRequired;
    private final boolean playerRequired;
    private final Method method;

    /**
     * Constructor to initialise a Command Object.
     *
     * @param name - Name of the command.
     * @param usage - Usage Example for the command.
     * @param description - Description of the Command.
     * @param permissionRequired - Permission required to run the command.
     * @param playerRequired - Is the command player-specific?
     * @param method - Method to be invoked (Run the command).
     */
    public Command (
            String name,
            String usage,
            String description,
            String permissionRequired,
            boolean playerRequired,
            Method method
    ) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permissionRequired = permissionRequired;
        this.playerRequired = playerRequired;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPermissionRequired() {
        return permissionRequired;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isPlayerRequired() {
        return playerRequired;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + ", usage='" + usage + '\''
                + ", description='" + description + '\''
                + ", permissionRequired='" + permissionRequired + '\''
                + ", playerRequired=" + playerRequired
                + ", method=" + method
        + '}';
    }

}
