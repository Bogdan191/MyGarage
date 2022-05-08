package com.example.mygarage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class UpdateDialog extends AppCompatDialogFragment {
    private EditText editTextUpdateCarOdometer;
    private EditText editTextUpdateCarColor;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_update_car, null);

        builder.setView(view)
                .setTitle("Actualizati datele masinii")
                .setNegativeButton("inchide", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String carOdometer = editTextUpdateCarOdometer.getText().toString();
                        String carColor = editTextUpdateCarColor.getText().toString();
                        listener.applyTexts(carOdometer, carColor);
                    }
                });

        editTextUpdateCarOdometer = view.findViewById(R.id.update_car_odometer);
        editTextUpdateCarColor = view.findViewById(R.id.update_car_color);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Dialog listener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String carOometer, String carColor);
    }

}