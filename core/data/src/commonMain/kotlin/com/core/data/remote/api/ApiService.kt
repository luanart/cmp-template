package com.core.data.remote.api

import com.core.common.provider.DispatcherProvider
import com.core.common.error.AppException
import com.core.data.remote.ConnectivityObserver
import com.core.data.remote.utils.NoContent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.withContext

class ApiService(
    val httpClient: HttpClient,
    val dispatcher: DispatcherProvider,
    val connectivityObserver: ConnectivityObserver
) {

    suspend inline fun <reified T> get(url: String) = request<T>(
        url = url,
        method = HttpMethod.Get
    )

    suspend inline fun <reified T> post(url: String, data: Any? = null) = request<T>(
        url = url,
        method = HttpMethod.Post,
        data = data
    )

    suspend fun download(
        url: String,
        onProgress: ((received: Long, length: Long?) -> Unit)? = null,
    ) = httpClient.get(urlString = url) {
        onDownload { received, length ->
            onProgress?.invoke(received, length)
        }
    }.bodyAsBytes()

    suspend inline fun <reified T> request(
        url: String,
        method: HttpMethod,
        data: Any? = null,
        contentType: ContentType = ContentType.Application.Json
    ) = withContext(dispatcher.io) {

        if (!connectivityObserver.isConnected()) {
            throw AppException.NoInternet()
        }

        val response = httpClient.request(url) {
            this.method = method
            this.contentType(contentType)
            data?.let {
                this.setBody(it)
            }
        }

        if (response.status == HttpStatusCode.NoContent) {
            NoContent as T
        } else {
            response.body<T>()
        }
    }
}