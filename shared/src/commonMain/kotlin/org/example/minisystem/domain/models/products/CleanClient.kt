package org.example.minisystem.domain.models.products

class CleanProduct {

    fun getCleanProduct(): Product {
        return Product(
            productId = 0,
            name = "",
            listPrice = 0L,
            sellPrice = 0L
        )
    }

}