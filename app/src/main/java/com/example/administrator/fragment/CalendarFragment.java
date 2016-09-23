package com.example.administrator.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.CalendarAdapter;
import com.example.administrator.entity.D;
import com.example.administrator.myapplication.R;


@SuppressLint({ "ValidFragment", "NewApi" })
public class CalendarFragment extends DialogFragment implements
		OnDateSetListener {
	public static final Integer oneDayMesc=86400000;
 	private GestureDetector gestureDetector = null;
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private TextView yishen=null;
	private TextView yizhan=null;
	private int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	Context context;

	private Date currentDate;

	private TextView nextMonth; // 下一月文本框
	private TextView preMonth; // 上一月文本框
	private Button cancelBtn; // 取消按钮
	D bookDay, dd;

	List<D> bookDayList = new ArrayList<D>();
	List<D> aa = new ArrayList<D>();

	JSONObject tJson = null;
	String nth_HallsId, result, orderTime=null;
	Integer type;

	// private Calendar calendar_c; // 当前日历
	public CalendarFragment() {
		super();
	}

	public CalendarFragment(Date date, String nth_HallsId, String result,
			Integer type, String orderTime) {
		super();
		this.orderTime = orderTime;
		this.nth_HallsId = nth_HallsId;
		this.result = result;
		this.type = type;
		currentDate = date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.CHINA);
		String currentDateString = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDateString.split("-")[0]);
		month_c = Integer.parseInt(currentDateString.split("-")[1]);
		day_c = Integer.parseInt(currentDateString.split("-")[2]);

		if (!TextUtils.isEmpty(nth_HallsId) && result == null) {
		//	init(nth_HallsId);
		} 
		else if (!TextUtils.isEmpty(result) && nth_HallsId == null) {
			initschedule(result);
		}

	}

	String[] cutDates = new String[2];

	/**
	 * 解析我的喜事堂占用情况
	 * 
	 * @param result
	 */
	public void initschedule(String result) {

		if (!TextUtils.isEmpty(orderTime)) {
			if (orderTime.contains("——")) {
				cutDates = orderTime.split("——");
				cutDates[0] = cutDates[0].replace("/", "");
				cutDates[1] = cutDates[1].replace("/", "");
			} else {
				cutDates[0] = orderTime.replace("/", "");
				cutDates[1] = orderTime.replace("/", "");
			}

		}

		try {
			JSONObject jsonObject = new JSONObject(result.trim());
			JSONArray jsonArry = jsonObject.getJSONArray("占用情况");
			String userName = null;
			String status=null;
			String orderID=null;
			for (int i = 0; i < jsonArry.length(); i++) {
				bookDay = new D();
				tJson = jsonArry.getJSONObject(i);

				String SolarDtFrom = tJson.getString("SolarDtFrom");
				String SolarDtTo = tJson.getString("SolarDtTo");
				if(type==1){//喜事堂
					userName = tJson.getString("UserName");
					status=tJson.getString("HallStatus");
				}
				if(type==2){//厨师
					userName = tJson.getString("ROrderName");
					status=tJson.getString("OccupyStatus");
					orderID=tJson.getString("OrderID");
					
				}
				

				Date dtFromDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.parse(SolarDtFrom);
				Date dtToDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.parse(SolarDtTo);
				String startDay = new SimpleDateFormat("yyyyMMdd")
						.format(dtFromDate);
				String endDay = new SimpleDateFormat("yyyyMMdd")
						.format(dtToDate);
				Long msecStart = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.parse(SolarDtFrom).getTime();
				Long msecEnd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.parse(SolarDtTo).getTime();
				bookDay.setStartDay(startDay);
				bookDay.setEndDay(endDay);
				bookDay.setMsecStart(msecStart);
				bookDay.setMsecEnd(msecEnd);

				String[] t = SolarDtFrom.split("/");
				bookDay.setQiNian(t[0]);
				bookDay.setQiYue(t[1]);
				bookDay.setQiRi(t[2].split(" ")[0]);
				

				String[] t1 = SolarDtTo.split("/");
				bookDay.setJieNian(t1[0]);
				bookDay.setJieYue(t1[1]);
				bookDay.setJieRi(t1[2].split(" ")[0]);

				bookDay.setS(status);
				bookDay.setUserName(userName);
				bookDay.setOrderID(orderID);
				System.out.println("bookDay=" + startDay + endDay);
				if (!TextUtils.isEmpty(orderTime)
						&& startDay.equals(cutDates[0])
						&& endDay.equals(cutDates[1])) {

				} else {
					bookDayList.add(bookDay);
				}
			}

			calV = new CalendarAdapter(CalendarFragment.this.getActivity(),
					getResources(), currentDate, jumpMonth, jumpYear, year_c,
					month_c, day_c, bookDayList, aa,orderTime,type,true);
			gridView.setAdapter(calV);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 解析喜事堂占用情况
	 * 
	 *
	 */
//	public void init(String nth_HallsId) {
//		RequestParams params = new RequestParams();
//		System.out.println("nth_HallsId=" + nth_HallsId);
//		params.put("ID", nth_HallsId);
//		SmartFruitsRestClient.post("HallsHandler.ashx?Action=getTake", params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						Toast.makeText(getActivity(), "数据加载失败，请重试...",
//								Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						String result = new String(arg2);
//						System.out.println("G" + result);
//						try {
//							JSONObject occupation = new JSONObject(result
//									.trim());
//							JSONArray arry = occupation.getJSONArray("占用情况");
//							for (int i = 0; i < arry.length(); i++) {
//								dd = new D();
//								tJson = arry.getJSONObject(i);
//
//								String SolarDtFrom = tJson
//										.getString("SolarDtFrom");
//								String SolarDtTo = tJson.getString("SolarDtTo");
//
//								String[] t = SolarDtFrom.split("/");
//								dd.setQiNian(t[0]);
//								dd.setQiYue(t[1]);
//								dd.setQiRi(t[2].split(" ")[0]);
//								dd.setS(tJson.getString("HallStatus"));
//
//								String[] t1 = SolarDtTo.split("/");
//								dd.setJieNian(t1[0]);
//								dd.setJieYue(t1[1]);
//								dd.setJieRi(t1[2].split(" ")[0]);
//								bookDayList.add(dd);
//
//							}
//
//							calV = new CalendarAdapter(CalendarFragment.this
//									.getActivity(), getResources(),
//									currentDate, jumpMonth, jumpYear, year_c,
//									month_c, day_c, bookDayList, aa,orderTime,type,true);
//							gridView.setAdapter(calV);
//
//						} catch (JSONException e) {
//
//							e.printStackTrace();
//						}
//
//					}
//
//				});
//
//	}

	 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View calendarV = inflater.inflate(R.layout.calendar, container,
				false);
		calendarV.setBackgroundColor(Color.WHITE);
		gestureDetector = new GestureDetector(this.getActivity(),
				new MyGestureListener());
		calV = new CalendarAdapter(this.getActivity(), getResources(),
				currentDate, jumpMonth, jumpYear, year_c, month_c, day_c,
				bookDayList, aa,orderTime,type,true);
		addGridView(calendarV);
		gridView.setAdapter(calV);
		if (!TextUtils.isEmpty(nth_HallsId) || !TextUtils.isEmpty(result)) {
			// 当需要显示占用的情况时，则显示颜色所对应的占用情况的布局
			calendarV.findViewById(R.id.showcalendarcolor).setVisibility(
					View.VISIBLE);
		}
		
		yishen=(TextView) calendarV.findViewById(R.id.yishen);
		yizhan=(TextView) calendarV.findViewById(R.id.yizhan);
		if(type==2){
			yishen.setText("订单");
			yizhan.setText("个人");
		}

		topText = (TextView) calendarV.findViewById(R.id.tv_month);
		addTextToTopTextView(topText);
		nextMonth = (TextView) calendarV.findViewById(R.id.right_img);
		preMonth = (TextView) calendarV.findViewById(R.id.left_img);
		nextMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Hwd", "kjkkjk");
				addGridView(calendarV); // 添加一个gridView
				jumpMonth++; // 下一个月

				calV = new CalendarAdapter(CalendarFragment.this.getActivity(),
						getResources(), currentDate, jumpMonth, jumpYear,
						year_c, month_c, day_c, bookDayList, aa,orderTime,type,false);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}
		});

		preMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addGridView(calendarV); // 添加一个gridView
				jumpMonth--; // 上一个月

				calV = new CalendarAdapter(CalendarFragment.this.getActivity(),
						getResources(), currentDate, jumpMonth, jumpYear,
						year_c, month_c, day_c, bookDayList, aa,orderTime,type,false);
				gridView.setAdapter(calV);
				addTextToTopTextView(topText);
			}
		});
		cancelBtn = (Button) calendarV.findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (aa.size() == 0) {
					// 只选择一天时
					if (!TextUtils.isEmpty(result)) {
						try {
							callBackValue.SendMessageValue(parseDate(oneDay),
									parseDate(oneDay));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					CalendarFragment.this.dismiss();
				} else {
					// 选择一个时间段
					if (callBackValue != null) {
						try {
							callBackValue.SendMessageValue(
									parseDate(startItem), parseDate(endItem));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						CalendarFragment.this.dismiss();
					}
				}
				startItem = null;
				endItem = null;
			}
		});
		return calendarV;
	}

	public String parseDate(String str) throws ParseException {
		if (!TextUtils.isEmpty(str)) {
			str = new SimpleDateFormat("yyyy/MM/dd")
					.format(new SimpleDateFormat("yyyyMMdd").parse(str));
		}

		return str;
	}

	CallBackValue callBackValue;

	/**
	 * fragment与activity产生关联是 回调这个方法
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// 当前fragment从activity重写了回调接口 得到接口的实例化对象
		if (activity instanceof CallBackValue) {
			callBackValue = (CallBackValue) getActivity();
		}

	}

	@Override
	public void onDetach() {
		super.onDetach();
		callBackValue = null;
	}

	// 定义一个回调接口
	public interface CallBackValue {
		public void SendMessageValue(String startDay, String endDay);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，则进行cancel，状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
		setCancelable(true);
		// 可以设置dialog的显示风格，如style为STYLE_NO_TITLE，将被显示title。theme为0，表示由系统选择合适的theme。
		int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
		setStyle(style, theme);
	}

	// 添加头部的年份 闰哪月等信息
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("年")
				.append(calV.getShowMonth()).append("月").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.GRAY);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	String startItem = "", endItem = "";
	boolean isBook = true, isFirst = true;
	String newScheduleMonth, newScheduleDay;
	String nowItem;
	String oneDay;

	// 添加gridview
	private void addGridView(View view) {

		gridView = (GridView) view.findViewById(R.id.gridview);
		gridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.performClick();
				// 将GridView中的触摸事件回传给gestureDetector
				return CalendarFragment.this.gestureDetector
						.onTouchEvent(event);
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			// gridView中的每一个item的点击事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				
				orderTime=null;

				if (nth_HallsId != null && result == null) {
					// 喜事堂显示当前，无法点击
					return;
				}
				try {
					dd = new D();
					aa = new ArrayList<D>();
					isBook = true;
					CalendarAdapter.currentFlag = position;
					calV.notifyDataSetChanged();

					int startPosition = calV.getStartPositon();
					int endPosition = calV.getEndPosition();
					if (startPosition <= position + 7
							&& position <= endPosition - 7) {
						String scheduleDay = calV.getDateByClickItem(position)
								.split("\\.")[0]; // 这一天的阳历
						String scheduleMonth = calV.getShowMonth();
						String scheduleYear = calV.getShowYear();
						Integer yearNum = Integer.parseInt(scheduleYear);
						Integer monthNum = Integer.parseInt(scheduleMonth);
						Integer dayNum = Integer.parseInt(scheduleDay);
						if (scheduleMonth.length() == 1) {
							nowItem = scheduleYear + "0" + scheduleMonth;
						} else {
							nowItem = scheduleYear + scheduleMonth;
						}
						if (scheduleDay.length() == 1) {
							nowItem = nowItem + "0" + scheduleDay;
						} else {
							nowItem = nowItem + scheduleDay;
						}
						// 20160718
						System.out.println("nowItem=" + nowItem);
						oneDay = nowItem;
						Long msecNow = new SimpleDateFormat("yyyyMMdd").parse(
								nowItem).getTime();

						// ============================================普通日历开始================================================================//
						if (nth_HallsId == null && result == null) {
							Date todayaa = new Date();
							SimpleDateFormat sfaaaa = new SimpleDateFormat(
									"yyyyMMdd");
							if (sfaaaa.parse(nowItem).getTime() < todayaa
									.getTime()+6*oneDayMesc) {
								Toast.makeText(getActivity(),
										"请选择一周之后的日期再进行操作~", Toast.LENGTH_SHORT)
										.show();
								return;
							} else {
								onDateSet(null, yearNum, monthNum - 1, dayNum);
								cancelBtn.setText("选定");
							}
							// CalendarFragment.this.dismiss();
						}
						// ============================================普通日历结束================================================================//


						// ============================================我的喜事堂日历开始================================================================//
						else if (result != null && nth_HallsId == null) {

							Date today = new Date();
							SimpleDateFormat sfaa = new SimpleDateFormat(
									"yyyyMMdd");
							if (sfaa.parse(nowItem).getTime() < today.getTime()) {
								Toast.makeText(getActivity(),
										"请选择今日之后的日期再进行操作~", Toast.LENGTH_SHORT)
										.show();
								oneDay = null;
								return;
							}

							for (int i = 0; i < bookDayList.size(); i++) {
								if (bookDayList.get(i).getMsecStart() <= msecNow
										&& bookDayList.get(i).getMsecEnd() >= msecNow) {
									Toast.makeText(getActivity(),
											"该日期已被预订，请重新选择", Toast.LENGTH_SHORT)
											.show();
									oneDay = null;
									startItem = null;
									endItem = null;
									isBook = false;
									break;
								}
							}
							if (isBook) {
								if (TextUtils.isEmpty(endItem)
										&& (TextUtils.isEmpty(startItem) || startItem
												.equals(nowItem))) {
									startItem = nowItem;
									System.out.println(nowItem);
									isBook = false;

								} else if (!TextUtils.isEmpty(startItem)
										&& !TextUtils.isEmpty(endItem)
										&& startItem.equals(nowItem)) {
									aa.clear();
									cancelBtn.setText("关闭");
									startItem = null;
									endItem = null;
									calV = new CalendarAdapter(
											CalendarFragment.this.getActivity(),
											getResources(), currentDate,
											jumpMonth, jumpYear, year_c,
											month_c, day_c, bookDayList, aa,orderTime,type,true);
									gridView.setAdapter(calV);
									return;
								} else if (!TextUtils.isEmpty(startItem)) {
									if (new SimpleDateFormat("yyyyMMdd").parse(
											startItem).getTime() > msecNow) {
										endItem = startItem;
										startItem = nowItem;
									} else {
										endItem = nowItem;
									}
									isBook = true;
									for (int i = 0; i < bookDayList.size(); i++) {
										if (new SimpleDateFormat("yyyyMMdd")
												.parse(startItem).getTime() <= bookDayList
												.get(i).getMsecStart()
												&& new SimpleDateFormat(
														"yyyyMMdd").parse(
														endItem).getTime() >= bookDayList
														.get(i).getMsecStart()) {
											Toast.makeText(getActivity(),
													"您选的日期含有已被预订的时间，请重新选择",
													Toast.LENGTH_SHORT).show();
											oneDay = null;
											startItem = null;
											endItem = null;
											isBook = false;
											isFirst = true;
											break;
										}

									}
									calV = new CalendarAdapter(
											CalendarFragment.this.getActivity(),
											getResources(), currentDate,
											jumpMonth, jumpYear, year_c,
											month_c, day_c, bookDayList, aa,orderTime,type,true);
									gridView.setAdapter(calV);
									System.out.println(startItem + "    "
											+ endItem);

									if (isBook && !TextUtils.isEmpty(startItem)
											&& !TextUtils.isEmpty(endItem)) {
										aa.clear();
										System.out.println(startItem
												.subSequence(0, 4).toString());
										System.out.println(startItem
												.subSequence(4, 6).toString());
										System.out.println(startItem
												.subSequence(6, 8).toString());

										System.out.println(endItem.subSequence(
												0, 4).toString());
										System.out.println(endItem.subSequence(
												4, 6).toString());
										System.out.println(endItem.subSequence(
												6, 8).toString());

										if (startItem.subSequence(4, 5)
												.toString().equals("0")) {
											dd.setQiYue(startItem.subSequence(
													5, 6).toString());
										} else {
											dd.setQiYue(startItem.subSequence(
													4, 6).toString());
										}
										if (startItem.subSequence(6, 7)
												.toString().equals("0")) {
											dd.setQiRi(startItem.subSequence(7,
													8).toString());
										} else {
											dd.setQiRi(startItem.subSequence(6,
													8).toString());
										}

										if (endItem.subSequence(4, 5)
												.toString().equals("0")) {
											dd.setJieYue(endItem.subSequence(5,
													6).toString());
										} else {
											dd.setJieYue(endItem.subSequence(4,
													6).toString());
										}
										if (endItem.subSequence(6, 7)
												.toString().equals("0")) {
											dd.setJieRi(endItem.subSequence(7,
													8).toString());
										} else {
											dd.setJieRi(endItem.subSequence(6,
													8).toString());
										}

										dd.setQiNian(startItem
												.subSequence(0, 4).toString());
										dd.setJieNian(endItem.subSequence(0, 4)
												.toString());

										aa.add(dd);

										calV = new CalendarAdapter(
												CalendarFragment.this
														.getActivity(),
												getResources(), currentDate,
												jumpMonth, jumpYear, year_c,
												month_c, day_c, bookDayList, aa,orderTime,type,true);
										gridView.setAdapter(calV);

									}

								}

							}
							cancelBtn.setText("选定");
						}
						// ============================================我的喜事堂日历结束================================================================//

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		// 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return super.onSingleTapUp(e);
		}

		// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
		}

		// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN,
		// 多个ACTION_MOVE触发
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		// 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN,
		// 多个ACTION_MOVE, 1个ACTION_UP触发
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > 120) {
				// 向左滑动
				nextMonth.performClick();
				return true;
			} else if (e1.getX() - e2.getX() < -120) {
				// 向右滑动
				preMonth.performClick();
				return true;
			}
			return false;
		}

		// 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
		// 注意和onDown()的区别，强调的是没有松开或者拖动的状态
		@Override
		public void onShowPress(MotionEvent e) {
			super.onShowPress(e);
		}

		// 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		// 双击的第二下Touch down时触发
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return super.onDoubleTap(e);
		}

		// 双击的第二下Touch down和up都会触发，可用e.getAction()区分
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return super.onDoubleTapEvent(e);
		}

		// 点击一下稍微慢点的(不滑动)Touch Up:
		// onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return super.onSingleTapConfirmed(e);
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {

	}
	
}
