package com.tufike.taxi.common.events;

public class ConnectResultEvent extends BaseResultEvent {
    public ConnectResultEvent(int code) {
        super(code);
    }
    public ConnectResultEvent(int code,String message) {
        super(code,message);
    }
}
