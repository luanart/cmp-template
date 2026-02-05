package com.core.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.core.presentation.data.AppError
import com.core.presentation.theme.AppTheme
import com.resources.Res
import com.resources.try_again
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorStateView(error: AppError) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = AppTheme.dimens.default)
    ) {
        Text(text = error.message, textAlign = TextAlign.Center)
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { error.retryAction?.invoke() }
        ) {
            Text(text = stringResource(Res.string.try_again))
        }
    }
}