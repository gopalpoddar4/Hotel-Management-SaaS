package com.nexifotech.hotelsaas.core.di

import com.nexifotech.hotelsaas.core.navigation.AppNavigator
import org.koin.dsl.module

/** Koin DI module that wires core infrastructure bindings. */
val coreModule = module {
    single { AppNavigator() }
}
