package com.example.wedrive.domain.repository

import com.example.wedrive.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val phoneFlow: Flow<String?>

    fun createUser(): Flow<Result<User>>
}