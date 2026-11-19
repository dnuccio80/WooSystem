package org.example.minisystem.domain.usecases

import org.example.minisystem.domain.models.products.Product
import org.example.minisystem.domain.models.products.ProductError
import org.example.minisystem.domain.repositories.ProductRepository

class AddProduct(private val repository: ProductRepository) {

    suspend operator fun invoke(product: Product) {

        when {
            product.name.isBlank() -> throw ProductError.EmptyName
            product.listPrice <= 0 -> throw ProductError.NoListPrice
            product.sellPrice < product.listPrice  -> throw ProductError.SellPriceError
        }

        repository.addProduct(product)

    }

}