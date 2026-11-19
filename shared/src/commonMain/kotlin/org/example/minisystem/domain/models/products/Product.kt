package org.example.minisystem.domain.models.products

import org.example.minisystem.data.db.entities.ProductEntity

data class Product(
    val id:Int,
    val name:String,
    val description:String,
    val categoryId:Int,
    val sellPrice: Long,
    val imageUrl:String?,
) {
    fun toEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            name = name,
            description = description,
            categoryId = categoryId,
            sellPrice = sellPrice,
            imageUrl = imageUrl,
        )
    }
}