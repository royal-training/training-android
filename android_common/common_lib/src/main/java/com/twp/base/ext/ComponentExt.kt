/*
 * Copyright © 2010-2020 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.ext

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.twp.base.constant.State
import com.twp.base.module.RefreshNotify
import com.twp.base.module.StatusNotify
import com.twp.base.widget.LoadingDialogFragment
import com.twp.base.widget.StateView
import es.dmoral.toasty.Toasty
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import qiu.niorgai.StatusBarCompat
import java.io.IOException

/**
 * Created by Royal on 2020/2/27.
 */
fun AppCompatActivity.setFullScreenWithStatusBar() {
    var uiVisibility = window.decorView.systemUiVisibility
    uiVisibility = uiVisibility.or(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    window.decorView.systemUiVisibility = uiVisibility
}

fun AppCompatActivity.setFullScreenHideNavigation() {
    var uiVisibility = window.decorView.systemUiVisibility
    uiVisibility = uiVisibility.or(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        .or(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
        uiVisibility = uiVisibility.or(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
    window.decorView.systemUiVisibility = uiVisibility
}


fun AppCompatActivity.lightTranslateStatusBar() {
    StatusBarCompat.changeToLightStatusBar(this)
    StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT)
    setFullScreenWithStatusBar()
    setFullScreenHideNavigation()
}

fun DialogFragment.lightTranslateStatusBar() {
    val target = requireActivity()
    if (target is AppCompatActivity) {
        target.lightTranslateStatusBar()
    }
}

fun AppCompatActivity.hideInputMethodManager() {
    getSystemService(Context.INPUT_METHOD_SERVICE).apply {
        (this as InputMethodManager).hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }
}


fun <T> ioToMain(): ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}

fun AppCompatActivity.showWaitLoading(tag: String = "showWaitLoading") {
    if (supportFragmentManager.findFragmentByTag(tag) == null) {
        LoadingDialogFragment().show(supportFragmentManager, tag)
    }
}

fun AppCompatActivity.hideWaitLoading(tag: String = "showWaitLoading") {
    (supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
}


fun Fragment.showWaitLoading(tag: String = "showWaitLoading") {
    if (childFragmentManager.findFragmentByTag(tag) == null) {
        LoadingDialogFragment().show(childFragmentManager, tag)
    }
}

fun Fragment.hideWaitLoading(tag: String = "showWaitLoading") {
    (childFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
}

fun RefreshLayout.finishedWithNotify(notify: RefreshNotify) {
    if (notify.isRefresh) {
        if (notify.noMoreData) finishRefreshWithNoMoreData() else finishRefresh()
    } else {
        if (notify.noMoreData) finishLoadMoreWithNoMoreData() else finishLoadMore()
    }
}

fun StateView.finishedWithNotify(statusNotify: StatusNotify) {
    when (statusNotify.state) {
        State.Normal -> showNormal()
        State.Loading -> showLoadingView()
        State.Empty -> showEmptyView(statusNotify.msg)
        State.Error -> {
            if (statusNotify.error is IOException) {
                showDisable()
            } else {
                showError()
            }
        }
        State.Disable -> showDisable()

    }
}

fun Boolean?.f(): Boolean {
    return this ?: false
}

fun Boolean?.t(): Boolean {
    return this ?: true
}

fun Context?.toast(@StringRes resId: Int){
    this?.let {
        Toasty.normal(it, resId).show()
    }
}

fun Context?.toast(msg: String) {
    this?.let {
        Toasty.normal(it, msg).show()
    }
}