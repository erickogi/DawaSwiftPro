package com.tufike.taxi.common.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tufike.taxi.common.R;

import java.io.InputStream;

public class LoadingDialog {

    static Dialog dialog;
    static InputStream stream = null;

    public static void show(Context context, String message) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.ALPHA_CHANGED);
        TextView txtmessage = (TextView) dialog.findViewById(R.id.waiting_message);


        txtmessage.setText(message);
        dialog.show();
    }
    public static void showWithTimer(Context context, String message, int Seconds) {
        show(context,message);
        new CountDownTimer(Seconds * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                dismiss();
            }
        }.start();
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}
