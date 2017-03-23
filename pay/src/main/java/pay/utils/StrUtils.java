package pay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtils {

	/**
	 *
	 * @param str
	 * @return
     */
	public static boolean strIsNull(String str) {
		if(str == null || str.isEmpty())
			return true;
		return false;
	}

	/**
	 * 根据传入的格式化样式，取得当前的时间的格式化字符串
	 * @param format 格式化样式
	 * @return
     */
	public static String formatDate(String format) {
		return formatDate(new Date(),format);
	}

	/**
	 *
	 * @param date 时间
	 * @param format
     * @return
     */
	public static String formatDate(Date date,String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 取得默认的当前时间格式化字符串，默认格式是 yyyyMMddHHmmss
	 * @return
     */
	public static String getformatDate(Date date) {
		return formatDate(date,"yyyyMMddHHmmss");
	}

}
