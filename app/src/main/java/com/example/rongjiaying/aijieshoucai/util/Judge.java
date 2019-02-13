package com.example.rongjiaying.aijieshoucai.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 根据一个或多个变量判断，返回Boolean类型
 * 
 * @author 容嘉英
 * @date 2016年6月16日下午2:46:38
 */
public class Judge {

	/**
	 * 根据1或者0判断是与否 1；true 0：false
	 */
	public static boolean getBoolean(String str) {
		if (str.equals("1")) {
			return true;
		} else if (str.equals("0")) {
			return false;
		}
		return false;
	}

	/**
	 * 根据1或者0判断是与否 1；true 0：false
	 */
	public static boolean getBoolean(int num) {
		if (num == 1) {
			return true;
		} else if (num == 0) {
			return false;
		}
		return false;
	}

	/**
	 * true变成false，false变成true
	 */
	public static boolean getOppositeBoolean(Boolean istrue) {
		if (istrue) {
			return false;
		} else if (!istrue) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean getBoolean_isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 密码由6-16位字符组成，必须同时包含字母数字；区分大小写
	 * 
	 * @param str_password
	 * @return
	 */
	public static boolean getBoolean_isPassword(String str_password) {
		String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";// 必须同时包含字母数字并且是6-16位
		if (str_password.matches(str)) {
			return true;
		}
		return false;

	}

	/**
	 * 若服务器传回的数据是否为空，为空则返回true
	 */
	public static boolean getBoolean_isNull(String str) {

		if (str == null || TextUtils.isEmpty(str) || str.equals("")
				|| str.equals("null")) {
			return true;
		}

		return false;

	}

	/**
	 * 判断服务器是否成功请求 成功返回true 判断条件：1.传回的数据不为空 2.不是系统繁忙
	 * 
	 * @return
	 */
	public static boolean getBoolean_isRequestSuccess(String content) {
		if (getBoolean_isNull(content) || content.contains("系统繁忙")
				|| content.contains("违反API安全校验规则")) {
			return false;
		}

		return true;
	}

	/**
	 * 判断服务器返回的数控是否是json数组，若不是，则返回false 判断方法：至少包含“[]”、“{}”其中一个
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isJson(String json) {

		if ((json.contains("[") && json.contains("]"))
				|| (json.contains("{") && json.contains("}"))) {

			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断图片url是否正确
	 * 
	 * @param
	 * @return
	 */
	public static boolean isImage(String imageurl) {

		if (imageurl.length() < 15) {

			return false;
		}

		return true;
	}

//	/**
//	 * 验证手机格式是否正确
//	 *
//	 * @param mobiles
//	 *            1.手机号格式正确2.手机号码长度为11位
//	 * @return
//	 */
//	public static boolean isMobileNO(String mobiles) {
//		/*
//		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
//		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
//		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
//		 */
//		String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[3458]"代表第二位可以为3、4、5、8、7中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//		if (TextUtils.isEmpty(mobiles))
//			return false;
//		else
//			return mobiles.matches(telRegex) && mobiles.length() == 11;
//	}

	/**
	 * 验证邮箱格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

}
