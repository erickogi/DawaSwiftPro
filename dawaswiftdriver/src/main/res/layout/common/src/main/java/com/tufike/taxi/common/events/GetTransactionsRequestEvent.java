package com.tufike.taxi.common.events;

public class GetTransactionsRequestEvent extends BaseRequestEvent {
    public GetTransactionsRequestEvent() {
        super(new GetTransactionsResultEvent());
    }
}
