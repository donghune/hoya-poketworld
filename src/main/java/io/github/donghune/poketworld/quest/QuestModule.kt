package io.github.donghune.poketworld.quest

import io.github.donghune.poketworld.Module
import org.bukkit.plugin.java.JavaPlugin

class QuestModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        plugin.getCommand("seasonpass")?.executor = SeasonPassCommand()
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}