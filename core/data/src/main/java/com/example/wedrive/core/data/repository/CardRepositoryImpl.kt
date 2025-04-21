package com.example.wedrive.core.data.repository

import com.example.wedrive.core.common.Dispatcher
import com.example.wedrive.core.common.WeDriveDispatchers
import com.example.wedrive.core.data.mapper.toCard
import com.example.wedrive.core.network.models.request.CardRequest
import com.example.wedrive.core.network.service.card.CardService
import com.example.wedrive.domain.model.Card
import com.example.wedrive.domain.repository.CardRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    @Dispatcher(WeDriveDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val cardService: CardService
) : CardRepository {

    override fun getCards(): Flow<Result<List<Card>>> = flow {
        emit(cardService.getCards().map { cards -> cards.map { card -> card.toCard() } })
    }.flowOn(ioDispatcher)

    override fun addNewCard(name: String, expireDate: String): Flow<Result<Card>> = flow {
        val request = CardRequest(name, expireDate)
        emit(cardService.addNewCard(request).map { it.toCard() })
    }.flowOn(ioDispatcher)
}