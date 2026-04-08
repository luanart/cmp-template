package com.features.auth.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.features.auth")
@Configuration
class AuthModule