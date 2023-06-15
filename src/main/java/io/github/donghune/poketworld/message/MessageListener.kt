package io.github.donghune.poketworld.message

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class MessageListener : Listener {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        event.player.sendTitle("§6§lPoketWorld", "§e§l포켓월드에 오신 것을 환영합니다!", 10, 70, 20)
        BossBarManager.bossBar.addPlayer(event.player)
    }
}