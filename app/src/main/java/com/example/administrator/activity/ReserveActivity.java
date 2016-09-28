package com.example.administrator.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.ToDoItem;
import com.example.administrator.entity.AnLi;
import com.example.administrator.fragment.CalendarFragment;
import com.example.administrator.list.Utils;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.XUtilsHelper;
import com.example.administrator.utils.ACache;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.Diolg;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.LoginCheckAlertDialogUtils;
import com.example.administrator.utils.ProConst;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;


/**
 * 预定页
 *
 * @author Administrator
 */
public class ReserveActivity extends BaseActivity implements ProConst, TextWatcher {
    String selectHallId;


    @ViewInject(R.id.et_UserInfo_Name)
    private EditText edittext_reservation_name;// 预定姓名 喜事堂名称
    @ViewInject(R.id.rb_Sex_Male)
    private RadioButton rb_Sex_Male;// 男
    @ViewInject(R.id.rb_Sex_Woman)
    private RadioButton rb_Sex_Woman;// 女
    @ViewInject(R.id.et_UserInfo_Age)
    private TextView edittext_reservation_phone;// 预定电话
    @ViewInject(R.id.et_UserInfo_Site)
    private TextView edittext_reservation_site;// 预定地址
    @ViewInject(R.id.et_UserInfo_Table)
    private EditText edittext_reservation_dinner;// 预定主餐
    @ViewInject(R.id.et_UserInfo_FTable)
    private EditText edittext_reservation_meal;// 预定副餐
    @ViewInject(R.id.ets_UserInfo_Sites)
    private TextView ets_UserInfo_Sites;
    private ToDoItem toDoItem;
    @ViewInject(R.id.dateBtn)
    private Button dateBtn; // 事件日期和时间
    private SimpleDateFormat sfDate;
    @ViewInject(R.id.reserva_tion)
    private Button reserva;
    @ViewInject(R.id.zhucanshijian_xuanze)
    private Spinner zhucanshijian_xuanze;

    @ViewInject(R.id.xishitang_xuanze)
    private Spinner xishitang_xuanze;

    int sex = 1;// 默认选择三星级
    @ViewInject(R.id.btn_back)
    private Button iv;
    private ACache mCache;
    private String UserTel = "", nickName;
    private String UserPass = "";
    @ViewInject(R.id.caixi_xuanze)
    private Spinner caixixuanze;
    private List<String> ls, ll, ld;
    private AnLi anli;
    private List<AnLi> lal;
    private String series, feastTypy, date;
    private JSONObject tJson = null;
    private int caiID, yanhuiID, dateID;

    private void tryc(String result) {
        try {
            JSONObject json = new JSONObject(result.trim());
            JSONArray configlist = json.getJSONArray("Type");
            JSONArray configlists = json.getJSONArray("series");
            System.out.println(result);
            if (configlist.length() >= 1) {
                mCache.put("预定", result, 60 * 60 * 24);
            }
            if (configlist.length() <= configlists.length()) {

                configlist = json.getJSONArray("series");
                configlists = json.getJSONArray("Type");

            }
            System.out.println(configlist.length() + "RTYUJHG"
                    + configlists.length());

            for (int i = 0; i < configlist.length(); i++) {
                tJson = configlist.getJSONObject(i);
                anli = new AnLi();
                anli.setHallsName(tJson.getString("Name"));// 菜系ming
                System.out.println(i + "DFGH" + tJson.getString("Name"));
                anli.setPkCaseID(tJson.getInt("ID"));// caixiID
                ls.add(tJson.getString("Name"));
                lal.add(anli);
            }
            for (int j = 0; j < configlists.length(); j++) {
                tJson = configlists.getJSONObject(j);
                ll.add(tJson.getString("Name"));
                lal.get(j).setPkTbWareCgyID(tJson.getInt("pkFstCgyID"));// xiyanID
                lal.get(j).setName(tJson.getString("Name"));// xiyanName
            }

            ArrayAdapter<String> mDistrictAdapter = new ArrayAdapter<String>(
                    this, R.layout.myspinner, ls);
            mDistrictAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            caixixuanze.setAdapter(mDistrictAdapter);

            ArrayAdapter<String> mDistrictAdapterss = new ArrayAdapter<String>(
                    this, R.layout.myspinner, ll);
            mDistrictAdapterss
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            xishitang_xuanze.setAdapter(mDistrictAdapterss);

            ArrayAdapter<String> mDistrictAdapters = new ArrayAdapter<String>(
                    this, R.layout.myspinner, ld);
            mDistrictAdapters
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            zhucanshijian_xuanze.setAdapter(mDistrictAdapters);
            caixixuanze.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    series = lal.get(arg2).getHallsName();
                    caiID = arg2;
                    System.out.println(caiID);
                    System.out.println(series);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
            xishitang_xuanze
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub
                            feastTypy = lal.get(arg2).getName();
                            yanhuiID = arg2;
                            System.out.println(feastTypy);
                            System.out.println(yanhuiID);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

            zhucanshijian_xuanze
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub

                            date = ld.get(arg2);
                            dateID = arg2;
                            System.out.println(date);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub

                        }
                    });

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // Toast.makeText(ReserveActivity.this, e.getMessage(),
            // Toast.LENGTH_SHORT).show();
        }
    }

    private void getMsg() {

        XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "BookHandler.ashx?Action=bookPage",1);
        RequestParams requestParams = new RequestParams();

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
                if (ciShu >= 3) {
                    isFalse = false;
                    toast(ReserveActivity.this, getString(R.string.conn_failed));
                }
                if (isFalse) {
                    getMsg();
                    ciShu++;
                }
            }

            @Override
            public void onNext(String s) {

                tryc(s);
            }
        });

    }

    private String zhuangTai = "0";

    private void getDingDanWei() {


        if (LocalStorage.get("UserTel").toString().length() != 11) {

        } else {
            System.out.println(LocalStorage.get("UserTel").toString()
                    + "_________");
            XUtilsHelper xUtilsHelper1 = new XUtilsHelper(this, "BookHandler.ashx?Action=bookAllInfo",1);
            RequestParams requestParams = new RequestParams();

            requestParams.addBodyParameter("fkCusTel", LocalStorage.get("UserTel").toString());

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
                    if (ciShu >= 3) {
                        isFalse = false;
                    }
                    ;
                    if (isFalse) {
                        getDingDanWei();
                        ciShu++;
                    }
                }

                @Override
                public void onNext(String s) {
                    try {
                        JSONObject json = new JSONObject(s.trim());
                        JSONArray j = json.getJSONArray("预定信息列表");
                        JSONObject tj;
                        if (j.length() != 0) {
                            for (int i = 0; i < j.length(); i++) {
                                tj = j.getJSONObject(i);
                                zhuangTai = tj.getString("OrdStatus");
                            }

                        }
                        System.out.println(zhuangTai);
                        if ("1".equals(zhuangTai)
                                || "2".equals(zhuangTai)
                                || "3".equals(zhuangTai)) {
                            new Diolg(ReserveActivity.this, "确定",
                                    "null", "您还有未完成的订单", "提示", 16);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
        }
    }
    private boolean isFalse = true;
    private int ciShu = 0;
    @ViewInject(R.id.xzxst)// 选择喜事堂
    private Button xzxst;
    private String xst;
    private int jiaRu;
    @ViewInject(R.id.tv_title)
    private TextView tv;
    private float dy, uy;
    @ViewInject(R.id.wo_de_yu_ding1)
    private LinearLayout ll1;
    public static Activity ssthis;
    @ViewInject(R.id.awefasdfasdfasdfasdf)
    private ScrollView s;

    @Override
    protected int setContentView() {
        return R.layout.activity_reservations;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);

        jiaRu = getIntent().getIntExtra("jiaRu", 0);
        ssthis = ReserveActivity.this;
        lal = new ArrayList<AnLi>();
        tv.setText("预定信息");
        mCache = ACache.get(this);

        LocalStorage.initContext(this);
        ll1.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dy = arg1.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        uy = arg1.getY();
                        if ((uy - dy) <= 10 || (uy - dy) >= -10) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            return imm.hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(), 0);
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // System.out.println("111111密码：  " + UserPass + "22222手机号  ：" +
        UserTel = LocalStorage.get("UserTel").toString();
        UserPass = LocalStorage.get("UserPass").toString();
        nickName = LocalStorage.get("RealName").toString();
        // UserTel);
        xzxst.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ReserveActivity.this,
                        WeddinghallActivity.class);
                intent.putExtra("yuding", 1);
                startActivityForResult(intent, 123);

            }
        });
        edittext_reservation_name.setText(nickName);
        ls = new ArrayList<String>();
        ll = new ArrayList<String>();
        ld = new ArrayList<String>();
        ld.add("中午");
        ld.add("晚上");
        caixixuanze.setAdapter(new ArrayAdapter<String>(ReserveActivity.this,
                android.R.layout.simple_list_item_2, ls));

        String se = (String) LocalStorage.get("Sex");
        if ("0".equals(se)) {
            rb_Sex_Woman.setChecked(true);
        } else {
            rb_Sex_Male.setChecked(true);
        }


        edittext_reservation_phone.setText(UserTel);
        reserva.setOnClickListener(reservation);
        String reservation_site = (String) LocalStorage.get("HomeAddress");
        edittext_reservation_site.setText(reservation_site);
        edittext_reservation_dinner.addTextChangedListener(this);
        edittext_reservation_meal.addTextChangedListener(this);
        Date now = new Date();
        sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
        Bundle extras = getIntent().getExtras();
        if (extras == null || extras.get("FastDateTime") == null) {
            // 创建task为空的ToDoItem对象
            toDoItem = new ToDoItem("");
            // 新建待办事项，将界面中事件时间设置为当前时间
            dateBtn.setText(sfDate.format(now));
            toDoItem.setTime(now); // 设置ToDoItem事件时间
        }
        // 处理事件日期点击事件
        dateBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment calendarFragment = new CalendarFragment(toDoItem
                        .getTime(), null, null, 0, null) {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        super.onDateSet(view, year, monthOfYear, dayOfMonth);
                        toDoItem.setTime(year, monthOfYear, dayOfMonth);
                        Date date = toDoItem.getTime();
                        dateBtn.setText(sfDate.format(date));

                    }

                };
                calendarFragment.show(getFragmentManager(), "calendarPcker");
            }
        });

        rb_Sex_Male
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // three_star.setChecked(true);
                        if (isChecked) {
                            sex = 1;
                        }
                    }
                });
        rb_Sex_Woman
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            sex = 0;
                        }
                    }
                });
        iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                HomeActivity.dianJiShiJian = System.currentTimeMillis();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ReserveActivity.this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                // 结束当前界面
                ReserveActivity.this.finish();
                ActivityCollector.finishAll();
            }
        });

        edittext_reservation_name.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                String t1 = edittext_reservation_name.getText().toString();
                System.out.println(arg1);
                switch (arg1) {
                    case 62:
                        t1 = t1.trim();
                        edittext_reservation_name.setText(t1);
                        edittext_reservation_name.setSelection(t1.length());
                        Toast.makeText(ReserveActivity.this, "姓名不允许包含空格，请重新输入！",
                                Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }

                return false;
            }

        });
        if (null != mCache.getAsString("预定")) {
            String result = mCache.getAsString("预定");
            getDingDanWei();
            tryc(result);
        } else {
            System.out.println(147852);
            getDingDanWei();
            getMsg();
        }
        setMoreListener();
    }


    @SuppressWarnings("static-access")
    private Boolean checkLogin() {
        return new LoginCheckAlertDialogUtils(ReserveActivity.this)
                .showDialog();// 若登录返回false,反之true
    }

        @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more,
                ReserveActivity.this, 0));
    }

    private String FeastDatetime;
    private Matcher m;
    private boolean flag = false;
    private int zhu;
    private int fuz;
    private OnClickListener reservation = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            if (checkLogin()) {
                System.out.println("没登录");

            } else {
                if (rb_Sex_Male.isChecked()) {
                    sex = 1;
                } else {
                    sex = 0;
                }
                String UserTel = (String) LocalStorage.get("UserTel");
                String RealName = edittext_reservation_name.getText()
                        .toString().trim();
                String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern p = Pattern.compile(regEx);
                m = p.matcher(RealName);
                final String sexa = sex + "";
                String Tel = edittext_reservation_phone.getText().toString();
                String DeliveryAddress = edittext_reservation_site.getText()
                        .toString().trim();
                String MainSetQuantity = edittext_reservation_dinner.getText()
                        .toString();
                if (!TextUtils.isEmpty(MainSetQuantity)) {
                    zhu = Integer.parseInt(MainSetQuantity);
                }

                String SunSetQuantity = edittext_reservation_meal.getText()
                        .toString();
                if (!TextUtils.isEmpty(SunSetQuantity)) {
                    fuz = Integer.parseInt(SunSetQuantity);
                }
                Date today = new Date();
                SimpleDateFormat ss = new SimpleDateFormat("yyyy/MM/dd",
                        Locale.CHINA);
                if (ss.format(today).equals(dateBtn.getText().toString())) {
                    toast(ReserveActivity.this, "请选择一周之后的日期再进行预订！");
                    return;
                } else {
                    FeastDatetime = dateBtn.getText().toString();
                }
                System.out.println(FeastDatetime + "!!!!!!!!!!!!!!#D$#C");

                String xstdz = ets_UserInfo_Sites.getText().toString();

                if (m.find()) {
                    toast(ReserveActivity.this, "姓名不允许输入特殊符号！");
                    return;
                }
                if (RealName == null || "".equals(RealName)) {
                    toast(ReserveActivity.this, "请输入您的姓名");
                    return;
                } else if (!Utils.isMobile(Tel)) {
                    toast(ReserveActivity.this, "输入手机号码有误，请重新输入");
                    return;
                } else if (DeliveryAddress == null
                        || "".equals(DeliveryAddress)) {
                    toast(ReserveActivity.this, "请输入联系地址");
                    return;

                } else if ((TextUtils.isEmpty(MainSetQuantity) && TextUtils
                        .isEmpty(SunSetQuantity)) || (0 == zhu && 0 == fuz)) {
                    toast(ReserveActivity.this, "请输入主餐桌数");
                    return;
                } else if ((TextUtils.isEmpty(SunSetQuantity) && TextUtils
                        .isEmpty(MainSetQuantity)) || (0 == zhu && 0 == fuz)) {
                    toast(ReserveActivity.this, "请输入副餐桌数");
                    return;
                } else if (DeliveryAddress.contains("<")
                        || DeliveryAddress.contains(">")) {
                    toast(ReserveActivity.this, "请不要输入‘<’ 或‘>’ ");
                    return;
                } else if (TextUtils.isEmpty(feastTypy)) {
                    toast(ReserveActivity.this, "请选择喜宴类别");
                    return;
                } else if ("".equals(xstdz)) {
                    toast(ReserveActivity.this, "请选择喜事堂");
                    return;
                }

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Name", edittext_reservation_name.getText()
                        .toString());
                bundle.putString("sex", sexa);
                System.out.println("====ReserveActivity====" + sexa);
                bundle.putString("cxName", series);
                bundle.putString("xyName", feastTypy);
                bundle.putString("xstAdd", ets_UserInfo_Sites.getText()
                        .toString());
                bundle.putString("Tel", edittext_reservation_phone.getText()
                        .toString());
                bundle.putString("DeliveryAdr", edittext_reservation_site
                        .getText().toString());
                bundle.putString("MainCount", edittext_reservation_dinner
                        .getText().toString());
                bundle.putString("SubCount", edittext_reservation_meal
                        .getText().toString());
                bundle.putString("FastDateTime", dateBtn.getText().toString());
                bundle.putString("HallID", selectHallId);
                bundle.putInt("caiID", caiID);
                bundle.putInt("yanhuiID", yanhuiID);
                bundle.putString("date", date);
                bundle.putInt("dateID", dateID);
                bundle.putInt("jiaRu", 21);

                intent.putExtras(bundle);

                intent.setClass(ReserveActivity.this, ReserveWinActivity.class);
                startActivity(intent);
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 123:
                String result = data.getStringExtra("resultString");
                selectHallId = data.getStringExtra("selectHallId");
                ets_UserInfo_Sites.setText(result);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
                HomeActivity.dianJiShiJian = System.currentTimeMillis();
                ReserveActivity.this.finish();
                ActivityCollector.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        if (edittext_reservation_dinner.getText().toString().startsWith("0")) {
            edittext_reservation_dinner.setText("");
            toast(ReserveActivity.this, "主桌桌数首位不能为0，请重新输入！");
        }
        if (edittext_reservation_meal.getText().toString().startsWith("0")) {
            edittext_reservation_meal.setText("");
            toast(ReserveActivity.this, "副桌桌数首位不能为0，请重新输入！");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }
}
