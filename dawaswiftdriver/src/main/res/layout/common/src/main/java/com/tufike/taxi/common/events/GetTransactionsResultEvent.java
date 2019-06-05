package com.tufike.taxi.common.events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tufike.taxi.common.models.Transaction;
import com.tufike.taxi.common.utils.ServerResponse;

import java.lang.reflect.Type;
import java.util.List;

public class GetTransactionsResultEvent extends BaseResultEvent {
    public List<Transaction> transactions;

    public GetTransactionsResultEvent(){
        super(ServerResponse.REQUEST_TIMEOUT);
    }

    public GetTransactionsResultEvent(Object... args) {
        super(args);
        if(response != ServerResponse.OK)
            return;
        Type type = new TypeToken<List<Transaction>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(LatLng.class, new LatLngDeserializer());

        Gson customGson = gsonBuilder.create();
        this.transactions = customGson.fromJson(args[1].toString(),type);
    }
}
