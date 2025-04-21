package com.example.wedrive.domain.repository

import kotlinx.coroutines.flow.Flow

interface PromoCodeRepository {

    fun addPromoCode(code: String): Flow<Result<String>>
}