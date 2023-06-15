package io.github.donghune.poketworld.customitem

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction

class CustomItemCommand : UsageCommandExecutor {

    override val usage: String
        get() = """
            /customitem create <name> -> 커스텀 아이템을 생성합니다.
            /customitem delete <id> -> 커스텀 아이템을 제거합니다.
            /customitem update <id> -> 커스텀 아이템을 수정합니다.
            /customitem get <id> -> 커스텀 아이템을 얻습니다.
            /customitem list <page> <size> -> 커스텀 아이템 목록을 확인합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (!checkCommandValidation(sender, args)) {
            return false
        }
        when (args[0]) {
            "create" -> createItem(sender as Player)
            "delete" -> {
                val itemId = args.getOrNull(1)?.toIntOrNull()
                if (itemId == null) {
                    sender.sendMessage(usage)
                    return false
                }
                deleteItem(sender as Player, itemId)
            }

            "update" -> {
                val itemId = args.getOrNull(1)?.toIntOrNull()
                if (itemId == null) {
                    sender.sendMessage(usage)
                    return false
                }
                updateItem(sender as Player, itemId)
            }

            "get" -> {
                val itemId = args.getOrNull(1)?.toIntOrNull()
                if (itemId == null) {
                    sender.sendMessage(usage)
                    return false
                }
                getItem(sender as Player, itemId)
            }

            "list" -> {
                val page = args.getOrNull(2)?.toIntOrNull() ?: 0
                val size = args.getOrNull(3)?.toIntOrNull() ?: 10
                getItemList(sender, page, size)
            }

            else -> sender.sendMessage(usage)
        }
        return true
    }

    private fun checkCommandValidation(sender: CommandSender, args: Array<out String>): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("권한이 없습니다.")
            return false
        }
        if (args.isEmpty()) {
            sender.sendMessage("사용법: /customitem <create|delete|get>")
            return false
        }
        return true
    }

    private fun createItem(player: Player) = transaction {
        val itemStack = player.inventory.itemInMainHand
        if (itemStack == null || itemStack.type == Material.AIR) {
            player.sendMessage("손에 아이템을 들고 사용해주세요.")
            return@transaction
        }
        CustomItem.new { this.itemStack = itemStack }
        player.sendMessage("아이템을 생성하였습니다.")
    }

    private fun deleteItem(player: Player, id: Int) = transaction {
        val customItem = CustomItem.findById(id)
        if (customItem == null) {
            player.sendMessage("존재하지 않는 아이템입니다.")
            return@transaction
        }
        customItem.delete()
        player.sendMessage("아이템을 삭제하였습니다.")
    }

    private fun updateItem(player: Player, id: Int) = transaction {
        val customItem = CustomItem.findById(id)
        if (customItem == null) {
            player.sendMessage("존재하지 않는 아이템입니다.")
            return@transaction
        }
        val updateItem = player.inventory.itemInMainHand
        if (updateItem == null || updateItem.type == Material.AIR) {
            player.sendMessage("손에 아이템을 들고 사용해주세요.")
            return@transaction
        }
        customItem.itemStack = updateItem
        player.sendMessage("아이템을 수정하였습니다.")
    }

    private fun getItem(player: Player, id: Int) = transaction {
        val customItem = CustomItem.findById(id)
        if (customItem == null) {
            player.sendMessage("존재하지 않는 아이템입니다.")
            return@transaction
        }
        player.inventory.addItem(customItem.itemStack)
        player.sendMessage("아이템을 지급하였습니다.")
    }

    private fun getItemList(sender: CommandSender, page: Int, size: Int) = transaction {
        val customItemList = CustomItem.all().limit(size, (page * size).toLong())
        sender.sendMessage("아이템 목록")
        customItemList.forEach {
            sender.sendMessage("${it.id.value} : ${it.itemStack.itemMeta?.displayName ?: it.itemStack.type}")
        }
    }
}