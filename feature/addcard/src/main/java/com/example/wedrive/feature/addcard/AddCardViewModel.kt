package com.example.wedrive.feature.addcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wedrive.domain.usecase.AddCardUseCase
import com.example.wedrive.feature.addcard.event.AddCardEvent
import com.example.wedrive.feature.addcard.intent.AddCardIntent
import com.example.wedrive.feature.addcard.state.AddCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddCardState())
    val state: StateFlow<AddCardState> = _state.asStateFlow()

    private val _event = Channel<AddCardEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onIntent(intent: AddCardIntent) {
        when (intent) {
            is AddCardIntent.ChangeCardFieldType -> {
                _state.update {
                    it.copy(cardFieldType = intent.type)
                }
            }

            AddCardIntent.OnAddCardClick -> {
                addCard()
            }

            is AddCardIntent.OnCardDateChange -> {
                _state.update {
                    it.copy(expireDate = intent.date)
                }
            }

            is AddCardIntent.OnCardNumberChange -> {
                _state.update {
                    it.copy(number = intent.number)
                }
            }

            AddCardIntent.OnRemoveNumberClick -> {
                when (state.value.cardFieldType) {
                    CardFieldType.NUMBER -> {
                        if (state.value.number.isNotEmpty()) {
                            _state.update {
                                it.copy(
                                    number = it.number.dropLast(1),
                                    saveButtonEnabled = false
                                )
                            }
                        }
                    }

                    CardFieldType.DATE -> {
                        if (state.value.expireDate.isNotEmpty()) {
                            _state.update {
                                it.copy(
                                    expireDate = it.expireDate.dropLast(1),
                                    saveButtonEnabled = false
                                )
                            }
                        }
                    }
                }
            }

            is AddCardIntent.OnNumberClick -> {
                when (val type = state.value.cardFieldType) {
                    CardFieldType.NUMBER -> {
                        if (state.value.number.length < type.maxValueLength) {
                            _state.update {
                                val newNumber = it.number + intent.number

                                val isNumberMax = newNumber.length == type.maxValueLength
                                val isDateMax =
                                    it.expireDate.length == CardFieldType.DATE.maxValueLength

                                it.copy(
                                    number = newNumber,
                                    cardFieldType = if (newNumber.length == type.maxValueLength)
                                        CardFieldType.DATE
                                    else it.cardFieldType,
                                    saveButtonEnabled = isNumberMax && isDateMax
                                )
                            }
                        }
                    }

                    CardFieldType.DATE -> {
                        if (state.value.expireDate.length < type.maxValueLength) {
                            _state.update {
                                val newDate = it.expireDate + intent.number

                                val isNumberMax =
                                    it.number.length == CardFieldType.NUMBER.maxValueLength
                                val isDateMax = newDate.length == type.maxValueLength

                                it.copy(
                                    expireDate = newDate,
                                    saveButtonEnabled = isNumberMax && isDateMax
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addCard() {
        addCardUseCase(state.value.number, state.value.expireDate)
            .onStart {
                _state.update {
                    it.copy(isLoading = true)
                }
            }
            .onEach { result ->
                if (result.isSuccess) {
                    _event.send(AddCardEvent.OnSuccess)
                } else {
                    result.exceptionOrNull()?.message?.let {
                        _event.send(AddCardEvent.OnError(it))
                    }
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