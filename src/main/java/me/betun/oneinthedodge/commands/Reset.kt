package me.betun.oneinthedodge.commands

import me.betun.oneinthedodge.OneInTheDodge
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Reset: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        OneInTheDodge.instance.config.set("shrink.start",0)
        sender.sendMessage(Component.text("${ChatColor.GREEN}Shrink reseteado correctamente"));
        return true;
    }
}