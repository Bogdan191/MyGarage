package com.example.mygarage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class UpdateDialog extends AppCompatDialogFragment {
    private EditText editTextUpdateCarOdometer;
    private EditText editTextUpdateCarColor;
    private EditText editTextUpdateCarPrice;
    private UpdateCarListener listener;






    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String typeOfUpdate = this.getArguments().getString("type_of_update");

        switch(typeOfUpdate) {
            case "EDIT_CAR":
                builder = setTheDialogForCarEdit();
                return builder.create();
            case "UPDATE_ITP":
                builder = setTheDialogForITPUpdate();
                return builder.create();
            case "UPDATE_INSURANCE":
                builder = setTheDialogForInsuranceUpdate();
                return builder.create();

            case "UPDATE_ROAD_TAX":
                builder = setTheDialogForRoadTaxUpdate();
                return builder.create();
            case "ADD_SERVICE_INFO":
                builder = setTheDialogForAddNewServiceInfo();
                return builder.create();
        }

        return builder.create();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (UpdateCarListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Dialog listener");
        }
    }

    public interface UpdateCarListener {
        void saveNewDataForCar(String carOometer, String carColor, String carPrice);

        void saveDocsNewEndDate(String newDate, String typeUpdate);

        void saveNewServiceInfo(String date, String typeOfServiceWork, String details);
    }





    private AlertDialog.Builder setTheDialogForCarEdit() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title = "Actualizeaza datele masinii dumneavoastra";
        String negativeButton = "Inchide";
        String positiveButton = "Salveaza";
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_update_car, null);
        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String carOdometer = editTextUpdateCarOdometer.getText().toString();
                        String carColor = editTextUpdateCarColor.getText().toString();
                        String carPrice = editTextUpdateCarPrice.getText().toString();
                        listener.saveNewDataForCar(carOdometer, carColor, carPrice);
                    }
                });

        editTextUpdateCarOdometer = view.findViewById(R.id.update_car_odometer);
        editTextUpdateCarColor = view.findViewById(R.id.update_car_color);
        editTextUpdateCarPrice = view.findViewById(R.id.update_car_price);

        return builder;
    }

    private AlertDialog.Builder setTheDialogForITPUpdate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title =  "Seteaza noua data la care expira ITP-ul masinii dumneavoastra";
        String negativeButton = "Inchide";
        String positiveButton = "Salveaza";
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_update_car_itp, null);

        DatePicker dp_newDate = view.findViewById(R.id.datePickerUpdateCarITPEndDate);


        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newITPEndDate =  dp_newDate.getDayOfMonth() + "/" + (dp_newDate.getMonth() + 1) + "/" + dp_newDate.getYear();
                        listener.saveDocsNewEndDate(newITPEndDate, "UPDATE_ITP");
                    }
                });

        return builder;
    }

    private AlertDialog.Builder setTheDialogForInsuranceUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title =  "Seteaza noua data la care expira Asigurarea masinii dumneavoastra";
        String negativeButton = "Inchide";
        String positiveButton = "Salveaza";
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_update_car_insurance, null);

        DatePicker dp_newDate = view.findViewById(R.id.datePickerUpdateCarInsuranceEndDate);


        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newITPEndDate =  dp_newDate.getDayOfMonth() + "/" + (dp_newDate.getMonth() + 1) + "/" + dp_newDate.getYear();
                        listener.saveDocsNewEndDate(newITPEndDate, "UPDATE_INSURANCE");
                    }
                });

        return builder;
    }
    private AlertDialog.Builder setTheDialogForRoadTaxUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title =  "Seteaza noua data la care expira Rovinieta masinii dumneavoastra";
        String negativeButton = "Inchide";
        String positiveButton = "Salveaza";
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_car_road_tax, null);

        DatePicker dp_newDate = view.findViewById(R.id.datePickerUpdateCarRoadTaxEndDate);


        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newITPEndDate =  dp_newDate.getDayOfMonth() + "/" + (dp_newDate.getMonth() + 1) + "/" + dp_newDate.getYear();
                        listener.saveDocsNewEndDate(newITPEndDate, "UPDATE_ROAD_TAX");
                    }
                });

        return builder;
    }

    private AlertDialog.Builder setTheDialogForAddNewServiceInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title =  "Adauga o noua intrare service";
        String negativeButton = "Renunta";
        String positiveButton = "Salveaza";
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_add_service_info, null);

        DatePicker dp_newDate = view.findViewById(R.id.datePickerAddServiceInfoCar);
        EditText et_service_info = view.findViewById(R.id.addServiceInfoCarDetails);
        Spinner spinner_type_Of_Work = view.findViewById(R.id.spinnerChooseTypeOfServiceWorkAdd);

        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date =  dp_newDate.getDayOfMonth() + "/" + (dp_newDate.getMonth() + 1) + "/" + dp_newDate.getYear();
                        String typeOfServiceWork = spinner_type_Of_Work.getSelectedItem().toString();
                        listener.saveNewServiceInfo(date, typeOfServiceWork, et_service_info.getText().toString());
                    }
                });

        return builder;
    }

}