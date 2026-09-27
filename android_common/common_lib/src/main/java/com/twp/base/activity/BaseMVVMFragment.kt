/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

/**
 * Created by Royal on 3/28/21.
 */
abstract class BaseMVVMFragment<VM : StatusViewModel, VB : ViewBinding>: BaseVMFragment<VM>() {

    var vb: VB? = null
    var stateView: StateView? = null

    abstract fun initVB(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = initVB(inflater, container)
        return vb?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun setUpViews() {
        super.setUpViews()
        stateView = vb?.root?.findViewById(R.id.state_view)
    }


    override fun setUpListeners() {
        super.setUpListeners()
        stateView?.setStateViewClickListener { v, s -> onStateViewClick(v, s) }
    }

    override fun setUpViewModel() {
        super.setUpViewModel()
        vm.state.observe(viewLifecycleOwner, { setStateView(it) })
        vm.wait.observe(viewLifecycleOwner, { setWaitView(it) })
    }

    open fun onStateViewClick(view: View, state: State) {
        vm.refresh()
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