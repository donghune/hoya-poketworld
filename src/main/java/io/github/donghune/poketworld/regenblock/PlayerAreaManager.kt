package io.github.donghune.poketworld.regenblock

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.UUID

object PlayerAreaManager {
    private val pos1 = mutableMapOf<UUID, Location>()
    private val pos2 = mutableMapOf<UUID, Location>()

    fun setPos1(player: Player, location: Location) {
        pos1[player.uniqueId] = location
    }

    fun setPos2(player: Player, location: Location) {
        pos2[player.uniqueId] = location
    }

    fun getPos1(player: Player): Location? {
        return pos1[player.uniqueId]
    }

    fun getPos2(player: Player): Location? {
        return pos2[player.uniqueId]
    }
}