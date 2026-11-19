package org.example.minisystem.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import org.example.minisystem.data.db.daos.InventoryMovementDao
import org.example.minisystem.data.db.daos.ProductDao
import org.example.minisystem.data.db.entities.ProductEntity
import org.example.minisystem.data.db.entities.ProductVariantEntity

const val DATABASE_NAME = "ss_app_database.db"

expect object SystemCTor : RoomDatabaseConstructor<SystemDatabase>

@Database(
    entities = [ProductEntity::class, ProductVariantEntity::class],
    version = 2,
    exportSchema = true
)
@ConstructedBy(SystemCTor::class)
@TypeConverters(Converters::class)
abstract class SystemDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun inventoryMovementDao(): InventoryMovementDao
}