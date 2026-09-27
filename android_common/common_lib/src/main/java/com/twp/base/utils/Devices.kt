package com.twp.base.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.TextUtils
import android.util.Log
import java.lang.reflect.InvocationTargetException

/**
 * Created by pengqinping on 2019-06-15.
 * @email pengqinping@hotmail.com
 * @description
 */
object Devices {
    private const val TAG = "Devices"

    fun isHuawei(): Boolean {
        if (TextUtils.isEmpty(Build.MANUFACTURER)) {
            return false
        }
        if (Build.MANUFACTURER.contains("Huawei", true)) {
            return true
        }
        if (Build.BRAND.contains("Huawei", true)) {
            return true
        }
        return false
    }

    fun isMiui(): Boolean {
        return !TextUtils.isEmpty(getSystemProsperity("ro.miui.ui.version.name"))
    }

    fun isOppo(): Boolean {
        return !TextUtils.isEmpty(getSystemProsperity("ro.product.brand"))
    }

    fun isVivo(): Boolean {
        return !TextUtils.isEmpty(getSystemProsperity("ro.vivo.os.name"))
    }


    @SuppressLint("PrivateApi")
    fun getSystemProsperity(key: String): String {

        var clazz: Class<*>? = null
        try {
            clazz = Class.forName("android.os.SystemProperties")
        } catch (classNotFound: ClassNotFoundException) {
            Log.e(TAG, "getSystemProsperity forName : $classNotFound")
            try {
                clazz = ClassLoader.getSystemClassLoader().loadClass("android.os.SystemProperties")
            } catch (classNotFound: ClassNotFoundException) {
                Log.e(TAG, "getSystemProsperity loadClass : $classNotFound")
            }
        }

        try {
            val method = clazz?.getMethod("get", String::class.java)
            return method?.invoke(null, key) as String
        } catch (noSuchMethod: NoSuchMethodException) {
            Log.e(TAG, "getSystemProsperity noSuchMethod:$noSuchMethod")
        } catch (access: IllegalAccessException) {
            Log.e(TAG, "getSystemProsperity access:$access")
        } catch (argument: IllegalArgumentException) {
            Log.e(TAG, "getSystemProsperity argument:$argument")
        } catch (invocation: InvocationTargetException) {
            Log.e(TAG, "getSystemProsperity invocation:$invocation")
        }
        return ""
    }
}