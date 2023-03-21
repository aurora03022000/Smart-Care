package com.example.smartcare;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    private EditText nameInputRegister ,numberInputRegister,birthdateInputRegister, addressInputRegister, genderInputRegister;
    private EditText deviceIDInputRegister, roomInputRegister, diseaseInputRegister;
    private String number,name, birthdate, gender, address, deviceID;
    Calendar calendar;

    String currentUser, returnPress, firstTime, concatinatedReturnPress, copyOfReturnPress, shareOption;

    private String ipAddress = "192.168.254.114:8080";

    LinearLayout parentLayout, centerLayout, back, buttonRegister;

    ScrollView scrollView;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6;
    ProgressDialog progressDialog7, progressDialog8;

    String cancel = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        setContentView(R.layout.register);
        setUpToolbar();

        currentUser = getIntent().getStringExtra("USER_ID");
        returnPress = getIntent().getStringExtra("RETURN");

        copyOfReturnPress = returnPress;

        progressDialog2 = new ProgressDialog(Register.this);
        progressDialog2.show();
        progressDialog2.setContentView(R.layout.progress_bar);
        progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        progressDialog2.setCancelable(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getProfile(currentUser);
            }
        }, 500);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnPress = returnPress + "b";

                Intent intentMonitorPatient = new Intent(Register.this, Patient.class);
                intentMonitorPatient.putExtra("USER_ID", currentUser);
                intentMonitorPatient.putExtra("RETURN", returnPress);
                intentMonitorPatient.putExtra("FIRST_TIME", "no");
                startActivity(intentMonitorPatient);
                finish();
            }
        });


        nameInputRegister = findViewById(R.id.nameInputRegister);
        genderInputRegister = findViewById(R.id.genderInputRegister);
        numberInputRegister = findViewById(R.id.mobileNumberInputRegister);
        birthdateInputRegister = findViewById(R.id.birthdateInputRegister);
        addressInputRegister = findViewById(R.id.addressInputRegister);
        diseaseInputRegister = findViewById(R.id.diseaseInputRegister);
        deviceIDInputRegister = findViewById(R.id.deviceIDInputRegister);
        roomInputRegister = findViewById(R.id.roomInputRegister);

        numberInputRegister.setInputType(InputType.TYPE_CLASS_NUMBER);
        deviceIDInputRegister.setInputType(InputType.TYPE_CLASS_NUMBER);
        roomInputRegister.setInputType(InputType.TYPE_CLASS_NUMBER);

        buttonRegister = findViewById(R.id.buttonRegister);

        parentLayout = findViewById(R.id.parentLayout);
        centerLayout = findViewById(R.id.centerLayout);
        scrollView = findViewById(R.id.scrollView);

        back = findViewById(R.id.back);


        nameInputRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.name_icon_black, 0);
                nameInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(nameInputRegister.getText().toString().isEmpty()){
                    nameInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.name_icon, 0);
                }
            }
        });

        genderInputRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInputRegister.clearFocus();
                addressInputRegister.clearFocus();
                numberInputRegister.clearFocus();
                diseaseInputRegister.clearFocus();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
                View inflater = getLayoutInflater().inflate(R.layout.gender_selection, null);
                TextView male = inflater.findViewById(R.id.male);
                TextView female = inflater.findViewById(R.id.female);
                builder.setView(inflater);

                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genderInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gender_icon_black, 0);
                        genderInputRegister.setTextColor(Color.parseColor("#000000"));
                        genderInputRegister.setText("Male");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        }, 200);
                    }
                });

                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        genderInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gender_icon_black, 0);
                        genderInputRegister.setTextColor(Color.parseColor("#000000"));
                        genderInputRegister.setText("Female");
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
        });

        birthdateInputRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                birthdateInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                birthdateInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.birthdate_icon_black, 0);
                birthdateInputRegister.setTextColor(Color.parseColor("#000000"));
                birthdateInputRegister.setText(sdf.format(calendar.getTime()));
            }
        };

        birthdateInputRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                nameInputRegister.clearFocus();
                addressInputRegister.clearFocus();
                numberInputRegister.clearFocus();
                diseaseInputRegister.clearFocus();
                new DatePickerDialog(Register.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addressInputRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addressInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.address_icon_black, 0);
                addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(addressInputRegister.getText().toString().isEmpty()){
                    addressInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.address_icon, 0);
                }
            }
        });

        numberInputRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                numberInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.number_icon_black, 0);
                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(numberInputRegister.getText().toString().isEmpty()){
                    numberInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.number_icon, 0);
                }
            }
        });

        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                diseaseInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.disease_icon_black, 0);
                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(diseaseInputRegister.getText().toString().isEmpty()){
                    diseaseInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.disease_icon, 0);
                }
            }
        });

        deviceIDInputRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Register.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                progressDialog.setCancelable(false);

                progressDialog.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceID();
                    }
                }, 500);

            }
        });

        roomInputRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Register.this);
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
                }, 500);
            }
        });


        // adding on click listener to our button.
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String genderValueString = genderInputRegister.getText().toString();
                String deviceIDValueString = deviceIDInputRegister.getText().toString();
                String roomValueString = roomInputRegister.getText().toString();


                // validating if the text field is empty or not.
                if ( numberInputRegister.getText().toString().isEmpty() || birthdateInputRegister.getText().toString().isEmpty() || nameInputRegister.getText().toString().isEmpty() ||
                        genderInputRegister.getText().toString().isEmpty() || addressInputRegister.getText().toString().isEmpty() || diseaseInputRegister.getText().toString().isEmpty() || deviceIDValueString.equals("") || roomValueString.equals("")) {

                    ////*****************************//
                    nameInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            nameInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(nameInputRegister.getText().toString().isEmpty()){
                                nameInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }
                        }
                    });

                    genderInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            genderInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            genderInputRegister.setTextColor(Color.parseColor("#000000"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                    birthdateInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            birthdateInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            birthdateInputRegister.setTextColor(Color.parseColor("#000000"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                    addressInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(addressInputRegister.getText().toString().isEmpty()){
                                addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }
                        }
                    });

                    numberInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(numberInputRegister.getText().toString().isEmpty()){
                                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }
                        }
                    });

                    diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if(diseaseInputRegister.getText().toString().isEmpty()){
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }
                        }
                    });

                    deviceIDInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            deviceIDInputRegister.setTextColor(Color.parseColor("#000000"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });

                    roomInputRegister.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            roomInputRegister.setTextColor(Color.parseColor("#000000"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });


                    ////*****************************//

                    if(nameInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                        nameInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                        nameInputRegister.requestFocus();

                        nameInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(nameInputRegister.getText().toString().isEmpty()){
                                    nameInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });


                        addressInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(addressInputRegister.getText().toString().isEmpty()){
                                    addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        numberInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(numberInputRegister.getText().toString().isEmpty()){
                                    numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(diseaseInputRegister.getText().toString().isEmpty()){
                                    diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        if(genderInputRegister.getText().toString().isEmpty()){
                            genderInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(birthdateInputRegister.getText().toString().isEmpty()){
                            birthdateInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(addressInputRegister.getText().toString().isEmpty()){
                            addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(numberInputRegister.getText().toString().isEmpty()){
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(diseaseInputRegister.getText().toString().isEmpty()){
                            diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(deviceIDValueString.equals("")){
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(roomValueString.equals("")){
                            roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(genderInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                        nameInputRegister.clearFocus();
                        addressInputRegister.clearFocus();
                        numberInputRegister.clearFocus();
                        diseaseInputRegister.clearFocus();
                        genderInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));


                        addressInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(addressInputRegister.getText().toString().isEmpty()){
                                    addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        numberInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(numberInputRegister.getText().toString().isEmpty()){
                                    numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(diseaseInputRegister.getText().toString().isEmpty()){
                                    diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        if(numberInputRegister.getText().toString().isEmpty()){
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(birthdateInputRegister.getText().toString().isEmpty()){
                            birthdateInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(addressInputRegister.getText().toString().isEmpty()){
                            addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(diseaseInputRegister.getText().toString().isEmpty()){
                            diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(deviceIDValueString.equals("")){
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(roomValueString.equals("")){
                            roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(birthdateInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                        nameInputRegister.clearFocus();
                        addressInputRegister.clearFocus();
                        numberInputRegister.clearFocus();
                        diseaseInputRegister.clearFocus();
                        birthdateInputRegister.requestFocus();


                        addressInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(addressInputRegister.getText().toString().isEmpty()){
                                    addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        numberInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(numberInputRegister.getText().toString().isEmpty()){
                                    numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(diseaseInputRegister.getText().toString().isEmpty()){
                                    diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        if(addressInputRegister.getText().toString().isEmpty()){
                            addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(numberInputRegister.getText().toString().isEmpty()){
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(diseaseInputRegister.getText().toString().isEmpty()){
                            diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(deviceIDValueString.equals("")){
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(roomValueString.equals("")){
                            roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(addressInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                        addressInputRegister.requestFocus();

                        addressInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(addressInputRegister.getText().toString().isEmpty()){
                                    addressInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        numberInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(numberInputRegister.getText().toString().isEmpty()){
                                    numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(diseaseInputRegister.getText().toString().isEmpty()){
                                    diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        if(numberInputRegister.getText().toString().isEmpty()){
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(diseaseInputRegister.getText().toString().isEmpty()){
                            diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(deviceIDValueString.equals("")){
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(roomValueString.equals("")){
                            roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(numberInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                        numberInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(numberInputRegister.getText().toString().isEmpty()){
                                    numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(diseaseInputRegister.getText().toString().isEmpty()){
                                    diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        numberInputRegister.requestFocus();

                        if(diseaseInputRegister.getText().toString().isEmpty()){
                            diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(deviceIDValueString.equals("")){
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(roomValueString.equals("")){
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(diseaseInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

                        diseaseInputRegister.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_input_layout));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(diseaseInputRegister.getText().toString().isEmpty()){
                                    diseaseInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_empty_field_layout));
                                }
                            }
                        });

                        diseaseInputRegister.requestFocus();

                        if(deviceIDValueString.equals("")){
                            deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }

                        if(roomValueString.equals("")){
                            numberInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(deviceIDValueString.equals("")){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                        nameInputRegister.clearFocus();
                        addressInputRegister.clearFocus();
                        numberInputRegister.clearFocus();
                        diseaseInputRegister.clearFocus();
                        deviceIDInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));

                        if(roomValueString.equals("")){
                            roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                        }
                    }

                    else if(roomValueString.equals("")){
                        Toast.makeText(Register.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();
                        nameInputRegister.clearFocus();
                        addressInputRegister.clearFocus();
                        numberInputRegister.clearFocus();
                        diseaseInputRegister.clearFocus();
                        roomInputRegister.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.login_invalid_credentials_layout));
                    }
                }

                else{

                    new AlertDialog.Builder(Register.this)
                            .setMessage("Are you sure you want to register this patient?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(Register.this);
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_bar);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    progressDialog.setCancelable(false);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            InputMethodManager imm = (InputMethodManager)Register.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                            registerPatientData(nameInputRegister.getText().toString(), genderInputRegister.getText().toString() ,numberInputRegister.getText().toString(),
                                                    birthdateInputRegister.getText().toString(), addressInputRegister.getText().toString(), diseaseInputRegister.getText().toString(), deviceIDInputRegister.getText().toString(), roomInputRegister.getText().toString(), currentUser);
                                        }
                                    }, 2000);

                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }

            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        returnPress = returnPress + "b";

                        Intent intentHome = new Intent(Register.this, Patient.class);
                        intentHome.putExtra("USER_ID", currentUser);
                        intentHome.putExtra("RETURN", returnPress);
                        intentHome.putExtra("FIRST_TIME", "no");
                        startActivity(intentHome);
                        finish();
                        break;

                    case  R.id.contact:
                        returnPress = returnPress + "b";

                        Intent intentContact = new Intent(Register.this, Contact.class);
                        intentContact.putExtra("USER_ID", currentUser);
                        intentContact.putExtra("RETURN", returnPress);
                        intentContact.putExtra("FIRST_TIME", "no");
                        startActivity(intentContact);
                        finish();
                        break;

                    case  R.id.about:
                        returnPress = returnPress + "b";

                        Intent intentAbout = new Intent(Register.this, About.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);
                        finish();
                        break;

                    case  R.id.logout:

                        new AlertDialog.Builder(Register.this)
                                .setMessage("Are you sure you want to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentLogout = new Intent(Register.this, Login.class);
                                        startActivity(intentLogout);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                        break;
                }
                return false;
            }
        });
    }

    @DrawableRes
    public int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    private void getDeviceID(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/deviceID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                if(response.equals("failure")){

                    progressDialog.dismiss();

                    progressDialog8 = new ProgressDialog(Register.this);
                    progressDialog8.show();
                    progressDialog8.setContentView(R.layout.failure_get_device_id);
                    progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Register.this, Register.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
                        }
                    }, 2000);
                }

                else if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog.dismiss();
                        addDeviceID(response);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog.dismiss();
                            addDeviceID(response);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog4 = new ProgressDialog(Register.this);
                            progressDialog4.show();
                            progressDialog4.setContentView(R.layout.connectionfailure);
                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog4.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Register.this, Register.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }
                    }
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog4 = new ProgressDialog(Register.this);
                progressDialog4.show();
                progressDialog4.setContentView(R.layout.connectionfailure);
                progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog4.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Register.this, Register.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
                    }
                }, 2000);


            }
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }


    @Override
    public void onBackPressed() {
        if(returnPress.length() > 1){
            // para ma concatinate ang return press para ang pina ka dulo lang kwaon
            copyOfReturnPress = returnPress;
            concatinatedReturnPress = returnPress.substring(returnPress.length() - 1);
            returnPress = concatinatedReturnPress;
        }

        if(returnPress.equals("0")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Register.this, Home.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("1")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Register.this, Patient.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("3")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Register.this, About.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("4")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Register.this, Account.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("5")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Register.this, Contact.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("6")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Register.this, DismissPatient.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        else if(returnPress.equals("7")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentDismiss = new Intent(Register.this, Monitoring.class);
            intentDismiss.putExtra("USER_ID", currentUser);
            intentDismiss.putExtra("RETURN", returnPress);
            intentDismiss.putExtra("FIRST_TIME", "no");
            startActivity(intentDismiss);
            finish();
        }

        else if(returnPress.equals("8")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentPatientLogs = new Intent(Register.this, PatientLogs.class);
            intentPatientLogs.putExtra("USER_ID", currentUser);
            intentPatientLogs.putExtra("RETURN", returnPress);
            intentPatientLogs.putExtra("FIRST_TIME", "no");
            startActivity(intentPatientLogs);
            finish();
        }

        else if(returnPress.equals("9")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentSharedPatient = new Intent(Register.this, SharePatient.class);
            intentSharedPatient.putExtra("USER_ID", currentUser);
            intentSharedPatient.putExtra("RETURN", returnPress);
            intentSharedPatient.putExtra("FIRST_TIME", "no");
            startActivity(intentSharedPatient);
            finish();
        }

        else if(returnPress.equals("a")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentSearchShare = new Intent(Register.this, SearchUserShare.class);
            intentSearchShare.putExtra("USER_ID", currentUser);
            intentSearchShare.putExtra("RETURN", returnPress);
            intentSearchShare.putExtra("FIRST_TIME", "no");
            startActivity(intentSearchShare);
            finish();
        }

        else if(returnPress.equals("c")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Register.this, SharingMenu.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

        else if(returnPress.equals("d")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Register.this, PatientInfo.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

        else if(returnPress.equals("f")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Register.this, LiveMonitoring.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }
    }

    private void getStaffName(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getStaffName.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                        View header = navigationView.getHeaderView(0);
                        TextView staff_name = header.findViewById(R.id.staff_name);

                        staff_name.setText(response);

                        progressDialog2.dismiss();
                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);
                    }

                    else {
                        String firstThreeChar = response.substring(0,3);

                        if(firstThreeChar.equals("<br")){
                            progressDialog2.dismiss();

                            progressDialog4 = new ProgressDialog(Register.this);
                            progressDialog4.show();
                            progressDialog4.setContentView(R.layout.connectionfailure);
                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog4.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Register.this, Register.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                            View header = navigationView.getHeaderView(0);
                            TextView staff_name = header.findViewById(R.id.staff_name);

                            staff_name.setText(response);

                            progressDialog2.dismiss();
                            LinearLayout middleLayout = findViewById(R.id.middleLayout);
                            middleLayout.setVisibility(View.VISIBLE);
                        }
                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog2.dismiss();

                    progressDialog6 = new ProgressDialog(Register.this);
                    progressDialog6.show();
                    progressDialog6.setContentView(R.layout.error_retrieve_name);
                    progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Register.this, Register.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
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
                progressDialog2.dismiss();

                progressDialog4 = new ProgressDialog(Register.this);
                progressDialog4.show();
                progressDialog4.setContentView(R.layout.connectionfailure);
                progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog4.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Register.this, Register.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
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
    private void getProfile(String currentUserID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getProfile.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                        View header = navigationView.getHeaderView(0);
                        ImageView profile = header.findViewById(R.id.profile);
                        LinearLayout editAccount;
                        editAccount = header.findViewById(R.id.editAccount);

                        Glide.with(Register.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                        editAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress + "b";
                                Intent intentAccount = new Intent(Register.this, Account.class);
                                intentAccount.putExtra("USER_ID", currentUser);
                                intentAccount.putExtra("RETURN", returnPress);
                                intentAccount.putExtra("FIRST_TIME", "no");
                                startActivity(intentAccount);
                                finish();
                            }
                        });

                        getStaffName(currentUser);
                    }

                    else {
                        String profileString = response.substring(0,8);

                        if(profileString.equals("profile/")){
                            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                            View header = navigationView.getHeaderView(0);
                            ImageView profile = header.findViewById(R.id.profile);
                            LinearLayout editAccount;
                            editAccount = header.findViewById(R.id.editAccount);

                            Glide.with(Register.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                            editAccount.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress + "b";
                                    Intent intentAccount = new Intent(Register.this, Account.class);
                                    intentAccount.putExtra("USER_ID", currentUser);
                                    intentAccount.putExtra("RETURN", returnPress);
                                    intentAccount.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAccount);
                                    finish();
                                }
                            });

                            getStaffName(currentUser);
                        }

                        else {
                            progressDialog2.dismiss();

                            progressDialog4 = new ProgressDialog(Register.this);
                            progressDialog4.show();
                            progressDialog4.setContentView(R.layout.connectionfailure);
                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog4.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Register.this, Register.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog2.dismiss();
                    progressDialog3 = new ProgressDialog(Register.this);
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.error_retrieve_profile);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Register.this, Register.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
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
                progressDialog2.dismiss();

                progressDialog4 = new ProgressDialog(Register.this);
                progressDialog4.show();
                progressDialog4.setContentView(R.layout.connectionfailure);
                progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog4.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Register.this, Register.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
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

    private void registerPatientData(String name, String gender, String number, String birthdate, String address, String disease, String deviceID, String room, String encoder) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/register.php";


        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("success")){
                    progressDialog.dismiss();

                    progressDialog1 = new ProgressDialog(Register.this);
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.registered_success);
                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            genderInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gender_icon, 0);
                            birthdateInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.birthdate_icon, 0);
                            roomInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.room_icon, 0);
                            deviceIDInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.device_icon, 0);

//                            scrollView.scrollTo(0,0);
                            nameInputRegister.setText("");
                            genderInputRegister.setText("");
                            birthdateInputRegister.setText("");
                            addressInputRegister.setText("");
                            numberInputRegister.setText("");
                            diseaseInputRegister.setText("");
                            deviceIDInputRegister.setText("");
                            roomInputRegister.setText("");

                            nameInputRegister.clearFocus();
                            addressInputRegister.clearFocus();
                            numberInputRegister.clearFocus();

//                            progressDialog1.dismiss();
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Register.this, Register.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
                        }
                    }, 2000);
                }

                else if(!response.equals("failure")){
                    //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                    progressDialog.dismiss();

                    progressDialog1 = new ProgressDialog(Register.this);
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.connectionfailure);
                    progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog1.dismiss();

                            LinearLayout middleLayout = findViewById(R.id.middleLayout);
                            middleLayout.setVisibility(View.VISIBLE);

                            middleLayout.removeAllViews();

                            LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT
                            );
                            middleParams.gravity = Gravity.CENTER;
                            middleLayout.setLayoutParams(middleParams);
                            middleLayout.setGravity(Gravity.CENTER);

                            int resId = getSelectableItemBackground();

                            LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(Register.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(Register.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(Register.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(Register.this, Register.class);
                                    intentAbout.putExtra("USER_ID", currentUser);
                                    intentAbout.putExtra("RETURN", returnPress);
                                    intentAbout.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAbout);
                                    finish();
                                }
                            });
                        }
                    }, 2000);
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog5 = new ProgressDialog(Register.this);
                    progressDialog5.show();
                    progressDialog5.setContentView(R.layout.fail_register);
                    progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Register.this, Register.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
                        }
                    }, 2000);
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String name = respObj.getString("name");
                    String gender = respObj.getString("gender");
                    String number = respObj.getString("number");
                    String birthdate = respObj.getString("birthdate");
                    String address = respObj.getString("address");
                    String disease = respObj.getString("disease");
                    String deviceID = respObj.getString("deviceID");
                    String room = respObj.getString("room");
                    String encoder = respObj.getString("encoder");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(Register.this);
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog1.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Register.this, Register.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
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
                params.put("number", number);
                params.put("birthdate", birthdate);
                params.put("address", address);
                params.put("disease", disease);
                params.put("deviceID", deviceID);
                params.put("room", room);
                params.put("encoder", encoder);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getRoom(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/room.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
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
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog.dismiss();
                            addRoom(response);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog4 = new ProgressDialog(Register.this);
                            progressDialog4.show();
                            progressDialog4.setContentView(R.layout.connectionfailure);
                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog4.dismiss();

                                    LinearLayout middleLayout = findViewById(R.id.middleLayout);
                                    middleLayout.setVisibility(View.VISIBLE);

                                    middleLayout.removeAllViews();

                                    LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    middleParams.gravity = Gravity.CENTER;
                                    middleLayout.setLayoutParams(middleParams);
                                    middleLayout.setGravity(Gravity.CENTER);

                                    int resId = getSelectableItemBackground();

                                    LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Register.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Register.this, Register.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                            finish();
                                        }
                                    });
                                }
                            }, 2000);
                        }
                    }
                }


                //kung ang response is failure
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog7 = new ProgressDialog(Register.this);
                    progressDialog7.show();
                    progressDialog7.setContentView(R.layout.failure_get_room_number);
                    progressDialog7.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Register.this, Register.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
                        }
                    }, 2000);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(Register.this);
                progressDialog1.show();
                progressDialog1.setContentView(R.layout.connectionfailure);
                progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog1.dismiss();

                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                        middleLayout.removeAllViews();

                        LinearLayout.LayoutParams middleParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        middleParams.gravity = Gravity.CENTER;
                        middleLayout.setLayoutParams(middleParams);
                        middleLayout.setGravity(Gravity.CENTER);

                        int resId = getSelectableItemBackground();

                        LinearLayout refreshLinearLayout = new LinearLayout(Register.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Register.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Register.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Register.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Register.this, Register.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                                finish();
                            }
                        });
                    }
                }, 2000);
            }
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addRoom(String responseResultRoom) {
        // do something
        String roomNumberValue;
        int count = 0;

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
        View inflater2 = getLayoutInflater().inflate(R.layout.room_selection, null);
        LinearLayout roomNumberLayout = inflater2.findViewById(R.id.roomNumberLayout);
        builder.setView(inflater2);

        android.app.AlertDialog alertDialog1 = builder.create();
        alertDialog1.show();

        for(;;){

            if(responseResultRoom.equals(" ") || responseResultRoom.equals("")){
                if(count == 0){
                    TextView roomNumber = new TextView(Register.this);
                    LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    roomNumber.setLayoutParams(paramsSharerName);
                    roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    roomNumber.setPadding(25,25,25,25);
                    roomNumber.setText("No room number available");
                    Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
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

                TextView roomNumber = new TextView(Register.this);
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                roomNumber.setLayoutParams(paramsSharerName);
                roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                roomNumber.setPadding(25,25,25,25);
                roomNumber.setText(roomNumberValue);
                Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                roomNumber.setTypeface(typeface);
                roomNumber.setForeground(ContextCompat.getDrawable(Register.this, resID));
                roomNumber.setTextColor(Color.BLACK);
                roomNumberLayout.addView(roomNumber);

                if(!responseResultRoom.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(this);
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
                        roomInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.room_icon_black, 0);
                        roomInputRegister.setTextColor(Color.parseColor("#000000"));
                        roomInputRegister.setText(roomNumberValue1);

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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Register.this);
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
                        TextView deviceID = new TextView(Register.this);
                        LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        deviceID.setLayoutParams(paramsSharerName);
                        deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        deviceID.setPadding(25,25,25,25);
                        deviceID.setText("No device ID available");
                        Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
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

                TextView deviceID = new TextView(Register.this);
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                deviceID.setLayoutParams(paramsSharerName);
                deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                deviceID.setPadding(25,25,25,25);
                deviceID.setText(datavalue);
                Typeface typeface = ResourcesCompat.getFont(Register.this, R.font.poppinsmedium);
                deviceID.setTypeface(typeface);
                deviceID.setForeground(ContextCompat.getDrawable(Register.this, resID));
                deviceID.setTextColor(Color.BLACK);
                deviceIDLayout.addView(deviceID);

                if(!responseResult.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(this);
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
                        deviceIDInputRegister.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.device_icon_black, 0);
                        deviceIDInputRegister.setTextColor(Color.parseColor("#000000"));
                        deviceIDInputRegister.setText(deviceIDValue);

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}
