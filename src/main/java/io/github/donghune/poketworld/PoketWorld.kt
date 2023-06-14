package io.github.donghune.poketworld

import io.github.donghune.poketworld.customitem.CustomItemModule
import io.github.donghune.poketworld.database.SQLiteModule
import org.bukkit.plugin.java.JavaPlugin

class PoketWorld : JavaPlugin() {
    private val modules = listOf(SQLiteModule(), CustomItemModule())

    override fun onEnable() {
        modules.forEach { it.onEnable(plugin = this) }
    }

    override fun onDisable() {
        modules.forEach { it.onDisable(plugin = this) }
    }
}

// RegenBlock
// Message
// 보스바공지 -> 보스바에 메시지를 출력하며, 추가 삭제가 가능해야 합니다.
// 일반공지 -> 일반 채팅에서 눈에 띄게 메시지가 출력되어야 합니다.
// 지역채팅 -> 같은 지역 내에서만 메시지가 전송되어야 합니다.
// EconomyUtil
// 수표 -> 명령어를 통해 재화를 아이템 화 시킵니다.
// RandomBox
// 랜덤박스 생성
// 랜덤박스 지급
