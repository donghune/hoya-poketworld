package io.github.donghune.poketworld.randombox

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.jetbrains.exposed.sql.transactions.transaction

class RandomBoxListener : Listener {
    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) = transaction {
        val itemStack = event.player.inventory.itemInMainHand ?: return@transaction
        if (!itemStack.isRandomBox()) return@transaction
        val randomBox = itemStack.parseRandomBox() ?: return@transaction
        val randomBoxItem = randomBox.items.toList().random()
        event.player.inventory.addItem(randomBoxItem.itemStack)
        event.player.sendMessage("랜덤박스를 열어 ${randomBoxItem.itemStack.type} 아이템을 획득하였습니다.")
    }
}