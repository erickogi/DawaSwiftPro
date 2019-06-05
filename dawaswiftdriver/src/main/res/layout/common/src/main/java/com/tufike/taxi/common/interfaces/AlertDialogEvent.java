package com.tufike.taxi.common.interfaces;

import com.tufike.taxi.common.utils.AlertDialogBuilder;

public interface AlertDialogEvent {
    void onAnswerDialog(AlertDialogBuilder.DialogResult result);
}