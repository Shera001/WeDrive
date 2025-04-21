package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.repository.CardRepository
import javax.inject.Inject

class GetAllCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {

    operator fun invoke() = cardRepository.getCards()
}