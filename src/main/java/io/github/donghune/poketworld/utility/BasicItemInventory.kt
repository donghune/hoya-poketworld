package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.CustomInventory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.jetbrains.exposed.sql.transactions.transaction

class BasicItemInventory(
    plugin: Plugin, player: Player
) : CustomInventory(plugin, player, "기본 아이템", 9 * 3) {
    init {
        onInventoryOpen {
            transaction {
                BasicItem.all().forEachIndexed { index, basicItem ->
                    setItem(index, basicItem.itemStack)
                }
            }
        }
        onInventoryClose {
            transaction {
                BasicItem.all().forEach { it.delete() }
                inventory.contents.forEach { itemStack ->
                    if (itemStack != null && itemStack.type != Material.AIR) {
                        BasicItem.new {
                            this.itemStack = itemStack
                        }
                    }
                }
                player.sendMessage("기본 아이템이 수정되었습니다.")
            }
        }
    }
}
