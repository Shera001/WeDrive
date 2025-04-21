package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.createUser()
}