/**   
* @Title: DataConvert.java 
* @Package cn.intellioy.smarttoy.utils 
* @Description: TODO
* () 
* @author sean chen, sean@intellijoy.cn 
* @date 2015年4月3日 下午2:32:31 
* @version V1.0   
*/


package com.example.esycab.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: DataConvert 
 * @Description: TODO
 * () 
 * @author sean chen, sean@intellijoy.cn 
 * @date 2015年4月3日 下午2:32:31 
 *  
 */

public class DataConvert {
	
	/**
	 * short 转化成数组
	
	* @Title: shortToByteArray 
	
	* @Description: TODO() 
	
	* @param @param s
	* @param @return     
	
	* @return byte[]    数组 
	
	* @throws
	 */
	public static byte[] shortToByteArray(short s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }
	
	public static void main(String[] args) {
		short original = 10;
		byte[] tmp = shortToByteArray(original);
		for(byte b: tmp)
		{
			System.out.println(b);
		}
	}
	
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
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
	
}
