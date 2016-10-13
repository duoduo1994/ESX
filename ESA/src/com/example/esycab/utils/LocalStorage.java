package com.example.esycab.utils;

import java.util.Map.Entry;

import com.example.esycab.utils.encrypter.Channel;
import com.example.esycab.utils.encrypter.LoganHashEncrypter;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * @author Administrator
 *LocalStorage包含的方法大致有：setitem、getItem、removeItem、clean、getlength等方法。
 *另外要注意的是，此类的写入写出需要进行加解密。
 */
public class LocalStorage {

	  boolean hasInit=false;
		
	  public static final String LOCALSTORAGE="localstorage";
	  
	  public static final String SETITEM="setitem";
	  
	  public static final String GETITEM="getitem";
	  
	  public static final String REMOVEITEM="removeitem";
	  
	  public static final String CLEAN="clean";
	  
	  public static final String GETLENGTH="getlength";
	  
	  public static final String GETKEY="getkey";
	  
	  
	  public static Context context;
	  
	  transient int recordId;	
	  
	  String key;
	  
	  String value;
	  
	  
	  public static String package_name;
	  
	  public static String localName;
	 
	  
	  public static void initContext(Context context1){
		  context=context1;  
	  }
	  
	  private LocalStorage(int id, String key, String value){
		  this.recordId = id;
		  this.key = key;
		  this.value = value;
	  }
	  
	  public static Object get(Object key){
		  
		  return get(context,key.toString());
		  
	  }
	  
	 
	  //根据特定的样式进行设置
	  public static void set(Object key, Object value){
		  put(context,key.toString(),value.toString());
	  }
	  
	  //删除cookies
	  public static void clear() {
  
		  clear(context);
		  
	  }
	  
	  public static void removeItem(Object key) {
		  removeItem(context,key.toString());
	  }
	  
	  
	  public static int  getLength() {
		  return getLength(context);
	  }
   	  
	  public static String key(String i) {
		  return key(context,i);
	  }
	  
	  
	  //-------------------------------------------------------------------------------------------------
	  static void put(Context ctx,String key,String value){
			
			try{
				
				SharedPreferences  prefs =ctx.getSharedPreferences(LOCALSTORAGE,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();
				
			key = channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(key.getBytes("utf-8")));
				
				value=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(value.getBytes("utf-8")));
				
		        Editor editor = prefs.edit();
		        
		        editor.putString(key, value);
		        
		        editor.commit();
	        
			}
			catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
	  }
	  

	  static void put(Context ctx,String name,String key,String value){
			
			try{
				
				SharedPreferences  prefs =ctx.getSharedPreferences(name,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();
				
				key=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(key.getBytes("utf-8")));
				
				value=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(value.getBytes("utf-8")));
				
		        Editor editor = prefs.edit();
		        
		        editor.putString(key, value);
		        
		        editor.commit();
	        
			}
			catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
	  }
	  
	  
	  
	  
		
	  //-------------------------------------------------------------------------------------------------
	  public synchronized static String get(Context ctx, String key) {
			
			try{
			
				SharedPreferences  prefs = ctx.getSharedPreferences(LOCALSTORAGE,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();
				
				key=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(key.getBytes("utf-8")));
				
				String temp=prefs.getString(key, "");
				
				if(temp!=null && temp!=""){
					
					temp=new String(LoganHashEncrypter.getInstance().hashdecrypt(channel.hexStringToByte(temp)),"utf-8");
				
				}
			
				return temp;
				
			}
			catch(Exception e) {
				
			}
			
			return "";
	  }
	  
	  static String get(Context ctx, String name, String key) {
			
			try{
			
				SharedPreferences  prefs = ctx.getSharedPreferences(name,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();
				
				key=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(key.getBytes("utf-8")));
				
				String temp=prefs.getString(key, "");
				
				if(temp!=null && temp!=""){
					
					temp=new String(LoganHashEncrypter.getInstance().hashdecrypt(channel.hexStringToByte(temp)),"utf-8");
				
				}
			
				return temp;
				
			}
			catch(Exception e) {
				
			}
			
			return "";
	  }
	  
	  
	  
	  
	  
	  //-------------------------------------------------------------------------------------------------
	  static void clear(Context ctx){
			
			SharedPreferences  prefs = ctx.getSharedPreferences(LOCALSTORAGE,Context.MODE_PRIVATE );
			
			Editor editor = prefs.edit();
	       
	        editor.clear();
	        
	        editor.commit();
			
	  }
	  
	  static void clear(Context ctx, String name){
			
			SharedPreferences  prefs = ctx.getSharedPreferences(name,Context.MODE_PRIVATE );
			
			Editor editor = prefs.edit();
	       
	        editor.clear();
	        
	        editor.commit();
			
	  }
	  
	  
	  
	
	  //--------------------------------------------------------------------------------------------------
	  static void removeItem(Context ctx, String key) {
			
			try{
				
				SharedPreferences  prefs = ctx.getSharedPreferences(LOCALSTORAGE,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();
				
				key=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(key.getBytes("utf-8")));
				
				Editor editor = prefs.edit();
				
				editor.remove(key);
				
				editor.commit();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
	  }
	
	  static void removeItem(Context ctx, String name,String key) {
			
			try{
				
				SharedPreferences  prefs = ctx.getSharedPreferences(name,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();
				
				key=channel.bytesToHexString(LoganHashEncrypter.getInstance().hashencrypt(key.getBytes("utf-8")));
				
				Editor editor = prefs.edit();
				
				editor.remove(key);
				
				editor.commit();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
	  }
	  
	  
	  
	  
	  //------------------------------------------------------------------------------------------------
	  static int getLength(Context ctx) {
			 
			try{
				
				SharedPreferences  prefs = ctx.getSharedPreferences(LOCALSTORAGE,Context.MODE_PRIVATE );
				
				Editor editor = prefs.edit();
				
				return prefs.getAll().size();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
				
			return 0;
			
	  }
	  
	  static int getLength(Context ctx,String name) {
			 
			try{
				
				SharedPreferences  prefs = ctx.getSharedPreferences(name,Context.MODE_PRIVATE );
				
				Editor editor = prefs.edit();
				
				return prefs.getAll().size();
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
				
			return 0;
			
	  }
	  
	  
		
	  //-------------------------------------------------------------------------------------------
	  @SuppressWarnings("unchecked")
	  static String key(Context ctx,String key) {
			
			try{
			
				int index=Integer.parseInt(key);
				
				SharedPreferences  prefs = ctx.getSharedPreferences(LOCALSTORAGE,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();

				String temp="";
				
				java.util.Iterator<?>  iterator=prefs.getAll().entrySet().iterator();
				
				int i=0;
				boolean whileable=true;
				while(iterator.hasNext() && whileable) {
					Entry<String,?> set=((Entry<String, ?>) iterator.next());
					if(i==index) {
						whileable=false;
						temp=set.getKey();
					}
					i++;
				}
				
				if(temp!=null && temp!=""){
					
					temp=new String(LoganHashEncrypter.getInstance().hashdecrypt(channel.hexStringToByte(temp)),"utf-8");
				
				}
			
				return temp;
				
			}
			catch(Exception e) {
				
			}
			
			return "";
	  }
		
	  
	  public String key(Context ctx,String name,String key) {
			
			try{
			
				int index=Integer.parseInt(key);
				
				SharedPreferences  prefs = ctx.getSharedPreferences(name,Context.MODE_PRIVATE );
				
				Channel channel=new Channel();

				String temp="";
				
				java.util.Iterator<?>  iterator=prefs.getAll().entrySet().iterator();
				
				int i=0;
				boolean whileable=true;
				while(iterator.hasNext() && whileable) {
					Entry<String,?> set=((Entry<String, ?>) iterator.next());
					if(i==index) {
						whileable=false;
						temp=set.getKey();
					}
					i++;
				}
				
				if(temp!=null && temp!=""){
					
					temp=new String(LoganHashEncrypter.getInstance().hashdecrypt(channel.hexStringToByte(temp)),"utf-8");
				
				}
			
				return temp;
				
			}
			catch(Exception e) {
				
			}
			
			return "";
	  }
	  
	
}
