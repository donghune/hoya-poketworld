package io.github.donghune.poketworld.database

import io.github.donghune.poketworld.Module
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database

class SQLiteModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        Database.connect("jdbc:sqlite:${plugin.dataFolder.path}/mydatabase.db", "org.sqlite.JDBC")
    }

    override fun onDisable(plugin: JavaPlugin) {
    }
}
