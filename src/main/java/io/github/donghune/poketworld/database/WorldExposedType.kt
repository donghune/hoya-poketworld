package io.github.donghune.poketworld.database

import org.bukkit.Bukkit
import org.bukkit.World
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect

class WorldExposedType : ColumnType() {
    override fun sqlType(): String {
        return currentDialect.dataTypeProvider.textType()
    }

    override fun valueFromDB(value: Any): World = when (value) {
        is String -> value.toWorld()
        is World -> value
        else -> error("$value is not a valid World on from db value is ${value::class.simpleName}")
    }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is World -> value.name
        else -> error("$value is not a valid World on to db")
    }

    companion object {
        internal val INSTANCE = WorldExposedType()
    }
}

private fun String.toWorld(): World {
    return Bukkit.getWorld(this) ?: throw RuntimeException("WorldNotFoundException: $this")
}

fun Table.world(name: String): Column<World> = registerColumn(name, WorldExposedType.INSTANCE)
