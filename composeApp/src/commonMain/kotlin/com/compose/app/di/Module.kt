package com.compose.app.di

import com.compose.app.ui.app.NavigationViewModel
import com.core.data.di.DataModule
import com.features.auth.di.AuthModule
import com.features.home.di.HomeModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.ksp.generated.module

val appModule = module {
    includes(DataModule().module, AuthModule().module, HomeModule().module)
    viewModelOf(::NavigationViewModel)
}