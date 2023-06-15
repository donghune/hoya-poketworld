package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GiveAllCommand : UsageCommandExecutor {
    override val usage: String
        get() = """
            /giveall -> 손에 든 아이템을 모든 플레이어에게 지급합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("플레이어만 사용 가능합니다.")
            return false
        }
        if (!sender.isOp) {
            sender.sendMessage("권한이 없습니다.")
            return false
        }
        val item = sender.inventory.itemInMainHand
        if (item == null) {
            sender.sendMessage("손에 아이템을 들고 명령어를 사용해주세요.")
            return false
        }
        Bukkit.getOnlinePlayers().forEach { it.inventory.addItem(item) }
        sender.sendMessage("모든 플레이어에게 ${item.type.name}을(를) 지급하였습니다.")
        return true
    }
}