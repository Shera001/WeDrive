package com.example.wedrive.domain.repository

import com.example.wedrive.domain.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun getCards(): Flow<Result<List<Card>>>

    fun addNewCard(name: String, expireDate: String): Flow<Result<Card>>
}