package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.model.Card
import com.example.wedrive.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {

    operator fun invoke(name: String, expireDate: String): Flow<Result<Card>> {
        return cardRepository.addNewCard(name, expireDate)
    }
}