package me.luligabi.coreessentials.command;

/*
    
    Created By:     Callum Johnson
    Created In:     Dec/2020
    Project Name:   CoreEssentials
    Package Name:   me.luligabi.coreessentials.command
    Class Purpose:  Display the current time.
    
*/

import me.luligabi.coreessentials.CoreEssentials;
import me.luligabi.coreessentials.command.abstraction.CustomCommand;
import me.luligabi.coreessentials.utils.MessageUtils;
import me.luligabi.coreessentials.utils.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Clock extends CustomCommand<CoreEssentials> {

    /**
     * Constructor to initialise a Command instance.
     *
     * @param plugin                - Plugin which is used to register the command.
     */
    public Clock(CoreEssentials plugin) {
        super(
                plugin,
                "clock",
                MessageUtils.permissionMessage(Permissions.COMMAND_CLOCK),
                Permissions.COMMAND_CLOCK,
                plugin.getConfig().getString("notAPlayer"),
                true
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
        return Collections.emptyList();
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
        sender.sendMessage(
                MessageUtils.successMessage(
                        plugin.getConfig().getString("clockPrefix"),
                        Objects.requireNonNull(plugin.getConfig().getString("clockSuccess"))
                                .replace("%clock%", formatTime(((Player)sender).getWorld().getTime()))
                )
        );
        return true;
    }

    /**
     * Format Milliseconds into a Time Formatted String.
     *
     * @param millis - Milliseconds to Convert.
     * @return - HH:mm:ss or 13:15:23
     */
    private String formatTime(final long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

}
