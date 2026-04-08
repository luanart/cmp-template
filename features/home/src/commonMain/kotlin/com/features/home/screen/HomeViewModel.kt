package com.features.home.screen

import com.core.presentation.base.BaseViewModel
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel : BaseViewModel<HomeState, HomeAction, HomeEffect>(
    initialState = HomeState()
)