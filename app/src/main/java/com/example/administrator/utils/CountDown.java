package com.example.administrator.utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by WangYueli on 2016/10/14.
 */

public class CountDown {

        public static Observable<Integer> countdown(int time) {
            if (time < 0) time = 0;

            final int countTime = time;
            return Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(increaseTime -> countTime - increaseTime.intValue())
                    .take(countTime + 1);

        }
}
