package com.example.wedrive.domain.usecase

import com.example.wedrive.domain.repository.UserRepository
import javax.inject.Inject

class GetUserPhoneUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.phoneFlow
}