package io.github.donghune.poketworld.quest

import io.github.donghune.poketworld.CustomInventory
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class SeasonPassRewardInventory(
    plugin: Plugin, player: Player
) : CustomInventory(plugin, player, "시즌패스 보상", 9) {
    init {
        inventory {

        }
    }
}