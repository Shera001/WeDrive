package com.example.wedrive.core.network.di

import com.example.wedrive.core.datastore.UserPreferencesDataSource
import com.example.wedrive.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(dataSource: UserPreferencesDataSource): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url(BuildConfig.BASE_URL)

                contentType(ContentType.Application.Json)

                val phone = runBlocking {
                    dataSource.phoneFlow.firstOrNull()
                }

                if (!phone.isNullOrEmpty()) {
                    header("X-Account-Phone", phone)
                }
            }

            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}