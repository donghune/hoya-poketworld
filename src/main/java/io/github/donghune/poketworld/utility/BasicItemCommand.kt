package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction

class BasicItemCommand(private val plugin: JavaPlugin) : UsageCommandExecutor {
    override val usage: String
        get() = """
            /basicitem -> 기본 아이템을 받습니다.
            /basicitem reset -> 기본 아이템을 받지 않은 상태로 설정합니다.
            /basicitem check <player> -> 기본 아이템을 받았는지 확인합니다.
            /basicitem items -> 기본 아이템을 수정합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean = transaction {
        if (sender !is Player) return@transaction false

        when (args.getOrNull(0)) {
            "reset" -> {
                sender.isReceiveBasicItem = false
                sender.sendMessage("기본 아이템을 받지 않은 상태로 설정하였습니다.")
            }

            "check" -> {
                if (args.size < 2) {
                    sender.sendMessage("플레이어 이름을 입력해주세요.")
                    return@transaction false
                }
                val targetPlayer = sender.server.getPlayer(args[1])
                if (targetPlayer == null) {
                    sender.sendMessage("플레이어를 찾을 수 없습니다.")
                    return@transaction false
                }
                if (targetPlayer.isReceiveBasicItem) {
                    sender.sendMessage("${targetPlayer.name}님은 기본 아이템을 받았습니다.")
                } else {
                    sender.sendMessage("${targetPlayer.name}님은 기본 아이템을 받지 않았습니다.")
                }
            }

            "items" -> {
                BasicItemInventory(plugin, sender).open()
            }

            else -> {
                if (sender.isReceiveBasicItem) {
                    sender.sendMessage("이미 기본 아이템을 받았습니다.")
                } else {
                    sender.isReceiveBasicItem = true
                    sender.sendMessage("기본 아이템을 받았습니다.")
                }
            }
        }
        return@transaction true
    }
}