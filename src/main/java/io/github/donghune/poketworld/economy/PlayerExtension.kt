package io.github.donghune.poketworld.economy

import org.bukkit.entity.Player

var Player.money: Int
    get() = EconomyModule.economy.getBalance(this).toInt()
    set(value) {
        EconomyModule.economy.depositPlayer(this, value.toDouble())
    }