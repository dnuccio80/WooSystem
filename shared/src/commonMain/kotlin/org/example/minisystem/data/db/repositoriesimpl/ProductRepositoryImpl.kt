package org.example.minisystem.data.db.repositoriesimpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.minisystem.data.db.SystemDatabase
import org.example.minisystem.domain.models.products.Product
import org.example.minisystem.domain.repositories.ProductRepository

class ProductRepositoryImpl(private val db: SystemDatabase): ProductRepository {
    override fun getAllProducts(): Flow<List<Product>> {
        return db.productDao().getAllProducts().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addProduct(product: Product) {
        db.productDao().addProduct(product.toEntity())
    }

    override suspend fun deleteProductById(productId: Int) {
        db.productDao().deleteProductById(productId)
    }
}