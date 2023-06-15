package io.github.donghune.poketworld.message

import io.github.donghune.poketworld.Module
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class MessageModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        plugin.getCommand("announce").executor = AnnounceCommand()
        plugin.getCommand("localchat").executor = LocalChatCommand()
        Bukkit.getPluginManager().registerEvents(MessageListener(), plugin)

        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendTitle("§6§lPoketWorld", "§e§l포켓월드에 오신 것을 환영합니다!", 10, 70, 20)
            BossBarManager.bossBar.addPlayer(player)
        }
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}


