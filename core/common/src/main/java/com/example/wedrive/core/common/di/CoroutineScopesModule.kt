package com.example.wedrive.core.common.di

import com.example.wedrive.core.common.Dispatcher
import com.example.wedrive.core.common.WeDriveDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutineScopesModule {

    @Provides
    @Singleton
    fun providesCoroutineScope(
        @Dispatcher(WeDriveDispatchers.Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
