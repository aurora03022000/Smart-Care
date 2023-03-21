package com.example.smartcare;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditPatientInfoDialogBox extends AppCompatDialogFragment {

    LinearLayout edit, save, cancel, close;
    EditText name, birthdate, age, address, phonenumber, disease;

    EditText gender, room, device;

    private String ipAddress = "192.168.254.114:8080";

    String currentUser, patientID, returnPress;

    List<String> deviceIDList = new ArrayList<>();
    List<String> roomList = new ArrayList<>();

    String nameResponse;
    String genderResponse;
    String birthdateResponse;
    String ageResponse;
    String addressResponse;
    String numberResponse;
    String diseaseResponse;
    String roomResponse;
    String deviceResponse;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6, progressDialog7;
    ProgressDialog progressDialog8, progressDialog9;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_patient_info, null);
        name = view.findViewById(R.id.name);
        gender = view.findViewById(R.id.gender);
        birthdate = view.findViewById(R.id.birthdate);
        address = view.findViewById(R.id.address);
        phonenumber = view.findViewById(R.id.number);
        disease = view.findViewById(R.id.disease);
        room = view.findViewById(R.id.room);
        device = view.findViewById(R.id.device);
        phonenumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        room.setInputType(InputType.TYPE_CLASS_NUMBER);
        device.setInputType(InputType.TYPE_CLASS_NUMBER);

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();
                disease.clearFocus();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                View inflater = getLayoutInflater().inflate(R.layout.gender_selection, null);
                TextView male = inflater.findViewById(R.id.male);
                TextView female = inflater.findViewById(R.id.female);
                builder.setView(inflater);

                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.edit_patient_info, null);
                LinearLayout genderInputLine = view1.findViewById(R.id.genderInputLine);

                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                genderInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                                gender.setTextColor(Color.parseColor("#0EB021"));
                                gender.setText("Male");
                                alertDialog.dismiss();
                            }
                        }, 200);
                    }
                });

                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                genderInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                                gender.setTextColor(Color.parseColor("#0EB021"));
                                gender.setText("Female");
                                alertDialog.dismiss();
                            }
                        }, 200);
                    }
                });

            }
        });

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalendar();
            }

            private void updateCalendar(){
                String Format = "MMMM dd, yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                birthdate.setText(sdf.format(calendar.getTime()));
            }
        };

        birthdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name.clearFocus();
                age.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();
                new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        device.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                name.clearFocus();
                age.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceID();
                    }
                }, 2000);

            }
        });

        room.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                name.clearFocus();
                age.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRoom();
                    }
                }, 2000);
            }
        });

        name.setEnabled(false);
        gender.setEnabled(false);
        birthdate.setEnabled(false);
        age.setEnabled(false);
        address.setEnabled(false);
        phonenumber.setEnabled(false);
        disease.setEnabled(false);
        room.setEnabled(false);
        device.setEnabled(false);
        name.setTextColor(Color.parseColor("#6B6C6B"));
        gender.setTextColor(Color.parseColor("#6B6C6B"));
        birthdate.setTextColor(Color.parseColor("#6B6C6B"));
        age.setTextColor(Color.parseColor("#6B6C6B"));
        address.setTextColor(Color.parseColor("#6B6C6B"));
        phonenumber.setTextColor(Color.parseColor("#6B6C6B"));
        disease.setTextColor(Color.parseColor("#6B6C6B"));
        room.setTextColor(Color.parseColor("#6B6C6B"));
        device.setTextColor(Color.parseColor("#6B6C6B"));

        edit = view.findViewById(R.id.edit);
        save = view.findViewById(R.id.save);
        cancel = view.findViewById(R.id.cancel);
        close = view.findViewById(R.id.close);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                gender.setEnabled(true);
                birthdate.setEnabled(true);
                age.setEnabled(true);
                address.setEnabled(true);
                phonenumber.setEnabled(true);
                disease.setEnabled(true);
                room.setEnabled(true);
                device.setEnabled(true);

                name.setTextColor(Color.parseColor("#0EB021"));
                gender.setTextColor(Color.parseColor("#0EB021"));
                birthdate.setTextColor(Color.parseColor("#0EB021"));
                age.setTextColor(Color.parseColor("#0EB021"));
                address.setTextColor(Color.parseColor("#0EB021"));
                phonenumber.setTextColor(Color.parseColor("#0EB021"));
                disease.setTextColor(Color.parseColor("#0EB021"));
                room.setTextColor(Color.parseColor("#0EB021"));
                device.setTextColor(Color.parseColor("#0EB021"));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameValue = name.getText().toString();
                String genderValue = gender.getText().toString();
                String birthdateValue = birthdate.getText().toString();
                String ageValue = age.getText().toString();
                String addressValue = address.getText().toString();
                String numberValue = phonenumber.getText().toString();
                String diseaseValue = disease.getText().toString();
                String roomValue = room.getText().toString();
                String deviceValue = device.getText().toString();

                LinearLayout nameInputLine, genderInputLine, birthdateInputLine, ageInputLine, addressInputLine, numberInputLine, diseaseInputLine, roomInputLine, deviceInputLine;
                nameInputLine = view.findViewById(R.id.nameInputLine);
                genderInputLine = view.findViewById(R.id.genderInputLine);
                birthdateInputLine = view.findViewById(R.id.birthDateInputLine);
                ageInputLine = view.findViewById(R.id.ageInputLine);
                addressInputLine = view.findViewById(R.id.addressInputLine);
                numberInputLine = view.findViewById(R.id.numberInputLine);
                diseaseInputLine = view.findViewById(R.id.diseaseInputLine);
                roomInputLine = view.findViewById(R.id.roomInputLine);
                deviceInputLine = view.findViewById(R.id.deviceInputLine);

                if(nameValue.equals("") || ageValue.equals("") || addressValue.equals("") || numberValue.equals("")){
                    if(nameValue.equals("")){
                        nameInputLine.setBackgroundColor(Color.RED);
                        name.requestFocus();
                        Toast.makeText(getActivity(), "Field/s should not be empty", Toast.LENGTH_SHORT).show();


                        name.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                nameInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(name.getText().toString().isEmpty()){
                                    nameInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        age.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                ageInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(age.getText().toString().isEmpty()){
                                    ageInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        address.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(address.getText().toString().isEmpty()){
                                    addressInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        phonenumber.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(phonenumber.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        disease.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(disease.getText().toString().isEmpty()){
                                    diseaseInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                        if(ageValue.equals("")){
                            ageInputLine.setBackgroundColor(Color.RED);
                        }

                        if(addressValue.equals("")){
                            addressInputLine.setBackgroundColor(Color.RED);
                        }

                        if(numberValue.equals("")){
                            numberInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(ageValue.equals("")){

                        ageInputLine.setBackgroundColor(Color.RED);
                        age.requestFocus();
                        Toast.makeText(getActivity(), "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                        age.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                ageInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(age.getText().toString().isEmpty()){
                                    ageInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        address.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(address.getText().toString().isEmpty()){
                                    addressInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        phonenumber.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(phonenumber.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        disease.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(disease.getText().toString().isEmpty()){
                                    diseaseInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                        if(addressValue.equals("")){
                            addressInputLine.setBackgroundColor(Color.RED);
                        }

                        if(numberValue.equals("")){
                            numberInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(addressValue.equals("")){
                        addressInputLine.setBackgroundColor(Color.RED);
                        address.requestFocus();
                        Toast.makeText(getActivity(), "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                        address.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(address.getText().toString().isEmpty()){
                                    addressInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        phonenumber.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(phonenumber.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        disease.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(disease.getText().toString().isEmpty()){
                                    diseaseInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                        if(numberValue.equals("")){
                            numberInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(numberValue.equals("")){
                        numberInputLine.setBackgroundColor(Color.RED);
                        phonenumber.requestFocus();
                        Toast.makeText(getActivity(), "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                        phonenumber.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(phonenumber.getText().toString().isEmpty()){
                                    numberInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        disease.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(disease.getText().toString().isEmpty()){
                                    diseaseInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(diseaseValue.equals("")){
                        diseaseInputLine.setBackgroundColor(Color.RED);
                        disease.requestFocus();
                        Toast.makeText(getActivity(), "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                        disease.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(disease.getText().toString().isEmpty()){
                                    diseaseInputLine.setBackgroundColor(Color.RED);
                                }
                            }
                        });

                    }
                }

                else {
                    new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                            .setMessage("Are you sure you want to register this patient?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(getActivity());
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_bar);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    progressDialog.setCancelable(false);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            updatePatientInformation(nameValue, genderValue, birthdateValue, ageValue, addressValue, diseaseValue, numberValue, roomValue, deviceValue, patientID);
                                        }
                                    }, 2000);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);

                name.setEnabled(false);
                gender.setEnabled(false);
                birthdate.setEnabled(false);
                age.setEnabled(false);
                address.setEnabled(false);
                phonenumber.setEnabled(false);
                disease.setEnabled(false);
                room.setEnabled(false);
                device.setEnabled(false);

                name.setTextColor(Color.parseColor("#6B6C6B"));
                gender.setTextColor(Color.parseColor("#6B6C6B"));
                birthdate.setTextColor(Color.parseColor("#6B6C6B"));
                age.setTextColor(Color.parseColor("#6B6C6B"));
                address.setTextColor(Color.parseColor("#6B6C6B"));
                phonenumber.setTextColor(Color.parseColor("#6B6C6B"));
                disease.setTextColor(Color.parseColor("#6B6C6B"));
                room.setTextColor(Color.parseColor("#6B6C6B"));
                device.setTextColor(Color.parseColor("#6B6C6B"));

                LinearLayout nameInputLine, genderInputLine, birthdateInputLine, ageInputLine, addressInputLine, numberInputLine, diseaseInputLine, roomInputLine, deviceInputLine;
                nameInputLine = view.findViewById(R.id.nameInputLine);
                genderInputLine = view.findViewById(R.id.genderInputLine);
                birthdateInputLine = view.findViewById(R.id.birthDateInputLine);
                ageInputLine = view.findViewById(R.id.ageInputLine);
                addressInputLine = view.findViewById(R.id.addressInputLine);
                numberInputLine = view.findViewById(R.id.numberInputLine);
                diseaseInputLine = view.findViewById(R.id.diseaseInputLine);
                roomInputLine = view.findViewById(R.id.roomInputLine);
                deviceInputLine = view.findViewById(R.id.deviceInputLine);

                nameInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));

                name.setText(nameResponse);
                gender.setText(genderResponse);
                birthdate.setText(birthdateResponse);
                age.setText(ageResponse);
                address.setText(addressResponse);
                phonenumber.setText(numberResponse);
                disease.setText(numberResponse);
                room.setText(roomResponse);
                device.setText(deviceResponse);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        setCancelable(false);

        getCurrentID();

        return builder.create();

    }

    private void getDeviceID(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/deviceID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                if(!response.equals("failure")){
                    if(response.equals("")){
                        progressDialog.dismiss();
                        addDeviceID(response);
                    }

                    else {
                        String responseString = response.substring(0,3);

                        if(responseString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(getActivity());
                            progressDialog1.show();
                            progressDialog1.setContentView(R.layout.connectionfailure);
                            progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog1.dismiss();
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog.dismiss();
                            addDeviceID(response);
                        }
                    }

                }

                //kung ang response is failure
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog2 = new ProgressDialog(getActivity());
                    progressDialog2.show();
                    progressDialog2.setContentView(R.layout.failure_get_device_id);
                    progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog2.dismiss();
                        }
                    }, 2000);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog4 = new ProgressDialog(getActivity());
                progressDialog4.show();
                progressDialog4.setContentView(R.layout.connectionfailure);
                progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog4.dismiss();
                    }
                }, 2000);
            }
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getRoom(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/room.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog.dismiss();
                        addRoom(response);
                    }

                    else {
                        String responseString = response.substring(0,3);

                        if(responseString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(getActivity());
                            progressDialog1.show();
                            progressDialog1.setContentView(R.layout.connectionfailure);
                            progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog1.dismiss();
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog.dismiss();
                            addRoom(response);
                        }
                    }

                }


                //kung ang response is failure
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog3 = new ProgressDialog(getActivity());
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.failure_get_room_number);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog3.dismiss();
                        }
                    }, 2000);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(getActivity());
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                    }
                }, 2000);
            }
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    @DrawableRes
    public int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addRoom(String responseResultRoom) {
        // do something
        String roomNumberValue;
        int count = 0;

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        View inflater2 = getLayoutInflater().inflate(R.layout.room_selection, null);
        LinearLayout roomNumberLayout = inflater2.findViewById(R.id.roomNumberLayout);
        builder.setView(inflater2);

        android.app.AlertDialog alertDialog1 = builder.create();
        alertDialog1.show();

        for(;;){

            if(responseResultRoom.equals(" ") || responseResultRoom.equals("")){
                if(count == 0){
                    TextView roomNumber = new TextView(getActivity());
                    LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    roomNumber.setLayoutParams(paramsSharerName);
                    roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    roomNumber.setPadding(25,25,25,25);
                    roomNumber.setText("No room number available");
                    Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppinsmedium);
                    roomNumber.setTypeface(typeface);
                    roomNumber.setTextColor(Color.BLACK);
                    roomNumberLayout.addView(roomNumber);
                }
                break;
            }

            else{
                count++;

                roomNumberValue = responseResultRoom.replaceAll("\\`.*", "");
                String newWord = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord.trim();
                responseResultRoom = newWord;


                int resID = getSelectableItemBackground();

                TextView roomNumber = new TextView(getActivity());
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                roomNumber.setLayoutParams(paramsSharerName);
                roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                roomNumber.setPadding(25,25,25,25);
                roomNumber.setText(roomNumberValue);
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppinsmedium);
                roomNumber.setTypeface(typeface);
                roomNumber.setForeground(ContextCompat.getDrawable(getActivity(), resID));
//                roomNumber.setForeground(ContextCompat.getDrawable(getActivity(), resID));
                roomNumber.setTextColor(Color.BLACK);
                roomNumberLayout.addView(roomNumber);

                if(!responseResultRoom.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1
                    );
                    newLinearLayoutLine.setOrientation(LinearLayout.VERTICAL);
                    newLinearLayoutLine.setLayoutParams(params1Parent);
                    newLinearLayoutLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                    roomNumberLayout.addView(newLinearLayoutLine);
                }

                String roomNumberValue1 = roomNumberValue;
                roomNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        room.setTextColor(Color.parseColor("#0EB021"));
                        room.setText(roomNumberValue1);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog1.dismiss();
                            }
                        }, 200);
                    }
                });

            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addDeviceID(String responseResult) {
        // do something
        String datavalue;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        View inflater1 = getLayoutInflater().inflate(R.layout.deviceid_selection, null);
        LinearLayout deviceIDLayout = inflater1.findViewById(R.id.deviceIDLayout);
        builder.setView(inflater1);

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

        int  count = 0;

        for(;;){

            if(responseResult.equals(" ") || responseResult.equals("")){
                if(count == 0){
                    if(count == 0){
                        TextView deviceID = new TextView(getActivity());
                        LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        deviceID.setLayoutParams(paramsSharerName);
                        deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        deviceID.setPadding(25,25,25,25);
                        deviceID.setText("No device ID available");
                        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppinsmedium);
                        deviceID.setTypeface(typeface);
                        deviceID.setTextColor(Color.BLACK);
                        deviceIDLayout.addView(deviceID);
                    }
                }
                break;
            }

            else{
                count++;
                datavalue = responseResult.replaceAll("\\`.*", "");
                String newWord = responseResult.substring(responseResult.indexOf("`")+1);
                newWord.trim();
                responseResult = newWord;

                int resID = getSelectableItemBackground();

                TextView deviceID = new TextView(getActivity());
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                deviceID.setLayoutParams(paramsSharerName);
                deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                deviceID.setPadding(25,25,25,25);
                deviceID.setText(datavalue);
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppinsmedium);
                deviceID.setTypeface(typeface);
                deviceID.setForeground(ContextCompat.getDrawable(getActivity(), resID));
                deviceID.setTextColor(Color.BLACK);
                deviceIDLayout.addView(deviceID);

                if(!responseResult.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(getActivity());
                    LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1
                    );
                    newLinearLayoutLine.setOrientation(LinearLayout.VERTICAL);
                    newLinearLayoutLine.setLayoutParams(params1Parent);
                    newLinearLayoutLine.setBackgroundColor(Color.parseColor("#CED5CE"));
                    deviceIDLayout.addView(newLinearLayoutLine);
                }

                String deviceIDValue = datavalue;
                deviceID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        device.setTextColor(Color.parseColor("#0EB021"));
                        device.setText(deviceIDValue);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        }, 200);
                    }
                });

            }

        }

    }

    private void getCurrentID() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getCurrentUserID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!response.equals("failure")){

                            if(response.equals("")){
                                getCurrentPatientID();
                                currentUser = response;
                            }

                            else {
                                String firstChar = response.substring(0,1);

                                boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                                if(isNumeric){
                                    getCurrentPatientID();
                                    currentUser = response;
                                }

                                else {
                                    progressDialog.dismiss();

                                    progressDialog1 = new ProgressDialog(getActivity());
                                    progressDialog1.show();
                                    progressDialog1.setContentView(R.layout.connectionfailure);
                                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog1.dismiss();
                                        }
                                    }, 2000);
                                }
                            }

                        }
                        //kung ang response is success
                        else if(response.equals("failure")){
                            progressDialog.dismiss();

                            progressDialog5 = new ProgressDialog(getActivity());
                            progressDialog5.show();
                            progressDialog5.setContentView(R.layout.fail_current_user_id);
                            progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog5.dismiss();
                                }
                            }, 2000);
                        }

                        try {
                            // on below line we are parsing the response
                            // to json object to extract data from it.
                            JSONObject respObj = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 500);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(getActivity());
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getCurrentPatientID() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getCurrentPatientID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        patientID = response;
                        getPatientInformation(patientID);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            patientID = response;
                            getPatientInformation(patientID);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(getActivity());
                            progressDialog1.show();
                            progressDialog1.setContentView(R.layout.connectionfailure);
                            progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog1.dismiss();
                                }
                            }, 2000);
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog6 = new ProgressDialog(getActivity());
                    progressDialog6.show();
                    progressDialog6.setContentView(R.layout.fail_current_patient_id);
                    progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog6.dismiss();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(getActivity());
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getPatientInformation(String currentUserID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientInformation.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog.dismiss();
                        setPatientInfo(response);
                    }

                    else {
                        String responeString = response.substring(0,3);

                        if(responeString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog7 = new ProgressDialog(getActivity());
                            progressDialog7.show();
                            progressDialog7.setContentView(R.layout.failure_patient_info);
                            progressDialog7.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog7.dismiss();
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog.dismiss();
                            setPatientInfo(response);
                        }
                    }


                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog1 = new ProgressDialog(getActivity());
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.connectionfailure);
                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog1.dismiss();
                        }
                    }, 2000);//here
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(getActivity());
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                    }
                }, 2000);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("currentUserID", currentUserID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void updatePatientInformation(String name, String gender, String birthdate, String age, String address, String number, String disease, String room, String device, String patientID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updatePatientInformation.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){

                    progressDialog.dismiss();

                    progressDialog8 = new ProgressDialog(getActivity());
                    progressDialog8.show();
                    progressDialog8.setContentView(R.layout.success_update_patient_info);
                    progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog8.dismiss();
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View view = inflater.inflate(R.layout.edit_patient_info, null);
                            EditText name = view.findViewById(R.id.name);
                            EditText gender = view.findViewById(R.id.gender);
                            EditText birthdate = view.findViewById(R.id.birthdate);
                            EditText address = view.findViewById(R.id.address);
                            EditText phonenumber = view.findViewById(R.id.number);
                            EditText disease = view.findViewById(R.id.disease);
                            EditText room = view.findViewById(R.id.room);
                            EditText device = view.findViewById(R.id.device);

                            name.setEnabled(true);
                            gender.setEnabled(true);
                            birthdate.setEnabled(true);
                            address.setEnabled(true);
                            phonenumber.setEnabled(true);
                            disease.setEnabled(true);
                            room.setEnabled(true);
                            device.setEnabled(true);

                            edit.setVisibility(View.VISIBLE);
                            save.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);

                            cancel.performClick();

                            returnPress = returnPress + "d";

                            Intent intentAbout = new Intent(getActivity(), PatientInfo.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                        }
                    }, 2000);

                }

                else if(!response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog1 = new ProgressDialog(getActivity());
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.connectionfailure);
                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog1.dismiss();
                        }
                    }, 2000);
                }

                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog9 = new ProgressDialog(getActivity());
                    progressDialog9.show();
                    progressDialog9.setContentView(R.layout.failed_patient_info_update);
                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog9.dismiss();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(getActivity());
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog1.dismiss();
                    }
                }, 2000);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", name);
                params.put("gender", gender);
                params.put("birthdate", birthdate);
                params.put("age", age);
                params.put("address", address);
                params.put("number", number);
                params.put("disease", disease);
                params.put("room", room);
                params.put("device", device);
                params.put("patientID", patientID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void setPatientInfo(String responseResultRoom) {
        // do something


        for(;;){

            if(responseResultRoom.equals(" ") || responseResultRoom.equals("")){
                break;
            }

            else{
                nameResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord1 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord1.trim();
                responseResultRoom = newWord1;

                genderResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord2 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord2.trim();
                responseResultRoom = newWord2;

                birthdateResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord3 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord3.trim();
                responseResultRoom = newWord3;

                ageResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord4 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord4.trim();
                responseResultRoom = newWord4;

                addressResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord5 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord5.trim();
                responseResultRoom = newWord5;

                numberResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord6 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord6.trim();
                responseResultRoom = newWord6;

                diseaseResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord6a = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord6a.trim();
                responseResultRoom = newWord6a;

                roomResponse = responseResultRoom.replaceAll("\\`.*", "");
                String newWord7 = responseResultRoom.substring(responseResultRoom.indexOf("`")+1);
                newWord7.trim();
                responseResultRoom = newWord7;

                deviceResponse = responseResultRoom.replaceAll("\\%.*", "");
                String newWord8 = responseResultRoom.substring(responseResultRoom.indexOf("%")+1);
                newWord8.trim();
                responseResultRoom = newWord8;

                name.setText(nameResponse);
                gender.setText(genderResponse);
                birthdate.setText(birthdateResponse);
                age.setText(ageResponse);
                address.setText(addressResponse);
                phonenumber.setText(numberResponse);
                disease.setText(diseaseResponse);
                room.setText(roomResponse);
                device.setText(deviceResponse);

            }

        }

    }
}
