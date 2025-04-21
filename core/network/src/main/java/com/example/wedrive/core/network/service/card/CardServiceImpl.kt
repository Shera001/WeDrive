package com.example.wedrive.core.network.service.card

import com.example.wedrive.core.network.models.dto.CardDTO
import com.example.wedrive.core.network.models.request.CardRequest
import com.example.wedrive.core.network.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class CardServiceImpl @Inject constructor(
    private val client: HttpClient
) : CardService {

    override suspend fun getCards(): Result<List<CardDTO>> {
        return safeApiCall {
            client.get("cards").body()
        }
    }

    override suspend fun addNewCard(request: CardRequest): Result<CardDTO> {
        return safeApiCall {
            client.post("cards") {
                setBody(request)
            }.body()
        }
    }
}