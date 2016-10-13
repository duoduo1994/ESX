package com.eyoucab.list;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * 
 * @author Ly
 * 
 */
public class Utils {
	/**
	 * 跳转Activity
	 * 
	 * @param sActivity
	 * @param cls
	 * @param IsClose
	 * @param isAnim
	 */
	// public static void SkipActivityIn(Activity sActivity, Class<?> cls,
	// Boolean IsClose, Boolean isAnim) {
	// Intent intent = new Intent(sActivity, cls);
	// sActivity.startActivity(intent);
	// if (isAnim) {
	// sActivity.overridePendingTransition(R.anim.in_from_right,
	// R.anim.out_to_left);
	// }
	// if (IsClose)// 判断是否关闭
	// {
	// sActivity.finish();
	// }
	// }

	/**
	 * 返回Activity
	 * 
	 * @param sActivity
	 * @param IsClose
	 * @param isAnim
	 */
	// public static void SkipActivityBack(Activity sActivity,
	// Boolean IsClose, Boolean isAnim) {
	// sActivity.finish();
	// if (isAnim) {
	// sActivity.overridePendingTransition(R.anim.in_from_left,
	// R.anim.out_to_right);
	// }
	// if (IsClose)// 判断是否关闭
	// {
	// sActivity.finish();
	// }
	// }
	/**
	 * 检测参数中是否有空的存在
	 * 
	 * @param args
	 * @return 存在 返回TRUE 不存在 返回FALSE
	 */
	public static Boolean CheckEmptyIsExist(String... args) {
		for (String s : args) {
			if (s.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否是手机
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	/**
	 * 验证名字合法性
	 * 中文开头则中文结尾
	 */
	public static boolean isBookName(String str){
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^([\u4e00-\u9fa5]+)|([a-zA-Z]+)$"); // 验证名称
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	/**
	 * 验证用户名合法性
	 * 中文开头则中文结尾
	 */
	public static boolean isUserName(String str){
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]{1,8}$"); // 验证名称
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
}
