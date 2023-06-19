package io.github.donghune.poketworld.quest

import io.github.donghune.poketworld.CustomInventory
import io.github.donghune.poketworld.icon
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class SeasonPassInventory(
    plugin: Plugin,
    player: Player
) : CustomInventory(plugin, player, "시즌패스", 9) {
    object Icons {
        val SEASON_PASS_QUEST = icon {
            name = "시즌패스 퀘스트"
            lore = listOf(
                "시즌패스 퀘스트를 확인합니다."
            )
        }
        val SEASON_PASS_INFO = icon {
            name = "시즌패스 정보"
            lore = listOf(
                "시즌패스 정보를 확인합니다."
            )
        }
        val SEASON_PASS_REWARD = icon {
            name = "시즌패스 보상"
            lore = listOf(
                "시즌패스 보상을 받습니다."
            )
        }
    }

    init {
        inventory {
            button(Icons.SEASON_PASS_QUEST, 3 to 1) {
                SeasonPassQuestInventory(plugin, player).open()
            }
            button(Icons.SEASON_PASS_INFO, 5 to 1) {
                player.sendMessage("시즌패스 정보")
            }
            button(Icons.SEASON_PASS_REWARD, 7 to 1) {
                SeasonPassRewardInventory(plugin, player).open()
            }
        }
    }
}