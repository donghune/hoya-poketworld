package io.github.donghune.poketworld.utility

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ExplosionPrimeEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

class UtilityListener : Listener {
    @EventHandler
    fun onExplosionPrimeEvent(event: ExplosionPrimeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onHungerEvent(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }
}