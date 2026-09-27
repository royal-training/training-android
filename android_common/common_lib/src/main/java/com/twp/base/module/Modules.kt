/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.module

import androidx.lifecycle.MutableLiveData
import com.twp.base.constant.Const
import com.twp.base.constant.State

/**
 * Created by Royal on 3/27/21.
 */
data class StatusNotify(val state: State, val msg: String? = null, val error: Throwable? = null)

data class RefreshNotify(val isRefresh: Boolean, val noMoreData: Boolean)

data class WaitNotify(val operation: Int, val state: State)

class StatusLiveData : MutableLiveData<StatusNotify>() {

    fun showLoading() {
        value = StatusNotify(State.Loading)
    }

    fun showEmpty(msg: String? = null) {
        value = StatusNotify(State.Empty, msg = msg)
    }

    fun showError(error: Throwable? = null) {
        value = StatusNotify(State.Error, error = error)
    }

    fun showNormal() {
        value = StatusNotify(State.Normal)
    }
}

class WaitNotifyLiveData : MutableLiveData<WaitNotify>() {

    fun waitStart(op: Int = Const.OP_DELETE) {
        value = WaitNotify(op, State.Loading)
    }

    fun waitFinish(op: Int = Const.OP_DELETE) {
        value = WaitNotify(op, State.Normal)
    }

    fun waitError(op: Int = Const.OP_DELETE) {
        value = WaitNotify(op, State.Error)
    }
}

class RefreshNotifyLiveData : MutableLiveData<RefreshNotify>(){

    fun endRefreshLayout(isRefresh: Boolean, noMoreData: Boolean){
        value = RefreshNotify(isRefresh, noMoreData)
    }
}