package io.github.donghune.poketworld.quest

import org.bukkit.entity.Player
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction

object PlayerSeasonPasses : IntIdTable() {
    val uuid = uuid("uuid")
    val currentLevel = integer("current_level")
    val currentExp = integer("current_exp")
}

class PlayerSeasonPass(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerSeasonPass>(PlayerSeasonPasses)

    var uuid by PlayerSeasonPasses.uuid
    var currentLevel by PlayerSeasonPasses.currentLevel
    var currentExp by PlayerSeasonPasses.currentExp

    fun addExp(exp: Int) = transaction {
        currentExp += exp
        if (isLevelUp()) {
            val levelUp = exp / 10
            currentExp = exp % 10
            currentLevel += levelUp
        }
    }

    fun addLevel(level: Int) = transaction {
        currentLevel += level
    }

    fun setExp(exp: Int) = transaction {
        currentExp = exp
    }

    fun setLevel(level: Int) = transaction {
        currentLevel = level
    }

    fun takeExp(exp: Int) = transaction {
        currentExp -= exp
        if (isLevelDown()) {
            val levelDown = exp / 10
            currentExp = 10 + (exp % 10)
            currentLevel -= levelDown
        }
    }

    fun takeLevel(level: Int) = transaction {
        currentLevel -= level
    }

    fun resetAll() = transaction {
        currentLevel = 0
        currentExp = 0
    }

    fun resetExp() = transaction {
        currentExp = 0
    }

    fun resetLevel() = transaction {
        currentLevel = 0
    }

    fun isLevelUp(): Boolean = transaction {
        currentExp >= 10
    }

    fun isLevelDown(): Boolean = transaction {
        currentExp < 0
    }
}

val Player.seasonPass: PlayerSeasonPass
    get() = transaction {
        PlayerSeasonPass.find { PlayerSeasonPasses.uuid eq uniqueId }.firstOrNull()
            ?: PlayerSeasonPass.new {
                uuid = uniqueId
                currentLevel = 0
                currentExp = 0
            }
    }