package com.tufike.taxi.common.utils;

public enum ServerResponse {
    REQUEST_TIMEOUT(408),
    OK(200),
    UNKNOWN_ERROR(666),
    PAYMENT_ERROR(400),
    NOT_FOUND(404),
    UPDATE_APP(410),
    PROFILE_DISABLED(411),
    PROFILE_BLOCKED(412),
    DRIVER_IS_OFFLINE(413),
    NO_CLOSE_FOUND(303),
    NO_ROUTE_FOUND(304),
    COUPON_NOT_FOUND(704),
    COUPON_USED(703),
    COUPON_EXPIRED(702),
    HAS_PAYMENT_REQUEST(901),
    NO_SUFFICIENT_AMOUNT(902),
    NO_SUFFICIENT_CREDIT(903);
    private int value;
    ServerResponse(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static ServerResponse get(int code) {
        for(ServerResponse s : values()) {
            if(s.value == code) return s;
        }
        return UNKNOWN_ERROR;
    }
}
