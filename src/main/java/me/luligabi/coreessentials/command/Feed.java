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
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Feed extends CustomCommand<CoreEssentials> {

    /**
     * Constructor to initialise a Command instance.
     *
     * @param plugin                - Plugin which is used to register the command.
     */
    public Feed(CoreEssentials plugin) {
        super(
                plugin,
                "feed",
                MessageUtils.permissionMessage(Permissions.COMMAND_FEED),
                Permissions.COMMAND_FEED,
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
        return args.length == 0 ?
                Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList())
                : Collections.emptyList();
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
        boolean self;
        Player target;
        String prefix = plugin.getConfig().getString("feedPrefix");
        if (args.length == 0) {
            if (sender instanceof Player) {
                target = (Player) sender;
            } else {
                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                Objects.requireNonNull(plugin.getConfig().getString("notAPlayer"))
                        )
                );
                return false;
            }
        } else {
            target = Bukkit.getPlayerExact(args[0]);
        }
        if (target == null) {
            sender.sendMessage(
                    MessageUtils.errorMessage(
                            prefix,
                            plugin.getConfig().getString("targetNotOnline")
                                    .replace("%target%", args[0])
                    )
            );
            return false;
        }
        self = target.getName().equals(sender.getName());
        if (!self) {
            if (!sender.hasPermission(Permissions.COMMAND_FEED_OTHERS)) {
                sender.sendMessage(MessageUtils.permissionMessage(Permissions.COMMAND_FEED_OTHERS));
                return false;
            }
            target.sendMessage(
                    MessageUtils.successMessage(
                            prefix,
                            Objects.requireNonNull(plugin.getConfig().getString("feedSuccessOtherTarget"))
                                    .replace("%player%", sender.getName())
                    )
            );
        }
        target.setFoodLevel(20);
        sender.sendMessage(
                MessageUtils.successMessage(
                        prefix,
                        Objects.requireNonNull(plugin.getConfig().getString("feedSuccessOther"))
                                .replace("%target%", self ? "yourself" : target.getDisplayName())
                )
        );
        return true;
    }
}
