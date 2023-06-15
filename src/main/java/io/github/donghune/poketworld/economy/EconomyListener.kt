package io.github.donghune.poketworld.economy

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EconomyListener : Listener {
    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        val itemStack = event.item ?: return
        if (itemStack.type != Material.PAPER) return
        if (!itemStack.isPaperMoney()) return
        val price = itemStack.parsePaperMoney()
        event.player.money += price
        event.player.inventory.removeItem(itemStack)
        event.player.sendMessage("§a${price}원을 획득하였습니다.")
    }
}