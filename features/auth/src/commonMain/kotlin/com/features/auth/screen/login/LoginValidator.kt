package com.features.auth.screen.login

import androidx.compose.runtime.Composable
import com.core.presentation.data.Translatable
import com.core.presentation.data.Validator
import com.core.presentation.util.createValidators
import com.features.auth.data.model.LoginField
import com.resources.Res
import com.resources.err_field_required
import com.resources.password
import com.resources.username
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun getLoginValidators(field: LoginField) = createValidators(
    "username" to persistentListOf(
        Validator(
            isError = field.username.text.isBlank(),
            messageRes = Res.string.err_field_required,
            formatArgs = listOf(Translatable.Resource(Res.string.username))
        )
    ),
    "password" to persistentListOf(
        Validator(
            isError = field.password.text.isBlank(),
            messageRes = Res.string.err_field_required,
            formatArgs = listOf(Translatable.Resource(Res.string.password))
        )
    )
)