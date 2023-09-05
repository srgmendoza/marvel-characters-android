package com.sm.base_core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.base_core.user_intents.UiEffect
import com.sm.base_core.user_intents.UiEvent
import com.sm.base_core.user_intents.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


/**
 * Based on approach given [here](https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d)*/
abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {
    // Create Initial State of View
    private val initialState : State by lazy { createInitialState() }
    abstract fun createInitialState() : State

    // Get Current State
    val currentState: State
        get() = uiState.value

    /*For handling UiState we use StateFlow.
    StateFlow is just like LiveData but have initial value.
    So we have always a state. It is also a kind of SharedFlow.
    We always want to receive last view state when UI become visible.*/
    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    /*For handling UiEvent we use SharedFlow.
    We want to drop event if there is not any subscriber.*/
    private val _event : MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    /*For handling UiEffect we use Channels.
     Because Channels are hot and we do not need to show side effect again when orientation changed or UI become visible again. Simply we want to replicate SingleLiveEvent behavior.*/
    private val _effect : Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event : Event)


    /**
     * Set new Event
     */
    fun setEvent(event : Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }


    /**
     * Set new Ui State
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}