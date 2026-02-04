package com.features.home.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
internal fun HomeContent(
    state: HomeState = HomeState(),
    dispatcher: (HomeAction) -> Unit = {}
) {
    Text(text = "Home")
}
