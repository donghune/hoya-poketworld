package io.github.donghune.poketworld.message

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class AnnounceCommand : UsageCommandExecutor {

    override val usage: String
        get() = """
            /announce bossbar <message> -> 공지를 보스바로 전달합니다.
            /announce chat <message> -> 공지를 채팅으로 전달합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("권한이 없습니다.")
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(usage)
            return true
        }
        when (args[0]) {
            "bossbar" -> {
                if (args.size < 2) {
                    sender.sendMessage(usage)
                    return true
                }
                BossBarManager.bossBar.title = args.slice(1 until args.size).joinToString(" ")
            }

            "chat" -> {
                if (args.size < 2) {
                    sender.sendMessage(usage)
                    return true
                }
                Bukkit.broadcastMessage(args.slice(1 until args.size).joinToString(" "))
            }

            else -> {
                sender.sendMessage(usage)
                return true
            }
        }
        return true
    }
}