package com.fierydev.composemultiplatform.carddemo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun CardUi(
    cardHolderName: TextFieldValue,
    cardNumber: TextFieldValue,
    expiryNumber: TextFieldValue,
    cvcNumber: TextFieldValue,
    isCardShowing: Boolean
) {
    var visaType by remember { mutableStateOf(CardType.None) }
    val length = if (cardNumber.text.length > 16) 16 else cardNumber.text.length
    val defaultCardNumber =
        remember { "*****************" }.replaceRange(0..length, cardNumber.text.take(16))

    //handle card type logic.
    visaType = if (cardNumber.text.length >= 8) {
        CardType.Visa
    } else {
        CardType.None
    }

    val animatedColor = animateColorAsState(
        targetValue = if (visaType == CardType.Visa) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary
        }
    )

    AnimatedVisibility(visible = isCardShowing) {
        Column {
            CardViewFrontAndBack(
                true, animatedColor, defaultCardNumber, cardHolderName, expiryNumber, cvcNumber
            )
            CardViewFrontAndBack(
                false, animatedColor, defaultCardNumber, cardHolderName, expiryNumber, cvcNumber
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CardViewFrontAndBack(
    isFront: Boolean = true,
    animatedColor: State<Color>,
    initial: String,
    nameText: TextFieldValue,
    expiryNumber: TextFieldValue,
    cvcNumber: TextFieldValue
) {
    Box(
        modifier = Modifier.padding(top = 20.dp),
    ) {

        Surface(
            modifier = Modifier.padding(16.dp).fillMaxWidth().height(220.dp),
            shape = RoundedCornerShape(25.dp),
            color = animatedColor.value,
            tonalElevation = 18.dp,
            shadowElevation = 10.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isFront){
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(res = "card_symbol.png"),
                                contentDescription = "Visa Logo",
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                text = "VISA",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White
                            )
                        }

                        Text(
                            text = initial.chunked(4).joinToString(" "),
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            color = Color.White,
                            modifier = Modifier.animateContentSize(spring())
                                .padding(vertical = 16.dp, horizontal = 16.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "CARD HOLDER",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Text(
                                    text = nameText.text,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    modifier = Modifier.animateContentSize(TweenSpec(300))
                                        .padding(start = 16.dp, bottom = 16.dp)
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "Expiry",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Text(
                                    text = expiryNumber.text.take(4).chunked(2).joinToString("/"),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White,
                                    modifier = Modifier.animateContentSize(TweenSpec(300))
                                        .padding(end = 16.dp, bottom = 16.dp)
                                )
                            }
                        }
                    }
                }else{
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(
                            modifier = Modifier.height(40.dp).background(Color.Black).fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = !isFront,
            modifier = Modifier.padding(end = 50.dp, bottom = 50.dp).align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = Modifier.defaultMinSize(minWidth = 60.dp).clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cvcNumber.text,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    modifier = Modifier.animateContentSize()
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                )
            }
        }
    }
}


enum class CardType(
    val title: String,
    val image: String,
) {
    None("", "visa_logo.png"), Visa("visa", "visa_logo.png"),
}