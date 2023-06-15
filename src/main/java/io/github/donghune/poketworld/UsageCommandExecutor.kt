package io.github.donghune.poketworld

import org.bukkit.command.CommandExecutor

interface UsageCommandExecutor : CommandExecutor {
    val usage: String
}