package com.example.administrator.net;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/25.
 */
public class Net {
    private String method;
    private RequestParams params;
    private String path;
    private Subscriber subscriber;
    private HttpUtils httpUtils;
    public Net(String path,RequestParams params,Subscriber subscriber) {
        this.params = params;
        this.path = path;
        this.subscriber = subscriber;
        httpUtils = new HttpUtils();
        httpPost(path,params,subscriber);
    }

    public Net(String path,Subscriber subscriber) {
        this.path = path;
        this.subscriber = subscriber;
        httpUtils = new HttpUtils();
        httpGet(path ,subscriber);
    }

    private void httpGet(String path, Subscriber subscriber){
        httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                subscriber.onNext(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                subscriber.onError(e);
            }
        });
    }
    private void httpPost(String path,RequestParams params,Subscriber subscriber){
        httpUtils.send(HttpRequest.HttpMethod.POST, path, params, new RequestCallBack<String>() {
          @Override
           public void onSuccess(ResponseInfo<String> responseInfo) {
                subscriber.onNext(responseInfo.result);
           }

         @Override
         public void onFailure(HttpException e, String s) {
                subscriber.onError(e);
          }
    });
}
}
