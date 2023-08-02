package com.fierydev.composemultiplatform.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.fierydev.composemultiplatform.carddemo.CardUi
import com.fierydev.composemultiplatform.carddemo.ShowCardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowCardScreen()
        }
    }
}

//@Preview
//@Composable
//fun PreviewPaymentCard() {
//    CardUi(
//        TextFieldValue("Dinesh"),
//        TextFieldValue("*****************"),
//        TextFieldValue("5487"),
//        TextFieldValue("123")
//    )
//}