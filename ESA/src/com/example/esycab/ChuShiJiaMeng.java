package com.example.esycab;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.example.esycab.entity.IDCard;
import com.example.esycab.utils.ActivityCollector;
import com.example.esycab.utils.IvListener;
import com.example.esycab.utils.LoadingDialog;
import com.example.esycab.utils.Savefile;
import com.example.esycab.utils.SmartConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

public class ChuShiJiaMeng extends Activity implements OnClickListener {
	private Button  join;// 返回，申请加盟按钮
	private String FeastDatetime, tuan_geren, fuwufanwei, RealName, Tel,
			caixiID, jdnl;// 申请日期，团队个人，服务范围，姓名，电话，菜系id，订单能力
	private EditText shenfenzheng;// 身份证号
	private LinearLayout camera;// 拍照
	private LinearLayout photo; // 相册
	private ImageView sfzzm, sfzfm, jkz, csz, djz; // 照片显示
	private String result = "";
	private MyHandler handler;
	private LinearLayout ll;
	float dy, uy;
	private String[] imageURL = new String[5];
	private int flag = -1;
	private String sfzID;
	private MyThread mythread;
	private Thread thread;
	private TextView tv;
	boolean b = false;
	private static final int REQUESTCODE_PICK = 4;
	private static final int REQUESTCODE_CUTTING = 5; // 图片裁切标记

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
			// 透明导航栏
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		} else {
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		}
		setContentView(R.layout.chushijiamengsahngchuan);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ActivityCollector.addActivity(this);

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("厨师加盟");
		handler = new MyHandler();
		ll = (LinearLayout) findViewById(R.id.chushi_jiameng_yemian_scrollview);
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

		shenfenzheng = (EditText) findViewById(R.id.shenfenzheng);

		sfzzm = (ImageView) findViewById(R.id.sfzzm);
		sfzfm = (ImageView) findViewById(R.id.sfzfm);
		jkz = (ImageView) findViewById(R.id.jkz);
		csz = (ImageView) findViewById(R.id.csz);
		djz = (ImageView) findViewById(R.id.djz);

		sfzzm.setOnClickListener(this);

		sfzfm.setOnClickListener(this);

		jkz.setOnClickListener(this);

		csz.setOnClickListener(this);

		djz.setOnClickListener(this);

		/*----------------------------获取前一页得到的数据---------------------------------------------*/
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {

			FeastDatetime = bundle.getString("FeastDatetime");
			tuan_geren = bundle.getString("tuan_geren");
			fuwufanwei = bundle.getString("fuwufanwei");
			RealName = bundle.getString("RealName");
			Tel = bundle.getString("Tel");
			caixiID = bundle.getString("caixiID");
			jdnl = bundle.getString("jdnl");

		}

		if ("1".equals(tuan_geren)) {

			// chuling.setVisibility(View.VISIBLE);
			// zhuchu.setVisibility(View.VISIBLE);
			// tuandui.setVisibility(View.VISIBLE);
			// time.setVisibility(View.VISIBLE);
			// zc_name.setVisibility(View.VISIBLE);
			// cook_num.setVisibility(View.VISIBLE);
		}
		/*-----------------------返回--------------------------------------------------*/

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

					HomeActivity.dianJiShiJian = System.currentTimeMillis();
					InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputMethodManager.hideSoftInputFromWindow(
							ChuShiJiaMeng.this.getCurrentFocus()
									.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
					File f = new File(Environment.getExternalStorageDirectory()
							.toString() + "/EFHM/cache/photophoto-1.jpg");
					File f1 = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/EFHM/cache/photophoto0.jpg");
					File f2 = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/EFHM/cache/photophoto1.jpg");
					File f3 = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/EFHM/cache/photophoto2.jpg");
					File f4 = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/EFHM/cache/photophoto3.jpg");
					File f5 = new File(Environment
							.getExternalStorageDirectory().toString()
							+ "/EFHM/cache/photophoto4.jpg");
					f.delete();
					f1.delete();
					f2.delete();
					f3.delete();
					f4.delete();
					f5.delete();

					finish();

				}
			}
		});

		/*----------------------------申请加盟按钮---------------------------------------------*/
		join = (Button) findViewById(R.id.join);
		join.setOnClickListener(new OnClickEvent(1000) {
			@Override
			public void singleClick(View v) {
				ld = new LoadingDialog(ChuShiJiaMeng.this, "正在加载，请稍后");
				ld.showDialog();
				if ("1".equals(tuan_geren)) {
					Cook_Tuan();
				} else {
					initDatas();
				}

			}
		});

		setMoreListener();
	}

	private ImageView iv_more;

	private void setMoreListener() {
		iv_more = (ImageView) findViewById(R.id.iv_more);
		iv_more.setOnClickListener(new IvListener(iv_more, ChuShiJiaMeng.this,
				0));
	}

	private LoadingDialog ld;
	private TextView juTiXinXi, tiShi;
	private Dialog d;
	private Button p1;
	private Button n;
	private View v;

	private class MyHandler extends Handler {
		// 接受message的信息
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				final String res = (msg.getData().getString("result"));
				if (res != null) {
					if (ChuShiJiaMeng.this.isFinishing()) {
					} else {
						d = new Dialog(ChuShiJiaMeng.this,
								R.style.loading_dialog);
						v = LayoutInflater.from(ChuShiJiaMeng.this).inflate(
								R.layout.dialog, null);// 窗口布局
						d.setContentView(v);// 把设定好的窗口布局放到dialog中
						d.setCanceledOnTouchOutside(true);// 设定点击窗口空白处取消会话
						p1 = (Button) v.findViewById(R.id.p);
						n = (Button) v.findViewById(R.id.n);
						juTiXinXi = (TextView) v
								.findViewById(R.id.banben_xinxi);
						tiShi = (TextView) v.findViewById(R.id.banben_gengxin);
						juTiXinXi.setText(res);
						tiShi.setText("消息");
						p1.setText("确定");
						n.setText("返回");
						n.setVisibility(View.GONE);
						p1.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								if (res.contains("成功")) {
									File f = new File(Environment
											.getExternalStorageDirectory()
											.toString()
											+ "/EFHM/cache/photophoto-1.jpg");
									File f1 = new File(Environment
											.getExternalStorageDirectory()
											.toString()
											+ "/EFHM/cache/photophoto0.jpg");
									File f2 = new File(Environment
											.getExternalStorageDirectory()
											.toString()
											+ "/EFHM/cache/photophoto1.jpg");
									File f3 = new File(Environment
											.getExternalStorageDirectory()
											.toString()
											+ "/EFHM/cache/photophoto2.jpg");
									File f4 = new File(Environment
											.getExternalStorageDirectory()
											.toString()
											+ "/EFHM/cache/photophoto3.jpg");
									File f5 = new File(Environment
											.getExternalStorageDirectory()
											.toString()
											+ "/EFHM/cache/photophoto4.jpg");
									f.delete();
									f1.delete();
									f2.delete();
									f3.delete();
									f4.delete();
									f5.delete();

									ChuShiJiaMeng.this.finish();
									JoinInActivity.a5.finish();
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
				}
				ld.closeDialog();
			}

		}
	}

	private String appServer = new SmartConfig().APP_SERVER_URL;

	private class MyThread implements Runnable {

		@Override
		public void run() {
			sfzID = shenfenzheng.getText().toString();
			HttpClient httpclient = new DefaultHttpClient();
			// 设置通信协议版本
			httpclient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			if ("0".equals(tuan_geren)) {
				HttpPost httppost = new HttpPost(
						appServer
								+ "JoinHandler.ashx?Action=CookJoin&CookOrGrp=Cook&ApplyTime="
								+ FeastDatetime + "&PrincipalName=" + RealName
								+ "&PrincipalTel=" + Tel + "&GoodAt=" + caixiID
								+ "&Maximum=" + jdnl + "&ServiceArea="
								+ fuwufanwei + "&IDNum=" + sfzID);

				MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
				String name = "IDNumPosImg";
				mpEntity.addPart(name, new FileBody(new File(imageURL[0])));
				String name2 = "IDNumBackImg";
				mpEntity.addPart(name2, new FileBody(new File(imageURL[1])));
				String name3 = "HealthImg";
				mpEntity.addPart(name3, new FileBody(new File(imageURL[2])));
				String name4 = "CookImg";
				mpEntity.addPart(name4, new FileBody(new File(imageURL[3])));
				String name5 = "LevelImg";
				mpEntity.addPart(name5, new FileBody(new File(imageURL[4])));

				System.out.println(imageURL[0] + "," + imageURL[1] + ","
						+ imageURL[2] + "," + imageURL[3] + "," + imageURL[4]);

				httppost.setEntity(mpEntity);
				System.out.println("executing request "
						+ httppost.getRequestLine());
				try {
					HttpResponse response = httpclient.execute(httppost);

					HttpEntity resEntity = response.getEntity();

					System.out.println(response.getStatusLine());// 通信Ok
					String json = "";

					if (resEntity != null) {

						json = EntityUtils.toString(resEntity, "utf-8");
						System.out.println(json + "asdjnj");
						JSONObject p = null;
						try {
							p = new JSONObject(json);

							result = p.getString("提示");
							System.out.println(result);
							Message message = new Message();
							Bundle bundle = new Bundle();
							bundle.putString("result", result);
							message.setData(bundle);// bundle传值，耗时，效率低
							message.what = 1;// 标志是哪个线程传数据
							System.out.println(message + "," + bundle + ","
									+ handler + "d");
							handler.sendMessage(message);// 发送message信息

						} catch (Exception e) {
							System.out.println(e + "jasndj");
							e.printStackTrace();
						}
					}
					if (resEntity != null) {
						resEntity.consumeContent();
					}

				} catch (Exception e) {
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("result", "亲，上传不成功？请再尝试一次");
					message.setData(bundle);// bundle传值，耗时，效率低
					message.what = 1;// 标志是哪个线程传数据
					handler.sendMessage(message);// 发送message信息

				}

			} else {
				HttpPost httppost = new HttpPost(
						appServer
								+ "JoinHandler.ashx?Action=CookJoin&CookOrGrp=Grp&ApplyTime="
								+ FeastDatetime + "&Name=" + RealName + "&Tel="
								+ Tel + "&GoodAt=" + caixiID + "&Maximum="
								+ jdnl + "&ServiceArea=" + fuwufanwei
								+ "&IDNum=" + sfzID);

				MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
				String name = "IDNumPosImg";
				mpEntity.addPart(name, new FileBody(new File(imageURL[0])));
				String name2 = "IDNumBackImg";
				mpEntity.addPart(name2, new FileBody(new File(imageURL[1])));
				String name3 = "HealthImg";
				mpEntity.addPart(name3, new FileBody(new File(imageURL[2])));
				String name4 = "CookImg";
				mpEntity.addPart(name4, new FileBody(new File(imageURL[3])));
				String name5 = "LevelImg";
				mpEntity.addPart(name5, new FileBody(new File(imageURL[4])));

				httppost.setEntity(mpEntity);
				System.out.println("executing request "
						+ httppost.getRequestLine());
				try {
					HttpResponse response = httpclient.execute(httppost);

					HttpEntity resEntity = response.getEntity();

					System.out.println(response.getStatusLine());// 通信Ok
					String json = "";
					String result = "";
					if (resEntity != null) {

						json = EntityUtils.toString(resEntity, "utf-8");
						JSONObject p = null;
						try {
							p = new JSONObject(json);
							result = p.getString("提示");
							System.out.println(result);
							Message message = new Message();
							Bundle bundle = new Bundle();
							bundle.putString("result", result);
							message.setData(bundle);// bundle传值，耗时，效率低
							message.what = 1;// 标志是哪个线程传数据
							handler.sendMessage(message);// 发送message信息

						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(e);
						}
					}
					if (resEntity != null) {
						resEntity.consumeContent();
					}

				} catch (Exception e) {
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("result", "亲，上传不成功？请再尝试一次");
					message.setData(bundle);// bundle传值，耗时，效率低
					message.what = 1;// 标志是哪个线程传数据
					handler.sendMessage(message);// 发送message信息
				}

			}

			httpclient.getConnectionManager().shutdown();
		}

	}

	private IDCard IDcard = new IDCard();// 身份证判断

	/*
	 * 厨师个人上传
	 */
	private void initDatas() {

		//
		String sfzID = shenfenzheng.getText().toString();
		sfzID = sfzID.toUpperCase();
		String card;
		try {
			card = IDcard.IDCardValidate(sfzID);
			if (!"".equals(card)) {
				Toast.makeText(ChuShiJiaMeng.this, card, Toast.LENGTH_LONG)
						.show();
				ld.closeDialog();
				return;

			}

		} catch (ParseException e1) {
			System.out.println("身份证号验证出错");
			e1.printStackTrace();
		}

		if (imageURL[0] == null || "".equals(imageURL[0])) {

			Toast.makeText(ChuShiJiaMeng.this, "请上传身份证正面", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;

		} else if (imageURL[1] == null || "".equals(imageURL[1])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传身份证反面", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		} else if (imageURL[2] == null || "".equals(imageURL[2])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传健康证", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		} else if (imageURL[3] == null || "".equals(imageURL[3])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传厨师证", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		} else if (imageURL[4] == null || "".equals(imageURL[4])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传等级证", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		}
		;

		for (int i = 0; i < imageURL.length; i++) {
			imageURL[i] = imageURL[i].replace("file://", "");
		}

		mythread = new MyThread();
		thread = new Thread(mythread);
		thread.start();
	}

	/*
	 * 厨师团队上传
	 */

	private void Cook_Tuan() {

		// String age = cook_age.getText().toString();
		// String name = zhuchuName.getText().toString();
		// String num = CookNum.getText().toString();
		sfzID = shenfenzheng.getText().toString();
		// String ChengLitime = chengli.getText().toString();
		sfzID = sfzID.toUpperCase();
		String card;
		try {
			card = IDcard.IDCardValidate(sfzID);
			if (!"".equals(card)) {
				Toast.makeText(ChuShiJiaMeng.this, card, Toast.LENGTH_LONG)
						.show();
				ld.closeDialog();
				return;

			}

		} catch (ParseException e1) {
			System.out.println("身份证号验证出错");
			e1.printStackTrace();
		}

		if (imageURL[0] == null || "".equals(imageURL[0])) {

			Toast.makeText(ChuShiJiaMeng.this, "请上传身份证正面", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;

		} else if (imageURL[1] == null || "".equals(imageURL[1])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传身份证反面", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		} else if (imageURL[2] == null || "".equals(imageURL[2])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传健康证", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		} else if (imageURL[3] == null || "".equals(imageURL[3])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传厨师证", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		} else if (imageURL[4] == null || "".equals(imageURL[4])) {
			Toast.makeText(ChuShiJiaMeng.this, "请上传等级证", Toast.LENGTH_LONG)
					.show();
			ld.closeDialog();
			return;
		}
		;

		for (int i = 0; i < imageURL.length; i++) {
			imageURL[i] = imageURL[i].replace("file://", "");
		}

		mythread = new MyThread();
		thread = new Thread(mythread);
		thread.start();
		System.out.println(result + "sadanksjfjka");

	}

	// data
	private static final String TAG = "FragmentNotify";
	private static final String IMAGE_FILE_LOCATION = "file://"
			+ getSdcardDir();
	private Uri imageUri;// to store the big bitmap

	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1.5);
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
			bitmap = BitmapFactory.decodeStream(ChuShiJiaMeng.this
					.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 1:
			Log.d(TAG, "TAKE_BIG_PICTURE: data = " + data);// it seems to be
															// null
			// TODO sent to crop

			cropImageUri(imageUri, 100, 100, 2);

			break;
		case 2:// from crop_big_picture
			Log.d(TAG, "CROP_BIG_PICTURE: data = " + data);// it seems to be
			if (imageUri != null & resultCode == RESULT_OK) {
				Bitmap bitmap = decodeUriAsBitmap(imageUri);
				image.setImageBitmap(bitmap);
				System.out.println(flag + "aaa");
				imageURL[flag] = IMAGE_FILE_LOCATION + "photo" + flag + ".jpg";
			}
			break;
		case 3:
			Log.d(TAG, "CHOOSE_BIG_PICTURE: data = " + data);// it seems to be
			System.out.println(imageUri + "sadkaf");
			if (imageUri != null & resultCode == RESULT_OK) {
				Bitmap bitmap = decodeUriAsBitmap(imageUri);
				System.out.println(flag + "bbb");
				image.setImageBitmap(bitmap);
				imageURL[flag] = IMAGE_FILE_LOCATION + "photo" + flag + ".jpg";
			}
			break;

		case REQUESTCODE_PICK:// 直接从相册获取
			try {
				startPhotoZoom(data.getData());
			} catch (NullPointerException e) {
				e.printStackTrace();// 用户点击取消操作
			}
			break;

		case REQUESTCODE_CUTTING:// 取得裁剪后的图片
			if (data != null) {
				setPicToView(data);

			}
			break;

		default:
			break;
		}
	}

	private String urlpath;

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1.5);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	private Context mContext;

	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			// 取得SDCard图片路径做显示
			Bitmap photo = extras.getParcelable("data");
//			Drawable drawable = new BitmapDrawable(null, photo);
			urlpath = Savefile.saveFile(mContext, "photophoto" + flag + ".jpg",
					photo);
			imageURL[flag] = IMAGE_FILE_LOCATION + "photo" + flag + ".jpg";
			image.setImageBitmap(photo);

			// 新线程后台上传服务端
			// pd = ProgressDialog.show(mContext, null, "正在上传图片，请稍候...");
			// new Thread(uploadImageRunnable).start();
		}
	}

	// =======================================================================================================//
	private static String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			System.out.println("有SD卡");
			return Environment.getExternalStorageDirectory().toString()
					+ "/EFHM/cache/photo";
		} else {
			System.out.println("没有");
			return "/data/data/com.example.esycab/cache/photo";
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

	private ImageView image;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - HomeActivity.dianJiShiJian > 500) {

				HomeActivity.dianJiShiJian = System.currentTimeMillis();
				File f = new File(Environment.getExternalStorageDirectory()
						.toString() + "/EFHM/cache/photophoto-1.jpg");
				File f1 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/EFHM/cache/photophoto0.jpg");
				File f2 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/EFHM/cache/photophoto1.jpg");
				File f3 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/EFHM/cache/photophoto2.jpg");
				File f4 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/EFHM/cache/photophoto3.jpg");
				File f5 = new File(Environment.getExternalStorageDirectory()
						.toString() + "/EFHM/cache/photophoto4.jpg");
				f.delete();
				f1.delete();
				f2.delete();
				f3.delete();
				f4.delete();
				f5.delete();
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void click() {

		final AlertDialog alertDialog = new AlertDialog.Builder(
				ChuShiJiaMeng.this).create();
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.dialog_main_info);
		photo = (LinearLayout) window.findViewById(R.id.photo);
		photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				imageUri = Uri.parse(IMAGE_FILE_LOCATION + "photo" + flag
						+ ".jpg");

				String a = android.os.Build.MANUFACTURER;
				a = a.toUpperCase();
				// if ("MEIZU".equals(a)) {

				Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
				// 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
				pickIntent
						.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
				startActivityForResult(pickIntent, REQUESTCODE_PICK);
				alertDialog.dismiss();

			}
		});
		camera = (LinearLayout) window.findViewById(R.id.camera);
		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				imageUri = Uri.parse(IMAGE_FILE_LOCATION + "photo" + flag
						+ ".jpg");
				Intent intent = null;
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 1);
				alertDialog.dismiss();

			}

		});

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.sfzzm:
			if (initDirs()) {
				click();
				image = (ImageView) findViewById(R.id.sfzzm);
				flag = 0;
			}
			break;
		case R.id.sfzfm:
			if (initDirs()) {
				click();
				image = (ImageView) findViewById(R.id.sfzfm);
				flag = 1;
			}
			break;
		case R.id.jkz:
			if (initDirs()) {
				click();
				image = (ImageView) findViewById(R.id.jkz);
				flag = 2;
			}
			break;
		case R.id.csz:
			if (initDirs()) {
				click();
				image = (ImageView) findViewById(R.id.csz);
				flag = 3;
			}
			break;
		case R.id.djz:
			if (initDirs()) {
				click();
				image = (ImageView) findViewById(R.id.djz);
				flag = 4;
			}
			break;

		}

	}

}
