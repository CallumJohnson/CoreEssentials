package me.luligabi.coreessentials.command.abstraction;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command.abstraction
    Class Purpose:  Annotation to define a CustomCommand Sub-Command.
    
*/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface which defines a SubCommand of the CustomCommand API.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SubCommand {


    String name(); // Name of the Sub-Command.

    String description() default "[NOT SET]"; // Description of the Sub-Command.

    String usage() default "[NOT SET]"; // Usage of the Sub-Command.

    String perm(); // Permission required to perform the Sub-Command.

    boolean playerCommand(); // Is this command player-exclusive?

}
