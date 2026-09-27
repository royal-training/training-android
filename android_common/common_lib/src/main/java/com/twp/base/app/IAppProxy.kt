package com.twp.base.app

import android.app.Application

/**
 * Created by [pengqinping] on [2016/11/18].<br></br>
 * Email: pengqinping@gmail.com <br></br>
 * Motto: A man can be destroyed but not defeated <br></br>
 * Blog:  https://github.com/pengqinping <br></br>
 * Keys: Android Java IOS <br></br>
 * 类描述：Application 代理类，由于基类中可能需要application 但是库项目中拿不到Application上下文，<br></br>
 */

interface IAppProxy {
    fun app(): Application
}
