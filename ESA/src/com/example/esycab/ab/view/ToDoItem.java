package com.example.esycab.ab.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 待办事项对象
 */
public class ToDoItem implements Serializable {

	private static final long serialVersionUID = 3199251197683853468L;
	private Date createTime; // 创建时间(日期及时间)
	private Date time; // 待办事项时间
	
	public ToDoItem(String task) {
		this(task, new Date(java.lang.System.currentTimeMillis()));
	}

	public ToDoItem(String task, Date createTime) {
		super();

		this.createTime = createTime;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return "(" + sdf.format(createTime) + ")" ;
	}
	public Date getCreateDate() {
		return createTime;
	}

	public void setCreateDate(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTime(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		if (time != null)
			c.setTime(time);
		c.set(year, month, day);
		time = c.getTime();
	}
}
