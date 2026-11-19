package org.example.minisystem.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.minisystem.domain.models.products.Product

@Entity(
    tableName = "products"
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val categoryId: Int,
    val sellPrice: Long,
    val imageUrl: String?,
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            categoryId = categoryId,
            sellPrice = sellPrice,
            imageUrl = imageUrl,
        )
    }
}
