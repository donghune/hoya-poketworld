package io.github.donghune.poketworld.customitem

import io.github.donghune.poketworld.database.itemStack
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CustomItems : IntIdTable("items") {
    val itemStack = itemStack("item_stack")
}

class CustomItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomItem>(CustomItems)

    var itemStack by CustomItems.itemStack
}