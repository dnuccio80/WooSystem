package org.example.minisystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import minisystem.shared.generated.resources.Res
import minisystem.shared.generated.resources.logo
import org.example.minisystem.ui.ext.capitalizeSentences
import org.example.minisystem.ui.ext.capitalizeWords
import org.example.minisystem.ui.ext.toPercentAdd
import org.example.minisystem.ui.ext.toPercentOff
import org.example.minisystem.ui.ext.toPrice
import org.example.project.ui.utils.GrayText
import org.example.project.ui.utils.GreenText
import org.example.project.ui.utils.PrimaryCardBackground
import org.example.project.ui.utils.WhiteText
import org.jetbrains.compose.resources.painterResource


enum class Capitalization {
    WORDS, SENTENCES, NONE
}

@Composable
fun MainHeader() {
    Column(modifier = Modifier.safeContentPadding().fillMaxWidth()) {
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.White)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painterResource(Res.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.width(150.dp)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "notificaciones",
                    tint = Color.White
                )
            }
        }
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.White)
    }

}

@Composable
fun MainContainer(
    titleText:String,
    buttonText:String,
    onButtonClick:() -> Unit,
    content: @Composable () -> Unit,
) {
    Box(Modifier.fillMaxSize().background(color = Color.Black)) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TitleText(titleText)
                GenericButtonText(buttonText, color = GreenText) { onButtonClick() }
            }
            Spacer(Modifier.size(16.dp))
            content()
        }
    }
}

@Composable
fun SearchTextField(
    value: String,
    capitalization: Capitalization = Capitalization.SENTENCES,
    modifier: Modifier = Modifier,
    placeHolderText: String = "Buscar...",
    onDelete: () -> Unit,
    onValueChange: (String) -> Unit,
) {

    val capitalizedValue = when (capitalization) {
        Capitalization.WORDS -> value.capitalizeWords()
        Capitalization.SENTENCES -> value.capitalizeSentences()
        Capitalization.NONE -> value
    }

    TextField(
        value = capitalizedValue,
        onValueChange = { onValueChange(it) },
        modifier = modifier,
        placeholder = { Text(placeHolderText) },
        trailingIcon = {
            if (value.isBlank()) {
                Icon(
                    Icons.Filled.Search, contentDescription = null
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable { onDelete() }.pointerHoverIcon(
                        PointerIcon.Hand
                    )
                )
            }

        },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedPlaceholderColor = Color.White,
            unfocusedPlaceholderColor = Color.White,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Green,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Green
        )
    )
}

@Composable
fun TitleText(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleLargeEmphasized,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun BodyText(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun GenericButtonText(text: String, color: Color = Color.DarkGray, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun GenericTextField(
    value: String,
    labelText: String,
    onlyNumbers: Boolean = false,
    isPrice: Boolean = false,
    isPercentOff: Boolean = false,
    isPercentAdd: Boolean = false,
    modifier: Modifier = Modifier.fillMaxWidth(),
    capitalizationMethod: Capitalization = Capitalization.SENTENCES,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState()

    TextField(
        value = when {
            isPrice && !isFocused.value && value.isNotBlank() -> value.toLong().toPrice()
            isPercentOff && !isFocused.value && value.isNotBlank() -> value.toLong().toPercentOff()
            isPercentAdd && !isFocused.value && value.isNotBlank() -> value.toLong().toPercentAdd()
            else -> value
        },
        modifier = modifier,
        onValueChange = { valueChange ->
            if (onlyNumbers) {
                val newVal = valueChange.filter { it.isDigit() }
                onValueChange(newVal)
            } else {
                val capitalized = when (capitalizationMethod) {
                    Capitalization.WORDS -> valueChange.capitalizeWords()
                    Capitalization.SENTENCES -> valueChange.capitalizeSentences()
                    Capitalization.NONE -> valueChange
                }
                onValueChange(capitalized)
            }
        },
        interactionSource = interactionSource,
        label = { Text(labelText) },
        shape = RoundedCornerShape(4.dp),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedPlaceholderColor = WhiteText,
            unfocusedPlaceholderColor = WhiteText,
            focusedTrailingIconColor = WhiteText,
            unfocusedTrailingIconColor = WhiteText,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = GreenText,
            unfocusedIndicatorColor = GrayText,
            cursorColor = GreenText,
            focusedLabelColor = GreenText,
            unfocusedLabelColor = GrayText
        ),
        trailingIcon = trailingIcon,
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun AcceptDeclineButtons(
    acceptText: String = "Aceptar",
    declineText: String = "Cancelar",
    acceptColor: Color = GreenText,
    declineColor: Color = GrayText,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenericButtonText(declineText, color = declineColor) { onDismiss() }
            GenericButtonText(acceptText, color = acceptColor) { onAccept() }
        }
    }
}

@Composable
fun SimpleAdviceDialog(msg: String, show: Boolean, onDismiss: () -> Unit) {
    if (!show) return
    Dialog(onDismissRequest = { }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = PrimaryCardBackground),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(msg, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                GenericButtonText(
                    text = "Aceptar",
                    onClick = { onDismiss() }
                )
            }
        }
    }
}

@Composable
fun RowWithMidBodyAndDescription(title: String, description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            description,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = GreenText
        )
    }
}

@Composable
fun ConfirmDialog(msg: String, onAccept: () -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = PrimaryCardBackground),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(msg, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                AcceptDeclineButtons(
                    onDismiss = { onDismiss() },
                    onAccept = { onAccept() }
                )
            }
        }
    }
}

