package com.example.wedrive.feature.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wedrive.domain.usecase.AddPromoCodeUseCase
import com.example.wedrive.domain.usecase.CreateUserUseCase
import com.example.wedrive.domain.usecase.GetAllCardUseCase
import com.example.wedrive.domain.usecase.GetUserPhoneUseCase
import com.example.wedrive.domain.usecase.GetWalletInfoUseCase
import com.example.wedrive.domain.usecase.UpdateWalletSourceUseCase
import com.example.wedrive.feature.wallet.event.WalletEvent
import com.example.wedrive.feature.wallet.intent.WalletIntent
import com.example.wedrive.feature.wallet.state.WalletSource
import com.example.wedrive.feature.wallet.state.WalletUIState
import com.example.wedrive.feature.wallet.state.toCardUI
import com.example.wedrive.feature.wallet.state.toPaymentMethod
import com.example.wedrive.feature.wallet.state.toWalletSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getUserPhoneUseCase: GetUserPhoneUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val getWalletInfoUseCase: GetWalletInfoUseCase,
    private val getAllCardUseCase: GetAllCardUseCase,
    private val updateWalletSourceUseCase: UpdateWalletSourceUseCase,
    private val addPromoCodeUseCase: AddPromoCodeUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WalletUIState())
    val state: StateFlow<WalletUIState> = _state.asStateFlow()

    private val _event = Channel<WalletEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onIntent(intent: WalletIntent) {
        when (intent) {
            WalletIntent.Init -> {
                initWallet()
            }

            WalletIntent.ShowAddPromoCodeDialog -> {
                _state.update {
                    it.copy(showBottomSheet = true)
                }
            }

            WalletIntent.HideAddPromoCodeDialog -> {
                _state.update {
                    it.copy(showBottomSheet = false)
                }
            }

            is WalletIntent.AddPromoCode -> {
                addPromoCode(intent.code)
            }

            is WalletIntent.UpdateWalletSource -> {
                updateWalletSource(intent.walletSource)
            }
        }
    }

    private fun initWallet() {
        viewModelScope.launch {
            val phoneNumber = getUserPhoneUseCase().firstOrNull()

            if (phoneNumber == null) {
                createUserUseCase()
            } else {
                getWalletInfoUseCase()
            }
                .onStart {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }
                .onEach { result ->
                    val data = result.getOrNull()

                    if (result.isSuccess && data != null) {
                        _state.update {
                            it.copy(
                                balance = data.balance,
                                activeMethod = data.activeMethod.toWalletSource(),
                            )
                        }

                        getAllCards()
                    } else {
                        _event.send(WalletEvent.OnInitError)
                    }
                }
                .onCompletion {
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getAllCards() {
        viewModelScope.launch {
            getAllCardUseCase().collect { result ->
                val cards = result.getOrNull()
                if (result.isSuccess && cards != null) {
                    _state.update { uiState ->
                        uiState.copy(
                            cards = cards.map { card -> card.toCardUI() }
                        )
                    }
                }
            }
        }
    }

    private fun updateWalletSource(walletSource: WalletSource) {
        if (walletSource == state.value.activeMethod) return

        var oldWalletSource: WalletSource? = null

        _state.update {
            oldWalletSource = it.activeMethod
            it.copy(
                activeMethod = walletSource
            )
        }

        viewModelScope.launch {
            updateWalletSourceUseCase(walletSource.toPaymentMethod()).collect { result ->
                val data = result.getOrNull()

                if (result.isSuccess && data != null) {
                    _state.update {
                        it.copy(
                            balance = data.balance,
                            activeMethod = data.activeMethod.toWalletSource()
                        )
                    }
                } else {
                    result.exceptionOrNull()?.message?.let {
                        _event.send(WalletEvent.ShowMessage(it))
                    }

                    oldWalletSource?.let { source ->
                        _state.update { uiState ->
                            uiState.copy(
                                activeMethod = source
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addPromoCode(code: String) {
        addPromoCodeUseCase(code)
            .onStart {
                _state.update {
                    it.copy(isLoading = true)
                }
            }
            .onEach { result ->
                val message = if (result.isSuccess) result.getOrNull()
                else result.exceptionOrNull()?.message

                message?.let {
                    _event.send(
                        element = if (result.isSuccess) WalletEvent.ShowMessage(message)
                        else WalletEvent.OnAddPromoCodeError(message)
                    )
                }
            }
            .onCompletion {
                _state.update {
                    it.copy(
                        showBottomSheet = false,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}