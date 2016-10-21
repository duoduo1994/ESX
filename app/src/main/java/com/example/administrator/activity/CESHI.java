package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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

import com.example.administrator.ab.view.Connect;
import com.example.administrator.list.Utils;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;
import com.example.administrator.utils.MD5Util;
import com.example.administrator.utils.ProConst;
import com.example.administrator.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 登入
 *
 * @author Administrator
 */
public class CESHI extends BaseActivity implements ProConst {

    @ViewInject(R.id.edittext_login_account)// ///
    private EditText edittext_login_account;
    @ViewInject(R.id.edittext_login_password)// //
    private EditText edittext_login_password;
    @ViewInject(R.id.btn_register)
    private Button btn_register;// 注册按钮
    @ViewInject(R.id.btn_login_login)
    private Button btn_login_login;// 登陆按钮
    @ViewInject(R.id.text_login_findpass)
    private TextView text_login_findpass;
    @ViewInject(R.id.checkBox1)
    private ImageView checkBox1;// 显示密码
    private int flag = -1;
    private String UserPass;
    private String UserTel;
    private int dengRu;
    @ViewInject(R.id.deng_lu_scrollview)
    private LinearLayout ll;
    private float dy, uy;
    static Activity a;
    public static int a5;

    @Override
    protected int setContentView() {
        return R.layout.login;
    }

    @Override
    protected void initView() {
        ActivityCollector.addActivity(this);
        LocalStorage.initContext(this);

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
        String mobile = (String) LocalStorage.get("UserTel");
        edittext_login_account.setText(mobile);
//		String password = (String) LocalStorage.get("UserPass");
        // edittext_login_password.setText(password);


        edittext_login_account.addTextChangedListener(watcher);

        // 登陆
        btn_login_login.setOnClickListener(myLogin);
        // 注册用户
        btn_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                    Intent intent = new Intent();
                    intent.setClass(CESHI.this, RegisterActivity.class);
                    startActivity(intent);
finish();;


            }
        });

    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub
            edittext_login_password.setText("");
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }
    };

    private boolean isChecked = true;
    // 加载对话框
    private LoadingDialog dialog = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (dialog != null) {
                        dialog.closeDialog();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    //	private SharedPreferences preferences;
//	private Editor editor;
    // 登陆
    private OnClickListener myLogin = new OnClickListener() {

        @Override
        public void onClick(View v) {

            login();
        }
    };
    static Connect connect=new Connect();
   Handler myhandler;
    private void login() {

        UserTel = edittext_login_account.getText().toString();
        UserPass = edittext_login_password.getText().toString();

        if (!Utils.isMobile(UserTel) || StringUtils.isEmpty(UserPass)) {
            toast(CESHI.this, "用户名或密码不能为空或有误");
            return;
        }
     //   dialog = new LoadingDialog(CESHI.this, "正在登陆，请稍候...");
      //  dialog.showDialog();
      //  // TODO Auto-generated method stub
       String passEnt = MD5Util.string2MD5(UserPass);

        JieShou();


        connect.dengru(UserTel, passEnt, "170976fa8a862b0a3df",myhandler);



//

    }


    public void JieShou(){
        myhandler=new Handler()
        {
            public void handleMessage(Message msg)
            {
                if(msg.what==0x123)
                {
                    String	s=msg.obj.toString();
                    if("登录成功".endsWith(s)){
                        // dialog.closeDialog();
                        LocalStorage.set("LoginStatus", "login");
                        finish();
                    }
                }
            }
        };


    }

//	private boolean isFalse = true;
//	private int ciShu = 0;

    private String authority = "null";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("HJJJJJ" + flag);

            if (flag == 5) {
                Intent data = new Intent();
                data.putExtra("Authority", authority);
                data.putExtra("resultString", "亲，该咋称呼您~");
                CESHI.this.setResult(123, data);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
