package com.davidmerchan.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

internal fun <Type> Flow<Type>.stateInWhileSubscribed(
    scope: CoroutineScope,
    initialValue: Type,
    onStart: suspend FlowCollector<Type>.() -> Unit,
): StateFlow<Type> {
    return this
        .onStart(action = onStart)
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = initialValue,
        )
}