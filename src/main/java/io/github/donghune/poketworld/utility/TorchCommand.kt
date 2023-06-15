package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TorchCommand : UsageCommandExecutor {

    override val usage: String
        get() = """
            /torch -> 횃불을 하나 지급합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender is Player) {
            sender.inventory.addItem(ItemStack(Material.TORCH, 64))
        }
        sender.sendMessage("횃불을 지급하였습니다.")
        return true
    }
}