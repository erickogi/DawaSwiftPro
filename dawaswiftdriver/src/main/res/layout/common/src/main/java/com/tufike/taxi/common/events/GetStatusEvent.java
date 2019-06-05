package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class GetStatusEvent extends BaseRequestEvent {
    public GetStatusEvent() {
        super(new GetStatusResultEvent(ServerResponse.REQUEST_TIMEOUT));
    }
}
