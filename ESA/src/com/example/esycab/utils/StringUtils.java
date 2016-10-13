/**   
 * @Title: StringUtils.java 
 * @Package cn.intellioy.smarttoy.utils 
 * @Description: TODO
 * () 
 * @author sean chen, sean@intellijoy.cn 
 * @date 2015年5月8日 下午1:50:33 
 * @version V1.0   
 */

package com.example.esycab.utils;

import android.annotation.SuppressLint;

/**
 * @ClassName: StringUtils
 * @Description: TODO ()
 * @author sean chen, sean@intellijoy.cn
 * @date 2015年5月8日 下午1:50:33
 * 
 */

public class StringUtils {

	// {5a, 5a 44 5a 4b 4a 00 00 00 01 54 4a 16 29 3a 9a
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[8];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 8; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		byte[] tmp = { 0x5a, 0x5a, 0x44, 0x5a, 0x4b, 0x4a, 0x00, 0x00, 0x01,
				0x54, 0x4a, 0x16, 0x29, 0x3a, (byte) 0x9a };
		System.out.println(bytesToHexString(tmp));

	}

	/**
	 * 日期转换工具
	 * 
	 * @param date
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	// public static String changeDate(String date) {
	// // date = (String) date.subSequence(6, 19);
	// // Date d = new Date(Long.valueOf(date));
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// return formatter.format(date);
	// }
	public static String cutString(String str) {
		str = (String) str.subSequence(0, 10);// 不包含start，包含end
		return str;
	}

}