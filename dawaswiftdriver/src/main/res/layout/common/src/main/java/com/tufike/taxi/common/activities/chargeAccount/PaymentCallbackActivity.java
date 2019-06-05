package com.tufike.taxi.common.activities.chargeAccount;

import android.os.Bundle;

import com.tufike.taxi.common.R;
import com.tufike.taxi.common.components.BaseActivity;
import com.tufike.taxi.common.events.ChargeAccountEvent;

public class PaymentCallbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_callback);
        eventBus.post(new ChargeAccountEvent("online","token",100));
    }
}
