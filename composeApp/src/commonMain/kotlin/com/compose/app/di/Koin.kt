package com.compose.app.di

import com.core.data.di.DataModule
import com.features.auth.di.AuthModule
import com.features.home.di.HomeModule
import org.koin.core.annotation.KoinApplication
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(modules = [
    DataModule::class, MainModule::class, AuthModule::class, HomeModule::class
])
class KoinApp

fun initKoin(app: KoinAppDeclaration? = null) {
    startKoin<KoinApp> {
        includes(app)
        printLogger(Level.DEBUG)
    }
}