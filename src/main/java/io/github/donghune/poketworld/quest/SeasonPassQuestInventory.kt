package io.github.donghune.poketworld.quest

import io.github.donghune.poketworld.CustomInventory
import io.github.donghune.poketworld.icon
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.jetbrains.exposed.sql.transactions.transaction

class SeasonPassQuestInventory(
    plugin: Plugin, player: Player
) : CustomInventory(plugin, player, "시즌패스 퀘스트", 9) {
    object Icons {
        val DAILY_QUEST: (Player) -> ItemStack = { player ->
            icon {
                name = "일일 퀘스트"
                lore = getQuests(player)
            }
        }
        val WEEKLY_QUEST: (Player) -> ItemStack = { player ->
            icon {
                name = "주간 퀘스트"
                lore = getQuests(player)
            }
        }
        val SEASON_QUEST: (Player) -> ItemStack = { player ->
            icon {
                name = "시즌 퀘스트"
                lore = getQuests(player)
            }
        }

        private fun getQuests(player: Player) = transaction {
            val playerQuests = PlayerQuest.find { PlayerQuests.playerId eq player.uniqueId }.toList()
            playerQuests.map {
                if (it.isComplete) {
                    "§a${it.quest.name}"
                } else {
                    "§c${it.quest.name}"
                }
            }
        }
    }

    init {
        inventory {
            button(Icons.DAILY_QUEST(player), 3 to 1) {

            }
            button(Icons.WEEKLY_QUEST(player), 5 to 1) {

            }
            button(Icons.SEASON_QUEST(player), 7 to 1) {

            }
        }
    }
}