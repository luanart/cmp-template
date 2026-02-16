@file:OptIn(ExperimentalAtomicApi::class)

package com.core.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.analytics.AnalyticsTracker
import com.firebase.analytics.NoOpAnalyticsTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

abstract class BaseViewModel<S : ViewState, A : ViewAction, E : ViewEffect>(
    initialState: S
) : ViewModel(), KoinComponent {

    protected val analytics by lazy {
        getKoin().getOrNull<AnalyticsTracker>() ?: NoOpAnalyticsTracker
    }

    protected open val screenName = this::class.simpleName
        ?.removeSuffix("ViewModel")
        ?: "Unknown"

    private var pendingRetryAction: (() -> Unit)? = null

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _error = Channel<Throwable>(Channel.BUFFERED)
    val error = _error.receiveAsFlow()

    private val didDataLoaded = AtomicBoolean(false)

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()
        .onStart {
            if (didDataLoaded.compareAndSet(expectedValue = false, newValue = true)) {
                loadInitialData()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialState
        )

    val currentState: S
        get() = state.value

    init {
        analytics.logScreen(screenName = screenName)
    }

    open fun handleAction(action: A) {
        when(action) {
            is ViewAction.CallApi -> onCallApi(action)
            is ViewAction.SideEffect -> onSideEffect(action)
            is ViewAction.UpdateData -> onUpdateData(action)
        }
    }

    protected open fun onCallApi(action: ViewAction.CallApi) {}

    protected open fun onSideEffect(action: ViewAction.SideEffect) {}

    protected open fun onUpdateData(action: ViewAction.UpdateData) {}

    protected open fun loadInitialData() {}

    protected fun postEffect(effect: E) {
        _effect.trySend(effect)
    }

    protected fun sendError(throwable: Throwable) {
        _error.trySend(throwable)
    }

    protected fun updateState(transform: S.() -> S) {
        _state.update(transform)
    }

    protected fun launchWithRetry(
        onRetry: (() -> Unit)? = null,
        onStart: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            onRetry?.let { pendingRetryAction = it }
            onStart?.invoke()
            block()
        }
    }

    fun retry(): Boolean {
        return pendingRetryAction?.let { action ->
            action()
            true
        } ?: false
    }

    override fun onCleared() {
        super.onCleared()
        _effect.close()
        pendingRetryAction = null
    }
}

