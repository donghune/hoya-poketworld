package io.github.donghune.poketworld.regenblock

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class RegenBlockCommand : UsageCommandExecutor {
    override val usage: String
        get() = """
            /regenblock area add <name> -> 지정한 영역을 생성합니다.
            /regenblock area remove <name> -> 지정한 영역을 삭제합니다.
            /regenblock area status <name> -> 지정한 영역의 정보를 확인합니다.
            /regenblock block set <name> <block> <weight> -> 리젠 될 블럭을 추가합니다.
            /regenblock block remove <name> <block> -> 리젠 될 블럭을 삭제합니다.
        """.trimIndent()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean = transaction {
        val player = sender as? Player
        if (player == null) {
            sender.sendMessage("플레이어만 사용 가능한 명령어 입니다.")
            return@transaction false
        }
        if (args.isEmpty()) {
            sender.sendMessage(usage)
            return@transaction false
        }

        when (args[0]) {
            "area" -> {
                when (args[1]) {
                    "add" -> {
                        val name = args.getOrNull(2)
                        if (name == null) {
                            player.sendMessage("영역 이름을 입력해주세요.")
                            return@transaction false
                        }
                        val pos1 = PlayerAreaManager.getPos1(player)
                        if (pos1 == null) {
                            player.sendMessage("pos1 을 설정해주세요.")
                            return@transaction false
                        }
                        val pos2 = PlayerAreaManager.getPos2(player)
                        if (pos2 == null) {
                            player.sendMessage("pos2 를 설정해주세요.")
                            return@transaction false
                        }
                        val regenTime = 0
                        RegenArea.new {
                            this.name = name
                            this.pos1 = pos1
                            this.pos2 = pos2
                            this.regenTime = regenTime
                        }
                        player.sendMessage("RegenArea $name 이 생성되었습니다.")
                    }

                    "remove" -> {
                        val name = args.getOrNull(2)
                        if (name == null) {
                            player.sendMessage("영역 이름을 입력해주세요.")
                            return@transaction false
                        }
                        RegenArea.find { RegenAreas.name eq name }.forEach { it.delete() }
                        player.sendMessage("RegenArea $name 이 삭제되었습니다.")
                    }

                    "status" -> {
                        val name = args.getOrNull(2)
                        if (name == null) {
                            player.sendMessage("영역 이름을 입력해주세요.")
                            return@transaction false
                        }
                        val area = RegenArea.find { RegenAreas.name eq name }.first()
                        val pos1 = area.pos1
                        val pos2 = area.pos2
                        val regenTime = area.regenTime
                        player.sendMessage("RegenArea $name 의 정보")
                        player.sendMessage("pos1: $pos1")
                        player.sendMessage("pos2: $pos2")
                        player.sendMessage("regenTime: $regenTime")
                        player.sendMessage("blocks")
                        area.blocks.forEach {
                            player.sendMessage("${it.block} ${it.weight}")
                        }
                    }
                }
            }

            "block" -> {
                when (args[1]) {
                    "set" -> {
                        val name = args.getOrNull(2)
                        if (name == null) {
                            player.sendMessage("영역 이름을 입력해주세요.")
                            return@transaction false
                        }
                        val block = args.getOrNull(3)
                        if (block == null) {
                            player.sendMessage("블럭 이름을 입력해주세요.")
                            return@transaction false
                        }
                        val weight = args.getOrNull(4)?.toIntOrNull()
                        if (weight == null) {
                            player.sendMessage("가중치를 입력해주세요.")
                            return@transaction false
                        }
                        RegenBlock.new {
                            this.area = RegenArea.find { RegenAreas.name eq name }.first()
                            this.block = Material.valueOf(block)
                            this.weight = weight
                        }
                        player.sendMessage("RegenBlock $block 이 $name 에 추가되었습니다.")
                    }

                    "remove" -> {
                        val name = args.getOrNull(2)
                        if (name == null) {
                            player.sendMessage("영역 이름을 입력해주세요.")
                            return@transaction false
                        }
                        val block = args.getOrNull(3)
                        if (block == null) {
                            player.sendMessage("블럭 이름을 입력해주세요.")
                            return@transaction false
                        }
                        RegenBlock.find {
                            (RegenBlocks.area eq RegenArea.find { RegenAreas.name eq name }.first().id) and
                                    (RegenBlocks.block eq Material.valueOf(block))
                        }.forEach { it.delete() }
                        player.sendMessage("RegenBlock $block 이 $name 에서 삭제되었습니다.")
                    }
                }
            }
        }
        return@transaction true
    }
}