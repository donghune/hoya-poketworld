package io.github.donghune.poketworld.economy

import io.github.donghune.poketworld.Module
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.java.JavaPlugin


class EconomyModule : Module() {
    companion object {
        lateinit var economy: Economy
    }

    override fun onEnable(plugin: JavaPlugin) {
        if (!setupEconomy(plugin)) {
            plugin.logger.severe("Vault 플러그인이 없습니다.")
            return
        }
        plugin.getCommand("checkpaper").executor = CheckPaperCommand()
        Bukkit.getPluginManager().registerEvents(EconomyListener(), plugin)
    }

    override fun onDisable(plugin: JavaPlugin) {

    }

    private fun setupEconomy(plugin: JavaPlugin): Boolean {
        if (plugin.server.pluginManager.getPlugin("Vault") == null) {
            return false
        }
        val rsp: RegisteredServiceProvider<Economy> = plugin.server.servicesManager.getRegistration(
            Economy::class.java
        ) ?: return false
        economy = rsp.provider
        return true
    }
}

fun generatePaperMoney(price: Int): ItemStack {
    val itemStack = ItemStack(Material.PAPER)
    val itemMeta = itemStack.itemMeta
    itemMeta.displayName = "§f§l${price}원"
    itemStack.itemMeta = itemMeta
    return itemStack
}

fun ItemStack.isPaperMoney(): Boolean {
    val itemMeta = this.itemMeta
    return itemMeta.displayName.contains("원") && itemMeta.displayName.contains("§f§l")
}

fun ItemStack.parsePaperMoney(): Int {
    val itemMeta = this.itemMeta
    return itemMeta.displayName.replace("원", "").replace("§f§l", "").toInt()
}