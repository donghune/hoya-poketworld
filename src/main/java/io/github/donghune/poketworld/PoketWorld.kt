package io.github.donghune.poketworld

import io.github.donghune.poketworld.customitem.CustomItemModule
import io.github.donghune.poketworld.database.SQLiteModule
import io.github.donghune.poketworld.economy.EconomyModule
import io.github.donghune.poketworld.message.MessageModule
import io.github.donghune.poketworld.randombox.RandomBoxModule
import io.github.donghune.poketworld.regenblock.RegenBlockModule
import io.github.donghune.poketworld.utility.UtilityModule
import org.bukkit.Utility
import org.bukkit.plugin.java.JavaPlugin

class PoketWorld : JavaPlugin() {
    private val modules =
        listOf(
            SQLiteModule(),
            CustomItemModule(),
            MessageModule(),
            EconomyModule(),
            RandomBoxModule(),
            UtilityModule(),
            RegenBlockModule()
        )

    override fun onEnable() {
        modules.forEach { it.onEnable(plugin = this) }
    }

    override fun onDisable() {
        modules.forEach { it.onDisable(plugin = this) }
    }
}

// RegenBlock