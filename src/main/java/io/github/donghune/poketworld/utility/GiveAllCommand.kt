package io.github.donghune.poketworld.utility

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GiveAllCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        return true
    }
}