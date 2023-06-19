package io.github.donghune.poketworld.quest

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PlayerQuests : IntIdTable("player_quests") {
    val questId = reference("quest_id", Quests)
    val playerId = uuid("player_id")
    val isComplete = bool("is_complete")
}

class PlayerQuest(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerQuest>(PlayerQuests)

    var questId by PlayerQuests.questId
    var playerId by PlayerQuests.playerId
    var isComplete by PlayerQuests.isComplete

    val quest by Quest referencedOn PlayerQuests.questId
}