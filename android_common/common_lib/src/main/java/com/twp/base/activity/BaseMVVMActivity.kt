/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.activity

import android.view.View
import androidx.viewbinding.ViewBinding
import com.twp.base.R
import com.twp.base.constant.State
import com.twp.base.ext.finishedWithNotify
import com.twp.base.ext.hideWaitLoading
import com.twp.base.ext.showWaitLoading
import com.twp.base.module.StatusNotify
import com.twp.base.module.StatusViewModel
import com.twp.base.module.WaitNotify
import com.twp.base.widget.StateView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * Created by Royal on 3/28/21.
 */
abstract class BaseMVVMActivity<VM : StatusViewModel, VB : ViewBinding> : BaseVMActivity<VM>() {

    val vb: VB by lazy { initVB() }
    var stateView: StateView? = null
    var titleView: CommonTitleBar? = null

    abstract fun initVB(): VB

    override fun setUpViews() {
        setContentView(vb.root)
        super.setUpViews()
        stateView = vb.root.findViewById(R.id.state_view)
        titleView = vb.root.findViewById(R.id.title_view)
    }

    override fun setUpListeners() {
        super.setUpListeners()
        stateView?.setStateViewClickListener { v, s -> onStateViewClick(v, s) }
        titleView?.leftImageButton?.setOnClickListener { onTitleViewLeftButtonClick(it) }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        vm.state.observe(this, { setStateView(it) })
        vm.wait.observe(this, { setWaitView(it) })
    }

    open fun onStateViewClick(view: View, state: State) {
        vm.refresh()
    }

    open fun onTitleViewLeftButtonClick(v: View){
        onBackPressed()
    }

    open fun setStateView(statusNotify: StatusNotify) {
        stateView?.finishedWithNotify(statusNotify)
    }

    open fun setWaitView(waitNotify: WaitNotify) {
        when (waitNotify.state) {
            State.Loading -> showWaitLoading()
            else -> hideWaitLoading()
        }
    }

}