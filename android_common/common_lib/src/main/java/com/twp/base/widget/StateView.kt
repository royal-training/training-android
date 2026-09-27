/*
 * Copyright © 2010-2020 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.twp.base.R
import com.twp.base.constant.State
import com.twp.base.databinding.LayoutStateViewBinding

/**
 * Created by Royal on 2020/3/7.
 */
class StateView : FrameLayout {

    private val defaultLoadingDrawable = R.drawable.state_loading
    private val defaultEmptyDrawable = R.drawable.state_empty
    private val defaultNetWorkDrawable = R.drawable.state_network
    private val defaultErrorDrawable = R.drawable.state_error
    private val defaultLoadingString = R.string.state_loading
    private val defaultEmptyString = R.string.state_empty
    private val defaultNetworkDisableString = R.string.state_network_disable
    private val defaultErrorString = R.string.state_error
    private val defaultStringColor = ContextCompat.getColor(context, R.color.base_primary_light)
    private val defaultStringSize = context.resources.getDimension(R.dimen.sp_14)
    private val defaultStringBackground = android.R.color.transparent

    private var state = State.Normal
    private var loadingDrawable = defaultLoadingDrawable
    private var emptyDrawable = defaultEmptyDrawable
    private var disableDrawable = defaultNetWorkDrawable
    private var errorDrawable = defaultErrorDrawable
    private var loadingString = defaultLoadingString
    private var emptyString = defaultEmptyString
    private var disableString = defaultNetworkDisableString
    private var errorString = defaultErrorString

    @ColorInt
    private var stringColor = defaultStringColor

    @Dimension
    private var stringSize = defaultStringSize

    @DrawableRes
    private var stringBackground = defaultStringBackground
    private var loadingAnim: ObjectAnimator? = null
    var viewLifeCycle: ViewLifeCycle? = null
    private lateinit var vb: LayoutStateViewBinding
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        initAttributes(context, attrs)
        attacheRootView(context)
        showNormal()
    }

    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        attrs ?: return
        val attribute = context.obtainStyledAttributes(attrs, R.styleable.StateView)
        emptyDrawable = attribute.getResourceId(
                R.styleable.StateView_state_empty_drawable,
                defaultEmptyDrawable
        )
        disableDrawable = attribute.getResourceId(
                R.styleable.StateView_state_network_drawable,
                defaultNetWorkDrawable
        )
        errorDrawable = attribute.getResourceId(
                R.styleable.StateView_state_error_drawable,
                defaultErrorDrawable
        )
        emptyString =
                attribute.getResourceId(R.styleable.StateView_state_empty_string, defaultEmptyString)
        disableString = attribute.getResourceId(
                R.styleable.StateView_state_network_string,
                defaultNetworkDisableString
        )
        errorString =
                attribute.getResourceId(R.styleable.StateView_state_error_string, defaultErrorString)
        stringColor =
                attribute.getColor(R.styleable.StateView_android_textColor, defaultStringColor)
        stringSize = attribute.getDimension(
                R.styleable.StateView_android_textSize,
                defaultStringSize
        )
        stringBackground = attribute.getResourceId(
                R.styleable.StateView_android_background,
                defaultStringBackground
        )
        attribute.recycle()
    }

    private fun attacheRootView(context: Context) {
        vb = LayoutStateViewBinding.inflate(LayoutInflater.from(context), this, true)
        vb.tvwString.setTextSize(TypedValue.COMPLEX_UNIT_PX, stringSize)
        vb.tvwString.setTextColor(stringColor)
        viewLifeCycle?.inflate(vb.imgDrawable, vb.tvwString)
    }

    private fun setStateView(state: State, @DrawableRes drawableResId: Int?, @StringRes stringResId: Int? ) {
        val mDrawable = if(drawableResId == null) null else ContextCompat.getDrawable(context, drawableResId)
        val value = if(stringResId == null) null else resources.getString(stringResId)
        setStateView(state, mDrawable, value)
    }


    private fun setStateView(state: State, drawable: Drawable? = null, value: String? = null) {
        this.state = state
        if (state == State.Normal) {
            setDrawableAndString()
            visibility = View.GONE
            return
        }

        visibility = View.VISIBLE
        setDrawableAndString(drawable, value)
    }

    private fun setDrawableAndString(drawable: Drawable? = null, value: String? = null) {
        if (null != drawable) {
            vb.imgDrawable.setImageDrawable(drawable)
        }
        if (null != value) {
            vb.tvwString.text = value
        }
        if (state == State.Loading && drawable != null) {
            if (null == loadingAnim) {
                loadingAnim = ObjectAnimator.ofFloat(vb.imgDrawable, "rotation", 0.0F, 359.0F)
                loadingAnim?.interpolator = LinearInterpolator()
                loadingAnim?.repeatCount = -1
                loadingAnim?.duration = 1000L
            }
            loadingAnim?.start()
        } else {
            loadingAnim?.end()
        }
    }

    fun setStateViewClickListener(listener: (view: View, state: State) -> Unit) {
        setOnClickListener {
            listener(it, state)
        }
        vb.imgDrawable.setOnClickListener { listener(it, state) }
    }

    fun showLoadingView(drawableResId: Int? = loadingDrawable, stringResId: Int? = loadingString) {
        setStateView(State.Loading, drawableResId, stringResId)
    }

    fun showEmptyView(msg: String?){
        setStateView(State.Empty,  ContextCompat.getDrawable(context, defaultEmptyDrawable), msg ?: context.getString(defaultEmptyString))
    }

    fun showEmptyView(drawableResId: Int? = emptyDrawable, stringResId: Int? = emptyString) {
        setStateView(State.Empty, drawableResId, stringResId)
    }

    fun showDisable(drawableResId: Int? = disableDrawable, stringResId: Int? = disableString) {
        setStateView(State.Disable, drawableResId, stringResId)
    }

    fun showError(msg: String?){
        setStateView(State.Error, ContextCompat.getDrawable(context, defaultErrorDrawable), msg ?: context.getString(defaultErrorString))
    }

    fun showError(drawableResId: Int? = errorDrawable, stringResId: Int? = errorString) {
        setStateView(State.Error, drawableResId, stringResId)
    }

    fun showNormal() {
        setStateView(State.Normal)
    }

    interface ViewLifeCycle {
        fun inflate(image: AppCompatImageView, textView: AppCompatTextView)
    }


}