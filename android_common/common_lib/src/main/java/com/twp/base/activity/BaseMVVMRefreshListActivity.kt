/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.activity

import androidx.viewbinding.ViewBinding
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.twp.base.R
import com.twp.base.ext.finishedWithNotify
import com.twp.base.module.CoroutineListViewModel
import com.twp.base.module.RefreshNotify

/**
 * Created by Royal on 3/28/21.
 */
abstract class BaseMVVMRefreshListActivity<Data, VM : CoroutineListViewModel<Data>, VB : ViewBinding> :
    BaseMVVMActivity<VM, VB>() {

    var refreshView: RefreshLayout? = null

    override fun setUpViews() {
        super.setUpViews()
        refreshView = vb.root.findViewById(R.id.refresh_layout)
    }

    override fun setUpListeners() {
        super.setUpListeners()
        refreshView?.setOnRefreshListener { vm.refresh() }
        refreshView?.setOnLoadMoreListener {
            vm.loadListData(
                isRefresh = false,
                showLoading = false
            )
        }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        vm.refresh.observe(this, { setRefreshView(it) })
    }

    open fun setRefreshView(refreshNotify: RefreshNotify) {
        refreshView?.finishedWithNotify(refreshNotify)
    }
}