package com.tufike.taxi.common.events;

public class AcceptOrderEvent {
    public long travelId;
    public Double cost;
    public AcceptOrderEvent(long travelId, Double cost){
        this.travelId = travelId;
        this.cost = cost;
    }
}
