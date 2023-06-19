package io.github.donghune.poketworld.quest

import org.bukkit.entity.Player
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Quests : IntIdTable("quests") {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val action = enumerationByName("action", 255, QuestAction::class)
    val target = varchar("target", 255)
    val amount = integer("amount")
    val type = enumerationByName("type", 255, QuestType::class)
}

class Quest(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Quest>(Quests)

    var name by Quests.name
    var description by Quests.description
    var action by Quests.action
    var target by Quests.target
    var amount by Quests.amount
    var type by Quests.type

    val rewards by QuestReward referrersOn QuestRewards.questId
}

enum class QuestType {
    DAILY,
    WEEKLY,
    SEASON,
}

enum class QuestAction {
    PIXELMON_CATCH,
    PIXELMON_KILL,
    PIXELMON_EVOLVE,
    PIXELMON_LEVEL_UP,
    PIXELMON_TRADE,
    PIXELMON_BREED,
    PIXELMON_HATCH,
    BLOCK_BREAK,
    BLOCK_PLACE,
    ITEM_CRAFT,
    ITEM_SMELT,
    PLAYER_JOIN,
}