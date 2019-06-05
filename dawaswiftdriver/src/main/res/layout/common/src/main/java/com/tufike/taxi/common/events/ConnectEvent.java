package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class ConnectEvent extends BaseRequestEvent {
    public String token;
    public ConnectEvent(String token){
        super(new ConnectResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));
        this.token = token;
    }
}
