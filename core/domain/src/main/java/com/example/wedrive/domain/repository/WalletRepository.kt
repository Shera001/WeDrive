package com.example.wedrive.domain.repository

import com.example.wedrive.domain.model.PaymentMethodType
import com.example.wedrive.domain.model.User
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    fun getWalletInfo(): Flow<Result<User>>

    fun updateWalletSource(paymentMethodType: PaymentMethodType): Flow<Result<User>>
}