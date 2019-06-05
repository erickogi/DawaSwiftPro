package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class ChargeAccountEvent extends BaseRequestEvent {
    public String type;
    public String stripeToken;
    public float amount;
    public ChargeAccountEvent(String type,String stripeToken,float amount) {
        super(new ChargeAccountResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue(),"TIMEOUT"));
        this.type = type;
        this.stripeToken = stripeToken;
        this.amount = amount;
    }
}
