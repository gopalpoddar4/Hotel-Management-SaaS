package com.nexifotech.hotelsaas.feature.auth.di

import com.nexifotech.hotelsaas.feature.auth.data.repository.DummyAuthRepository
import com.nexifotech.hotelsaas.feature.auth.domain.repository.AuthRepository
import com.nexifotech.hotelsaas.feature.auth.domain.usecase.LoginUseCase
import com.nexifotech.hotelsaas.feature.auth.presentation.viewmodel.LoginViewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { DummyAuthRepository() }
    factory { LoginUseCase(get()) }
    factory { LoginViewModel(get(), get()) }
}
