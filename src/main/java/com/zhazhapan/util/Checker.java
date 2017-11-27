/**
 * 
 */
package com.zhazhapan.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author pantao
 *
 */
public class Checker {

	public static boolean isSorted(int[] nums) {
		boolean desc = nums[0] - nums[nums.length - 1] < 0 ? false : true;
		for (int i = 0; i < nums.length - 1; i++) {
			if (!desc && nums[i] > nums[i + 1]) {
				return false;
			}
			if (desc && nums[i] < nums[i + 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 超链接匹配，忽略大小写
	 */
	public static final Pattern HYPER_LINK_PATTERN = Pattern
			.compile("^(https*://)?([^\\s&;\"':<>]+\\.)+[a-z0-9]+(/[^\\s]*)*$", Pattern.CASE_INSENSITIVE);

	/**
	 * 日期匹配
	 */
	public static final Pattern DATE_PATTERN = Pattern.compile("^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$");

	/**
	 * 整数匹配
	 */
	public static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]+$");

	/**
	 * 数字匹配
	 */
	public static final Pattern DECIMAL_PATTERN = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");

	/**
	 * 邮箱匹配，忽略大小写
	 */
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile("^[0-9a-z]+([0-9a-z]|(\\.[0-9a-z]+))*@[0-9a-z]+(\\.{1}[0-9a-z]+)+$", Pattern.CASE_INSENSITIVE);

	/**
	 * 是否为日期格式
	 * 
	 * @param date
	 *            需要判断的日期
	 * @return 匹配结果
	 */
	public static boolean isDate(String date) {
		return isNull(date) ? false : DATE_PATTERN.matcher(date).matches();
	}

	/**
	 * 替换字符之前检查字符串是否为空
	 * 
	 * @param string
	 *            需要检测的字符串
	 * @param oldChar
	 *            需要替换的字符
	 * @param newChar
	 *            新的字符
	 * @return 结果字符串
	 */
	public static String replace(String string, char oldChar, char newChar) {
		return checkNull(string).replace(oldChar, newChar);
	}

	/**
	 * 替换字符串之前检查字符串是否为空
	 * 
	 * @param string
	 *            需要检测的字符串
	 * @param oldString
	 *            需要替换的字符串
	 * @param newString
	 *            新的字符串
	 * @return 结果字符串
	 */
	public static String replace(String string, String oldString, String newString) {
		return checkNull(string).replace(oldString, newString);
	}

	/**
	 * 是否为邮箱格式
	 * 
	 * @param email
	 *            需要判断的邮箱地址
	 * @return 匹配结果
	 */
	public static boolean isEmail(String email) {
		return isNullOrEmpty(email) ? false : EMAIL_PATTERN.matcher(email).matches();
	}

	/**
	 * 是否为数字（含小数）格式
	 * 
	 * @param decimal
	 *            需要判断的数字
	 * @return 匹配结果
	 */
	public static boolean isDecimal(String decimal) {
		return isNull(decimal) ? false : DECIMAL_PATTERN.matcher(decimal).matches();
	}

	/**
	 * 是否为整数格式
	 * 
	 * @param number
	 *            需要判断的整数
	 * @return 匹配结果
	 */
	public static boolean isNumber(String number) {
		return isNull(number) ? false : NUMBER_PATTERN.matcher(number).matches();
	}

	/**
	 * 对象是否为NULL
	 * 
	 * @param object
	 *            需要判断的对象
	 * @return 判断结果
	 */
	public static boolean isNull(Object object) {
		return object == null ? true : false;
	}

	/**
	 * 对象是否不为NULL
	 * 
	 * @param object
	 *            需要判断的对象
	 * @return 判断结果
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * 字符串是否为NULL或空
	 * 
	 * @param string
	 *            需要判断的字符串
	 * @return 判断结果
	 */
	public static boolean isNullOrEmpty(String string) {
		return isNull(string) ? true : string.isEmpty();
	}

	/**
	 * 字符串是否不为空
	 * 
	 * @param string
	 *            需要判断的字符串
	 * @return 判断结果
	 */
	public static boolean isNotEmpty(String string) {
		return !isNullOrEmpty(string);
	}

	/**
	 * 检测字符串是否为NULL
	 * 
	 * @param string
	 *            需要检测的字符串
	 * @return 如果字符串为NULL，则返回空字符串，否则返回原字符串
	 */
	public static String checkNull(String string) {
		return isNull(string) ? "" : string;
	}

	/**
	 * 检测整数是否为NULL
	 * 
	 * @param longNum
	 *            需要检测的整数
	 * @return 如果整数为NULL，则返回0，否则返回原整数
	 */
	public static long checkNull(long longNum) {
		return isNull(longNum) ? 0 : longNum;
	}

	/**
	 * 判断LIST是否不为空
	 * 
	 * @param list
	 *            需要判断的LIST
	 * @return 判断结果
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * 判断LIST是否为空或NULL
	 * 
	 * @param list
	 *            需要判断的LIST
	 * @return 判断结果
	 */
	public static boolean isEmpty(List<?> list) {
		return isNull(list) ? true : list.isEmpty();
	}

	/**
	 * 判断MAP是否为不空
	 * 
	 * @param map
	 *            需要判断的MAP
	 * @return 判断结果
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 判断MAP是否为空或NULL
	 * 
	 * @param map
	 *            需要判断的MAP
	 * @return 判断结果
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) ? true : map.isEmpty();
	}

	/**
	 * 是否为超链接
	 * 
	 * @param hyperLink
	 *            需要匹配超链接
	 * @return 匹配结果
	 */
	public static boolean isHyperLink(String hyperLink) {
		return isNotEmpty(hyperLink) ? HYPER_LINK_PATTERN.matcher(hyperLink).matches() : false;
	}

	/**
	 * 检测日期是否为NULL
	 * 
	 * @param date
	 *            需要检测的日期
	 * @return 如果日期为NULL，则返回当前日期，否则返回原日期
	 */
	public static Date checkDate(Date date) {
		return isNull(date) ? new Date() : date;
	}
}