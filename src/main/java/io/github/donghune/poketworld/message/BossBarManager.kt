package io.github.donghune.poketworld.message

import org.bukkit.Bukkit
import org.bukkit.boss.BossBar

object BossBarManager {
    val bossBar: BossBar = Bukkit.createBossBar("공지", null, null)
}