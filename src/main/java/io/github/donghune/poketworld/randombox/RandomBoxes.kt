package io.github.donghune.poketworld.randombox

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RandomBoxes : IntIdTable("random_box") {
    val name = varchar("name", 20)
    val description = varchar("description", 100)
}

class RandomBox(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RandomBox>(RandomBoxes)

    var name by RandomBoxes.name
    var description by RandomBoxes.description

    val items by RandomBoxItem referrersOn RandomBoxItems.randomBoxId
}
