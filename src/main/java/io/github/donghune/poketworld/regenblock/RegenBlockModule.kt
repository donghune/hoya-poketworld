package io.github.donghune.poketworld.regenblock

import io.github.donghune.poketworld.Module
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class RegenBlockModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        plugin.server.pluginManager.registerEvents(RegenBlockListener(plugin), plugin)
        plugin.getCommand("regenblock")?.executor = RegenBlockCommand()
        transaction { SchemaUtils.create(RegenAreas, RegenBlocks) }
        RegenBlockManager.cancelAll()
    }

    override fun onDisable(plugin: JavaPlugin) {
        RegenBlockManager.cancelAll()
    }
}

