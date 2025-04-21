package com.example.wedrive.core.data.repository

import com.example.wedrive.core.common.Dispatcher
import com.example.wedrive.core.common.WeDriveDispatchers
import com.example.wedrive.core.network.models.request.PromoCodeRequest
import com.example.wedrive.core.network.service.promoCode.PromoCodeService
import com.example.wedrive.domain.repository.PromoCodeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PromoCodeRepositoryImpl @Inject constructor(
    @Dispatcher(WeDriveDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val service: PromoCodeService
) : PromoCodeRepository {

    override fun addPromoCode(code: String): Flow<Result<String>> = flow {
        emit(service.addPromoCode(PromoCodeRequest(code)).map { it.message })
    }.flowOn(ioDispatcher)
}