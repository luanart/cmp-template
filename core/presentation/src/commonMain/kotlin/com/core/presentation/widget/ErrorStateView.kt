package com.core.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.core.presentation.theme.AppTheme
import com.resources.Res
import com.resources.try_again
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorStateView(message: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = AppTheme.dimens.default,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier.fillMaxSize()
            .background(color = AppTheme.colors.background),
    ) {
        Text(text = message, textAlign = TextAlign.Center)
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onRetry
        ) {
            Text(text = stringResource(Res.string.try_again))
        }
    }
}