/*
 * Copyright © 2010-2021 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.twp.base.databinding.LayoutLoadingViewBinding

/**
 * Created by Royal on 3/28/21.
 */
class LoadingDialogFragment: AppCompatDialogFragment() {

    lateinit var vb: LayoutLoadingViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = LayoutLoadingViewBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.loadingView.smoothToShow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb.loadingView.smoothToHide()
    }
}