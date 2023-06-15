package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction

class BasicItemCommand : UsageCommandExecutor {
    override val usage: String
        get() = """
            /basicitem -> 기본 아이템을 받습니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean = transaction {
        if (sender !is Player) {
            sender.sendMessage("플레이어만 사용 가능합니다.")
            return@transaction false
        }
        if (sender.isReceiveBasicItem) {
            sender.sendMessage("이미 기본 아이템을 받았습니다.")
            return@transaction false
        }
        sender.inventory.addItem(*BasicItem.all().map { it.itemStack }.toTypedArray())
        sender.isReceiveBasicItem = true
        sender.sendMessage("기본 아이템을 받았습니다.")
        return@transaction true
    }
}