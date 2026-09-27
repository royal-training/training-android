/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.activity

import android.os.Bundle
import android.view.View
import com.twp.base.module.StatusViewModel

/**
 * Created by Royal on 3/28/21.
 */
abstract class BaseVMFragment<VM : StatusViewModel>: BaseFragment() {

    val vm: VM by lazy { initVM() }

    abstract fun initVM(): VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.setUpBundle(arguments)
        setUpViewModel()
    }

    open fun setUpViewModel(){

    }
}