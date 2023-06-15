package io.github.donghune.poketworld.regenblock

import io.github.donghune.poketworld.database.location
import org.bukkit.Location
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RegenAreas : IntIdTable("regen_area") {
    val name = varchar("name", 16)
    val pos1 = location("pos1")
    val pos2 = location("pos2")
    val regenTime = integer("regen_time")
}

class RegenArea(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RegenArea>(RegenAreas)

    var name by RegenAreas.name
    var pos1 by RegenAreas.pos1
    var pos2 by RegenAreas.pos2
    var regenTime by RegenAreas.regenTime

    val blocks by RegenBlock referrersOn RegenBlocks.area

    fun contains(location: Location): Boolean {
        val x = location.x
        val y = location.y
        val z = location.z

        val x1 = pos1.x
        val y1 = pos1.y
        val z1 = pos1.z

        val x2 = pos2.x
        val y2 = pos2.y
        val z2 = pos2.z

        return (x in x1..x2) && (y in y1..y2) && (z in z1..z2)
    }
}