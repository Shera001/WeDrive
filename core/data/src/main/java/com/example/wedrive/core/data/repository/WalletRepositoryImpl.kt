package com.example.wedrive.core.data.repository

import com.example.wedrive.core.common.Dispatcher
import com.example.wedrive.core.common.WeDriveDispatchers
import com.example.wedrive.core.data.mapper.toPaymentMethodRequest
import com.example.wedrive.core.data.mapper.toUser
import com.example.wedrive.core.network.service.wallet.WalletService
import com.example.wedrive.domain.model.PaymentMethodType
import com.example.wedrive.domain.model.User
import com.example.wedrive.domain.repository.WalletRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    @Dispatcher(WeDriveDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val service: WalletService
) : WalletRepository {

    override fun getWalletInfo(): Flow<Result<User>> = flow {
        emit(service.getWalletInfo().map { it.toUser() })
    }.flowOn(ioDispatcher)

    override fun updateWalletSource(
        paymentMethodType: PaymentMethodType
    ): Flow<Result<User>> = flow {
        val result = service.updatePaymentMethod(paymentMethodType.toPaymentMethodRequest())
        emit(result.map { it.toUser() })
    }.flowOn(ioDispatcher)
}