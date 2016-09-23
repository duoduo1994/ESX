package com.example.administrator.activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.ToDoItem;
import com.example.administrator.entity.NTH_Halls;
import com.example.administrator.fragment.CalendarFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LoadingDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;


public class WeddinghallActivity extends BaseActivity {
    private ToDoItem toDoItema;
    @ViewInject(R.id.dateBt)
    private Button dateBtn; // 事件日期和时间
    private SimpleDateFormat sfDate;
    @ViewInject(R.id.btn_back)
    private Button iv;
    @ViewInject(R.id.surrounding_Age)
    private Button btn_Map;
    private String selectHallId = "";
    private NTH_Halls nth_halls;
    @ViewInject(R.id.xishitang)
    private LinearLayout ll;
    @ViewInject(R.id.netName)
    private TextView tv1;
    @ViewInject(R.id.address)
    private TextView tv2;
    @ViewInject(R.id.trafficInfo)
    private TextView tv3;
    @ViewInject(R.id.trafficInfoss)
    private TextView tv4;
    @ViewInject(R.id.xishitangimage)
    private ImageView im;
    @ViewInject(R.id.querenxst)
    private Button queren;
    private LoadingDialog dialog = null;
    @ViewInject(R.id.tv_title)
    private TextView tv;
    private String dist, tow, vill;

    private static final String TAG = "WeddinghallActivity";

    // 区
    private String[] mDistrictDatas;// mDistrictDatas
    // 镇
    private String[] mTownDatas;// mTownsDatas
    // 村
    private String[] mVilleageDatas;// mVilleageDatas
    private List<NTH_Halls> list = new ArrayList<NTH_Halls>();
    // 列表选择的区镇村
    private String selectedDistrict = "";// selectedDistrict
    private String selectedTown = "";// selectedTown
    private String selectedVilleage = "";// selectedVilleage
    @ViewInject(R.id.spin_district)
    private Spinner mDistrictSpinner;// mDistrictSpinner
    @ViewInject(R.id.spin_town)
    private Spinner mTownSpinner;// mTownsSpinner
    @ViewInject(R.id.spin_villeage)
    private Spinner mVilleageSpinner;// mVilleageSpinner

    private ArrayAdapter<String> mDistrictAdapter;// mDistrictAdapter
    private ArrayAdapter<String> mTownAdapter;// mTownsAdapter
    private ArrayAdapter<String> mVilleageAdapter;// mVilleageAdapter

    // 存储区对应的所有镇
    private Map<String, String[]> mTownDataMap = new HashMap<String, String[]>();// mTownsDataMap
    // 存储镇对应的所有村
    private Map<String, String[]> mVilleageDataMap = new HashMap<String, String[]>();// mVilleageDataMap
    // 存储村的详情
    private Map<String, NTH_Halls> mVilleageInfoDataMap = new HashMap<String, NTH_Halls>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int setContentView() {
        return R.layout.wedding_hall;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);
        tv.setText("喜事堂");
        mCache = ACache.get(this);
        Load.getLoad(this);
        if (TextUtils.isEmpty(mCache.getAsString("xiShiTang"))) {
            Log.i(TAG, "mCache.getAsString IS NULL");
            initHalls();
        } else {
            Log.i(TAG, "mCache.getAsString");
            BeginJsonCitisData(mCache.getAsString("xiShiTang"));
        }
        if (getIntent().getIntExtra("yuding", 0) == 1) {
            queren.setVisibility(View.VISIBLE);
        }
        queren.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent data = new Intent();
                data.putExtra("resultString", tow + selectedVilleage);
                data.putExtra("selectHallId", selectHallId);

                setResult(123, data);
                WeddinghallActivity.this.finish();
            }
        });

        iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                System.out.println(getIntent().getIntExtra("yuding", 0)
                        + "@EDR");
                switch (getIntent().getIntExtra("yuding", 0)) {

                    case 1:
                        Intent data = new Intent();
                        data.putExtra("resultString", tow + selectedVilleage);
                        data.putExtra("selectHallId", selectHallId);
                        setResult(123, data);
                        finish();
                        break;

                    default:
                        // 结束当前界面
                        if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
                            HomeActivity.dianJiShiJian = System.currentTimeMillis();
                            WeddinghallActivity.this.finish();
                            ActivityCollector.finishAll();
                        }
                        break;
                }

            }
        });

        // 区选择
        mDistrictSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        selectedDistrict = "";
                        selectedDistrict = (String) parent.getSelectedItem();
                        // 根据省份更新城市区域信息
                        updateDistrict(selectedDistrict);
                        // Toast.makeText(getApplicationContext(), dist,
                        // Toast.LENGTH_LONG).show();
                        Log.d(TAG, "mCitySpinnere has excuted !"
                                + "selectedPro is " + selectedDistrict);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        // 镇选择
        mTownSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedTown = "";
                selectedTown = (String) parent.getSelectedItem();
                updateTown(selectedTown);
                // Toast.makeText(getApplicationContext(), dist + tow,
                // Toast.LENGTH_LONG).show();
                Log.d(TAG, "mCitySpinner has excuted !" + "selectedCity is "
                        + selectedTown);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 村选择
        mVilleageSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        selectedVilleage = "";
                        selectedVilleage = (String) parent.getSelectedItem();
                        Log.d(TAG, "mTownsSpinner has excuted !"
                                + "selectedArea is " + selectedVilleage);
                        vill = dist + tow + selectedVilleage;
                        NTH_Halls nth_Halls = mVilleageInfoDataMap.get(vill);
                        selectHallId = nth_Halls.getPkHallID();
                        showXishitang(selectHallId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        setMoreListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more,
                WeddinghallActivity.this, 0));
    }

    // ===================================================================================================================================//


    /**
     * 根据镇选择村
     *
     * @param town
     */
    private void updateTown(String town) {
        // Toast.makeText(getApplicationContext(), town, Toast.LENGTH_SHORT)
        // .show();
        tow = town;
        String[] villeage = mVilleageDataMap.get(dist + town);
        // 存在村
        mVilleageSpinner.setVisibility(View.VISIBLE);
        mVilleageAdapter = new ArrayAdapter<String>(this, R.layout.myspinner,
                villeage);
        mVilleageAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mVilleageSpinner.setAdapter(mVilleageAdapter);
        mVilleageAdapter.notifyDataSetChanged();
        mVilleageSpinner.setSelection(0);

    }

    /**
     * 根据区更新镇数据
     *
     * @param district
     */
    private void updateDistrict(String district) {
        // Toast.makeText(getApplicationContext(), district, Toast.LENGTH_SHORT)
        // .show();
        dist = district;
        String[] town = mTownDataMap.get(district);
        // for (int i = 0; i < town.length; i++) {
        // 存在镇
        mTownAdapter = new ArrayAdapter<String>(this, R.layout.myspinner, town);
        mTownAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTownSpinner.setAdapter(mTownAdapter);
        mTownAdapter.notifyDataSetChanged();
        mTownSpinner.setSelection(0);
        // }

    }

    /**
     * 开始解析数据
     */
    private void BeginJsonCitisData(String cityJson) {

        if (!TextUtils.isEmpty(cityJson)) {
            try {
                JSONObject object = new JSONObject(cityJson);
                JSONArray array = object.getJSONArray("cities");
                // 获取区的数量
                mDistrictDatas = new String[array.length()];
                String mDistrictStr = null;
                // 循环遍历
                for (int i = 0; i < array.length(); i++) {

                    // 循环遍历区，并将省保存在mDistrictDatas[]中
                    JSONObject mDistrictObject = array.getJSONObject(i);
                    if (mDistrictObject.has("CountyName")) {
                        mDistrictStr = mDistrictObject.getString("CountyName");
                        mDistrictDatas[i] = mDistrictStr;
                        Log.i(TAG, mDistrictDatas[i]);
                    } else {
                        mDistrictDatas[i] = "unknown District";
                    }

                    JSONArray townArray;
                    String mTownStr = null;
                    try {
                        // 循环遍历省对应下的镇
                        townArray = mDistrictObject.getJSONArray("Towns");
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    mTownDatas = new String[townArray.length()];
                    for (int j = 0; j < townArray.length(); j++) {
                        // 循环遍历镇，并将城市保存在mTownDatas[]中
                        JSONObject mTownObject = townArray.getJSONObject(j);
                        if (mTownObject.has("TownName")) {
                            mTownStr = mTownObject.getString("TownName");
                            mTownDatas[j] = mTownStr;
                        } else {
                            mTownDatas[j] = "unknown Town";
                        }

                        // 循环遍历村
                        JSONArray villeageArray;
                        String mVilleageStr = null;
                        try {
                            // 判断是否含有第三级区域划分，若没有，则跳出本次循环，重新执行循环遍历城市
                            villeageArray = mTownObject.getJSONArray("Names");
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }

                        // 执行下面过程则说明存在村
                        mVilleageDatas = new String[villeageArray.length()];
                        list.clear();
                        for (int m = 0; m < villeageArray.length(); m++) {

                            // 循环遍历村，并将镇保存在mCountyDatas[]中
                            JSONObject mVilleageObject = villeageArray
                                    .getJSONObject(m);
                            if (mVilleageObject.has("Name")) {
                                mVilleageStr = mVilleageObject
                                        .getString("Name");
                                mVilleageDatas[m] = mVilleageStr;
                                nth_halls = new NTH_Halls();
                                String name = mVilleageObject.getString("Name");
                                nth_halls.setName(name);

                                String pkHallID = mVilleageObject
                                        .getString("HallID");
                                nth_halls.setPkHallID(pkHallID);

                                list.add(nth_halls);

                            }
                            // 存储村的信息
                            mVilleageInfoDataMap.put(mDistrictStr + mTownStr
                                    + mVilleageStr, nth_halls);
                        }
                        // 存储镇对应的所有村
                        mVilleageDataMap.put(mDistrictStr + mTownStr,
                                mVilleageDatas);
                    }

                    // 存储区对应的所有镇
                    mTownDataMap.put(mDistrictStr, mTownDatas);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mDistrictAdapter = new ArrayAdapter<String>(this,
                    R.layout.myspinner, mDistrictDatas);
            mDistrictAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mDistrictSpinner.setAdapter(mDistrictAdapter);
        }

    }

    private ACache mCache;
    private int ciShu = 0;
    private boolean isFalse = true;

    private void initHalls() {
        dialog = new LoadingDialog(WeddinghallActivity.this, "正在加载中...");
        dialog.showDialog();

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "HallsHandler.ashx?Action=getVillageAllInfoByCityID");
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("CityID", "1");
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xUtilsHelper1.sendPost(requestParams, subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dialog.closeDialog();
                if (ciShu >= 3) {
                    isFalse = false;
                    ciShu = 0;
                    Toast.makeText(WeddinghallActivity.this,
                            "数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
                }

                if (isFalse) {
                    initHalls();
                    ciShu++;
                }
            }

            @Override
            public void onNext(String allData) {

                ciShu = 0;
                isFalse = true;

                System.out.println("***************" + allData);
                if (!TextUtils.isEmpty(allData)) {
                    mCache.put("xiShiTang", allData, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
                    String aCacheAllData = mCache
                            .getAsString("xiShiTang");
                    BeginJsonCitisData(aCacheAllData);
                }
                dialog.closeDialog();
            }
        });


//		RequestParams r = new RequestParams();
//		r.add("CityID", "1");
//		SmartFruitsRestClient.post(
//				"HallsHandler.ashx?Action=getVillageAllInfoByCityID", r,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						ciShu = 0;
//						isFalse = true;
//						String allData = new String(arg2);
//						System.out.println("***************" + allData);
//						if (!TextUtils.isEmpty(allData)) {
//							mCache.put("xiShiTang", allData, 60 * 60 * 24);// 60*60*24以秒为单位，每天删除
//							String aCacheAllData = mCache
//									.getAsString("xiShiTang");
//							BeginJsonCitisData(aCacheAllData);
//						}
//						dialog.closeDialog();
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						dialog.closeDialog();
//						if (ciShu >= 3) {
//							isFalse = false;
//							ciShu = 0;
//							Toast.makeText(WeddinghallActivity.this,
//									"数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
//						}
//
//						if (isFalse) {
//							initHalls();
//							ciShu++;
//						}
//					}
//				});
    }

    private TextView juTiXinXi, tiShi;
    private Dialog d;
    private Button p1;
    private Button n;
    private View v;

    public void lngIsNull(final NTH_Halls nth_Halls) {
        if (WeddinghallActivity.this.isFinishing()) {
        } else {
            d = new Dialog(WeddinghallActivity.this, R.style.loading_dialog);
            v = LayoutInflater.from(WeddinghallActivity.this).inflate(
                    R.layout.dialog, null);// 窗口布局
            d.setContentView(v);// 把设定好的窗口布局放到dialog中
            d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
            p1 = (Button) v.findViewById(R.id.p);
            n = (Button) v.findViewById(R.id.n);
            juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
            tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
//			fuZhi = (TextView) v.findViewById(R.id.fuzhi_jiantieban);
            juTiXinXi.setText("暂未收录此坐标，是否跳转至该镇下的其它喜事堂");
            tiShi.setText("提示");
            p1.setText("是");
            n.setText("否");
            p1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    GaodeMapActivity.actionStart(WeddinghallActivity.this,
                            nth_Halls.getDistrictID());
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

    }

    /**
     *
     *
     */
    public void showXishitang(final String hallId) {
        dialog = new LoadingDialog(WeddinghallActivity.this, "数据正在加载中...");
        dialog.showDialog();

        ll.setVisibility(View.VISIBLE);

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "HallsHandler.ashx?Action=getdetail");
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("ID", hallId);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                xUtilsHelper1.sendPost(requestParams, subscriber);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dialog.closeDialog();
                if (ciShu >= 3) {
                    isFalse = false;
                    ciShu = 0;
                    Toast.makeText(WeddinghallActivity.this,
                            "数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
                }

                if (isFalse) {
                    showXishitang(hallId);
                    ciShu++;
                }

            }

            @Override
            public void onNext(String result) {
                ciShu = 0;
                isFalse = true;
                dialog.closeDialog();

                System.out.println("喜事堂详情：" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result
                            .trim());
                    JSONArray jsonArray = jsonObject.getJSONArray("详情");
                    if (jsonArray != null) {
                        JSONObject object = jsonArray.getJSONObject(0);
                        NTH_Halls nth_Halls = new NTH_Halls();
                        nth_Halls.setPkHallID(object
                                .getString("HallID"));
                        nth_Halls.setContact(object
                                .getString("Principal"));
                        nth_Halls.setContactTel(object
                                .getString("PrincipalTel"));
                        nth_Halls.setName(object.getString("Name"));
                        nth_Halls.setLatitude(object
                                .getString("Latitude"));
                        nth_Halls.setLongitude(object
                                .getString("Longitude"));
                        nth_Halls.setWgsLatitude(object
                                .getString("WgsLatitude"));
                        nth_Halls.setWgsLongitude(object
                                .getString("WgsLongitude"));
                        nth_Halls.setImageUrl(object
                                .getString("ImageUrl"));
                        nth_Halls.setDetailAdr(object
                                .getString("DetailAddr"));
                        nth_Halls.setDistrictID(object
                                .getString("DistrictID"));
                        tv1.setText(object.getString("Name"));
                        tv2.setText(object.getString("DetailAddr"));
                        tv3.setText(object.getString("Principal"));
                        tv4.setText(object.getString("PrincipalTel"));

                        Load.imageLoader.displayImage(
                                nth_Halls.getImageUrl(), im,
                                Load.options);
                        setNth(nth_Halls);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂未收录此喜事堂详细信息！", Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });


//		RequestParams params = new RequestParams();
//		params.add("ID", hallId);
//		SmartFruitsRestClient.post("HallsHandler.ashx?Action=getdetail",
//				params, new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						// TODO Auto-generated method stub
//						ciShu = 0;
//						isFalse = true;
//						dialog.closeDialog();
//						String result = new String(arg2);
//						System.out.println("喜事堂详情：" + result);
//						try {
//							JSONObject jsonObject = new JSONObject(result
//									.trim());
//							JSONArray jsonArray = jsonObject.getJSONArray("详情");
//							if (jsonArray != null) {
//								JSONObject object = jsonArray.getJSONObject(0);
//								NTH_Halls nth_Halls = new NTH_Halls();
//								nth_Halls.setPkHallID(object
//										.getString("HallID"));
//								nth_Halls.setContact(object
//										.getString("Principal"));
//								nth_Halls.setContactTel(object
//										.getString("PrincipalTel"));
//								nth_Halls.setName(object.getString("Name"));
//								nth_Halls.setLatitude(object
//										.getString("Latitude"));
//								nth_Halls.setLongitude(object
//										.getString("Longitude"));
//								nth_Halls.setWgsLatitude(object
//										.getString("WgsLatitude"));
//								nth_Halls.setWgsLongitude(object
//										.getString("WgsLongitude"));
//								nth_Halls.setImageUrl(object
//										.getString("ImageUrl"));
//								nth_Halls.setDetailAdr(object
//										.getString("DetailAddr"));
//								nth_Halls.setDistrictID(object
//										.getString("DistrictID"));
//								tv1.setText(object.getString("Name"));
//								tv2.setText(object.getString("DetailAddr"));
//								tv3.setText(object.getString("Principal"));
//								tv4.setText(object.getString("PrincipalTel"));
//								System.out.println("ImageUrl="
//										+ SmartConfig.FILE_UPLOAD_URL
//										+ nth_Halls.getImageUrl());
//								Load.imageLoader.displayImage(
//										nth_Halls.getImageUrl(), im,
//										Load.options);
//								setNth(nth_Halls);
//							} else {
//								Toast.makeText(getApplicationContext(),
//										"暂未收录此喜事堂详细信息！", Toast.LENGTH_SHORT)
//										.show();
//							}
//
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						// TODO Auto-generated method stub
//						dialog.closeDialog();
//						if (ciShu >= 3) {
//							isFalse = false;
//							ciShu = 0;
//							Toast.makeText(WeddinghallActivity.this,
//									"数据加载失败，请重新加载~", Toast.LENGTH_SHORT).show();
//						}
//
//						if (isFalse) {
//							showXishitang(hallId);
//							ciShu++;
//						}
//					}
//				});

    }

    private void setNth(final NTH_Halls nth_Halls) {

        btn_Map.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (TextUtils.isEmpty(nth_Halls.getLatitude())
                        || TextUtils.isEmpty(nth_Halls.getLongitude())
                        || TextUtils.isEmpty(nth_Halls.getWgsLatitude())
                        || TextUtils.isEmpty(nth_Halls.getWgsLongitude())) {
                    lngIsNull(nth_Halls);
                    return;
                }
//				GaodeMapActivity.actionStart(WeddinghallActivity.this,
//						nth_Halls);
            }
        });

        final Date now = new Date();
        sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Bundle extras = getIntent().getExtras();
        if (extras == null || extras.get("") == null) {
            // 创建task为空的ToDoItem对象
            toDoItema = new ToDoItem("");
            // 新建待办事项，将界面中事件时间设置为当前时间
            dateBtn.setText(sfDate.format(now));
            toDoItema.setTime(now); // 设置ToDoItem事件时间
        }
        // 处理事件日期点击事件
        dateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment calendarFragment = new CalendarFragment(
                        toDoItema.getTime(), nth_Halls.getPkHallID(), null,
                        0, null) {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        super.onDateSet(view, year, monthOfYear, dayOfMonth);
                        toDoItema.setTime(year, monthOfYear, dayOfMonth);
//						Date date = toDoItema.getTime();
                    }

                };
                calendarFragment.show(getFragmentManager(), "calendarPcker");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println(getIntent().getIntExtra("yuding", 0));
        if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
            HomeActivity.dianJiShiJian = System.currentTimeMillis();
            if (getIntent().getIntExtra("yuding", 0) == 1) {
                System.out.println(123);
                Intent data = new Intent();
                data.putExtra("resultString", tow + selectedVilleage);
                data.putExtra("selectHallId", selectHallId);
                setResult(123, data);
                finish();
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                WeddinghallActivity.this.finish();
                ActivityCollector.finishAll();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Weddinghall Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.activity/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Weddinghall Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.administrator.activity/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
