package com.example.administrator.ab.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 某家:
 * @version 创建时间：2015年8月17日 下午3:04:14 类说明
 */
public class Connect {
	private static ThreadLocal<Socket> threadConnect = new ThreadLocal<Socket>();

	private static final String HOST = "120.27.141.95";

	private static final int PORT = 20001;

	private static Socket client;

	private static OutputStream outStr = null;

	private static InputStream inStr = null;
int num=0;

	static Thread mythread;

	private static Handler handler;
	private Button bt;

	Context content;


	public Connect() {
		mythread= new Thread(networkTask);
		mythread.start();

	}

	public void dengru(String UserTel, String UserPwd, String UserPhyAdd,Handler handler) {
		this.handler=handler;


		try {
			System.out.println("=====================传输数据==============");

			byte[] b = GetByte("{\"Module\":\"User\",\"Method\":\"Login\",\"UserTel\":\""
					+ UserTel
					+ "\",\"UserPwd\":\""
					+ UserPwd
					+ "\",\"UserPhyAdd\":\"" + UserPhyAdd + "\"}");
			outStr.write(b);

			outStr.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void tuichu() {
		try {
			System.out.println("=====================传输数据==============");

			byte[] b = GetByte("{\"Module\":\"User\",\"Method\":\"Exit\"}");
			outStr.write(b);
			outStr.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static Timer shijian;
	static int count = 0;

	public static void shijian() {
		shijian = new Timer();
		shijian.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				count++;
			}
		}, 0, 1000);

	}

	static Runnable networkTask = new Runnable() {

		@Override
		public void run() {
			// TODO
			// 在这里进行 http request.网络请求相关操作

			try {
				Connect.connect();

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	public static void connect() throws UnknownHostException, IOException {
		client = threadConnect.get();
		if (client == null) {
			client = new Socket(HOST, PORT);
			threadConnect.set(client);

			System.out.println("========链接开始！========");
		}

		outStr = client.getOutputStream();
		inStr = client.getInputStream();

		try {
			System.out.println("==============开始接收数据===============");

			byte[] b = new byte[100];
			 shijian();
			while (true) {

				if(count==10){
					mythread= new Thread(networkTask);
					mythread.start();
					count=0;
				}
				int r = inStr.read(b);

				if (r != -1) {
					System.out.println(r);
					String ss = new String(b, 0, r, "utf-8");
					System.out.println(ss + "dfx");
					if (ss.contains("登录成功")) {
						xin_start();

						Message msg=new Message();
						msg.what=0x123;
						msg.obj="登录成功";
						handler.sendMessage(msg);
						 shijian.cancel();
					} else if (ss.contains("注册")) {
						System.out.println("哈哈哈哈哈哈哈哈哈");
						// shijian.cancel();
						mythread = new Thread(networkTask);
						mythread.start();
						count=0;
						// disconnect();
					} else if (ss.contains("退出成功")) {
						Message msg=new Message();
						msg.what=0x123;
						msg.obj="退出成功";
						handler.sendMessage(msg);
						timer.cancel();
						// shijian.cancel();
						mythread = new Thread(networkTask);
						mythread.start();
						count=0;
						// disconnect();
					}
				} else {
					break;
				}


			}
		} catch (IOException e) {
			e.printStackTrace();

			System.out.println(e + "加大哈市");
		}

	}

	static Timer timer;

	public static void xin_start() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("发送心跳数据包");

				byte[] b;
				try {
					b = GetByte("{\"Module\":\"User\",\"Method\":\"HeartBeat\"}");
					outStr.write(b);
					outStr.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, 1000, 30000);

	}

	public static void xin_stop() {
		timer.cancel();
	}

	public static void disconnect() {
		try {
			client.shutdownInput();
			client.shutdownOutput();

			// outStr.close();
			// inStr.close();

			client.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static byte[] GetByte(String Msg) throws UnsupportedEncodingException {

		String str = "HEAD=@LLLLL\r\nBODY=";
		str += Msg;
		String ss = String.valueOf((str.getBytes("UTF-8").length - 5));

		try {
			int len = String.valueOf((str.getBytes("UTF-8").length - 5))
					.length();
			// System.out.println(len);
			while (len < 6) {
				ss = "0" + ss;
				len++;
			}

			str = str.replace("@LLLLL", ss);
			System.out.println(str);
			return str.getBytes("UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}