package com.compose.app.di

import com.core.data.di.DataModule
import com.features.auth.di.AuthModule
import com.features.home.di.HomeModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.ksp.generated.module

fun initKoin(configuration : KoinAppDeclaration? = null) {
    startKoin {
        includes(configuration)
        modules(
            DataModule().module,
            AuthModule().module,
            HomeModule().module,
            appModule
        )
        printLogger(Level.DEBUG)
    }
}