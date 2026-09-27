/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.module

import androidx.lifecycle.MutableLiveData

/**
 * Created by Royal on 3/28/21.
 */
abstract class CoroutineListViewModel<Data> : CoroutineViewModel() {

    val result = MutableLiveData<MutableList<Data>>()
    var isLastPage = true
    private var page: Int = 1

    fun loadListData(isRefresh: Boolean, showLoading: Boolean) {
        if (showLoading) {
            showLoading()
        }
        if (isRefresh) {
            page = 1
        }
        launch({
            val data = getListRepository(page)
            val old = result.value ?: mutableListOf()
            if (page == 1) {
                old.clear()
                if (data.isNullOrEmpty()) {
                    showEmpty()
                    endRefreshLayout(isRefresh, true)
                } else {
                    showNormal()
                    old.addAll(data)
                    endRefreshLayout(isRefresh, isLastPage)
                }
                result.value = old
            } else {
                showNormal()
                if (data.isNotEmpty()) {
                    old.addAll(data)
                    result.value = old
                }
                endRefreshLayout(isRefresh, isLastPage)
            }
            if (!isLastPage) {
                page += 1
            }
        }, {
            showError(it)
        })
    }

    abstract suspend fun getListRepository(page: Int): MutableList<Data>

    override fun refresh() {
        loadListData(isRefresh = true, showLoading = result.value.isNullOrEmpty())
    }

}