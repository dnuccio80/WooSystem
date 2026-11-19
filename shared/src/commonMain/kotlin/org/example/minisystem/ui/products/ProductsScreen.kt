package org.example.minisystem.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.minisystem.AcceptDeclineButtons
import org.example.minisystem.BodyText
import org.example.minisystem.ConfirmDialog
import org.example.minisystem.GenericButtonText
import org.example.minisystem.GenericTextField
import org.example.minisystem.MainContainer
import org.example.minisystem.RowWithMidBodyAndDescription
import org.example.minisystem.SimpleAdviceDialog
import org.example.minisystem.TitleText
import org.example.minisystem.domain.models.products.Product
import org.example.minisystem.ui.ext.toPrice
import org.example.project.ui.utils.AccentColor
import org.example.project.ui.utils.GrayText
import org.example.project.ui.utils.GreenText
import org.example.project.ui.utils.LightBlue
import org.example.project.ui.utils.PrimaryCardBackground
import org.example.project.ui.utils.SecondaryCardBackground
import org.koin.compose.viewmodel.koinViewModel

class ProductsScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel = koinViewModel<ProductsViewModel>()
        val productsList by viewModel.productsList.collectAsStateWithLifecycle()
        val editedProduct by viewModel.editedProduct.collectAsStateWithLifecycle()
        var isEdit by rememberSaveable { mutableStateOf(false) }
        var showAddProductDialog by rememberSaveable { mutableStateOf(false) }
        var showAdviceMessage by rememberSaveable { mutableStateOf(false) }
        var adviceMsg by rememberSaveable { mutableStateOf("") }
        var showConfirmDeleteMessage by rememberSaveable { mutableStateOf(false) }
        var deleteProductId by rememberSaveable { mutableIntStateOf(0) }

        LaunchedEffect(viewModel.events) {
            viewModel.events.collect { msg ->
                adviceMsg = msg
                showAdviceMessage = true
            }
        }

        MainContainer(
            titleText = "Productos",
            buttonText = "Agregar nuevo producto",
            onButtonClick = {
                viewModel.cleanProductData()
                isEdit = false
                showAddProductDialog = !showAddProductDialog
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(2f)
                ) {
                    if (productsList.isNotEmpty()) {
                        items(productsList) { product ->
                            ProductItem(
                                product,
                                onEdit = {
                                    viewModel.getProductById(product.productId)
                                    isEdit = true
                                    showAddProductDialog = true
                                },
                                onDelete = {
                                    deleteProductId = product.productId
                                    showConfirmDeleteMessage = true
                                })
                        }
                    } else {
                        item {
                            BodyText("No hay productos")
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            if (showAddProductDialog) {
                AddProductDialog(
                    product = editedProduct,
                    isEdit = isEdit,
                    onDismiss = {
                        showAddProductDialog = false
                    },
                    onAccept = {
                        viewModel.tryAddProduct {
                            showAddProductDialog = false
                            viewModel.cleanProductData()
                        }
                    },
                    onActionDone = { action, value ->
                        when (action) {
                            ProductAction.UPDATE_NAME -> {
                                viewModel.updateProductName(value)
                            }

                            ProductAction.UPDATE_LIST_PRICE -> {
                                viewModel.updateProductPriceList(value)
                            }

                            ProductAction.UPDATE_SELL_PRICE -> {
                                viewModel.updateProductSellList(value)
                            }
                        }
                    }
                )
            }
            SimpleAdviceDialog(adviceMsg, showAdviceMessage) { showAdviceMessage = false }
            if (showConfirmDeleteMessage) {
                ConfirmDialog(
                    msg = "Seguro que querés eliminar el producto?",
                    onAccept = {
                        showConfirmDeleteMessage = false
                        viewModel.deleteProduct(deleteProductId)
                        deleteProductId = 0
                    },
                    onDismiss = {
                        showConfirmDeleteMessage = false
                        deleteProductId = 0
                    }
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onEdit: () -> Unit, onDelete: () -> Unit) {

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val cardColor = if (isHovered) SecondaryCardBackground else PrimaryCardBackground

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                product.name,
                color = LightBlue,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            VerticalDivider(modifier = Modifier.height(30.dp), thickness = 2.dp, color = GrayText)
            RowWithMidBodyAndDescription("Precio de compra:", product.listPrice.toPrice())
            VerticalDivider(modifier = Modifier.height(30.dp), thickness = 2.dp, color = GrayText)
            RowWithMidBodyAndDescription("Precio de venta:", product.sellPrice.toPrice())
            VerticalDivider(modifier = Modifier.height(30.dp), thickness = 2.dp, color = GrayText)
            AcceptDeclineButtons(
                acceptText = "Eliminar",
                declineText = "Editar",
                acceptColor = AccentColor,
                declineColor = GrayText,
                modifier = Modifier,
                onDismiss = { onEdit() },
                onAccept = { onDelete() }
            )
        }
    }
}

enum class ProductAction {
    UPDATE_NAME, UPDATE_LIST_PRICE, UPDATE_SELL_PRICE()
}

@Composable
fun AddProductDialog(
    product: Product,
    isEdit: Boolean,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    onActionDone: (ProductAction, String) -> Unit,
) {
    val listPrice = if (product.listPrice == 0L) "" else product.listPrice.toString()
    val sellPrice = if (product.sellPrice == 0L) "" else product.sellPrice.toString()
    val title = if (isEdit) "Editar producto" else "Agregar nuevo producto"

    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Card(
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryCardBackground),
            elevation = CardDefaults.cardElevation(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        TitleText(title)
                    }
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "close dialog",
                            tint = Color.White
                        )
                    }
                }

                GenericTextField(
                    value = product.name,
                    labelText = "Nombre del producto",
                    onValueChange = { onActionDone(ProductAction.UPDATE_NAME, it) }
                )
                GenericTextField(
                    value = listPrice,
                    labelText = "Precio de compra",
                    onlyNumbers = true,
                    isPrice = true,
                    onValueChange = { onActionDone(ProductAction.UPDATE_LIST_PRICE, it) }
                )
                GenericTextField(
                    value = sellPrice,
                    labelText = "Precio de venta",
                    onlyNumbers = true,
                    isPrice = true,
                    onValueChange = { onActionDone(ProductAction.UPDATE_SELL_PRICE, it) }
                )
                AcceptDeclineButtons(
                    onDismiss = { onDismiss() },
                    onAccept = { onAccept() }
                )
            }
        }
    }
}