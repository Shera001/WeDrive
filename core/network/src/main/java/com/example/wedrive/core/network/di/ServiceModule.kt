package com.example.wedrive.core.network.di

import com.example.wedrive.core.network.service.auth.AuthService
import com.example.wedrive.core.network.service.auth.AuthServiceImpl
import com.example.wedrive.core.network.service.card.CardService
import com.example.wedrive.core.network.service.card.CardServiceImpl
import com.example.wedrive.core.network.service.promoCode.PromoCodeService
import com.example.wedrive.core.network.service.promoCode.PromoCodeServiceImpl
import com.example.wedrive.core.network.service.wallet.WalletService
import com.example.wedrive.core.network.service.wallet.WalletServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    fun bindAuthService(authService: AuthServiceImpl): AuthService

    @Binds
    fun bindCardService(cardService: CardServiceImpl): CardService

    @Binds
    fun bindPromoCodeService(promoCodeService: PromoCodeServiceImpl): PromoCodeService

    @Binds
    fun bindWalletService(walletServiceImpl: WalletServiceImpl): WalletService
}