package com.core.data.di

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class LocalModule {
    @Single
    fun provideLocalStorage(contextWrapper: ContextWrapper) = getLocalStorage(contextWrapper)

    @Single
    fun provideSecureStorage(contextWrapper: ContextWrapper) = getSecureStorage(contextWrapper)
}