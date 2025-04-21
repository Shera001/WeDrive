package com.example.wedrive.core.network.service.wallet

import com.example.wedrive.core.network.models.dto.UserDTO
import com.example.wedrive.core.network.models.request.PaymentMethodRequest

interface WalletService {

    suspend fun getWalletInfo(): Result<UserDTO>

    suspend fun updatePaymentMethod(request: PaymentMethodRequest): Result<UserDTO>
}