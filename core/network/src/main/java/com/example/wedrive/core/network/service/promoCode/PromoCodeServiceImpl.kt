package com.example.wedrive.core.network.service.promoCode

import com.example.wedrive.core.network.models.dto.MessageDTO
import com.example.wedrive.core.network.models.request.PromoCodeRequest
import com.example.wedrive.core.network.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class PromoCodeServiceImpl @Inject constructor(
    private val client: HttpClient
) : PromoCodeService {
    override suspend fun addPromoCode(request: PromoCodeRequest): Result<MessageDTO> {
        return safeApiCall {
            client.post("promocode") {
                setBody(request)
            }.body()
        }
    }
}