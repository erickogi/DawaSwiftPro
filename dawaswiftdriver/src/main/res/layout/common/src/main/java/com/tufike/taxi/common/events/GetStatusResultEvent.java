package com.tufike.taxi.common.events;

import com.tufike.taxi.common.models.Travel;

public class GetStatusResultEvent extends BaseResultEvent {
    public Travel travel;
    public GetStatusResultEvent(Object... args){
        super(args);
        if(hasError())
            return;
        travel = Travel.fromJson(args[1].toString());
    }
}
