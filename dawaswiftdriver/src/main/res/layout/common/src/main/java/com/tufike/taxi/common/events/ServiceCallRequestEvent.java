package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class ServiceCallRequestEvent extends BaseRequestEvent {
    public ServiceCallRequestEvent() {
        super(new ServiceCallRequestResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));
    }
}
