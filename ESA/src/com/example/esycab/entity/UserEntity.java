package com.example.esycab.entity;
/**
 * 用户实体对象
 * @author Ly
 *
 */
public class UserEntity {
	/**
	 * 用户ID
	 */
	private String UserID;
	/**
	 * 用户姓名
	 */
	private String UserName;
	/**
	 * 用户手机
	 */
	private String UserTel;
	/**
	 * 用户年龄
	 */
	private String UserAge;
	/**
	 * 用户性别
	 */
	private String UserSex;
	private String UserRole;
	
	public String getUserRole() {
		return UserRole;
	}
	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserTel() {
		return UserTel;
	}
	public void setUserTel(String userTel) {
		UserTel = userTel;
	}
	public String getUserAge() {
		return UserAge;
	}
	public void setUserAge(String userAge) {
		UserAge = userAge;
	}
	public String getUserSex() {
		return UserSex;
	}
	public void setUserSex(String userSex) {
		UserSex = userSex;
	}
	
	
}
