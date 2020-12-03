package me.luligabi.coreessentials.command;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command
    Class Purpose:  TODO
    
*/

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.command.abstraction.CustomCommand;
import me.luligabi.coreessentials.utils.MessageUtils;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day extends CustomCommand<CoreEssentials> {

    /**
     * Constructor to initialise a Command instance.
     *
     * @param plugin                - Plugin which is used to register the command.
     */
    public Day(CoreEssentials plugin) {
        super(
                plugin,
                "day",
                MessageUtils.permissionMessage(Permissions.COMMAND_DAY),
                Permissions.COMMAND_DAY,
                plugin.getConfig().getString("notAPlayer"),
                false
        );
    }

    /**
     * Abstract Method to handle Tab Completion given a sender the passed Arguments.
     *
     * @param sender - CommandSender who sent the TAB Completion request.
     * @param args   - Arguments provided.
     * @return - List of Strings based on given input.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
    }

    /**
     * Abstract Method to handle the 'Command' given a sender and the passed Arguments.
     *
     * @param sender - CommandSender object.
     * @param args   - Arguments provided with the command.
     * @return - true = Success, false = Failure.
     */
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        World world;
        if (sender instanceof Player && args.length == 0) {
            Player player = (Player) sender;
            world = player.getWorld();
        } else if (args.length == 0) {
            String usage = "Please use the syntax: '/day (world)'";
            sender.sendMessage(usage);
            return false;
        } else {
            world = Bukkit.getWorld(args[0]);
        }
        if (world == null) {
            sender.sendMessage("World not recognised.");
            return false;
        } else {
            world.setTime(1000);
            sender.sendMessage(
                    MessageUtils.successMessage(
                            plugin.getConfig().getString("dayPrefix"),
                            Objects.requireNonNull(plugin.getConfig().getString("daySuccess"))
                                    .replace("%world%", world.getName())
                    )
            );
            return true;
        }
    }
}
