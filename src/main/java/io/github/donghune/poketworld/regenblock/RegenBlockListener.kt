package io.github.donghune.poketworld.regenblock

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.transactions.transaction

class RegenBlockListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        val player = event.player
        if (!player.isOp) {
            return
        }
        if (player.inventory.itemInMainHand.type != Material.EMERALD) {
            return
        }
        val block = event.clickedBlock ?: return
        when (event.action) {
            Action.LEFT_CLICK_BLOCK -> {
                PlayerAreaManager.setPos1(player, block.location)
                event.isCancelled = true
                player.sendMessage("pos1: ${block.location}")
            }

            Action.RIGHT_CLICK_BLOCK -> {
                PlayerAreaManager.setPos2(player, block.location)
                event.isCancelled = true
                player.sendMessage("pos2: ${block.location}")
            }

            else -> return
        }
    }

    @EventHandler
    fun onBlockBreakEvent(event: BlockBreakEvent) = transaction {
        val block = event.block

        val regenArea = RegenArea.all().find { it.contains(block.location) } ?: return@transaction
        RegenBlockManager.regen(plugin, block.location, regenArea)
    }
}