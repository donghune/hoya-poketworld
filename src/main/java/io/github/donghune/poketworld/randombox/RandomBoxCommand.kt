package io.github.donghune.poketworld.randombox

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction

class RandomBoxCommand(private val plugin: JavaPlugin) : UsageCommandExecutor {

    override val usage: String
        get() = """
            /randombox create <name> -> 랜덤박스를 생성합니다.
            /randombox description <name> <description> -> 랜덤박스의 설명을 설정합니다.
            /randombox remove <name> -> 랜덤박스를 제거합니다.
            /randombox update <name> -> 랜덤박스 내용물을 설정합니다.
            /randombox give <name> -> 랜덤박스를 플레이어에게 지급합니다.
            /randombox list -> 랜덤박스 목록을 확인합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean = transaction {
        if (args.isEmpty()) {
            sender.sendMessage(usage)
            return@transaction true
        }
        when (args[0]) {
            "create" -> {
                val name = args.getOrNull(1)
                if (name == null) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                RandomBox.new { this.name = name }
                sender.sendMessage("랜덤박스가 생성되었습니다.")
            }

            "description" -> {
                val name = args.getOrNull(1)
                if (name == null) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                val description = args.slice(2..args.lastIndex).joinToString(" ")
                if (description.isEmpty()) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                val randomBox = RandomBox.find { RandomBoxes.name eq name }.firstOrNull()
                if (randomBox == null) {
                    sender.sendMessage("존재하지 않는 랜덤박스입니다.")
                    return@transaction true
                }
                randomBox.description = description
                sender.sendMessage("랜덤박스 설명이 변경되었습니다.")
            }

            "remove" -> {
                val name = args.getOrNull(1)
                if (name == null) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                val randomBox = RandomBox.find { RandomBoxes.name eq name }.firstOrNull()
                if (randomBox == null) {
                    sender.sendMessage("존재하지 않는 랜덤박스입니다.")
                    return@transaction true
                }
                randomBox.delete()
                sender.sendMessage("랜덤박스가 삭제되었습니다.")
            }

            "update" -> {
                val player = sender as? Player
                if (player == null) {
                    sender.sendMessage("플레이어만 사용할 수 있는 명령어입니다.")
                    return@transaction true
                }
                val name = args.getOrNull(1)
                if (name == null) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                val randomBox = RandomBox.find { RandomBoxes.name eq name }.firstOrNull()
                if (randomBox == null) {
                    sender.sendMessage("존재하지 않는 랜덤박스입니다.")
                    return@transaction true
                }
                RandomBoxUpdateInventory(plugin, sender, randomBox).open()
            }

            "give" -> {
                val player = sender as? Player
                if (player == null) {
                    sender.sendMessage("플레이어만 사용할 수 있는 명령어입니다.")
                    return@transaction true
                }
                val name = args.getOrNull(1)
                if (name == null) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                val amount = args.getOrNull(2)?.toIntOrNull()
                if (amount == null) {
                    sender.sendMessage(usage)
                    return@transaction true
                }
                val randomBox = RandomBox.find { RandomBoxes.name eq name }.firstOrNull()
                if (randomBox == null) {
                    sender.sendMessage("존재하지 않는 랜덤박스입니다.")
                    return@transaction true
                }
                val itemStack = generateRandomBox(randomBox)
                itemStack.amount = amount
                player.inventory?.addItem(itemStack)
                player.sendMessage("랜덤박스를 지급하였습니다.")
            }

            "list" -> {
                val page = args.getOrNull(1)?.toIntOrNull() ?: 1
                val size = args.getOrNull(2)?.toIntOrNull() ?: 10

                val randomBoxList = RandomBox.all().toList()
                val maxPage = randomBoxList.size / size + 1
                if (page > maxPage) {
                    sender.sendMessage("존재하지 않는 페이지입니다.")
                    return@transaction true
                }
                val startIndex = (page - 1) * size
                val endIndex = startIndex + size
                val subList = randomBoxList.subList(startIndex, endIndex)
                sender.sendMessage("랜덤박스 목록")
                subList.forEach {
                    sender.sendMessage("${it.id.value} : ${it.name}")
                }
            }

            else -> {
                sender.sendMessage(usage)
            }
        }
        return@transaction true
    }
}