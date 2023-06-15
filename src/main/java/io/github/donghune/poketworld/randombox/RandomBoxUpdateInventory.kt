package io.github.donghune.poketworld.randombox

import io.github.donghune.poketworld.CustomInventory
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.jetbrains.exposed.sql.transactions.transaction

class RandomBoxUpdateInventory(
    plugin: Plugin, player: Player, randomBox: RandomBox
) : CustomInventory(
    plugin, player, "랜덤박스에서 드랍 될 아이템을 넣어주세요", 54
) {
    init {
        onInventoryOpen {
            transaction {
                randomBox.items.forEach { randomBoxItem ->
                    inventory.addItem(randomBoxItem.itemStack)
                }
            }
        }
        onInventoryClose {
            transaction {
                randomBox.items.forEach { it.delete() }
                inventory.contents.associate { it.clone().apply { amount = 1 } to it.amount }
                    .forEach { (itemStack, amount) ->
                        RandomBoxItem.new {
                            this.randomBox = randomBox
                            this.itemStack = itemStack
                            this.weight = amount
                        }
                    }
                player.sendMessage("랜덤박스 아이템이 변경되었습니다.")
            }
        }
    }
}