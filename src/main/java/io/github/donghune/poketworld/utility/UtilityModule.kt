package io.github.donghune.poketworld.utility

import io.github.donghune.poketworld.Module
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

// Utility
//전체 지급 -> 명령어를 통해 손에 든 아이템을 모든 플레이어에게 지급 하여야 합니다.
//기본템 -> 기본템 명령어를 입력 시 기본템으로 설정된 아이템이 지급 되어야 합니다.
//폭팔방지 -> 이벤트를 체크해서 폭팔 시 캔슬 되어야 합니다.
//배고픔 고정 -> 이벤트를 체크해서 배고픔 감소 시 캔슬 되어야 합니다.
//횃불 -> 횃불을 하나 지급합니다.
class UtilityModule : Module() {
    override fun onEnable(plugin: JavaPlugin) {
        Bukkit.getPluginCommand("giveall")?.executor = GiveAllCommand()
        Bukkit.getPluginCommand("basicitem")?.executor = BasicItemCommand()
        Bukkit.getPluginCommand("torch")?.executor = TorchCommand()

        Bukkit.getPluginManager().registerEvents(UtilityListener(), plugin)
    }

    override fun onDisable(plugin: JavaPlugin) {

    }
}

