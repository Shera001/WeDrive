package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.repository.WalletRepository
import javax.inject.Inject

class GetWalletInfoUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {

    operator fun invoke() = walletRepository.getWalletInfo()
}