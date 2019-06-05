package com.tufike.taxi.common.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tufike.taxi.common.R;
import com.tufike.taxi.common.interfaces.AlertDialogEvent;

public class AlertDialogBuilder {
    public enum DialogButton {
        OK_CANCEL, OK, CANCEL_RETRY
    }

    public enum DialogResult {
        CANCEL, OK, RETRY
    }

    public static void show(final Context context, final String message, final String title, DialogButton button, final AlertDialogEvent event) {
        MaterialDialog.Builder materialDialogBuilder = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.alert_ok);
        if (button == DialogButton.OK || button == DialogButton.OK_CANCEL) {
            String positiveText = context.getString(R.string.alert_ok);
            materialDialogBuilder.positiveText(positiveText);
            materialDialogBuilder.onPositive((dialog, which) -> {
                if (event != null) event.onAnswerDialog(DialogResult.OK);
            });
        }
        if (button == DialogButton.OK_CANCEL || button == DialogButton.CANCEL_RETRY) {
            String negativeText = context.getString(R.string.alert_cancel);
            materialDialogBuilder.negativeText(negativeText);
            /*materialDialogBuilder.onNegative((dialog, which) -> {
                if (event != null) event.onAnswerDialog(DialogResult.CANCEL);
            });*/
        }
        if (button == DialogButton.CANCEL_RETRY) {
            String positiveText = context.getString(R.string.alert_retry);
            materialDialogBuilder.positiveText(positiveText);
            materialDialogBuilder.onPositive((dialog, which) -> {
                if (event != null) event.onAnswerDialog(DialogResult.RETRY);
            });
        }
        materialDialogBuilder.cancelListener(dialogInterface -> {
            if (event != null) event.onAnswerDialog(DialogResult.CANCEL);
        });
        materialDialogBuilder.show();
    }

    public static void show(final Context context, final String message, DialogButton button, final AlertDialogEvent event) {
        show(context, message, context.getString(R.string.message_default_title), button, event);
    }

    public static void show(final Context context, final String message) {
        show(context, message, context.getString(R.string.message_default_title), DialogButton.OK, null);
    }
}
