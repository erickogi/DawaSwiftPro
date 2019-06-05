package com.tufike.taxi.common.events;

import com.tufike.taxi.common.utils.ServerResponse;

public class WriteComplaintEvent extends BaseRequestEvent {
    public long travelId;
    public String subject;
    public String content;
    public WriteComplaintEvent(long travelId, String subject, String content) {
        super(new WriteComplaintResultEvent(ServerResponse.REQUEST_TIMEOUT.getValue()));
        this.travelId = travelId;
        this.subject = subject;
        this.content = content;
    }
}
