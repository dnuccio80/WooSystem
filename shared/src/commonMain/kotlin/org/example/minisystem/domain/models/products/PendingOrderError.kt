package org.example.minisystem.domain.models.products

sealed class ProductError(val msg: String): Exception() {
    data object EmptyName: ProductError("Agrega el nombre del producto chupapija")
    data object NoListPrice: ProductError("Te falta el precio de lista")
    data object SellPriceError: ProductError("El precio de venta no puede ser menor al de compra")
}
