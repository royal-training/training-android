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
import com.twp.base.widget.StateView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * Created by Royal on 3/29/21.
 */
abstract class BaseVBActivity<VB : ViewBinding> : BaseActivity() {

    val vb: VB by lazy { initVB() }

    abstract fun initVB(): VB

    var stateView: StateView? = null
    var titleView: CommonTitleBar? = null

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

    open fun onStateViewClick(view: View, state: State) {

    }

    open fun onTitleViewLeftButtonClick(v: View){
        onBackPressed()
    }

}