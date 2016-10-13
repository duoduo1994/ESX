package com.eyoucab.list;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.esycab.MyCookSchList;
import com.example.esycab.MyCooksActivity;
import com.example.esycab.MyHallSchList;
import com.example.esycab.MyHallsActivity;
import com.example.esycab.MyInfomationActivity;
import com.example.esycab.R;
import com.example.esycab.StartActivity;
import com.example.esycab.network.SmartFruitsRestClient;
import com.example.esycab.utils.Diolg;
import com.example.esycab.utils.LocalStorage;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<Map<String, Object>> listItems; // 商品信息集合
	private Integer type;//1 hall  2cook
	private LayoutInflater listContainer; // 视图容器
	ListViewAdapter listViewAdapter;

	public final class ListItemView { // 自定义控件集合
		public TextView orderName;
		public TextView orderTel;
		public TextView orderAddress;
		public TextView orderTime;
		public Button delete;
		public Button update;
		public LinearLayout updatesch;
		public LinearLayout myhall_item;
	}

	public ListViewAdapter(Context context, List<Map<String, Object>> listItems,Integer type) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.type=type;
		this.listItems = listItems;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.e("method", "getView");
		final int selectID = position;
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.myhallschitem, null);
			// 获取控件对象
			listItemView.orderName = (TextView) convertView
					.findViewById(R.id.myhall_bookname);
			listItemView.orderTel = (TextView) convertView
					.findViewById(R.id.myhall_booktel);
			listItemView.orderAddress=(TextView) convertView
					.findViewById(R.id.myhall_bookaddress);
			listItemView.orderTime = (TextView) convertView
					.findViewById(R.id.myhall_booktime);
			listItemView.delete = (Button) convertView
					.findViewById(R.id.myhall_delete);
			listItemView.update = (Button) convertView
					.findViewById(R.id.myhall_update);
			listItemView.updatesch = (LinearLayout) convertView
					.findViewById(R.id.updatesch);
			listItemView.myhall_item = (LinearLayout) convertView
					.findViewById(R.id.myhall_item);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		if(type==2){
			if(!TextUtils.isEmpty((String) listItems.get(position).get("orderID"))){
				listItemView.updatesch.setVisibility(View.GONE);
				listItemView.myhall_item.setBackgroundResource(R.drawable.ordersch);
				listItemView.myhall_item.setWeightSum(4f);
			}
			else{
				listItemView.updatesch.setVisibility(View.VISIBLE);
				listItemView.myhall_item.setBackgroundResource(R.drawable.gerensch);
			}
		}		
		
		// 设置文字和图片
		listItemView.orderName.setText((String) listItems.get(position).get(
				"orderName"));
		listItemView.orderTel.setText((String) listItems.get(position).get(
				"orderTel"));
		listItemView.orderAddress.setText((String) listItems.get(position).get(
				"orderAddress"));
		listItemView.orderTime.setText((String) listItems.get(position).get(
				"orderTime"));
		
		listItemView.delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 显示物品详情
				// showDetailInfo(selectID);
				d = new Dialog(context, R.style.loading_dialog);
				v = LayoutInflater.from(context).inflate(
						R.layout.dialog, null);// 窗口布局
				d.setContentView(v);// 把设定好的窗口布局放到dialog中
				d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
				p1 = (Button) v.findViewById(R.id.p);
				n = (Button) v.findViewById(R.id.n);
				juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
				tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
				fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
				juTiXinXi.setText("您确定要删除此预定信息吗？");
				tiShi.setText("警告");
				p1.setText("删除");
				n.setText("取消");
				p1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(type==1){
							deleteHallSch(selectID);
						}
						if(type==2){
							deleteCookSch(selectID);
						}
						
						d.dismiss();
					}
				});
				n.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// System.out.println(a);
						d.dismiss();
					}
				});
				d.show();
			}
		});
		// 注册多选框状态事件处理
		listItemView.update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(type==1){
					MyHallsActivity.actionStart(context, 
							listItems.get(position).get("SchID").toString(),
							listItems.get(position).get("orderName").toString(),
							listItems.get(position).get("orderTel").toString(),
							listItems.get(position).get("orderAddress").toString(),
							listItems.get(position).get("dinner").toString(),
							listItems.get(position).get("vice").toString(),
							listItems.get(position).get("orderTime").toString());
					MyHallSchList.myHallSchList.finish();
				}
				if(type==2){
					MyCooksActivity.actionStart(context, 
							listItems.get(position).get("SchID").toString(),
							listItems.get(position).get("orderName").toString(),
							listItems.get(position).get("orderTel").toString(),
							listItems.get(position).get("orderAddress").toString(),
							listItems.get(position).get("orderTime").toString());
				MyCookSchList.myCookSchList.finish();}
				
			}
		});

		return convertView;
	}
	
	TextView juTiXinXi, tiShi, fuZhi;
	Dialog d;
	Button p1;
	Button n;
	View v;
	
	

	int ciShu = 0;
	boolean isFalse = true;
	
	public void deleteCookSch(final int position) {
		RequestParams params = new RequestParams();
		params.add("ID", listItems.get(position).get("SchID").toString());
		SmartFruitsRestClient.post("CooksHandler.ashx?Action=DeleteDetailID",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						ciShu = 0;
						isFalse = true;
						String result = new String(arg2);
						try {
							JSONObject jsonObject = new JSONObject(result
									.trim());
							if (jsonObject.getString("提示").equals("删除失败！")) {
								Toast.makeText(context, "删除失败，请重试！",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "删除成功！",
										Toast.LENGTH_SHORT).show();
								listItems.remove(position);
								notifyDataSetChanged();
								if(listItems.size()==0){
									if(type==1){
										MyHallSchList.myHallSchList.findViewById(R.id.list_sch).setVisibility(View.GONE);
										MyHallSchList.myHallSchList.findViewById(R.id.myhalllist_back).setVisibility(View.VISIBLE);
									}
									if(type==2){
										MyCookSchList.myCookSchList.findViewById(R.id.list_sch).setVisibility(View.GONE);
										MyCookSchList.myCookSchList.findViewById(R.id.myhalllist_back).setVisibility(View.VISIBLE);
									}
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(context, "您的网络不太好哦！",
									Toast.LENGTH_SHORT).show();
						}

						if (isFalse) {
							deleteCookSch(position);
							ciShu++;
						}
					}
				});
	}

	public void deleteHallSch(final int position) {
		RequestParams params = new RequestParams();
		params.add("id", listItems.get(position).get("SchID").toString());
		SmartFruitsRestClient.post("HallsHandler.ashx?Action=DeleteDetailID",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						// TODO Auto-generated method stub
						ciShu = 0;
						isFalse = true;
						String result = new String(arg2);
						try {
							JSONObject jsonObject = new JSONObject(result
									.trim());
							if (jsonObject.getString("提示").equals("删除失败！")) {
								Toast.makeText(context, "删除失败，请重试！",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "删除成功！",
										Toast.LENGTH_SHORT).show();
								listItems.remove(position);
								notifyDataSetChanged();
								if(listItems.size()==0){
									if(type==1){
										MyHallSchList.myHallSchList.findViewById(R.id.list_sch).setVisibility(View.GONE);
										MyHallSchList.myHallSchList.findViewById(R.id.myhalllist_back).setVisibility(View.VISIBLE);
									}
									if(type==2){
										MyCookSchList.myCookSchList.findViewById(R.id.list_sch).setVisibility(View.GONE);
										MyCookSchList.myCookSchList.findViewById(R.id.myhalllist_back).setVisibility(View.VISIBLE);
									}
								}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						// TODO Auto-generated method stub
						if (ciShu >= 3) {
							isFalse = false;
							Toast.makeText(context, "您的网络不太好哦！",
									Toast.LENGTH_SHORT).show();
						}

						if (isFalse) {
							deleteHallSch(position);
							ciShu++;
						}
					}
				});
	}
}
