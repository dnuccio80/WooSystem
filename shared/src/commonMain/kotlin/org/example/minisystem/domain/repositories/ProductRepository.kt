package org.example.minisystem.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.example.minisystem.domain.models.products.Product

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun addProduct(product: Product)
    suspend fun deleteProductById(productId:Int)
}