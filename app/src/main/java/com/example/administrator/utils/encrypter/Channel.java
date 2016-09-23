package com.example.administrator.utils.encrypter;


public class Channel {

	public String byteToString(byte[] array) {
		
		StringBuilder sb=new StringBuilder(array.length*2);
		
		for(int k=0;k<array.length;k++)
		{
			
			byte bt=array[k];
			
			int temp=bt&0xFF;
			
			String hex = Integer.toHexString(temp); 
			
			if(hex.length()==1)
			{
				hex = "0"+hex;
			}
			
			sb.append(hex);
			
		}
		
		
		
		String str="";
		
		try{
			
			str=sb.toString();
			
		}
		catch(Exception e) {
			
		}
		
		return str;
		
	}
	
	
	public String bytesToHexString(byte[] bArray) { 
	    StringBuffer sb = new StringBuffer(bArray.length); 
	    String sTemp; 
	    for (int i = 0; i < bArray.length; i++) { 
	     sTemp = Integer.toHexString(0xFF & bArray[i]); 
	     if (sTemp.length() < 2) 
	      sb.append(0); 
	     sb.append(sTemp.toUpperCase()); 
	    } 
	    return sb.toString(); 
	}

	
	
	public  byte[] hexStringToByte(String hex) { 
	    int len = (hex.length() / 2); 
	    byte[] result = new byte[len]; 
	    char[] achar = hex.toCharArray(); 
	    for (int i = 0; i < len; i++) { 
	     int pos = i * 2; 
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1])); 
	    } 
	    return result; 
	}

	private byte toByte(char c) { 
	    byte b = (byte) "0123456789ABCDEF".indexOf(c); 
	    return b; 
	}

	
}
