package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.Module
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class UtilityModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        Bukkit.getPluginCommand("giveall")?.executor = GiveAllCommand()
        Bukkit.getPluginCommand("basicitem")?.executor = BasicItemCommand()
        Bukkit.getPluginCommand("torch")?.executor = TorchCommand()

        Bukkit.getPluginManager().registerEvents(UtilityListener(), plugin)
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}

