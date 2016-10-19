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
    @GET("{type}"+".ashx?")
    Call<ToppicBean> getpic(@Path("type") String type, @QueryMap Map<String, String> params);
    //根据接口的格式自行串接get的参数，这地方这个@Path("type") String type可以理解成调用getDataFromNet方法时的参数传到@GET里 params里面放参数
    //http://120.27.141.95:8221/ashx/Other.ashx?Function=GetTopPicture 以这个为例  http://120.27.141.95:8221/ashx/ 可以写成一个常量
    @GET("{type}"+".ashx?")
    Call<ResponseBody> getDataFromNet(@Path("type") String type, @QueryMap Map<String, String> params);

}
