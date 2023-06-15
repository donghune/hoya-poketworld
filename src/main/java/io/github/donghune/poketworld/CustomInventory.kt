package io.github.donghune.poketworld

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

abstract class CustomInventory private constructor(
    private val plugin: Plugin,
    protected open val player: Player,
    protected val inventory: Inventory,
) : Listener {

    private val clickEvents: MutableMap<Int, (InventoryClickEvent) -> Unit> = mutableMapOf()

    constructor(
        plugin: Plugin,
        player: Player,
        title: String,
        size: Int,
    ) : this(plugin, player, Bukkit.createInventory(null, size, title))

    fun inventory(block: () -> Unit) {
        content = block
    }

    fun button(itemStack: ItemStack, index: Int, block: (InventoryClickEvent) -> Unit) =
        setItem(index, itemStack, block)

    fun button(itemStack: ItemStack, index: Pair<Int, Int>, block: (InventoryClickEvent) -> Unit) =
        setItem((index.first - 1) + (index.second - 1) * 9, itemStack, block)

    fun onInventoryClose(block: InventoryCloseEvent.() -> Unit) {
        onInventoryClose = block
    }

    fun onInventoryOpen(block: InventoryOpenEvent.() -> Unit) {
        onInventoryOpen = block
    }

    fun onPlayerInventoryClick(isCancelled: Boolean = false, block: InventoryClickEvent.() -> Unit) {
        onPlayerInventoryClick = isCancelled to block
    }

    private var content: () -> Unit = {}
    private var onInventoryClose: InventoryCloseEvent.() -> Unit = {}
    private var onInventoryOpen: InventoryOpenEvent.() -> Unit = {}
    private var onPlayerInventoryClick: Pair<Boolean, InventoryClickEvent.() -> Unit> = false to {}

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryOpen(event)
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.clickedInventory == player.inventory && player.openInventory.topInventory == inventory) {
            event.isCancelled = onPlayerInventoryClick.first
            onPlayerInventoryClick.second(event)
            return
        }

        if (event.inventory != inventory) {
            return
        }

        event.isCancelled = true
        clickEvents[event.rawSlot]?.invoke(event)
    }

    @EventHandler
    fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryClose(event)
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    open fun refreshContent() {
        inventory.clear()
        clickEvents.clear()
        InventoryClickEvent.getHandlerList().unregister(this@CustomInventory)
        Bukkit.getPluginManager().registerEvents(this@CustomInventory, plugin)
        content()
    }

    fun open() {
        Bukkit.getPluginManager().registerEvents(this@CustomInventory, plugin)
        player.openInventory(inventory)
        content()
    }

    fun openLater() {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable { open() }, 1L)
    }

    fun setItem(index: Int, itemStack: ItemStack?, onClick: InventoryClickEvent.() -> Unit = {}) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }
}