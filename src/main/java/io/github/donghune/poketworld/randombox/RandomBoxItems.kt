package io.github.donghune.poketworld.randombox

import io.github.donghune.poketworld.database.itemStack
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RandomBoxItems : IntIdTable("random_box_item") {
    val randomBoxId = reference("random_box_id", RandomBoxes)
    val itemStack = itemStack("item_stack")
    val weight = integer("weight")
}

class RandomBoxItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RandomBoxItem>(RandomBoxItems)

    var randomBox by RandomBox referencedOn RandomBoxItems.randomBoxId
    var itemStack by RandomBoxItems.itemStack
    var weight by RandomBoxItems.weight
}