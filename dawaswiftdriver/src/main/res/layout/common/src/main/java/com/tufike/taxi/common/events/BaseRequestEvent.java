package com.tufike.taxi.common.events;

import android.os.CountDownTimer;
import android.util.Log;

import com.tufike.taxi.common.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

public class BaseRequestEvent {
    private BaseResultEvent resultEvent;
    CountDownTimer timer;
    public BaseRequestEvent() {
    }
    public BaseRequestEvent(BaseResultEvent _resultEvent){
        resultEvent = _resultEvent;
        timer = new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("Time Passing","1");
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(resultEvent);
            }
        }.start();
        CommonUtils.currentTimer = timer;
    }
}
