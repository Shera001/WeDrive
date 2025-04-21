package com.example.wedrive.core.network.service.card

import com.example.wedrive.core.network.models.dto.CardDTO
import com.example.wedrive.core.network.models.request.CardRequest

interface CardService {

    suspend fun getCards(): Result<List<CardDTO>>

    suspend fun addNewCard(request: CardRequest): Result<CardDTO>
}