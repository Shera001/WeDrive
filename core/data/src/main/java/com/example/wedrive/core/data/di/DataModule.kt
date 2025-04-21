package com.example.wedrive.core.data.di

import com.example.wedrive.core.data.repository.UserRepositoryImpl
import com.example.wedrive.core.data.repository.CardRepositoryImpl
import com.example.wedrive.core.data.repository.PromoCodeRepositoryImpl
import com.example.wedrive.core.data.repository.WalletRepositoryImpl
import com.example.wedrive.domain.repository.UserRepository
import com.example.wedrive.domain.repository.CardRepository
import com.example.wedrive.domain.repository.PromoCodeRepository
import com.example.wedrive.domain.repository.WalletRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindAuthRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    fun binWalletRepository(walletRepository: WalletRepositoryImpl): WalletRepository

    @Binds
    fun binCardRepository(cardRepository: CardRepositoryImpl): CardRepository

    @Binds
    fun binPromoCodeRepository(promoCodeRepository: PromoCodeRepositoryImpl): PromoCodeRepository
}