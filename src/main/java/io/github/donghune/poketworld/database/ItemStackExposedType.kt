package io.github.donghune.poketworld.database

import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.currentDialect

class ItemStackExposedType : ColumnType() {
    override fun sqlType(): String {
        return currentDialect.dataTypeProvider.blobType()
    }

    override fun valueFromDB(value: Any): ItemStack = when (value) {
        is ByteArray -> value.toItemStack()
        is ItemStack -> value
        else -> error("$value is not a valid ItemStack on from db value is ${value::class.simpleName}")
    }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is ItemStack -> value.toBytes()
        else -> error("$value is not a valid ItemStack on to db")
    }

    companion object {
        internal val INSTANCE = ItemStackExposedType()
    }
}

fun Table.itemStack(name: String): Column<ItemStack> = registerColumn(name, ItemStackExposedType.INSTANCE)
