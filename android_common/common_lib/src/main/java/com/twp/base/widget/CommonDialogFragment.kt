/*
 * Copyright © 2010-2020 QinPing Peng. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */

package com.twp.base.widget

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.twp.base.R
import com.twp.base.ext.lightTranslateStatusBar

/**
 * Created by Royal on 2020/3/8.
 */
class CommonDialogFragment : AppCompatDialogFragment() {

    companion object {
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_MESSAGE = "message"
        private const val EXTRA_NEGATIVE_TITLE = "negative_title"
        private const val EXTRA_POSITIVE_TITLE = "positive_title"
        private const val EXTRA_CANCELABLE = "can_cancel"
        fun newInstance(
            title: String? = null,
            message: String? = null,
            negativeTitle: String? = null,
            positiveTitle: String? = null,
            cancelable: Boolean = false
        ): CommonDialogFragment {
            return CommonDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_TITLE, title)
                    putString(EXTRA_MESSAGE, message)
                    putString(EXTRA_NEGATIVE_TITLE, negativeTitle)
                    putString(EXTRA_POSITIVE_TITLE, positiveTitle)
                    putBoolean(EXTRA_CANCELABLE, cancelable)
                }
            }
        }

    }

    var onPositiveClick: (() -> Unit)? = null
    var onNegativeClick: (() -> Unit)? = null

    private var title: String? = null
    private var message: String? = null
    private var negativeTitle: String? = null
    private var positiveTitle: String? = null
    private var canCancelable: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lightTranslateStatusBar()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initArguments()
        val builder = AlertDialog.Builder(requireContext())
        title?.let {
            builder.setTitle(it)
        }
        message?.let {
            builder.setMessage(it)
        }
        positiveTitle?.let {
            builder.setPositiveButton(it) { _, _ ->
                dismissAllowingStateLoss()
                onPositiveClick?.invoke()
            }
        }
        negativeTitle?.let {
            builder.setNegativeButton(it) { _, _ ->
                dismissAllowingStateLoss()
                onNegativeClick?.invoke()
            }
        }

        val dialog = builder.create()
        dialog.setCancelable(canCancelable)
        dialog.setCanceledOnTouchOutside(canCancelable)
        return dialog
    }

    private fun initArguments() {
        arguments?.run {
            title = getString(EXTRA_TITLE)
            message = getString(EXTRA_MESSAGE)
            negativeTitle = getString(
                EXTRA_NEGATIVE_TITLE,
                context?.resources?.getString(R.string.dialog_cancel)
            )
            positiveTitle =
                getString(EXTRA_POSITIVE_TITLE, context?.resources?.getString(R.string.dialog_ok))
            canCancelable = getBoolean(EXTRA_CANCELABLE, false)
        }
    }

}