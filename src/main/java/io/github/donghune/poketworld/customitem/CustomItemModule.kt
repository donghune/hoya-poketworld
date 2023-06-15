package io.github.donghune.poketworld.customitem

import io.github.donghune.poketworld.Module
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class CustomItemModule : Module() {

    override fun onEnable(plugin: JavaPlugin) {
        plugin.getCommand("customitem")?.executor = CustomItemCommand()
        plugin.getCommand("nbteditor")?.executor = NbtEditorCommand()
        transaction { SchemaUtils.create(CustomItems) }
        transaction { CustomItem.new { this.itemStack = ItemStack(Material.ACACIA_DOOR_ITEM) } }
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}