package com.mihir.testing.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mihir.testing.local.ShoppingDao
import com.mihir.testing.local.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}