package com.example.administrator.net;

import com.example.administrator.entity.ToppicBean;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by WangYueli on 2016/9/29.
 */

public interface INetServices {
    //http://120.27.141.95:8221/ashx/   Other.ashx?Function=GetTopPicture
    String URL_A="ashx/";
    @GET("{type}"+".ashx?")
    Call<ToppicBean> getpic(@Path("type") String type, @QueryMap Map<String, String> params);
    @GET("{type}"+".ashx?")
    Call<ResponseBody> getDataFromLOLNet(@Path("type") String type, @QueryMap Map<String, String> params);

}
