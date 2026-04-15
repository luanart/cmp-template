package com.core.data.di

import com.core.common.provider.DefaultDispatcher
import com.core.common.provider.DispatcherProvider
import com.core.data.local.SecureStorage
import com.core.data.remote.ConnectivityObserver
import com.core.data.remote.api.ApiClient
import com.core.data.remote.api.ApiService
import com.core.data.remote.api.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
class NetworkModule {
    @Single
    fun provideConnectivityObserver(contextWrapper: ContextWrapper) : ConnectivityObserver {
        return getConnectivityObserver(contextWrapper)
    }

    @Single
    fun provideHttpClientEngine() = getHttpClientEngine()

    @Single
    fun provideDispatcher() : DispatcherProvider = DefaultDispatcher()

    @Single
    fun provideTokenManager(
        engine: HttpClientEngine,
        storage: SecureStorage
    ) : TokenManager = TokenManager(
        engine = engine,
        storage = storage
    )

    @Single
    fun provideHttpClient(
        engine: HttpClientEngine,
        tokenManager: TokenManager
    ) : HttpClient = ApiClient(
        engine = engine,
        tokenManager = tokenManager
    ).client

    @Single
    fun provideApiService(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        connectivityObserver: ConnectivityObserver
    ) : ApiService = ApiService(
        httpClient = httpClient,
        dispatcher = dispatcherProvider,
        connectivityObserver = connectivityObserver
    )
}