package com.example.esycab.utils;

public interface ProConst {
	int SUCCESS = 0; // 注册成功
	int LOGIN_FIALED = -1; // 用户登陆失败  /   获取验证码失败
	// int MOBILE_IS_FIALED = -1; // 获取验证码失败
	int VALIDATION_TIME_OUT = -2; // 验证码超时
	int VALIDATION_INPUT_ERROR = -3;// 验证码输入错误
	int MOBILE_ISNOT_EXIST = -4; // 手机号码未注册
	int MOBILE_IS_EXIST = -5; // 手机号码已经被注册
	int NOT_EXIST = 4; // 忘记密码修改成功
	int INVAILD_USER = 11; // 无效用户

	int JOININ = 1;//申請成功
	int LoginSUCCESS = 2;
	int ILLEGAL_REQUEST = -99; // 非法请求，如参数格式不对、URL链接错误等
	int EXCEPITON_ERROR = 101; // 其他错误
	int LOGIN = 102; // 返回登陆界面 //返回登陆界面
	int SPLAH_TIME = 2000;
}
