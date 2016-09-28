package com.example.administrator.net;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Message;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.utils.LoadingDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.PreferencesCookieStore;

import rx.Subscriber;


/**
 * 快速开发框架XUtils帮助类，此类封装了该框架的各种操作
 *
 * @author Shyky
 * @date 2014-11-19
 */
public class XUtilsHelper {

    private Context context;
    private String url;
    /**
     * 返回文本的编码， 默认编码UTF-8
     */
    private HttpUtils httpUtils;
    private LoadingDialog loadingDialog;
    private ProgressDialog progressDialog;
    /**
     * 请求参数，默认编码UTF-8
     */
    private RequestParams params;
    private String filename;

    /**
     * 构造方法
     *
     * @param context
     *            用于程序上下文，必须用当前Activity的this对象，否则报错
     * @param url
     *            网络资源地址
     * @ param handler
     *            消息处理对象，用于请求完成后的怎么处理返回的结果数据
     */
    public XUtilsHelper(Context context, String url,int isnew) {
        this.context = context;
        try {
            // 保存网络资源文件名，要在转码之前保存，否则是乱码
            filename = url.substring(url.lastIndexOf("/") + 1, url.length());
            // 解决中文乱码问题，地址中有中文字符造成乱码问题
            String old_url = URLEncoder.encode(url, "GBK");
            // 替换地址中的特殊字符
            String new_url = old_url.replace("%3A", ":").replace("%2F", "/")
                    .replace("%3F", "?").replace("%3D", "=")
                    .replace("%26", "&").replace("%2C", ",")
                    .replace("%20", " ").replace("+", "%20")
                    .replace("%2B", "+").replace("%23", "#")
                    .replace("#", "%23");
            this.url = new_url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.httpUtils = XutilsHttpClient.getInstence(context);
if(isnew==1){
    this.url="http://120.27.141.95:8086/ashx/"+url;
}else{
    this.url="http://120.27.141.95:8221/ashx/"+url;
}
//        http://120.27.141.95:8086/ashx/CooksHandler.ashx?Action=GroupAndSingleInfo
        this.progressDialog = new ProgressDialog(context);
        this.params = new RequestParams("GBK"); // 编码与服务器端字符编码一致为gbk
    }

    public void sendGet(Subscriber subscriber) {
        this.loadingDialog = new LoadingDialog(context,"正在努力加载！");
        loadingDialog.showDialog();
        httpUtils.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        loadingDialog.closeDialog();
                        subscriber.onError(arg0);
                        arg0.printStackTrace();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (arg0.statusCode == 200) {
                            loadingDialog.closeDialog();
                        subscriber.onNext(arg0.result);
                        }
                    }
                });
    }


    /**
     * POST方式请求服务器资源
     *
     * @ param  dialogTitle
     *            加载中对话框显示标题文字
     * @ param  dialogNotingTitle
     *            提示对话框标题文字
     */
    public void sendPost(RequestParams params,Subscriber subscriber) {
        this.loadingDialog = new LoadingDialog(context,"正在努力加载！");
        loadingDialog.showDialog();
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        if (arg0.statusCode == 200) {
                                loadingDialog.closeDialog();
                            if(null!= subscriber){
                        subscriber.onNext(arg0.result);}
                        }
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                            loadingDialog.closeDialog();
                        subscriber.onError(arg0);
                        arg0.printStackTrace();
                    }
                });
    }

    /**
     * 上传文件到服务器
     *
     * @param param
     *            提交参数名称
     * @param file
     *            要上传的文件对象
     */
    public void uploadFile(String param, File file) {
        progressDialog.setTitle("上传文件中，请稍等...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        // 设置进度条风格，风格为水平进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        params.addBodyParameter(param, file);
        httpUtils.send(HttpRequest.HttpMethod.POST, url, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
                        progressDialog.setIndeterminate(false);
                        progressDialog.setProgress((int) current);
                        progressDialog.setMax((int) total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        System.out.println(arg0.statusCode);
                        System.out.println(arg0.result);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        progressDialog.dismiss();
                        arg0.printStackTrace();
                    }
                });
    }

    /**
     * 从服务器上下载文件保存到系统磁盘上
     *
     * @param saveLocation
     *            下载的文件保存路径
     * @param downloadBtn
     *            触发下载操作的控件按钮，用于设置下载进度情况
     */
    public void downloadFile(String saveLocation, final Button downloadBtn) {
        httpUtils.download(url, saveLocation + filename,
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        downloadBtn.setText("连接服务器中...");
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        DecimalFormat df = new DecimalFormat("#.##");
                        downloadBtn.setText("下载中... "
                                + df.format((double) current / 1024 / 1024)
                                + "MB/"
                                + df.format((double) total / 1024 / 1024)
                                + "MB");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        downloadBtn.setText("打开文件");
                        Toast.makeText(context, "下载成功！文件（"
                                + arg0.result.getAbsolutePath()
                                + "）保存在内部存储的Educ文件夹下。",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        progressDialog.dismiss();
                        arg0.printStackTrace();
                        Toast.makeText(context, "下载失败，请重试！",Toast.LENGTH_SHORT).show();
                        downloadBtn.setText("下载附件");
                    }
                });
    }

    /**
     * 从服务器上下载文件保存到系统磁盘上，此方法会弹出进度对话框显示下载进度信息（
     * 有的需要知道文件是否下载完成，如果下载完成返回的是改文件在磁盘中的完整路径）
     *
     * @param saveLocation
     *            下载的文件保存路径
     */
    public void downloadFile(String saveLocation) {
        progressDialog.setTitle("下载中，请稍等...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        // 设置进度条风格，风格为水平进度条
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        progressDialog.setIndeterminate(false);
        httpUtils.download(url, saveLocation + filename,
                new RequestCallBack<File>() {

                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        progressDialog.setProgress((int) current);
                        progressDialog.setMax((int) total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> arg0) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "下载成功！文件（"
                                + arg0.result.getAbsolutePath()
                                + "）保存在内部存储的Educ文件夹下。",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        progressDialog.dismiss();
                        arg0.printStackTrace();
                        Toast.makeText(context, "下载失败，请重试！",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

/**
 * 单例模式获取HttpUtils对象
 *
 * @author Shyky
 * @date 2014-11-19
 */
class XutilsHttpClient {

    private static HttpUtils client;

    /**
     * 单例模式获取实例对象
     *
     * @param context
     *            应用程序上下文
     * @return HttpUtils对象实例
     */
    public synchronized static HttpUtils getInstence(Context context) {
        if (client == null) {
            // 设置请求超时时间为10秒
            client = new HttpUtils(1000 * 10);
            client.configSoTimeout(1000 * 10);
            client.configTimeout(1000*10);
            client.configResponseTextCharset("UTF-8");
            // 保存服务器端(Session)的Cookie
            PreferencesCookieStore cookieStore = new PreferencesCookieStore(
                    context);
            cookieStore.clear(); // 清除原来的cookie
            client.configCookieStore(cookieStore);
        }
        return client;
    }

}