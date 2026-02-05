package com.compose.app.di

import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(app: KoinAppDeclaration? = null) {
    startKoin {
        printLogger(Level.DEBUG)
        includes(app)
        modules(appModule)
    }
}