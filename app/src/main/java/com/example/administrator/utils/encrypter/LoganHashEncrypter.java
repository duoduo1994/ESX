package com.example.administrator.utils.encrypter;

import java.io.ByteArrayOutputStream;




public class LoganHashEncrypter {

	private static LoganHashEncrypter lhe = null;
	
	
	
	
	public static synchronized LoganHashEncrypter getInstance() {
		if (lhe == null) {
			lhe = new LoganHashEncrypter();
		}
		return lhe;
	}
	
	public byte[] hashencrypt(byte[] msg)  throws Exception{
		//加上hash验证码
		byte[] hash = MD5.getMD5Bytes(msg); 
		int size = hash.length;//16
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(msg);
		bos.write(hash);
		msg = bos.toByteArray();
		//返回加密AES
		byte[] result=null;
		result = AESEncryptor.encrypt(msg);
		//位移
		size = result.length-1;
		byte temp1=0;
		byte temp2=0;
		temp1 = result[0];
		for (int i=0;i<size;i++){
			temp2 = result[i+1];
			result[i+1]=temp1;
			temp1 = temp2;
		}
		result[0]=temp2;
		
		return result;
	}
	
	
	public byte[] hashdecrypt(byte[] msg)  throws Exception{
		
		//位移
		int size = msg.length-1;
		byte temp1=0;
		byte temp2=0;
		temp1 = msg[size];
		for (int i=size;i>0;i--){
			temp2 = msg[i-1];
			msg[i-1]=temp1;
			temp1 = temp2;
		}
		msg[size]=temp2;
		//解密AES
		byte[] hashresult = AESEncryptor.decrypt(msg);
		//hash验证
//		String resultStr = new String(result,"utf-8");
//		String hash = resultStr.substring(resultStr.length()-32);
//		resultStr = resultStr.substring(0,resultStr.length()-32);
//		if(!hash.equals(MD5.getMD5(resultStr))){
//			throw new Exception("notEqualsHash");
//		}
		byte[] hash = new byte[16];
		size = hashresult.length;
		byte[] result =new byte[size-16];
		for(int i=0;i<size-16;i++){
			result[i]=hashresult[i];
		}
		int j=0;
		for(int i=size-16;i<size;i++){
			hash[j]=hashresult[i];
			j++;
		}
		
		if(!new String(hash).equals(new String(MD5.getMD5Bytes(result)))){
			throw new Exception("notEqualsHash"); 
		}
		
		
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String temp = "测试数据";
			lhe = LoganHashEncrypter.getInstance();
			//byte[] result = lhe.hashencrypt(temp);
		
			//System.out.println(new String(result,"utf-8"));
			//System.out.println(new String(lhe.hashdecrypt(result),"utf-8"));
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
