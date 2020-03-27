package com.ajith.silverprice;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class EditDialog extends AppCompatDialogFragment {

    private EditText editTextUsername, editTextPercent;
    private ExampleDialogListener listener;

    private String rate;
    private int percent;

    EditDialog(String rate, int percent) {
        this.rate = rate;
        this.percent = percent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.layout_dialog, null);

        Log.d("ajju", "onCreateDialog: "+rate +"  " + percent);

        builder.setView(view)
                .setTitle("Silver rate")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString();
                        String percent = editTextPercent.getText().toString();
                        listener.applyTexts(username, percent);
                    }
                });

        editTextUsername = view.findViewById(R.id.edit_username);
        editTextPercent = view.findViewById(R.id.edit_percent);

        editTextUsername.setText(rate);
        editTextPercent.setText(String.valueOf(percent));

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String percent);
    }
}
