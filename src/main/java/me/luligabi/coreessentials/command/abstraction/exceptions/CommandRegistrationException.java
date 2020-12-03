package me.luligabi.coreessentials.command.abstraction.exceptions;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command.abstraction.exceptions
    Class Purpose:  Exception to be thrown in the event that a command failed to Register.
    
*/

public class CommandRegistrationException extends Exception {

    public CommandRegistrationException(String message) {
        super(message);
    }

}
