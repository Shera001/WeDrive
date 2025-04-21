package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.model.PaymentMethodType
import com.example.wedrive.domain.model.User
import com.example.wedrive.domain.repository.WalletRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateWalletSourceUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    operator fun invoke(paymentMethodType: PaymentMethodType): Flow<Result<User>> {
        return walletRepository.updateWalletSource(paymentMethodType)
    }
}