package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class HideTravelEvent extends BaseRequestEvent {
    public Integer travelId;
    public HideTravelEvent(Integer travelId) {
        super(new HideTravelResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));
        this.travelId = travelId;
    }
}
