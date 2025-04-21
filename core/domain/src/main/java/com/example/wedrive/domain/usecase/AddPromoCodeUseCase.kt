package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.repository.PromoCodeRepository
import javax.inject.Inject

class AddPromoCodeUseCase @Inject constructor(
    private val promoCodeRepository: PromoCodeRepository,
) {

    operator fun invoke(code: String) = promoCodeRepository.addPromoCode(code)
}