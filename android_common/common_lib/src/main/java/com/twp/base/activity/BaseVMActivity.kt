/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.activity

import android.os.Bundle
import com.twp.base.module.StatusViewModel

/**
 * Created by Royal on 3/28/21.
 */
abstract class BaseVMActivity<VM : StatusViewModel> : BaseActivity() {

    val vm: VM by lazy { initVM() }

    abstract fun initVM(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.setUpBundle(intent.extras)
        setUpViewModel()
    }

    open fun setUpViewModel() {

    }


}