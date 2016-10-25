package com.example.administrator.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.list.Utils;
import com.example.administrator.myapplication.R;
import com.example.administrator.utils.ActivityCollector;
import com.example.administrator.utils.FileUtil;
import com.example.administrator.utils.GetPersonInfo;
import com.example.administrator.utils.IvListener;
import com.example.administrator.utils.Load;
import com.example.administrator.utils.LoadingDialog;
import com.example.administrator.utils.LocalStorage;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.client.ClientProtocolException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyInformationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout nickName, pass,  sex, Erweima, changeheadimage;
    private TextView nickText, nickEdit, sexText, sexEdit;
    private String editTel, editPass, editSex, editNickName, myHeadUrl;
    private TextView tv;
    private LoadingDialog dialog = null;
    private Button lonout;
    private ImageView myHead;
    private int H, W;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geren_zhiliao);
        ActivityCollector.addActivity(this);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        H = mDisplayMetrics.heightPixels;
        W = mDisplayMetrics.widthPixels;
        tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("个人资料");
        LocalStorage.initContext(this);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent data = new Intent();
                data.putExtra("resultStringName", editNickName);
                MyInformationActivity.this.setResult(13, data);
                finish();
            }
        });
        Load.getLoad(this);
        myHead = (ImageView) findViewById(R.id.myHead);
        lonout = (Button) findViewById(R.id.lonout);
        Erweima = (LinearLayout) findViewById(R.id.Erweima);
        changeheadimage = (LinearLayout) findViewById(R.id.changeheadimage);

        nickName = (LinearLayout) findViewById(R.id.NickName);
        nickText = (TextView) findViewById(R.id.NickText);
        nickEdit = (TextView) findViewById(R.id.NickEdit);

        pass = (LinearLayout) findViewById(R.id.Pass);

        sex = (LinearLayout) findViewById(R.id.Sex);
        sexText = (TextView) findViewById(R.id.SexText);
        sexEdit = (TextView) findViewById(R.id.SexEdit);

        editTel = (String) LocalStorage.get("UserTel");
        GetPersonInfo getPersonInfo = new GetPersonInfo();
        getPersonInfo.getInfo(MyInformationActivity.this, editTel);
//        getPersonInfo.setCallBackListener(new CallBackInfo() {
//
//            @Override
//            public void getSuccese() {
//                // TODO Auto-generated method stub
//                initData();
//            }
//
//            @Override
//            public void getFailure() {
//                // TODO Auto-generated method stub
//                initData();
//            }
//        });

        imageUri = Uri.parse(IMAGE_FILE_LOCATION);

        lonout.setOnClickListener(this);
        Erweima.setOnClickListener(this);
        nickName.setOnClickListener(this);
        changeheadimage.setOnClickListener(this);
        pass.setOnClickListener(this);
        sex.setOnClickListener(this);

        setMoreListener();
    }

    private ImageView iv_more;

    private void setMoreListener() {
        iv_more = (ImageView) findViewById(R.id.iv_more);
        iv_more.setOnClickListener(new IvListener(iv_more,
                MyInformationActivity.this, 0));
    }

    private static final String TAG = "MyInfomationActivity";
    private static final String IMAGE_FILE_LOCATION = "file://"
            + getSdcardDir() + "/myHead.jpg";
    private Uri imageUri;// to store the big bitmap

    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(this.getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 4);
    }

    private String urlpath = "";

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(MyInformationActivity.this,
                    "myHead.jpg", photo);
            myHead.setImageDrawable(drawable);
            File imageFile = new File(urlpath);
//            UploadImageThread mUploadImageThread = new UploadImageThread(
//                    MyInformationActivity.this, imageFile);
//            mUploadImageThread
//                    .setUploadImageListener(new UploadImageListener() {
//                        @Override
//                        public void uploadImageFail() {
//                            System.out.println("----> 图片上传失败");
//                        }
//
//                        @Override
//                        public void uploadImageSuccess() {
//                            System.out.println("----> 图片上传成功");
//                        }
//                    });
//            mUploadImageThread.start();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                Log.d(TAG, "TAKE_BIG_PICTURE: data = " + data);// it seems to be
                // null
                // TODO sent to crop
                cropImageUri(imageUri, 300, 300, 2);

                break;
            case 2:// from crop_big_picture
                Log.d(TAG, "CROP_BIG_PICTURE: data = " + data);// it seems to be
                if (imageUri != null& resultCode == RESULT_OK) {
                    Bitmap bitmap = decodeUriAsBitmap(imageUri);
                    myHead.setImageBitmap(bitmap);
                    try {
                        uploadPhoto();
                    } catch (ClientProtocolException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                Log.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);// it seems to be
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }

                break;

            case 4:
                if (data != null) {
                    setPicToView(data);
                }

                break;
            default:
                break;
        }
    }

    /**
     * 上传头像至服务器
     *
     * @throws IOException
     * @throws ClientProtocolException
     */
    private void uploadPhoto() throws ClientProtocolException, IOException {

        File imageFile = new File(IMAGE_FILE_LOCATION.replace("file://", ""));
        System.out.println("imageFile=" + imageFile);
//        UploadImageThread mUploadImageThread = new UploadImageThread(
//                MyInformationActivity.this, imageFile);
//        mUploadImageThread.setUploadImageListener(new UploadImageListener() {
//            @Override
//            public void uploadImageFail() {
//                System.out.println("----> 图片上传失败");
//            }
//
//            @Override
//            public void uploadImageSuccess() {
//                System.out.println("----> 图片上传成功");
//            }
//        });
//        mUploadImageThread.start();
    }

    private ImageView erweimaphoto, myerweimaHead;
    private TextView erweimanickname;
    private LinearLayout photo, camera;

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {

            case R.id.changeheadimage:

                if (initDirs()) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(
                            MyInformationActivity.this).create();
                    alertDialog.show();
                    Window window = alertDialog.getWindow();
                    window.setContentView(R.layout.dialog_main_info);
                    photo = (LinearLayout) window.findViewById(R.id.photo);
                    photo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub

                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                            intent.setDataAndType(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    "image/*");
                            startActivityForResult(intent, 3);
                            alertDialog.dismiss();

                        }
                    });
                    camera = (LinearLayout) window.findViewById(R.id.camera);
                    camera.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            Intent intent = null;
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File temp = new File(IMAGE_FILE_LOCATION.replace("file://", ""));
                            if (temp.exists()) {
                                temp.delete();
                            }
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, 1);
                            alertDialog.dismiss();
                        }
                    });
                }

                break;

            case R.id.NickName:
                alertDialog(editTel, "NickName", nickText.getText().toString(),
                        nickEdit.getText().toString(), "请输入您的用户名，长度为1-8个字符，可输入中英文或数字");
                break;

            case R.id.Pass:
                Intent intent = new Intent(MyInformationActivity.this,
                        FindPassActivity.class);
                intent.putExtra("flag", 6);
                startActivityForResult(intent, 66);
                break;
            case R.id.Sex:
                alertDialog(editTel, "Sex", sexText.getText().toString(), sexEdit
                        .getText().toString(), "请选择您的性别");
                break;

            case R.id.Erweima:
                final AlertDialog alertDialog = new AlertDialog.Builder(
                        MyInformationActivity.this).create();
                alertDialog.show();
                Window window = alertDialog.getWindow();
                window.setContentView(R.layout.ermerimacode);
                erweimaphoto = (ImageView) window.findViewById(R.id.erweimaphoto);
                myerweimaHead = (ImageView) window.findViewById(R.id.myerweimaHead);
                erweimanickname = (TextView) window
                        .findViewById(R.id.erweimanickname);
                erweimanickname.setText(editNickName);
                window.findViewById(R.id.erweimadia).setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // TODO Auto-generated method stub
                                alertDialog.dismiss();
                            }
                        });
                Load.imageLoader.displayImage(myHeadUrl, myerweimaHead,
                        Load.options);
                Bitmap bitmap;
//                try {
//                    bitmap = new CreateCode().createCode(editTel,
//                            BitmapFactory.decodeResource(getResources(),
//                                    R.mipmap.erweimalogo), BarcodeFormat.QR_CODE);
//                    Drawable drawable = new BitmapDrawable(bitmap);
//                    erweimaphoto.setBackground(drawable);
//                } catch (WriterException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }

                break;
            case R.id.lonout:
                if (MyInformationActivity.this.isFinishing()) {
                } else {
                    d = new Dialog(MyInformationActivity.this,
                            R.style.loading_dialog);
                    v = LayoutInflater.from(MyInformationActivity.this).inflate(
                            R.layout.dialog, null);// 窗口布局
                    d.setContentView(v);// 把设定好的窗口布局放到dialog中
                    d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
                    p1 = (Button) v.findViewById(R.id.p);
                    n = (Button) v.findViewById(R.id.n);
                    gengxinlin = (LinearLayout) v.findViewById(R.id.gengxinlin);
                    gengxinlin.setVisibility(View.VISIBLE);
                    juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
                    tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
                    juTiXinXi.setText("亲，您真的准备退出吗？");
                    tiShi.setText("退出提示");
                    p1.setText("确定");
                    n.setText("返回");
                    p1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            LocalStorage.clear();
                            LocalStorage.set("LoginStatus", "tuichu");
                            LocalStorage.set("UserPass", editPass);
                            LocalStorage.set("UserTel", editTel);

                            File file = new File(Environment
                                    .getExternalStorageDirectory().toString()
                                    + "/EFHM/cache/photo/myHead.jpg");
                            if (file.exists()) {
                                file.delete();
                                System.out.println("delete succ");
                            }
                            StartActivity.tuiChu();
                            startActivity(new Intent(MyInformationActivity.this,
                                    HomeActivity.class));
//                            HomeActivity.homethis.finish();
                            MyInformationActivity.this.finish();
                            d.dismiss();
                        }
                    });
                    n.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            // System.out.println(a);
                            d.dismiss();
                        }
                    });
                    d.show();
                }

            default:
                break;
        }
    }

    private TextView juTiXinXi, tiShi;
    private Dialog d;
    private Button p1;
    private Button n;
    private View v;

    /**
     * 从缓存中获取数据，初始化数据
     */
    private void initData() {
        // 获取缓存数据

        editPass = (String) LocalStorage.get("UserPass");
        editSex = (String) LocalStorage.get("Sex");
        editNickName = (String) LocalStorage.get("NickName");
        myHeadUrl = LocalStorage.get("ImageUrl").toString();

        Load.imageLoader.displayImage(myHeadUrl, myHead, Load.options);

        // 将缓存数据显示
        nickEdit.setText(editNickName);
        if (editSex.equals("0")) {
            sexEdit.setText("女");
        }
        if (editSex.equals("1")) {
            sexEdit.setText("男");
        }

    }

    private boolean isOk = true;
    private int ciShu = 0;

    private void editData(final String userTel, final String keyString,
                          final String editName) {

        dialog = new LoadingDialog(MyInformationActivity.this, "正在修改中...");
        dialog.showDialog();
        System.out.println("userTel=" + userTel);
        System.out.println("keyString=" + keyString);
        System.out.println("editName=" + editName);
//		String passEnt = MD5Util.string2MD5(editPass);
        RequestParams hall = new RequestParams();
//        hall.add("UserTel", userTel);
//        // hall.add("UserPass", passEnt);
//        hall.add(keyString, editName);
//        SmartFruitsRestClient.post("LoginCheckHandler.ashx?Action=UpUser",
//                hall, new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//                        // TODO Auto-generated method stub
//                        String result;
//                        dialog.closeDialog();
//                        try {
//                            result = new String(arg2, "utf-8");
//                            System.out.println("result" + result);
//                            LocalStorage.set(keyString, editName);
//                            initData();
//
//                        } catch (UnsupportedEncodingException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                            Toast.makeText(getApplicationContext(),
//                                    "修改失败了，辛苦点再改一次吧~", Toast.LENGTH_SHORT)
//                                    .show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//                                          Throwable arg3) {
//                        // TODO Auto-generated method stub
//                        if (ciShu >= 3) {
//                            isOk = false;
//                            Toast.makeText(MyInfomationActivity.this,
//                                    "网络君不给力，辛苦点再改一次吧~", Toast.LENGTH_SHORT)
//                                    .show();
//
//                        }
//                        if (isOk) {
//                            editData(userTel, keyString, editName);
//                            ciShu++;
//                        }
//                        dialog.closeDialog();
//                    }
//                });
    }

    private TextView name;
    private EditText change;
    private LinearLayout  changeinfo, gengxinlin;
    private RadioGroup changesex;
    private RadioButton male, female;
    private boolean isTrue = true;
//	private float dy, uy, zy;

    private void alertDialog(final String userTel, final String keyString,
                             String exitTextName, String exitEditName, String tips) {
        if (MyInformationActivity.this.isFinishing()) {
        } else {
            d = new Dialog(MyInformationActivity.this, R.style.loading_dialog);
            v = LayoutInflater.from(MyInformationActivity.this).inflate(
                    R.layout.dialog, null);// 窗口布局
            d.setContentView(v);// 把设定好的窗口布局放到dialog中
            d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话

            p1 = (Button) v.findViewById(R.id.p);
            n = (Button) v.findViewById(R.id.n);
            tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
            juTiXinXi = (TextView) v.findViewById(R.id.banben_xinxi);
            changesex = (RadioGroup) v.findViewById(R.id.changesex);

            changeinfo = (LinearLayout) v.findViewById(R.id.changeinfo);
            name = (TextView) v.findViewById(R.id.name);
            change = (EditText) v.findViewById(R.id.change);
            female = (RadioButton) v.findViewById(R.id.female);
            male = (RadioButton) v.findViewById(R.id.male);
            if (keyString.equals("Sex")) {
                changesex.setVisibility(View.VISIBLE);
                changeinfo.setVisibility(View.GONE);
                if (LocalStorage.get("Sex").toString().equals("0")) {
                    female.setChecked(true);
                } else {
                    change.setText("1");
                    male.setChecked(true);
                }
                changesex
                        .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup arg0,
                                                         int checkedId) {
                                // TODO Auto-generated method stub

                                if (checkedId == male.getId()) {
                                    change.setText("1");
                                } else if (checkedId == female.getId()) {
                                    change.setText("0");
                                }
                            }
                        });

            } else if (keyString.equals("HomeAddress")
                    || keyString.equals("NowAddress")) {
                changesex.setVisibility(View.GONE);
                changeinfo.setVisibility(View.VISIBLE);
                name.setText(exitTextName);
                change.setSingleLine(false);
                change.setMinLines(5);
                change.setText(exitEditName);
                change.setSelection(exitEditName.length());

            } else {
                changesex.setVisibility(View.GONE);
                changeinfo.setVisibility(View.VISIBLE);
                name.setText(exitTextName);
                change.setText(exitEditName);
                change.setSingleLine();
                change.setSelection(exitEditName.length());
            }
            juTiXinXi.setText(tips);
            if (isTrue) {

                tiShi.setText("修改" + exitTextName);
                tiShi.setTextColor(Color.parseColor("#000000"));
                p1.setText("取消修改");
                n.setText("确认修改");
                p1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        d.dismiss();
                    }
                });
                n.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        // System.out.println(a);
                        if (keyString.equals("Sex")) {
                            if (TextUtils.isEmpty(change.getText().toString())) {
                                d.dismiss();
                                return;
                            }
                            editData(userTel, keyString, change.getText()
                                    .toString());
                            d.dismiss();
                        } else {
                            if(change.getText().toString().equals(LocalStorage.get("NickName").toString())){
                                return;
                            }
                            if (Utils.isUserName(change.getText().toString())) {
                                editData(userTel, keyString, change.getText()
                                        .toString());
                                d.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "请输入正确的用户名！", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                    }
                });
                d.show();
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {
                HomeActivity.dianJiShiJian = System.currentTimeMillis();
                Intent data = new Intent();
                data.putExtra("resultStringName", editNickName);
                MyInformationActivity.this.setResult(13, data);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            System.out.println("有SD卡");
            return Environment.getExternalStorageDirectory().toString()
                    + "/EFHM/cache/photo";
        } else {
            System.out.println("没有");
            return null;
        }

    }

    private boolean initDirs() {
        if (getSdcardDir() == null) {
            return false;
        }
        File f = new File(getSdcardDir());
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
