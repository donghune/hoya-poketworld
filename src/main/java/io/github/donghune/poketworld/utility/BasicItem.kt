package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.database.itemStack
import org.bukkit.entity.Player
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object BasicItems : IntIdTable("basic_items") {
    val itemStack = itemStack("item_stack")
}

class BasicItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BasicItem>(BasicItems)

    var itemStack by BasicItems.itemStack
}

object PlayerBasicItems : IntIdTable("player_basic_items") {
    val player = uuid("uuid")
}

class PlayerBasicItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerBasicItem>(PlayerBasicItems)

    var player by PlayerBasicItems.player
}

var Player.isReceiveBasicItem: Boolean
    get() = PlayerBasicItem.find { PlayerBasicItems.player eq uniqueId }.count() > 0
    set(value) = transaction {
        if (value) {
            PlayerBasicItem.new {
                player = uniqueId
            }
        } else {
            PlayerBasicItem.find { PlayerBasicItems.player eq uniqueId }.forEach { it.delete() }
        }
    }
