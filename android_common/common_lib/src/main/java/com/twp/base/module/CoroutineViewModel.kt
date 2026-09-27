/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.module

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Royal on 3/28/21.
 */
abstract class CoroutineViewModel : StatusViewModel() {

    fun launch(
        block: suspend CoroutineScope.() -> Unit,
        onError: (e: Throwable) -> Unit = {},
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e -> onError(e) }) {
            try {
                coroutineScope {
                    block.invoke(this)
                }
            } finally {
                onComplete()
            }
        }
    }

}