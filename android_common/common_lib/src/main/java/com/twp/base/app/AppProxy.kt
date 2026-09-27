package com.twp.base.app

import android.content.Context

/**
 * Created by [pengqinping] on [2016/11/18].<br></br>
 * Email: pengqinping@gmail.com <br></br>
 * Motto: A man can be destroyed but not defeated <br></br>
 * Blog:  https://github.com/pengqinping <br></br>
 * Keys: Android Java IOS <br></br>
 * 类描述：<br></br>
 */

object AppProxy {
    private var appProxy: IAppProxy? = null

    fun init(app: IAppProxy) {
        appProxy = app
        initBaseApp()
    }

    private fun checkInit() {
        if (appProxy == null) {
            throw NullPointerException("please call Appproxy.init in application")
        }
    }

    fun context(): Context {
        checkInit()
        return appProxy!!.app()
    }

    private fun initBaseApp() {
        // base app init
    }
}
