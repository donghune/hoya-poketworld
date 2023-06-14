package io.github.donghune.poketworld

import org.bukkit.plugin.java.JavaPlugin

abstract class Module {
    abstract fun onEnable(plugin: JavaPlugin)
    abstract fun onDisable(plugin: JavaPlugin)
}