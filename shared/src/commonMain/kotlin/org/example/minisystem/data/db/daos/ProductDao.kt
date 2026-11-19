package org.example.minisystem.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.example.minisystem.data.db.entities.ProductEntity
import org.example.minisystem.data.db.entities.ProductVariantEntity

@Dao
interface ProductDao {
//    @Query("SELECT * FROM ProductEntity")
//    fun getAllProducts(): Flow<List<ProductEntity>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addProduct(productEntity: ProductEntity)
//
//    @Query("DELETE FROM ProductEntity WHERE productId = :productId")
//    suspend fun deleteProductById(productId:Int)

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): ProductEntity?

    @Query("""
        SELECT * FROM product_variants
        WHERE productId = :productId
    """)
    suspend fun getVariants(productId: Long): List<ProductVariantEntity>

    @Insert
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert
    suspend fun insertVariants(
        variants: List<ProductVariantEntity>
    )

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Update
    suspend fun updateVariant(variant: ProductVariantEntity)

    @Query("""
        UPDATE product_variants
        SET stock = :stock
        WHERE id = :variantId
    """)
    suspend fun updateStock(
        variantId: Long,
        stock: Int
    )

    @Query("""
        SELECT * FROM product_variants
        WHERE sku = :sku
    """)
    suspend fun getVariantBySku(sku: String): ProductVariantEntity?

}