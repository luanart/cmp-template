package com.features.home.screen

import androidx.compose.runtime.Immutable
import com.core.presentation.base.ViewAction
import com.core.presentation.base.ViewEffect
import com.core.presentation.base.ViewState

@Immutable
data class HomeState(val shimmer: Boolean = false): ViewState

sealed interface HomeAction: ViewAction

sealed interface HomeEffect: ViewEffect
