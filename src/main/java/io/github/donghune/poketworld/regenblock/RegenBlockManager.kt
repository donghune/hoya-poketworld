package io.github.donghune.poketworld.regenblock

import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.jetbrains.exposed.sql.transactions.transaction

object RegenBlockManager {
    private val tasks = mutableListOf<BukkitTask>()
    fun regen(plugin: JavaPlugin, location: Location, regenArea: RegenArea) = transaction {
        val bukkitTask = object : BukkitRunnable() {
            override fun run() {
                location.block.type = regenArea.blocks.toList().random().block
            }
        }.runTaskLater(plugin, regenArea.regenTime.toLong() * 20)
        tasks.add(bukkitTask)
    }

    fun cancelAll() = transaction {
        tasks.forEach { it.cancel() }
        RegenArea.all().forEach {
            (it.pos1..it.pos2).forEach { location ->
                location.block.type = it.blocks.toList().random().block
            }
        }
    }
}

private operator fun Location.rangeTo(pos2: Location): List<Location> {
    val x1 = this.x.toInt()
    val y1 = this.y.toInt()
    val z1 = this.z.toInt()

    val x2 = pos2.x.toInt()
    val y2 = pos2.y.toInt()
    val z2 = pos2.z.toInt()

    val locations = mutableListOf<Location>()

    for (x in x1..x2) {
        for (y in y1..y2) {
            for (z in z1..z2) {
                locations.add(Location(this.world, x.toDouble(), y.toDouble(), z.toDouble()))
            }
        }
    }

    return locations
}
