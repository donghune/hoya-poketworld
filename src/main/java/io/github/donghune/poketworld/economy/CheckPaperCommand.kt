package io.github.donghune.poketworld.economy

import io.github.donghune.poketworld.UsageCommandExecutor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CheckPaperCommand : UsageCommandExecutor {

    override val usage: String = "/checkpaper <price> <amount>"

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("플레이어만 사용 가능한 명령어 입니다.")
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage(usage)
            return true
        }
        val price = args[0].toIntOrNull() ?: run {
            sender.sendMessage("가격은 숫자로 입력해주세요.")
            return true
        }
        val amount = args[1].toIntOrNull() ?: 1
        if (price <= 0) {
            sender.sendMessage("가격은 0보다 커야합니다.")
            return true
        }
        if (sender.inventory.find { itemStack -> itemStack == null || itemStack.type == Material.AIR } == null) {
            sender.sendMessage("인벤토리가 가득 찼습니다.")
            return true
        }
        if (sender.money < price) {
            sender.sendMessage("돈이 부족합니다.")
            return true
        }
        sender.money -= price
        sender.inventory.addItem(generatePaperMoney(price).apply { this.amount = amount })
        return true
    }
}