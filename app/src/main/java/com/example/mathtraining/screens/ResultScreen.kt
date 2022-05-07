package com.example.mathtraining.screens

import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ResultScreen(
    onContinue: () -> Unit,
    onEnd: () -> Unit
) {
    Scaffold() {
        Text(text = "result")
        Button(onClick = { onContinue() }) {
            Text(text = "Next")
        }
    }
}