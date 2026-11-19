package org.example.minisystem.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.minisystem.data.db.entities.InventoryMovementEntity


@Dao
interface InventoryMovementDao {

    @Insert
    suspend fun insert(
        movement: InventoryMovementEntity
    )

    @Query("""
        SELECT * FROM inventory_movements
        WHERE variantId = :variantId
        ORDER BY createdAt DESC
    """)
    fun observeMovements(
        variantId: Long
    ): Flow<List<InventoryMovementEntity>>

    @Query("""
        SELECT * FROM inventory_movements
        ORDER BY createdAt DESC
    """)
    fun observeAllMovements():
            Flow<List<InventoryMovementEntity>>
}