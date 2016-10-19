package com.example.administrator.net;

import android.content.Context;
import android.util.Log;

import com.example.administrator.entity.ToppicBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.id.list;


/**
 * ====================================
 * 作者：王月丽
 * 版本：1.0
 * 创建日期：2016/9/29 10:00
 * 创建描述: Retrofit工具类，提供了 Bean,list,和String字符串获取
 * 更新日期：
 * 更新描述：
 * ====================================
 */
public class RetrofitUtil<T> {
    private Context context;

    public RetrofitUtil(Context context) {
        this.context = context;
    }

    /**
     * 加载完数据的接口回调
     *
     * @param <T>
     */
    public interface CallBack<T> {

        /**
         * 访问成功后回调
         *
         * @param body 返回数据
         */
        void onLoadingDataComplete(T body);

        /**
         * 访问失败时回调
         *
         * @param t
         */
        void onLoadingDataFailed(Throwable t);
    }
    public interface CallBack2<T> {

        /**
         * 访问成功后回调
         *
         * @param list 返回数据
         */

        void onLoadListDataComplete(List<T> list);
        /**
         * 访问失败时回调
         *
         * @param t
         */
        void onLoadingDataFailed(Throwable t);
    }

    /**
     * 获取BaseURL为URL_LOL_BASE的Bean网络数据
     *
//     * @param serviceType 服务类型，从Type类中获取
     * @param type        类型，从Type类中获取
     * @param params      请求参数
     * @param callBack    回调接口
     */
    public void getBeanDataFromNet(String type, Map<String, String> params, final Class<T> clzz, final CallBack<T> callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.URL_LOL_BASE)
                .build();
        if (params == null) {
            params = new HashMap<>();
        }
        INetServices service = retrofit.create(INetServices.class);
        Call<ResponseBody> dataFromNet = service.getDataFromNet(type, params);
        dataFromNet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String resultStr = response.body().string();

                        Gson gson = new Gson();
                        T body = gson.fromJson(resultStr, clzz);
                        callBack.onLoadingDataComplete(body);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onLoadingDataFailed(t);
            }
        });
    }

    /**
     * 获取BaseURL为URL_LOL_BASE的字符串网络数据
     *@param clzz  根据需要传数据类型
     * @param type        类型，从Type类中获取
     * @param params      请求参数
     * @param callBack2  数据回调接口
     */
    public void getListDataFromNet(String type, Map<String, String> params,  final Class<T> clzz,final CallBack2<T> callBack2) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.URL_LOL_BASE)//MyUrl.URL_LOL_BASE就是http://120.27.141.95:8221/ashx/
                .build();
        if (params == null) {
            params = new HashMap<>();
        }
        INetServices service = retrofit.create(INetServices.class);//这个我也不太懂，就是这么用的
        Call<ResponseBody> dataFromLOLNet = service.getDataFromNet( type, params);
        dataFromLOLNet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) try {
                    String resultStr = response.body().string();
                    JSONArray ja = new JSONArray(resultStr.trim());
                    Gson gson = new Gson();
                    List<T> list = new ArrayList<>();
                JsonArray array = new JsonParser().parse(resultStr).getAsJsonArray();
                for (JsonElement jsonElement : array) {
                    list.add(gson.fromJson(jsonElement, clzz));
               }
                    callBack2.onLoadListDataComplete(list);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack2.onLoadingDataFailed(t);
            }
        });
    }
    /**
     * 获取字符串网络数据
     *
     * @param type        类型，从Type类中获取
     * @param params      请求参数
     */
    public void getStringDataFromNet(String type, Map<String, String> params, final CallBack<String> callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.URL_LOL_BASE)
                .build();
        if (params == null) {
            params = new HashMap<>();
        }
        INetServices service = retrofit.create(INetServices.class);
        Call<ResponseBody> dataFromNet = service.getDataFromNet( type, params);
        dataFromNet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        callBack.onLoadingDataComplete(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onLoadingDataFailed(t);
            }
        });
    }

}
//用法如下：
//http://120.27.141.95:8221/ashx/Other.ashx?Function=GetTopPicture 这是接口
//    Map<String, String> map = new HashMap<>();
//    map.put("Function", "GetTopPicture");
//    TopPicUtil.getBeanDataFromNet("Other", map, ToppicBean.class, new RetrofitUtil.CallBack<ToppicBean>() {
//        @Override
//        public void onLoadingDataComplete(ToppicBean body) {
//            l = body.getTop();
//            Log.i("onLoadingDataComplete: ", "o" + l.toString());
//            setpic();
//        }
//        @Override
//        public void onLoadingDataFailed(Throwable t) {
//        }
//    });
