package org.example.minisystem.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "inventory_movements",
    foreignKeys = [
        ForeignKey(
            entity = ProductVariantEntity::class,
            parentColumns = ["id"],
            childColumns = ["variantId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["variantId"])]
)
data class InventoryMovementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val variantId: Long,
    // Positive = entry / Negative = exit
    val quantityDelta: Int,
    // INITIAL, SALE, ADJUSTMENT, RETURN
    val movementType: String,
    val referenceId: String? = null,
    val createdAt: Long
)