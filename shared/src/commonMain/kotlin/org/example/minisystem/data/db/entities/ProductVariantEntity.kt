package org.example.minisystem.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "product_variants",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["productId"]),
        Index(value = ["sku"], unique = true)
    ]
)
data class ProductVariantEntity(
    val id:Int,
    val productId:Int,
    val sku:String,
    val size:Int,
    val stock:Int,
    val wooProductId:Long? = null,
    val wooVariationId:Long? = null
)
