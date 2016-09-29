package com.example.administrator.net;

import android.content.Context;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * ====================================
 * 作者：王月丽
 * 版本：1.0
 * 创建日期：2016/9/29 10:00
 * 创建描述: Retrofit工具类，提供了 Bean获取和String字符串获取
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
//        params.put(Key.i_, "0");
//        params.put(Key.t_, String.valueOf(t_value));
//        params.put(Key.p_, String.valueOf(p_value));
        INetServices service = retrofit.create(INetServices.class);
        Call<ResponseBody> dataFromLOLNet = service.getDataFromLOLNet(type, params);
        dataFromLOLNet.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String resultStr = response.body().string();
                        //如果是VideoFragmentBean，就存到本地一份数据
//                        if ("VideoListBean".equals(clzz.getSimpleName())) {
//                            File cacheDir = context.getCacheDir();
//                            File file = new File(cacheDir, clzz.getSimpleName() + ".txt");
//                            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//                            writer.write(resultStr);
//                            writer.flush();
//                            writer.close();
//                        }
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
     *
//     * @param serviceType 服务类型，从Type类中获取
     * @param type        类型，从Type类中获取
     * @param params      请求参数（除去 i_,t_,p_）
     */
    public void getStringDataFromNet(String type, Map<String, String> params, final CallBack<String> callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrl.URL_LOL_BASE)
                .build();
        if (params == null) {
            params = new HashMap<>();
        }
//        long t_value = com.wyl.mytextlayout.MyUrl.get_t_Value();
//        long p_value = com.wyl.mytextlayout.MyUrl.get_p_Value(t_value);
//        params.put(Key.i_, "0");
//        params.put(Key.t_, String.valueOf(t_value));
//        params.put(Key.p_, String.valueOf(p_value));
        INetServices service = retrofit.create(INetServices.class);
        Call<ResponseBody> dataFromLOLNet = service.getDataFromLOLNet( type, params);
        dataFromLOLNet.enqueue(new Callback<ResponseBody>() {
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
//    Map<String, String> map = new HashMap<>();
//    map.put("Function", "GetTopPicture");
//    TopPicUtil.getBeanDataFromNet("Other", map, ToppicBean.class, new RetrofitUtil.CallBack<ToppicBean>() {
//        @Override
//        public void onLoadingDataComplete(ToppicBean body) {
//            l=body.getTop();
//            Toast.makeText(getActivity(),"11111"+l.toString(),Toast.LENGTH_SHORT).show();
//            for (int i = 0; i <20 ; i++) {
//                list.add(new DataBean("item"+i,l.get(i%4)));
//                Log.i("555555l.get(i%4)",l.get(i%4));
//            }
//            myRcvAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onLoadingDataFailed(Throwable t) {
//        }
//    });

}
