package io.github.donghune.poketworld.regenblock

import io.github.donghune.poketworld.Module
import org.bukkit.plugin.java.JavaPlugin

class RegenBlockModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        plugin.server.pluginManager.registerEvents(RegenBlockListener(plugin), plugin)
        plugin.getCommand("regenblock")?.executor = RegenBlockCommand()
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}

