package com.example.wedrive.core.network.service.promoCode

import com.example.wedrive.core.network.models.dto.MessageDTO
import com.example.wedrive.core.network.models.request.PromoCodeRequest

interface PromoCodeService {

    suspend fun addPromoCode(request: PromoCodeRequest): Result<MessageDTO>
}