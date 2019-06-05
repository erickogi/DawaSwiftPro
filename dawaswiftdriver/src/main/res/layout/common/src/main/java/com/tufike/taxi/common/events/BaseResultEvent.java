package com.tufike.taxi.common.events;

import android.content.Context;

import com.tufike.taxi.common.interfaces.AlertDialogEvent;
import com.tufike.taxi.common.utils.AlertDialogBuilder;
import com.tufike.taxi.common.utils.AlerterHelper;
import com.tufike.taxi.common.utils.CommonUtils;
import com.tufike.taxi.common.utils.ServerResponse;


public class BaseResultEvent {
    public ServerResponse response;
    public String message;

    public BaseResultEvent(Object... args) {
        if(args[0].toString().matches("-?\\d+(\\.\\d+)?")) {
            this.response = ServerResponse.get((int) args[0]);
            if ((int) args[0] == 666 && args.length > 1)
                this.message = args[1].toString();
        } else {
            this.response = ServerResponse.UNKNOWN_ERROR;
            this.message = args[0].toString();
        }
        if (CommonUtils.currentTimer != null)
            CommonUtils.currentTimer.cancel();
    }

    public BaseResultEvent(int code) {
        this.response = ServerResponse.get(code);
        if (CommonUtils.currentTimer != null)
            CommonUtils.currentTimer.cancel();
    }

    public BaseResultEvent(ServerResponse response) {
        this.response = response;
        if (CommonUtils.currentTimer != null)
            CommonUtils.currentTimer.cancel();
    }

    public BaseResultEvent(int code, String message) {
        this.response = ServerResponse.get(code);
        this.message = message;
        if (CommonUtils.currentTimer != null)
            CommonUtils.currentTimer.cancel();
    }

    public boolean hasError() {
        return response.getValue() != 200;
    }

    private String getErrorMessage(Context context) {
        if (message != null && !message.equals(""))
            return message;
        else {
            String message = response.name();
            int messageExists = context.getResources().getIdentifier("error_" + String.valueOf(response.getValue()), "string", context.getPackageName());
            if (messageExists > 0)
                message = context.getString(messageExists);
            return message;
        }
    }

    public void showError(Context context, AlertDialogEvent alertDialogEvent) {
        AlertDialogBuilder.show(context, getErrorMessage(context), AlertDialogBuilder.DialogButton.CANCEL_RETRY, alertDialogEvent);
    }

    public void showAlert(Context context) {
        AlerterHelper.showError(context, getErrorMessage(context));
    }
}
