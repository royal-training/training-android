/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Royal on 3/28/21.
 */
abstract class BaseFragment : Fragment() {

    val composite by lazy { CompositeDisposable() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        composite.clear()
    }

    open fun setUpViews(){

    }

    open fun setUpListeners(){

    }

}