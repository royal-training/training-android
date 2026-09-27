/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.module

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.twp.base.constant.Const
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Royal on 3/27/21.
 */
abstract class StatusViewModel : ViewModel() {

    val composite by lazy { CompositeDisposable() }

    val state by lazy { StatusLiveData() }
    val wait by lazy { WaitNotifyLiveData() }
    val refresh by lazy { RefreshNotifyLiveData() }

    abstract fun setUpBundle(bundle: Bundle?)

    override fun onCleared() {
        super.onCleared()
        composite.clear()
    }

    open fun showLoading() {
        state.showLoading()
    }

    open fun showEmpty(msg: String? = null) {
        state.showEmpty(msg)
    }

    open fun showError(error: Throwable? = null) {
        state.showError(error)
    }

    open fun showNormal() {
        state.showNormal()
    }

    open fun waitStart(op: Int = Const.OP_DELETE) {
        wait.waitStart(op)
    }

    open fun waitFinish(op: Int = Const.OP_DELETE) {
        wait.waitFinish(op)
    }

    open fun waitError(op: Int = Const.OP_DELETE) {
        wait.waitError(op)
    }

    open fun endRefreshLayout(isRefresh: Boolean, noMoreData: Boolean) {
        refresh.endRefreshLayout(isRefresh, noMoreData)
    }

    open fun refresh() { // 刷新页面

    }
}