package com.core.data.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module(includes = [ContextModule::class, LocalModule::class, NetworkModule::class])
@ComponentScan("com.core.data")
@Configuration
class DataModule