package com.example.administrator.ab.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.entity.D;
import com.example.administrator.list.LunarCalendar;
import com.example.administrator.list.SpecialCalendar;
import com.example.administrator.myapplication.R;

public class CalendarAdapter extends BaseAdapter {
	private boolean isLeapyear = false;
	private int daysOfMonth = 0;// 每月的天数
	private int dayOfWeek = 0;// 每月第一天是星期几
	private int lastDaysOfMonth = 0;// 每月最后一天
	private Context context;
	private String[] dayNumber = new String[42];// 格子数

	private SpecialCalendar sc = null;
	private LunarCalendar lc = null;

	private String currentYear = "";
	private String currentMonth = "";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d",
			Locale.CHINA);
	public static int currentFlag = -1;
//	private int[] schDateTagFlag = null;

	private String showYear = "";
	private String showMonth = "";
	private String animalsYear = "";
	private String leapMonth = "";
	private String cyclical = "";

	private String sysDate = "";
	private String sys_year = "";
	private String sys_month = "";
	private String sys_day = "";

	private String orderTime = null;
	private Integer type=0;
	private String[] cutDates = new String[2];
	private boolean isChangeMonth=true;

	public CalendarAdapter(Date date) {// 将当日日期拆分
		sysDate = sdf.format(date);// 将日期格式转换成2016-7-8 选中的日期
		System.out.println(sysDate);
		sys_year = sysDate.split("-")[0];
		sys_month = sysDate.split("-")[1];
		sys_day = sysDate.split("-")[2];

	}

	public CalendarAdapter(Context context, Resources rs, Date date,
						   int jumpMonth, int jumpYear, int year_c, int month_c, int day_c,
						   List<D> bookList, List<D> useBook, String orderTime, Integer type, boolean isChangeMonth) {
		this(date);
		this.context = context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		this.bookList = bookList;
		this.useBook = useBook;
		this.orderTime = orderTime;
		this.type=type;
		this.isChangeMonth=isChangeMonth;
		int stepYear = year_c + jumpYear;// 获取改变后的年份
		int stepMonth = month_c + jumpMonth;// 获取改变后的月份
		if (stepMonth > 0) {// 当月份大于0时,还是当年的年份

			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {

			stepYear = year_c - 1 + stepMonth / 12;// 月份小于0，年份减1
			stepMonth = stepMonth % 12 + 12;
			if (stepMonth % 12 == 0) {

			}
		}

		currentYear = String.valueOf(stepYear);// 将改变后的年份转换成字符串

		currentMonth = String.valueOf(stepMonth);// 将改变后的月份转换成字符串

		getCalendar(Integer.parseInt(currentYear),// 在页面上显示更新后的年月
				Integer.parseInt(currentMonth));

		if (!TextUtils.isEmpty(orderTime)) {
			if (orderTime.contains("——")) {
				cutDates = orderTime.split("——");
				cutDates[0] = cutDates[0].replace("/", "");
				cutDates[1] = cutDates[1].replace("/", "");
				System.out.println("cutDate:" + cutDates[0] + cutDates[1]);
			} else {
				cutDates[0] = orderTime.replace("/", "");
				cutDates[1] = orderTime.replace("/", "");
			}

		}

	}

	public CalendarAdapter(Context context, Resources rs, Date date, int year,
			int month, int day) {
		this(date);
		this.context = context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();

		currentYear = String.valueOf(year);

		currentMonth = String.valueOf(month);

		getCalendar(Integer.parseInt(currentYear),
				Integer.parseInt(currentMonth));

	}

	@Override
	public int getCount() {
		return dayNumber.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	boolean jieShu = false;
	boolean shengqing = false;
	Date today = new Date();
	Long todaymesc = today.getTime();
	boolean isFirst = true;
	String startDay, bookStartDay;
	String endDay, bookEndDay;
	Long bst, ben, st, en;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.calendar_item, parent, false);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
		String d = dayNumber[position].split("\\.")[0];
		String dv = dayNumber[position].split("\\.")[1];
		String day = changeString(showYear, showMonth, d);
		Long da = getMesc(day);

		SpannableString sp = new SpannableString(d + "\n" + dv);
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
				d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (dv != null || dv != "") {
			sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1,
					dayNumber[position].length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		textView.setText(sp);
		textView.setTextColor(0xBB000000);
//		String clickDay;
		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
			if (currentFlag == position // 格子对上，且是今天
					&& sdf.format(today).equals(
							showYear + "-" + showMonth + "-" + d)) {
				convertView.setBackgroundColor(0xEEf05b60);
			} else if (currentFlag == position) {
				convertView.setBackgroundColor(0xEEf05b60);
			} else {
				convertView.setBackgroundColor(Color.WHITE);
			}

			if (bookList.size() != 0) {
				for (int i = 0; i < bookList.size(); i++) {

					bookStartDay = changeString(bookList.get(i).getQiNian(),
							bookList.get(i).getQiYue(), bookList.get(i)
									.getQiRi());
					bookEndDay = changeString(bookList.get(i).getJieNian(),
							bookList.get(i).getJieYue(), bookList.get(i)
									.getJieRi());

					bst = getMesc(bookStartDay);
					ben = getMesc(bookEndDay);
					if (bst <= da && ben >= da) {
						/**
						 *  蓝色：订单 或者 已申请
						 *  绿色：占用 或者 私人
						 */
						if (TextUtils.isEmpty(bookList.get(i).getOrderID())) {
							if(type==1){
								if (TextUtils.isEmpty(bookList.get(i).getUserName())) {
									if (bookList.get(i).getS().equals("2")) {
										convertView.setBackgroundColor(0xEEc0e9f3);
									} else if (bookList.get(i).getS().equals("3")) {
										convertView.setBackgroundColor(0xEE699637);
									}
								} else {
									if (bookList.get(i).getS().equals("2")) {
										textView.setText(bookList.get(i)
												.getUserName());
										textView.setTextColor(0xBB000000);
										convertView.setBackgroundColor(0xEEc0e9f3);
									} else if (bookList.get(i).getS().equals("3")) {
										textView.setText(bookList.get(i)
												.getUserName());
										textView.setTextColor(0xBB000000);
										convertView.setBackgroundColor(0xEE699637);
									}
								}
							}
							else if(type==2){
								if (TextUtils.isEmpty(bookList.get(i).getUserName())) {
									textView.setTextColor(0xBB000000);
									convertView.setBackgroundColor(0xEE699637);
								} else {
									textView.setText(bookList.get(i).getUserName());
									textView.setTextColor(0xBB000000);
									convertView.setBackgroundColor(0xEE699637);
								}
							}else{
								if (bookList.get(i).getS().equals("2")) {
									convertView.setBackgroundColor(0xEEc0e9f3);
								} else if (bookList.get(i).getS().equals("3")) {
									convertView.setBackgroundColor(0xEE699637);
								}
							}
							
						} else {
							if (TextUtils.isEmpty(bookList.get(i).getUserName())) {
								textView.setTextColor(0xBB000000);
								convertView.setBackgroundColor(0xEEc0e9f3);
							} else {
								textView.setText(bookList.get(i).getUserName());
								textView.setTextColor(0xBB000000);
								convertView.setBackgroundColor(0xEEc0e9f3);
							}
						}

					}

				}
			}

			if (useBook.size() != 0) {
				System.out.println(useBook.get(0).getQiRi());
				if ((showMonth.equals(useBook.get(0).getQiYue()))
						& (showYear.equals(useBook.get(0).getQiNian()))
						& (d.equals(useBook.get(0).getQiRi()))) {
					textView.setText("开始");
					textView.setTextColor(0xBB000000);
					convertView.setBackgroundColor(0xEEffc600);
				}
				if ((showYear.equals(useBook.get(0).getJieNian()))
						& (showMonth.equals(useBook.get(0).getJieYue()))
						& (d.equals(useBook.get(0).getJieRi()))) {
					textView.setText("结束");
					textView.setTextColor(0xBB000000);
					convertView.setBackgroundColor(0xEEffc600);
				}
				startDay = changeString(useBook.get(0).getQiNian(), useBook
						.get(0).getQiYue(), useBook.get(0).getQiRi());
				endDay = changeString(useBook.get(0).getJieNian(),
						useBook.get(0).getJieYue(), useBook.get(0).getJieRi());

				st = getMesc(startDay);
				en = getMesc(endDay);

				if (st <= da && en >= da) {
					convertView.setBackgroundColor(0xEEffc600);
				}

			}

			if (!TextUtils.isEmpty(orderTime)) {
				Long cutOne = getMesc(cutDates[0]);
				Long cutTwo = getMesc(cutDates[1]);
				if (cutOne <= da && cutTwo >= da) {
					convertView.setBackgroundColor(0xEEffc600);

				}
			}
		} else {
			textView.setTextColor(Color.GRAY);
			// convertView.setBackgroundColor(0xBBEEEEEE);
		}

		return convertView;
	}

	public Long getMesc(String daymesc) {
		Long mesc = null;

		try {
			mesc = new SimpleDateFormat("yyyyMMdd").parse(daymesc).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mesc;

	}

	public String changeString(String y, String m, String d) {
		String ymd = "";
		if (m.length() == 1) {
			ymd = y + "0" + m;
		} else {
			ymd = y + m;
		}
		if (d.length() == 1) {
			ymd = ymd + "0" + d;
		} else {
			ymd = ymd + d;
		}

		return ymd;
	}

	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year);
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);
		dayOfWeek = sc.getWeekdayOfMonth(year, month);
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);
		getweek(year, month);
	}

	List<D> useBook = null;
	List<D> bookList = null;

	private void getweek(int year, int month) {
		int j = 1;
		String lunarDay = "";

		for (int i = 0; i < dayNumber.length; i++) {
			if (i < dayOfWeek) {// i<这个月第一天星期几的时候执行
				int temp = lastDaysOfMonth - dayOfWeek + 1;// 上月天数减去本月第一天星期数+1
				lunarDay = lc.getLunarDate(year, month - 1, temp + i, false);
				dayNumber[i] = (temp + i) + "." + lunarDay;

			} else if (i < daysOfMonth + dayOfWeek) {// i在当月里的时候
				String day = String.valueOf(i - dayOfWeek + 1);
				lunarDay = lc.getLunarDate(year, month, i - dayOfWeek + 1,
						false);
				dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
				if (sys_year.equals(String.valueOf(year))
						&& sys_month.equals(String.valueOf(month))
						&& sys_day.equals(day)) {

						currentFlag = i;				

				}
				if(!isChangeMonth){
					currentFlag = -1;
					isChangeMonth=!isChangeMonth;
				}
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
				setAnimalsYear(lc.animalsYear(year));
				setLeapMonth(lc.leapMonth == 0 ? "" : String
						.valueOf(lc.leapMonth));
				setCyclical(lc.cyclical(year));
			} else {
				lunarDay = lc.getLunarDate(year, month + 1, j, false);
				dayNumber[i] = j + "." + lunarDay;
				j++;
			}
		}

		String abc = "";
		for (int i = 0; i < dayNumber.length; i++) {
			abc = abc + dayNumber[i] + ":";
		}
		Log.d("DAYNUMBER", abc);

	}

	public void matchScheduleDate(int year, int month, int day) {

	}

	public String getDateByClickItem(int position) {
		return dayNumber[position];
	}

	public int getStartPositon() {
		return dayOfWeek + 7;
	}

	public int getEndPosition() {
		return (dayOfWeek + daysOfMonth + 7) - 1;
	}

	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}

	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}

	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}

	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}
}
