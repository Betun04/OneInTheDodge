package me.betun.oneinthedodge.commands

import me.betun.oneinthedodge.OneInTheDodge
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object Recargar: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        OneInTheDodge.instance.reloadConfig()
        sender.sendMessage(Component.text("${ChatColor.RED}Configuración recargada correctamente"));
        return true;
    }
}