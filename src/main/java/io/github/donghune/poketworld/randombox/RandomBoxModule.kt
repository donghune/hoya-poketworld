package io.github.donghune.poketworld.randombox

import io.github.donghune.poketworld.Module
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class RandomBoxModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        transaction {
            SchemaUtils.create(RandomBoxes, RandomBoxItems)
        }
        plugin.getCommand("randombox")?.executor = RandomBoxCommand(plugin)
        plugin.server.pluginManager.registerEvents(RandomBoxListener(), plugin)
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}

fun generateRandomBox(randomBox: RandomBox): ItemStack = transaction {
    val itemStack = ItemStack(Material.CHEST)
    val meta = itemStack.itemMeta
    meta?.displayName = "RANDOM BOX: ${randomBox.name}"
    meta?.lore = listOf(randomBox.description)
    itemStack.itemMeta = meta
    return@transaction itemStack
}

fun ItemStack.isRandomBox(): Boolean {
    val meta = this.itemMeta ?: return false
    val name = meta.displayName ?: return false
    return name.startsWith("RANDOM BOX: ")
}

fun ItemStack.parseRandomBox(): RandomBox? = transaction {
    val meta = this@parseRandomBox.itemMeta ?: return@transaction null
    val name = meta.displayName ?: return@transaction null
    val randomBoxName = name.removePrefix("RANDOM BOX: ")
    return@transaction RandomBox.find { RandomBoxes.name eq randomBoxName }.firstOrNull()
}