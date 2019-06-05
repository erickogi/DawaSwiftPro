package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class GetTravelsEvent extends BaseRequestEvent {
    public GetTravelsEvent() {
        super(new GetTravelsResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue(),null));
    }
}
