package com.twp.base.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.twp.base.ext.lightTranslateStatusBar
import es.dmoral.toasty.Toasty
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by pengqinping on 2020-02-17.
 * @email pengqinping@hotmail.com
 * @description
 */
abstract class BaseActivity : AppCompatActivity() {

    val composite by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpStatusBar()
        setUpViews()
        setUpListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.clear()
    }

    open fun setUpStatusBar() {
        lightTranslateStatusBar()
    }

    open fun setUpViews() {

    }

    open fun setUpListeners() {

    }
}