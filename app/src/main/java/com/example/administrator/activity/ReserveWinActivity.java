package com.example.administrator.activity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ab.view.ToDoItem;
import com.example.administrator.fragment.CalendarFragment;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.ProConst;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预定成功页
 *
 * @author Administrator
 */
public class ReserveWinActivity extends BaseActivity implements ProConst {
    private String selectHallId;// 喜事堂ID
    // private TextView order_reference;// 訂單
    @ViewInject(R.id.av_UserInfo_Name)
    private TextView edittext_reservation_name;// 预定姓名
    @ViewInject(R.id.tv_UserInfo_Name)
    private EditText tv_reservation_name;// 预定姓名
    @ViewInject(R.id.av_UserInfo_Age)
    private TextView edittext_reservation_phone;// 预定电话
    @ViewInject(R.id.tv_UserInfo_Age)
    private EditText tv_reservation_phone;// 预定电话
    @ViewInject(R.id.av_UserInfo_Site)
    private TextView edittext_reservation_site;// 预定地址
    @ViewInject(R.id.tv_UserInfo_Site)
    private EditText tv_reservation_site;// 预定地址
    @ViewInject(R.id.av_UserInfo_Table)
    private TextView edittext_reservation_dinner;// 预定主餐
    @ViewInject(R.id.tv_UserInfo_Table)
    private EditText tv_reservation_dinner;// 预定主餐
    @ViewInject(R.id.av_UserInfo_FTable)
    private TextView edittext_reservation_meal;// 预定副餐
    @ViewInject(R.id.tv_UserInfo_FTable)
    private EditText tv_reservation_meal;// 预定副餐
    @ViewInject(R.id.av_dateBtnaa)
    private TextView edittext_reservation_dateBtna;// 预定时间
    @ViewInject(R.id.av_UserInfos_zhucanshijian)
    private TextView av_UserInfos_zhucanshijian;// 主餐时间
    @ViewInject(R.id.avs_UserInfo_Sites)
    private TextView avs_UserInfo_Sites;
    @ViewInject(R.id.tv_UserInfo_Sex)
    private TextView edittext_reservation_sex;// 显示性别=================================
    @ViewInject(R.id.tvs_UserInfo_Sites)
    private EditText tvs_UserInfo_Sites;
    @ViewInject(R.id.avs_UserInfo_Site)
    private TextView avs_UserInfo_Site;
    @ViewInject(R.id.av_UserInfos_Site)
    private TextView av_UserInfos_Site;
    @ViewInject(R.id.reserva_amend)
    private Button btn_Edit;
    private ToDoItem toDoItema;
    @ViewInject(R.id.dateBtnaa)
    private Button dateBtn; // 事件日期和时间
    private SimpleDateFormat sfDate;
    // private TableLayout alertTableLayout;
    @ViewInject(R.id.ensure)
    private Button reserva;
    @ViewInject(R.id.xgxst)
    private Button xgxst;
    @ViewInject(R.id.ra_Sex_Woman)
    RadioButton woman;
    @ViewInject(R.id.ra_Sex_Male)
    RadioButton man;
    //	private Context context;
    private int Feast = 1;
    private String Name = "";
    int jiaRu;
    private String Tel = "";
    private String DeliveryAdr = "";
    private String MainCount = "";
    private String SubCount = "";
    private String FastDateTime = "";
    private String Sex = "";
    private String caixiName = "";
    private String xyName = "";
    private String xstAdd = "";
    private int a;
    private String OderNo;
    // 定义改变后的值
    private String names;
    private String tel;
    private String address;
    private String zhucan;
    private String fucan;
    private String times = "";
    private int sexa;// 默认选择男
    private String t1;
    private int caiID, yanhuiID, dateID;
    private String strUniqueId = HomeActivity.strUniqueId;
    private float dy, uy, zy;

    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    @Override
    protected int setContentView() {
        return R.layout.order_form_a;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);
        LocalStorage.initContext(this);
        OderNo = LocalStorage.get("OderNo").toString();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Name = bundle.getString("Name");
            Tel = bundle.getString("Tel");
            DeliveryAdr = bundle.getString("DeliveryAdr");
            MainCount = bundle.getString("MainCount");
            SubCount = bundle.getString("SubCount");
            FastDateTime = bundle.getString("FastDateTime");
            caixiName = bundle.getString("cxName");
            xyName = bundle.getString("xyName");
            jiaRu = bundle.getInt("jiaRu");
            xstAdd = bundle.getString("xstAdd");
            selectHallId = bundle.getString("HallID");
            caiID = bundle.getInt("caiID");
            yanhuiID = bundle.getInt("yanhuiID");
            date = bundle.getString("date");
            dateID = bundle.getInt("dateID");
            System.out.println(caixiName + "UJ" + xyName + "TYUIK" + xstAdd);
            // ==============================================================================================//
            Sex = bundle.getString("sex");
            if (Sex.equals("0")) {
                edittext_reservation_sex.setText("女");
                woman.setChecked(true);
                sexa = 0;
            } else {
                edittext_reservation_sex.setText("男");
                man.setChecked(true);

                sexa = 1;
            }

            System.out.println("====ReserveWinActivity=====" + Sex
                    + "*************************");

            xgxst.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(ReserveWinActivity.this,
                            WeddinghallActivity.class);
                    intent.putExtra("yuding", 1);
                    startActivityForResult(intent, 123);

                }
            });
            if (Sex.equals("0")) {
                edittext_reservation_sex.setText("女");
                sexa = 0;
            } else {
                edittext_reservation_sex.setText("男");
                sexa = 1;
            }

            reserva.setOnClickListener(servatn);// 监听servatn
            btn_Edit.setOnClickListener(reservatn);
            avs_UserInfo_Sites.setText(xstAdd);
            avs_UserInfo_Site.setText(xyName);
            av_UserInfos_Site.setText(caixiName);
            tvs_UserInfo_Sites.setText(xstAdd);
            edittext_reservation_name.setText(Name);
            tv_reservation_name.setText(Name);
            edittext_reservation_phone.setText(Tel);
            tv_reservation_phone.setText(Tel);
            edittext_reservation_site.setText(DeliveryAdr);
            tv_reservation_site.setText(DeliveryAdr);
            edittext_reservation_dinner.setText(MainCount);
            tv_reservation_dinner.setText(MainCount);
            edittext_reservation_meal.setText(SubCount);
            tv_reservation_meal.setText(SubCount);
            edittext_reservation_dateBtna.setText(FastDateTime);
            av_UserInfos_zhucanshijian.setText(date);
            sfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            Date date;
            try {
                date = sfDate.parse(FastDateTime);
                Bundle extras = getIntent().getExtras();
                if (extras == null || extras.get("") == null) {
                    // 创建task为空的ToDoItem对象
                    toDoItema = new ToDoItem("");
                    // 新建待办事项，将界面中事件时间设置为当前时间
                    dateBtn.setText(FastDateTime);

                    toDoItema.setTime(date); // 设置ToDoItem事件时间
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 处理事件日期点击事件
            dateBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    DialogFragment calendarFragment = new CalendarFragment(
                            toDoItema.getTime(), null, null, 0, null) {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            super.onDateSet(view, year, monthOfYear, dayOfMonth);
                            toDoItema.setTime(year, monthOfYear, dayOfMonth);
                            Date date = toDoItema.getTime();
                            dateBtn.setText(sfDate.format(date));

                        }

                    };
                    calendarFragment.show(getFragmentManager(), "calendarPcker");
                    // Toast.makeText(getApplicationContext(),
                    // toDoItema.getTime().toString(), Toast.LENGTH_LONG)
                    // .show();
                }
            });
            t1 = tv_reservation_name.getText().toString();// 使光标位于名字最后面
            tv_reservation_name.setText(t1);
            tv_reservation_name.setSelection(t1.length());

            tv_reservation_name.setOnKeyListener(new OnKeyListener() {// 使姓名不能有空格

                @Override
                public boolean onKey(View arg0, int arg1, KeyEvent arg2) {

                    switch (arg1) {
                        case 62:
                            t1 = t1.trim();
                            tv_reservation_name.setText(t1);
                            tv_reservation_name.setSelection(t1.length());
                            Toast.makeText(ReserveWinActivity.this,
                                    "姓名不允许包含空格，请重新输入！", Toast.LENGTH_LONG)
                                    .show();
                            break;

                        default:
                            break;
                    }

                    return false;
                }

            });
            // ==============================================================================================//

            setMoreListener();
        }


    }


    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more,
                ReserveWinActivity.this, 0));
    }


    private OnClickListener reservatn = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            ReserveWinActivity.this.finish();
        }

    };

    private OnClickListener servatn = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            // 发送报文过去
            String sexaa = sexa + "";
            String FeastDatetime = dateBtn.getText().toString();
            // 如果不相同就说明修改了 ，相同就说明没有修改

            if (!edittext_reservation_name.getText().equals(
                    tv_reservation_name.getText())) {
                a = 1;
                names = tv_reservation_name.getText().toString();
            } else {
                names = edittext_reservation_name.getText().toString();
            }
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(names);
            if (!edittext_reservation_phone.getText().equals(
                    tv_reservation_phone.getText())) {

                tel = tv_reservation_phone.getText().toString();
                a = 1;
            } else {
                tel = edittext_reservation_phone.getText().toString();
            }
            if (!edittext_reservation_site.getText().equals(
                    tv_reservation_site.getText())) {
                address = tv_reservation_site.getText().toString();
                a = 1;
            } else {
                address = edittext_reservation_site.getText().toString();
            }
            if (!edittext_reservation_dinner.getText().equals(
                    tv_reservation_dinner.getText())) {
                zhucan = tv_reservation_dinner.getText().toString();
                a = 1;
            } else {
                zhucan = edittext_reservation_dinner.getText().toString();

            }
            if (!edittext_reservation_meal.getText().equals(
                    tv_reservation_meal.getText())) {
                fucan = tv_reservation_meal.getText().toString();
                a = 1;
            } else {
                fucan = edittext_reservation_meal.getText().toString();
            }

            // bianhao = order_reference.getText().toString();

            // 发送数据
            /**
             * http://192.168.0.203:8081/ashx/BookHandler.ashx?
             * Action=bookEdit&Name=000&sex=0（性别0：女 1男）
             * &Tel=15557250731&DeliveryAdr=地址1&MainCount=1(主桌数)
             * &SubCount=1(副桌数)&FastDateTime=2016/02/02(宴席时间)
             * &OderNo=16042200000084630995(订单号)T
             */

        }

    };

    public void fin() {
        ReserveWinActivity.this.finish();
        ReserveActivity.ssthis.finish();
    }

    private void goback(View view) {
        Intent intent = new Intent(ReserveWinActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 123:
                String result = data.getStringExtra("resultString");
                selectHallId = data.getStringExtra("selectHallId");
                tvs_UserInfo_Sites.setText(result);
                break;
            default:
                break;

        }

    }

    private String date;


}
