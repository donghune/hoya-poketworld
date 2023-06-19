package io.github.donghune.poketworld.quest

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SeasonPassCommand : UsageCommandExecutor {
    override val usage: String
        get() = """
            /seasonpass <exp|level> add <player> <amount> - 경험치 또는 레벨을 추가합니다.
            /seasonpass <exp|level> set <player> <amount> - 경험치 또는 레벨을 설정합니다.
            /seasonpass <exp|level> remove <player> <amount> - 경험치 또는 레벨을 제거합니다.
            /seasonpass <exp|level> reset <player> - 경험치 또는 레벨을 초기화합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {

        if (args.isNullOrEmpty()) {
            sender?.sendMessage(usage)
            return true
        }

        val type = args[0]
        val action = args[1]
        val player = Bukkit.getPlayer(args[2])
        val amount = args[3].toIntOrNull()

        if (player == null) {
            sender?.sendMessage("존재하지 않는 플레이어입니다.")
            return true
        }

        if (amount == null) {
            sender?.sendMessage("숫자를 입력해주세요.")
            return true
        }

        val playerSeasonPass = PlayerSeasonPass.find { PlayerSeasonPasses.uuid eq player.uniqueId }.firstOrNull()

        if (playerSeasonPass == null) {
            sender?.sendMessage("존재하지 않는 플레이어입니다.")
            return true
        }

        when (type) {
            "exp" -> {
                when (action) {
                    "add" -> {
                        playerSeasonPass.addExp(amount)
                        sender?.sendMessage("경험치를 $amount 만큼 추가하였습니다.")
                    }

                    "set" -> {
                        playerSeasonPass.setExp(amount)
                        sender?.sendMessage("경험치를 $amount 만큼 설정하였습니다.")
                    }

                    "remove" -> {
                        playerSeasonPass.takeExp(amount)
                        sender?.sendMessage("경험치를 $amount 만큼 제거하였습니다.")
                    }

                    "reset" -> {
                        playerSeasonPass.resetAll()
                        sender?.sendMessage("경험치를 초기화하였습니다.")
                    }

                    else -> {
                        sender?.sendMessage(usage)
                    }
                }
            }

            "level" -> {
                when (action) {
                    "add" -> {
                        playerSeasonPass.addLevel(amount)
                        sender?.sendMessage("레벨을 $amount 만큼 추가하였습니다.")
                    }

                    "set" -> {
                        playerSeasonPass.setLevel(amount)
                        sender?.sendMessage("레벨을 $amount 만큼 설정하였습니다.")
                    }

                    "remove" -> {
                        playerSeasonPass.takeLevel(amount)
                        sender?.sendMessage("레벨을 $amount 만큼 제거하였습니다.")
                    }

                    "reset" -> {
                        playerSeasonPass.resetAll()
                        sender?.sendMessage("레벨을 초기화하였습니다.")
                    }

                    else -> {
                        sender?.sendMessage(usage)
                    }
                }
            }

            else -> {
                sender?.sendMessage(usage)
            }
        }

        return true
    }
}