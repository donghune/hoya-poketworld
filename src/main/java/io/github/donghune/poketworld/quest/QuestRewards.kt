package io.github.donghune.poketworld.quest

import io.github.donghune.poketworld.economy.money
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object QuestRewards : IntIdTable("quest_rewards") {
    val questId = reference("quest_id", Quests)
    val material = enumeration<Material>("material")
    val amount = integer("amount")
    val money = integer("money")
    val seasonPassExp = integer("season_pass_exp")
}

class QuestReward(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<QuestReward>(QuestRewards)

    var questId by QuestRewards.questId
    var material by QuestRewards.material
    var amount by QuestRewards.amount
    var money by QuestRewards.money
    var seasonPassExp by QuestRewards.seasonPassExp

    val quest by Quest referencedOn QuestRewards.questId

    fun give(player: Player) = transaction {
        player.inventory.addItem(ItemStack(material, amount))
        player.money += money
        player.seasonPass.addExp(seasonPassExp)
    }
}