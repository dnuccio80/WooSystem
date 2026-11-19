package org.example.minisystem.ui.products

import androidx.compose.ui.window.rememberCursorPositionProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.minisystem.domain.models.products.CleanProduct
import org.example.minisystem.domain.models.products.ProductError
import org.example.minisystem.domain.repositories.ProductRepository
import org.example.minisystem.domain.usecases.AddProduct


class ProductsViewModel(
    private val productRepository: ProductRepository,
    private val addProduct: AddProduct
): ViewModel() {

    private val _productsList = productRepository.getAllProducts().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000), emptyList())
    val productsList = _productsList

    private val _editedProduct = MutableStateFlow(CleanProduct().getCleanProduct())
    val editedProduct = _editedProduct.asStateFlow()

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun updateProductName(newValue:String) {
        _editedProduct.update { it.copy(name = newValue) }
    }

    fun updateProductPriceList(newValue:String) {
        val priceList = if (newValue.isBlank()) 0L else newValue.toLong()
        _editedProduct.update { it.copy(listPrice = priceList) }
    }

    fun updateProductSellList(newValue:String) {
        val sellPrice = if (newValue.isBlank()) 0L else newValue.toLong()
        _editedProduct.update { it.copy(sellPrice = sellPrice) }
    }

    fun cleanProductData() {
        _editedProduct.update { CleanProduct().getCleanProduct() }
    }

    fun tryAddProduct(onDone:() -> Unit) {
        viewModelScope.launch {
            try {
                addProduct(_editedProduct.value)
                onDone()
            }catch (e: ProductError) {
                _events.emit(e.msg)
            }
        }
    }

    fun getProductById(id:Int) {
        val product = _productsList.value.first { it.productId == id }
        _editedProduct.update { product }
    }

    fun deleteProduct(id:Int) {
        viewModelScope.launch {
            productRepository.deleteProductById(id)
        }
    }

}