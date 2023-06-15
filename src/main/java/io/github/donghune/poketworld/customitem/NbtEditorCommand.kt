package io.github.donghune.poketworld.customitem

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class NbtEditorCommand : UsageCommandExecutor {
    override val usage: String
        get() = """
            /nbt displayName <name> -> 아이템의 이름을 설정합니다.
            /nbt type <type> -> 아이템의 타입을 설정합니다.
            /nbt lore add <lore> -> 아이템의 설명을 설정합니다.
            /nbt lore remove <index> -> 아이템의 설명을 제거합니다.
            /nbt lore set <index> <lore> -> 아이템의 설명을 수정합니다.
            /nbt lore insert <index> <lore> -> 아이템의 설명을 삽입합니다.
            /nbt lore clear -> 아이템의 설명을 모두 제거합니다.
            /nbt enchant add <enchant> <level> -> 아이템에 인첸트를 추가합니다.
            /nbt enchant remove <enchant> -> 아이템의 인첸트를 제거합니다.
            /nbt enchant clear -> 아이템의 인첸트를 모두 제거합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player
        if (player == null) {
            sender.sendMessage("플레이어만 사용 가능한 명령어입니다.")
            return false
        }
        val itemStack = player.inventory.itemInMainHand
        if (itemStack == null || itemStack.type == Material.AIR) {
            sender.sendMessage("아이템을 들고 명령어를 사용해주세요.")
            return false
        }
        if (args.isEmpty()) {
            player.sendMessage(usage)
            return false
        }
        when (args[0]) {
            "displayName" -> {
                if (args.size < 2) {
                    player.sendMessage(usage)
                    return false
                }
                val displayName = args.slice(1 until args.size).joinToString(" ")
                itemStack.itemMeta?.displayName = displayName
                player.sendMessage("아이템의 이름을 $displayName 으로 설정하였습니다.")
            }

            "type" -> {
                if (args.size < 2) {
                    player.sendMessage(usage)
                    return false
                }
                val type = args[1]
                itemStack.type = Material.getMaterial(type) ?: Material.AIR
                player.sendMessage("아이템의 타입을 $type 으로 설정하였습니다.")
            }

            "lore" -> {
                if (args.size < 2) {
                    player.sendMessage(usage)
                    return false
                }
                when (args[1]) {
                    "add" -> {
                        if (args.size < 3) {
                            player.sendMessage(usage)
                            return false
                        }
                        val lore = args.slice(2 until args.size).joinToString(" ")
                        itemStack.itemMeta?.lore?.add(lore)
                        player.sendMessage("아이템의 설명을 $lore 으로 추가하였습니다.")
                    }

                    "remove" -> {
                        if (args.size < 3) {
                            player.sendMessage(usage)
                            return false
                        }
                        val index = args[2].toIntOrNull()
                        if (index == null) {
                            player.sendMessage(usage)
                            return false
                        }
                        itemStack.itemMeta?.lore?.removeAt(index)
                        player.sendMessage("아이템의 설명을 $index 번째 줄을 제거하였습니다.")
                    }

                    "set" -> {
                        if (args.size < 4) {
                            player.sendMessage(usage)
                            return false
                        }
                        val index = args[2].toIntOrNull()
                        if (index == null) {
                            player.sendMessage(usage)
                            return false
                        }
                        val lore = args.slice(3 until args.size).joinToString(" ")
                        itemStack.itemMeta?.lore?.set(index, lore)
                        player.sendMessage("아이템의 설명을 $index 번째 줄을 $lore 으로 수정하였습니다.")
                    }

                    "insert" -> {
                        if (args.size < 4) {
                            player.sendMessage(usage)
                            return false
                        }
                        val index = args[2].toIntOrNull()
                        if (index == null) {
                            player.sendMessage(usage)
                            return false
                        }
                        val lore = args.slice(3 until args.size).joinToString(" ")
                        itemStack.itemMeta?.lore?.add(index, lore)
                        player.sendMessage("아이템의 설명을 $index 번째 줄을 $lore 으로 삽입하였습니다.")
                    }

                    "clear" -> {
                        itemStack.itemMeta?.lore?.clear()
                        player.sendMessage("아이템의 설명을 모두 제거하였습니다.")
                    }

                    else -> {
                        player.sendMessage(usage)
                        return false
                    }
                }
            }

            "enchant" -> {
                if (args.size < 2) {
                    player.sendMessage(usage)
                    return false
                }
                when (args[1]) {
                    "add" -> {
                        if (args.size < 4) {
                            player.sendMessage(usage)
                            return false
                        }
                        val enchant = args[2]
                        val level = args[3].toIntOrNull()
                        if (level == null) {
                            player.sendMessage(usage)
                            return false
                        }
                        itemStack.addUnsafeEnchantment(
                            Enchantment.getByName(enchant) ?: return false,
                            level
                        )
                        player.sendMessage("아이템에 $enchant 인첸트를 $level 레벨로 추가하였습니다.")
                    }

                    "remove" -> {
                        if (args.size < 3) {
                            player.sendMessage(usage)
                            return false
                        }
                        val enchant = args[2]
                        itemStack.removeEnchantment(Enchantment.getByName(enchant) ?: return false)
                        player.sendMessage("아이템의 $enchant 인첸트를 제거하였습니다.")
                    }

                    "clear" -> {
                        itemStack.enchantments.clear()
                        player.sendMessage("아이템의 인첸트를 모두 제거하였습니다.")
                    }

                    else -> {
                        player.sendMessage(usage)
                        return false
                    }
                }
            }

            else -> {
                player.sendMessage(usage)
                return false
            }
        }
        return true
    }
}