package io.github.donghune.poketworld.regenblock

import org.bukkit.Material
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RegenBlocks : IntIdTable("regen_block") {
    val area = reference("area", RegenAreas)
    val block = enumeration<Material>("block")
    val weight = integer("weight")
}

class RegenBlock(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RegenBlock>(RegenBlocks)

    var area by RegenArea referencedOn RegenBlocks.area
    var block by RegenBlocks.block
    var weight by RegenBlocks.weight
}