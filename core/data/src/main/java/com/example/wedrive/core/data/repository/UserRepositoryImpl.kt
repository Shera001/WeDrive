package com.example.wedrive.core.data.repository

import com.example.wedrive.core.common.Dispatcher
import com.example.wedrive.core.common.WeDriveDispatchers
import com.example.wedrive.core.data.mapper.toUser
import com.example.wedrive.core.datastore.UserPreferencesDataSource
import com.example.wedrive.core.network.models.request.UserRequest
import com.example.wedrive.core.network.service.auth.AuthService
import com.example.wedrive.domain.model.User
import com.example.wedrive.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @Dispatcher(WeDriveDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val userDataSource: UserPreferencesDataSource,
    private val authService: AuthService
) : UserRepository {

    override val phoneFlow: Flow<String?> = userDataSource.phoneFlow

    override fun createUser(): Flow<Result<User>> = flow {
        val userRequest = UserRequest(generateRandomPhoneNumber())
        val result = authService.createUser(userRequest)

        if (result.isSuccess) {
            result.getOrNull()?.let { user ->
                userDataSource.savePhone(user.phone)
            }
        }

        emit(result.map { it.toUser() })
    }.flowOn(ioDispatcher)

    private fun generateRandomPhoneNumber(): String {
        val number = (1000000..9999999).random()
        return "+99890$number"
    }
}