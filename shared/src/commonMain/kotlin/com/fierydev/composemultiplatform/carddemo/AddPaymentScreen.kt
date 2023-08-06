package com.fierydev.composemultiplatform.carddemo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fierydev.composemultiplatform.stringsLocal
import com.fierydev.composemultiplatform.ui.theme.AppTheme

@Composable
fun ShowCardScreen() {
    AppTheme {
        AddPaymentScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen() {
    var nameText by remember { mutableStateOf(TextFieldValue()) }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }
    var expiryNumber by remember { mutableStateOf(TextFieldValue()) }
    var cvcNumber by remember { mutableStateOf(TextFieldValue()) }

    var isCardShowing by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            item {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Show Card Details",
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    Switch(
                        checked = isCardShowing,
                        onCheckedChange = { isCardShowing = it },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                CardUi(
                    cardHolderName = nameText,
                    cardNumber = cardNumber,
                    expiryNumber = expiryNumber,
                    cvcNumber = cvcNumber,
                    isCardShowing = isCardShowing
                )
            }

            item {
                CardInputTextField(
                    textFieldValue = nameText,
                    label = stringsLocal.card_holder_name,
                    onTextChanged = { nameText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }

            item {
                CardInputTextField(
                    textFieldValue = cardNumber,
                    label = stringsLocal.card_holder_number,
                    keyboardType = KeyboardType.Number,
                    onTextChanged = { cardNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    visualTransformation = CreditCardFilter
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardInputTextField(
                        textFieldValue = expiryNumber,
                        label = stringsLocal.expiry_date,
                        keyboardType = KeyboardType.Number,
                        onTextChanged = { expiryNumber = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    CardInputTextField(
                        textFieldValue = cvcNumber,
                        label = stringsLocal.cvc,
                        keyboardType = KeyboardType.Number,
                        onTextChanged = { cvcNumber = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

private val creditCardOffsetTranslator = object : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 3) return offset
        if (offset <= 7) return offset + 1
        if (offset <= 11) return offset + 2
        if (offset <= 16) return offset + 3
        return 19
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 4) return offset
        if (offset <= 9) return offset - 1
        if (offset <= 14) return offset - 2
        if (offset <= 19) return offset - 3
        return 16
    }
}

val CreditCardFilter = VisualTransformation { text ->
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += " "
    }
    TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}