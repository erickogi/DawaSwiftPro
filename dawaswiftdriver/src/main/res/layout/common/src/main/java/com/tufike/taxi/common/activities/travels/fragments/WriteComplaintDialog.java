package com.tufike.taxi.common.activities.travels.fragments;


import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tufike.taxi.common.R;
import com.tufike.taxi.common.databinding.DialogWriteComplaintBinding;
import com.tufike.taxi.common.events.WriteComplaintEvent;

public class WriteComplaintDialog extends DialogFragment {
    DialogWriteComplaintBinding binding;

    private onWriteComplaintInteractionListener mListener;

    public WriteComplaintDialog() {
        // Required empty public constructor
    }

    public View onCreateDialogView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_write_complaint, container, false);
        return binding.getRoot();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.write_complaint);
        View view = onCreateDialogView(getActivity().getLayoutInflater(), null, null);
        onViewCreated(view, null);
        alertDialogBuilder.setView(view);
        binding.textContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AlertDialog dialog = (AlertDialog) getDialog();
                if (s.toString().trim().length() == 0)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                else
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        alertDialogBuilder.setPositiveButton(getString(R.string.alert_ok), (dialog, which) -> {
            WriteComplaintEvent event = new WriteComplaintEvent(0,binding.textSubject.getText().toString(),binding.textContent.getText().toString());
            mListener.onSaveComplaintClicked(event);
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.alert_cancel), (dialog, which) -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onWriteComplaintInteractionListener) {
            mListener = (onWriteComplaintInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onEditAddressInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onWriteComplaintInteractionListener {
        void onSaveComplaintClicked(WriteComplaintEvent event);
    }
}
