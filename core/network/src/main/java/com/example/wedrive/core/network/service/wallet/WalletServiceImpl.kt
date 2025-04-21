package com.example.wedrive.core.network.service.wallet

import android.util.Log
import com.example.wedrive.core.network.models.dto.UserDTO
import com.example.wedrive.core.network.models.request.PaymentMethodRequest
import com.example.wedrive.core.network.utils.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import javax.inject.Inject

class WalletServiceImpl @Inject constructor(
    private val client: HttpClient
) : WalletService {

    override suspend fun getWalletInfo(): Result<UserDTO> {
        return safeApiCall {
            client.get("wallet").body()
        }
    }

    override suspend fun updatePaymentMethod(request: PaymentMethodRequest): Result<UserDTO> {
        return safeApiCall {
            client.put("wallet/method") {
                setBody(request)
            }.body()
        }
    }
}