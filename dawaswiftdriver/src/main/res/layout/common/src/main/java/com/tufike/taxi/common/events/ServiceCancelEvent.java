package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class ServiceCancelEvent extends BaseRequestEvent {
    public ServiceCancelEvent(){
        super(new ServiceCallRequestResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));

    }
}
