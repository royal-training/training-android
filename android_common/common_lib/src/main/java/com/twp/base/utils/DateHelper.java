/*
 * 版       权:  Royal.k.peng@gmail.com, All rights reserved
 * 作       者:  Royal
 * 座 右  铭:  Never give up, adhere to in the end.
 */
package com.twp.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by [pengqinping] on [2016/11/17].<br>
 * Email: pengqinping@gmail.com <br>
 * Motto: A man can be destroyed but not defeated <br>
 * Blog:  https://github.com/pengqinping <br>
 * Keys: Android Java IOS <br>
 * 类描述：日期工具类 <br>
 */

public final class DateHelper {

	private static final String DATEFORMAT = "yyyy-MM-dd";

	public static String getNowTime4DefaultFormat() {
		return getNowTime4Format(DATEFORMAT);
	}

	public static String getNowTime4Format(String format) {
		return getNowTime4Format(new Date(), format);
	}

	private static String getNowTime4Format(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format,
				Locale.getDefault());
		return dateFormat.format(date).concat(" 00:00:00");
	}

	public static String getNowBeforeOneDate() {
		Calendar cla = Calendar.getInstance(Locale.getDefault());
		cla.set(cla.get(Calendar.YEAR), cla.get(Calendar.MONTH),
				cla.get(Calendar.DATE) - 1);
		return getNowTime4Format(cla.getTime(), DATEFORMAT);
	}

}
