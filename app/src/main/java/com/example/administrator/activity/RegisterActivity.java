package com.example.administrator.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.list.Utils;
import com.example.administrator.myapplication.R;
import com.example.administrator.net.RetrofitUtil;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.DataConvert;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.MD5Util;
import com.example.administrator.utils.ProConst;
import com.example.administrator.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.administrator.myapplication.R.id.map;

/**
 * 注册页
 *
 * @author Administrator
 */
public class RegisterActivity extends BaseActivity implements ProConst {

    @ViewInject(R.id.edit_register_mobile)
    private EditText edit_register_mobile; // 手机号码
    @ViewInject(R.id.et_register_valid)
    private EditText et_register_valid; // 验证码
    @ViewInject(R.id.edit_register_password)
    private EditText edit_register_password;// 密码
    @ViewInject(R.id.edit_confirm_password)
    private EditText edit_confirm_password;// 确认密码
    @ViewInject(R.id.btn_register_validcode)
    private Button btn_register_validcode;// 获取验证码按钮
    @ViewInject(R.id.btn_register_confirm)
    private Button btn_register_confirm;// 提交
    @ViewInject(R.id.btn_back)
    private Button leftBtn = null;
    private LoadingDialog dialog = null;// 加载
    // private Timer timer = null;
    private TimeCount time;
    private int zhuCe;
    @ViewInject(R.id.tv_title)

    private TextView tv;
    @ViewInject(R.id.zhaohui_mima_zhuce)
    private LinearLayout ll;
    private float dy, uy;
private RetrofitUtil registerUtil;


    @Override
    protected int setContentView() {
        return R.layout.myregister;
    }

    @Override
    protected void initView() {
        tv.setText("用户注册");
        registerUtil=new RetrofitUtil(this);
        ActivityCollector.addActivity(this);
        ll.setOnTouchListener(new OnTouchListener() {
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
//        LocalStorage.initContext(this);
        zhuCe = getIntent().getIntExtra("zhuCe", 0);
        time = new TimeCount(60000, 1000);
        leftBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(
                        RegisterActivity.this.getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                RegisterActivity.this.finish();
            }
        });
        // 获取验证码
        btn_register_validcode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String UserTel = edit_register_mobile.getText().toString();
                if (!Utils.isMobile(UserTel)) {
                    toast(v.getContext(), getString(R.string.register_mobile_format_error));
                    return;
                } else if (!DataConvert.isMobile(UserTel)) {
                    toast(v.getContext(), getString(R.string.register_mobile_format_error));
                    return;
                }
                dialog = new LoadingDialog(v.getContext(), "正在获取，请稍候...");
//                dialog.showDialog();
                edit_register_mobile.setEnabled(false);
                Map<String,String> map=new HashMap<String, String>();
                map.put("Function","GetCode");
                map.put("UserTel",UserTel);
                Toast.makeText(RegisterActivity.this,UserTel+"111",Toast.LENGTH_SHORT).show();
                registerUtil.getStringDataFromNet("User", map, new RetrofitUtil.CallBack<String>() {
                    @Override
                    public void onLoadingDataComplete(String body) {
                        toast(RegisterActivity.this, body+"1222");
                        dialog.closeDialog();
								handler.sendEmptyMessage(2);
								String result = new String(body);
                    }

                    @Override
                    public void onLoadingDataFailed(Throwable t) {
                        handler.sendEmptyMessage(2);
								setView(getString(R.string.conn_failed));
                    }
                });
                time.start();

            }
        });
        // 提交
        btn_register_confirm.setOnClickListener(confirmBtn);

        setMoreListener();
    }


    @ViewInject(R.id.iv_more)
    private ImageView iv_more;

    private void setMoreListener() {
        iv_more.setOnClickListener(new IvListener(iv_more,
                RegisterActivity.this, 0));
    }

    private OnClickListener confirmBtn = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            final String UserTel = edit_register_mobile.getText().toString();
            String UserCode = et_register_valid.getText().toString();
            final String UserPass = edit_register_password.getText().toString()
                    .trim();
            final String ConfirmPass = edit_confirm_password.getText()
                    .toString().trim();

            if (!Utils.isMobile(UserTel)) {
                toast(RegisterActivity.this, "输入手机号码有误");
                return;
            } else if (UserCode == null || "".equals(UserCode)) {
                toast(RegisterActivity.this, getString(R.string.register_code_hind));
                return;
            } else if (UserPass == null || "".equals(UserPass)) {
                toast(RegisterActivity.this, getString(R.string.login_password));
                return;
            } else if (!UserPass.equals(ConfirmPass)) {
                toast(RegisterActivity.this, getString(R.string.confirm_password));
                return;
            }
            dialog = new LoadingDialog(v.getContext(), "正在获取，请稍候...");
//            dialog.showDialog();
            //Regist:功能，注册用户(HTTP协议)参数:1.UserTel;用户电话2.UserPwd;登录密码3.UserCode;验证码,
            Map<String,String> map=new HashMap<String, String>();
            map.put("Function","Regist");
            map.put("UserTel",UserTel);
            map.put("UserPwd", MD5Util.string2MD5(UserPass));
            map.put("UserCode",UserCode);
registerUtil.getStringDataFromNet("User", map, new RetrofitUtil.CallBack<String>() {
    @Override
    public void onLoadingDataComplete(String body) {
        toast(RegisterActivity.this, body+"1224442");
        try {
            JSONObject jo=new JSONObject(body.trim());
           String result= jo.getString("提示");
            Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_LONG).show();
            if(result.equals("注册成功")){
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginInActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadingDataFailed(Throwable t) {

    }
});
        }
    };


    private void setView(String toast) {
        toast(RegisterActivity.this, toast);
        btn_register_validcode.setText(getString(R.string.get_valid_code));
        btn_register_validcode.setClickable(true);
        et_register_valid.setText("");
        edit_register_mobile.setEnabled(true);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    String valicodeStr = "";
                    int timeBalance = (Integer) msg.obj;
                    if (timeBalance <= 0) {
                        // valicodeStr = getString(R.string.get_valid_code);
                        btn_register_validcode.setClickable(true);
                    } else {
                        valicodeStr = timeBalance + "秒";
                        btn_register_validcode.setClickable(false);
                    }
                    btn_register_validcode.setText(valicodeStr);
                    break;
                case 2:
                    // if (dialog!=null) {
                    // dialog.closeDialog();
                    // }
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            btn_register_validcode.setClickable(false);
            btn_register_validcode.setText("(" + millisUntilFinished / 1000
                    + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            btn_register_validcode.setText("重新获取验证码");
            btn_register_validcode.setClickable(true);

        }

    }

}
