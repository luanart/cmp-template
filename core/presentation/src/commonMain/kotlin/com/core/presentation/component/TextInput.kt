package com.core.presentation.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import com.core.presentation.data.Validator
import com.core.presentation.theme.AppTheme
import com.core.presentation.util.ShowViewIf
import com.core.presentation.util.TextInputDefaults
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TextInput(
    label: String,
    state: TextFieldState,
    modifier: Modifier = Modifier,
    validators: PersistentList<Validator> = persistentListOf(),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = AppTheme.typography.bodyLarge,
    placeholder: String? = null,
    supportingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.MultiLine(),
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = AppTheme.colors.outlineVariant,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {

    val uiState = TextInputDefaults.rememberTextFieldUiState(
        interactionSource = interactionSource,
        state = state,
        validators = validators,
        enabled = enabled,
        label = label,
        supportingText = supportingText,
        textStyle = textStyle,
        colors = colors,
        placeholder = placeholder
    )

    val newTrailingIcon: @Composable () -> Unit = {
        if (trailingIcon != null) {
            trailingIcon()
        } else {
            ShowViewIf(visible = enabled && state.text.isNotEmpty()) {
                IconButton(
                    enabled = !readOnly,
                    onClick = { state.clearText() },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "Clear",
                        modifier = Modifier.rotate(45f)
                    )
                }
            }
        }
    }

    CompositionLocalProvider(LocalTextSelectionColors provides colors.textSelectionColors) {
        BasicTextField(
            state = state,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            inputTransformation = inputTransformation,
            textStyle = uiState.mergedTextStyle,
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            lineLimits = lineLimits,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(uiState.cursorColor),
            outputTransformation = outputTransformation,
            decorator = TextInputDefaults.decorator(
                state = state,
                colors = colors,
                uiState = uiState,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = newTrailingIcon,
                prefix = prefix,
                suffix = suffix
            ),
            scrollState = scrollState
        )
    }
}