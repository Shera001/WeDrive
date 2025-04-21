package com.example.wedrive.core.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: WeDriveDispatchers)

enum class WeDriveDispatchers {
    Default,
    IO,
}