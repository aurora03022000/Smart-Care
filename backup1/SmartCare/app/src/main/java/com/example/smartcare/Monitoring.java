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
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Monitoring extends AppCompatActivity implements NewPulseRateDialogBox.dialogListener, NewAirQualityDialogBox.dialogListener, NewBloodPressureDialogBox.dialogListener, NewBodyTemperatureDialogBox.dialogListener, NewHumidityDialogBox.dialogListener
        ,NewOxygenSaturationDialogBox.dialogListener, NewRespiratoryDialogBox.dialogListener, NewRoomTemperatureDialogBox.dialogListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    LinearLayout cancel1, save1;
    EditText pulseUpdate, oxygenUpdate, bodyTempUpdate, bloodpressureUpdate, respiratoryUpdate, roomTempUpdate, humidityEditTextUpdate, airQualityEditTextUpdate;

    LinearLayout parentLayout, centerLayout, monitor_patient_btn, patient_logs;
    ScrollView scrollView;
    LinearLayout pulseRateLinearLayout, oxygenSaturationLinearLayout, bodyTemperatureLinearLayout, bloodPressureLinearLayout, respiratoryRateLinearLayout;
    LinearLayout roomTemperatureLinearLayout, humidityLinearLayout, airQualityLinearLayout, liveMonitoringLayout;
    LinearLayout editPatientLayout, saveDelete, cancel, save;
    TextView pulse_rate_value, oxygen_saturation_value, body_temperature_value, blood_pressure_value, respiratory_rate_value, room_temperature_value, humidity_value, air_quality_value;

    private String ipAddress = "192.168.254.114:8080";

    String patientID;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6, progressDialog7;
    ProgressDialog progressDialog8, progressDialog9, progressDialog10, progressDialog11, progressDialog12, progressDialog13, progressDialog14, progressDialog15, progressDialog16;
    ProgressDialog progressDialog17, progressDialog18, progressDialog19, progressDialog20, progressDialog21, progressDialog22, progressDialog23, progressDialog24, progressDialog25;
    ProgressDialog progressDialog26, progressDialog27, progressDialog28, progressDialog29, progressDialog30, progressDialog31, progressDialog32, progressDialog33, progressDialog34;
    ProgressDialog progressDialog35, progressDialog36, progressDialog37, progressDialog38, progressDialog39, progressDialog40, progressDialog41, progressDialog42;

    String checker = "0";

    String currentUser, returnPress, firstTime, concatinatedReturnPress, copyOfReturnPress;

    String nameResponse;
    String genderResponse;
    String birthdateResponse;
    String addressResponse;
    String numberResponse;
    String diseaseResponse;
    String roomResponse;
    String deviceResponse;

    LinearLayout edit, close;
    EditText name, birthdate, address, phonenumber;
    EditText gender, room, device, disease;

    AlertDialog dialog;

    public int counter = 5;

    int editClicked = 0;

    private Handler handler = new Handler();

    CountDownTimer countDownTimer;

    @DrawableRes
    private int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Patient Status");
        setContentView(R.layout.monitoring);

        setUpToolbar();

        returnPress = getIntent().getStringExtra("RETURN");

        copyOfReturnPress = returnPress;

        pulseRateLinearLayout = findViewById(R.id.pulseRateLinearLayout);
        oxygenSaturationLinearLayout = findViewById(R.id.oxygenSaturationLinearLayout);
        bodyTemperatureLinearLayout = findViewById(R.id.bodyTemperatureLinearLayout);
        bloodPressureLinearLayout = findViewById(R.id.bloodPressureLinearLayout);
        respiratoryRateLinearLayout = findViewById(R.id.respiratoryRateLinearLayout);
        roomTemperatureLinearLayout = findViewById(R.id.roomTemperatureLinearLayout);
        humidityLinearLayout = findViewById(R.id.humidityLinearLayout);
        airQualityLinearLayout = findViewById(R.id.airQualityLinearLayout);

        pulse_rate_value = findViewById(R.id.pulse_rate_value);
        oxygen_saturation_value = findViewById(R.id.oxygen_saturation_value);
        body_temperature_value = findViewById(R.id.body_temperature_value);
        blood_pressure_value = findViewById(R.id.blood_pressure_value);
        respiratory_rate_value = findViewById(R.id.respiratory_rate_value);
        room_temperature_value = findViewById(R.id.room_temperature_value);
        humidity_value = findViewById(R.id.humidity_value);
        air_quality_value = findViewById(R.id.air_quality_value);

        progressDialog = new ProgressDialog(Monitoring.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        progressDialog.setCancelable(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCurrentID();
            }
        }, 500);

        TextView countdown = findViewById(R.id.countdown);

        parentLayout = findViewById(R.id.parentLayout);
        centerLayout = findViewById(R.id.centerLayout);
        scrollView = findViewById(R.id.scrollView);

        monitor_patient_btn = findViewById(R.id.monitor_patient_btn);

        patient_logs = findViewById(R.id.patient_logs);

        liveMonitoringLayout = findViewById(R.id.liveMonitoring);

        liveMonitoringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnPress = returnPress + "7";

                Intent intentAccount = new Intent(Monitoring.this, LiveMonitoring.class);
                intentAccount.putExtra("USER_ID", currentUser);
                intentAccount.putExtra("RETURN", returnPress);
                intentAccount.putExtra("FIRST_TIME", "no");
                startActivity(intentAccount);

            }
        });

        editPatientLayout = findViewById(R.id.editPatient);
        pulseRateLinearLayout.setEnabled(false);
        oxygenSaturationLinearLayout.setEnabled(false);
        bodyTemperatureLinearLayout.setEnabled(false);
        bloodPressureLinearLayout.setEnabled(false);
        respiratoryRateLinearLayout.setEnabled(false);
        roomTemperatureLinearLayout.setEnabled(false);
        humidityLinearLayout.setEnabled(false);
        airQualityLinearLayout.setEnabled(false);

        saveDelete = findViewById(R.id.saveDelete);

        editPatientLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog34 = new ProgressDialog(Monitoring.this);
                progressDialog34.show();
                progressDialog34.setContentView(R.layout.progress_bar);
                progressDialog34.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog34.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog34.dismiss();
                        openDialogBoxEditPatientInfo();
                    }
                }, 500);
//                pulseRateLinearLayout.setEnabled(true);
//                oxygenSaturationLinearLayout.setEnabled(true);
//                bodyTemperatureLinearLayout.setEnabled(true);
//                bloodPressureLinearLayout.setEnabled(true);
//                respiratoryRateLinearLayout.setEnabled(true);
//                roomTemperatureLinearLayout.setEnabled(true);
//                humidityLinearLayout.setEnabled(true);
//                airQualityLinearLayout.setEnabled(true);
//
//                saveDelete.setVisibility(View.VISIBLE);
//                editPatientLayout.setVisibility(View.GONE);
//                patient_logs.setVisibility(View.GONE);
            }
        });


        pulseRateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewPulseRate();
            }
        });

        oxygenSaturationLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewOxygenSaturation();
            }
        });

        bodyTemperatureLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewBodyTemperature();
            }
        });

        bloodPressureLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewBloodPressure();
            }
        });

        respiratoryRateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewRespiratoryRate();
            }
        });

        roomTemperatureLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewRoomTemperature();
            }
        });

        humidityLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewHumidity();
            }
        });

        airQualityLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogNewAirQuality();
            }
        });


        LinearLayout editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog13 = new ProgressDialog(Monitoring.this);
                progressDialog13.show();
                progressDialog13.setContentView(R.layout.progress_bar);
                progressDialog13.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog13.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPatientInformation();
                    }
                }, 500);
            }
        });

        monitor_patient_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnPress = returnPress + "0";

                Intent intentHome = new Intent(Monitoring.this, Patient.class);
                intentHome.putExtra("USER_ID", currentUser);
                intentHome.putExtra("RETURN", returnPress);
                intentHome.putExtra("FIRST_TIME", "no");
                startActivity(intentHome);

            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        returnPress = returnPress + "7";

                        Intent intentHome = new Intent(Monitoring.this, Patient.class);
                        intentHome.putExtra("USER_ID", currentUser);
                        intentHome.putExtra("RETURN", returnPress);
                        intentHome.putExtra("FIRST_TIME", "no");
                        startActivity(intentHome);

                        break;


                    case  R.id.contact:
                        returnPress = returnPress + "7";

                        Intent intentContact = new Intent(Monitoring.this, Contact.class);
                        intentContact.putExtra("USER_ID", currentUser);
                        intentContact.putExtra("RETURN", returnPress);
                        intentContact.putExtra("FIRST_TIME", "no");
                        startActivity(intentContact);

                        break;

                    case  R.id.about:
                        returnPress = returnPress + "7";

                        Intent intentAbout = new Intent(Monitoring.this, About.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);

                        break;

                    case  R.id.logout:
                        new AlertDialog.Builder(Monitoring.this)
                                .setMessage("Are you sure you want to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentLogout = new Intent(Monitoring.this, Login.class);
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

    public void openDialogNewPulseRate(){
        NewPulseRateDialogBox newPulseRateDialogBox = new NewPulseRateDialogBox();
        newPulseRateDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewOxygenSaturation(){
        NewOxygenSaturationDialogBox newOxygenSaturationDialogBox = new NewOxygenSaturationDialogBox();
        newOxygenSaturationDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewBodyTemperature(){
        NewBodyTemperatureDialogBox newBodyTemperatureDialogBox = new NewBodyTemperatureDialogBox();
        newBodyTemperatureDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewBloodPressure(){
        NewBloodPressureDialogBox newBloodPressureDialogBox = new NewBloodPressureDialogBox();
        newBloodPressureDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewRespiratoryRate(){
        NewRespiratoryDialogBox newRespiratoryDialogBox = new NewRespiratoryDialogBox();
        newRespiratoryDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewRoomTemperature(){
        NewRoomTemperatureDialogBox newRoomTemperatureDialogBox = new NewRoomTemperatureDialogBox();
        newRoomTemperatureDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewHumidity(){
        NewHumidityDialogBox newHumidityDialogBox = new NewHumidityDialogBox();
        newHumidityDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void openDialogNewAirQuality(){
        NewAirQualityDialogBox newAirQualityDialogBox = new NewAirQualityDialogBox();
        newAirQualityDialogBox.show(getSupportFragmentManager(), "Example Dialog");
    }

    public void showEditRecords(){
        EditRecords showEditRecords = new EditRecords();
        showEditRecords.show(getSupportFragmentManager(), "Example Dialog");
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

            Intent intentHome = new Intent(Monitoring.this, Home.class);
            intentHome.putExtra("USER_ID", currentUser);
            intentHome.putExtra("RETURN", returnPress);
            intentHome.putExtra("FIRST_TIME", "no");
            startActivity(intentHome);

        }

        else if(returnPress.equals("1")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentPatient = new Intent(Monitoring.this, Patient.class);
            intentPatient.putExtra("USER_ID", currentUser);
            intentPatient.putExtra("RETURN", returnPress);
            intentPatient.putExtra("FIRST_TIME", "no");
            startActivity(intentPatient);

        }

        else if(returnPress.equals("3")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(Monitoring.this, About.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);

        }

        else if(returnPress.equals("4")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAccount = new Intent(Monitoring.this, Account.class);
            intentAccount.putExtra("USER_ID", currentUser);
            intentAccount.putExtra("RETURN", returnPress);
            intentAccount.putExtra("FIRST_TIME", "no");
            startActivity(intentAccount);

        }

        else if(returnPress.equals("5")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentContact = new Intent(Monitoring.this, Contact.class);
            intentContact.putExtra("USER_ID", currentUser);
            intentContact.putExtra("RETURN", returnPress);
            intentContact.putExtra("FIRST_TIME", "no");
            startActivity(intentContact);

        }

        else if(returnPress.equals("6")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentDismiss = new Intent(Monitoring.this, DismissPatient.class);
            intentDismiss.putExtra("USER_ID", currentUser);
            intentDismiss.putExtra("RETURN", returnPress);
            intentDismiss.putExtra("FIRST_TIME", "no");
            startActivity(intentDismiss);

        }

        else if(returnPress.equals("8")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentPatientLogs = new Intent(Monitoring.this, PatientLogs.class);
            intentPatientLogs.putExtra("USER_ID", currentUser);
            intentPatientLogs.putExtra("RETURN", returnPress);
            intentPatientLogs.putExtra("FIRST_TIME", "no");
            startActivity(intentPatientLogs);

        }

        else if(returnPress.equals("9")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentSharedPatient = new Intent(Monitoring.this, SharePatient.class);
            intentSharedPatient.putExtra("USER_ID", currentUser);
            intentSharedPatient.putExtra("RETURN", returnPress);
            intentSharedPatient.putExtra("FIRST_TIME", "no");
            startActivity(intentSharedPatient);

        }

        else if(returnPress.equals("a")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentSearchShare = new Intent(Monitoring.this, SearchUserShare.class);
            intentSearchShare.putExtra("USER_ID", currentUser);
            intentSearchShare.putExtra("RETURN", returnPress);
            intentSearchShare.putExtra("FIRST_TIME", "no");
            startActivity(intentSearchShare);

        }

        else if(returnPress.equals("b")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentRegister = new Intent(Monitoring.this, Register.class);
            intentRegister.putExtra("USER_ID", currentUser);
            intentRegister.putExtra("RETURN", returnPress);
            intentRegister.putExtra("FIRST_TIME", "no");
            startActivity(intentRegister);

        }

        else if(returnPress.equals("c")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Monitoring.this, SharingMenu.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);

        }

        else if(returnPress.equals("d")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Monitoring.this, PatientInfo.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);

        }

        else if(returnPress.equals("f")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(Monitoring.this, LiveMonitoring.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);

        }

    }

    private void getCurrentID() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getCurrentUserID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

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
                        currentUser = response;
                        getPatientID(currentUser);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            currentUser = response;
                            getPatientID(currentUser);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Monitoring.this);
                            progressDialog1.show();
                            progressDialog1.setContentView(R.layout.connectionfailure);
                            progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

                                        }
                                    });
                                }
                            }, 2000);
                        }

                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog2 = new ProgressDialog(Monitoring.this);
                    progressDialog2.show();
                    progressDialog2.setContentView(R.layout.failure_get_current_id);
                    progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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

                progressDialog1 = new ProgressDialog(Monitoring.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getPatientID(String currentUser) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

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
                        getProfile(currentUser);

                        patient_logs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressDialog5 = new ProgressDialog(Monitoring.this);
                                progressDialog5.show();
                                progressDialog5.setContentView(R.layout.progress_bar);
                                progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                progressDialog5.setCancelable(false);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        updatePreviousLogID(patientID);
                                    }
                                }, 500);

                            }
                        });
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            patientID = response;
                            getProfile(currentUser);

                            patient_logs.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    progressDialog5 = new ProgressDialog(Monitoring.this);
                                    progressDialog5.show();
                                    progressDialog5.setContentView(R.layout.progress_bar);
                                    progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    progressDialog5.setCancelable(false);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            updatePreviousLogID(patientID);
                                        }
                                    }, 500);

                                }
                            });
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Monitoring.this);
                            progressDialog1.show();
                            progressDialog1.setContentView(R.layout.connectionfailure);
                            progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

                                        }
                                    });
                                }
                            }, 2000);
                        }
                    }
                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog3 = new ProgressDialog(Monitoring.this);
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.failure_get_patient_id);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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

                progressDialog1 = new ProgressDialog(Monitoring.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void updatePreviousLogID(String patientID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updatePreviousLogID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){
                    progressDialog5.dismiss();

                    returnPress = returnPress + "7";

                    Intent intentLogs = new Intent(Monitoring.this, PatientLogs.class);
                    intentLogs.putExtra("USER_ID", currentUser);
                    intentLogs.putExtra("PATIENT_ID", patientID);
                    intentLogs.putExtra("RETURN", returnPress);
                    intentLogs.putExtra("FIRST_TIME", "no");
                    startActivity(intentLogs);

                }

                else if(!response.equals("failure")){
                    progressDialog5.dismiss();

                    progressDialog6 = new ProgressDialog(Monitoring.this);
                    progressDialog6.show();
                    progressDialog6.setContentView(R.layout.connectionfailure);
                    progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog6.dismiss();

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

                            LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(Monitoring.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(Monitoring.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                    intentAbout.putExtra("USER_ID", currentUser);
                                    intentAbout.putExtra("RETURN", returnPress);
                                    intentAbout.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAbout);

                                }
                            });
                        }
                    }, 2000);
                }

                else if(response.equals("failure")){
                    progressDialog5.dismiss();

                    progressDialog4 = new ProgressDialog(Monitoring.this);
                    progressDialog4.show();
                    progressDialog4.setContentView(R.layout.failure_update_previous_log_id);
                    progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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
                progressDialog5.dismiss();

                progressDialog6 = new ProgressDialog(Monitoring.this);
                progressDialog6.show();
                progressDialog6.setContentView(R.layout.connectionfailure);
                progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog6.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

    private void getStaffName(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getStaffName.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

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

                        getPatientHealthStatus(patientID);
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Monitoring.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

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

                            getPatientHealthStatus(patientID);
                        }
                    }
                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog8 = new ProgressDialog(Monitoring.this);
                    progressDialog8.show();
                    progressDialog8.setContentView(R.layout.error_retrieve_name);
                    progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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

                progressDialog1 = new ProgressDialog(Monitoring.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

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

                        Glide.with(Monitoring.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                        editAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress + "7";

                                Intent intentAccount = new Intent(Monitoring.this, Account.class);
                                intentAccount.putExtra("USER_ID", currentUser);
                                intentAccount.putExtra("RETURN", returnPress);
                                intentAccount.putExtra("FIRST_TIME", "no");
                                startActivity(intentAccount);

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

                            Glide.with(Monitoring.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                            editAccount.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress + "7";

                                    Intent intentAccount = new Intent(Monitoring.this, Account.class);
                                    intentAccount.putExtra("USER_ID", currentUser);
                                    intentAccount.putExtra("RETURN", returnPress);
                                    intentAccount.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAccount);

                                }
                            });

                            getStaffName(currentUser);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Monitoring.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

                                        }
                                    });
                                }
                            }, 2000);
                        }
                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog7 = new ProgressDialog(Monitoring.this);
                    progressDialog7.show();
                    progressDialog7.setContentView(R.layout.error_retrieve_profile);
                    progressDialog7.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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

                progressDialog1 = new ProgressDialog(Monitoring.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

    private void getPatientHealthStatus(String patientID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientHealthStatus.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){

                        displayPatientStatus(response);

                        //last
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            checker = "1";
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Monitoring.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            displayPatientStatus(response);


                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    checker = "1";

                    progressDialog.dismiss();

                    progressDialog9 = new ProgressDialog(Monitoring.this);
                    progressDialog9.show();
                    progressDialog9.setContentView(R.layout.failure_to_get_patient_health_status);
                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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

                //Para ni sa error kung wala ka naka connect sa wifi sang hospital f
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(Monitoring.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

    public void displayPatientStatus(String responseResult) {

        String pulseRate;
        String oxygenSaturation;
        String bodyTemperature;
        String bloodPressure;
        String respiratoryRate;
        String roomTemperature;
        String humidity;
        String airQuality;

        int i = 0;
        TextView newLayoutClick;
        TextView numberOfRegisteredPatient;

        for (; ; ) {

            if (responseResult.equals(" ") || responseResult.equals("")) {
                break;
            }

            else {
                pulseRate = responseResult.replaceAll("\\`.*", "");

                String newWord = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord.trim();

                responseResult = newWord;

                oxygenSaturation = responseResult.replaceAll("\\`.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord1.trim();

                responseResult = newWord1;

                bodyTemperature = responseResult.replaceAll("\\`.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord2.trim();

                responseResult = newWord2;

                bloodPressure = responseResult.replaceAll("\\`.*", "");

                String newWord3 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord3.trim();

                responseResult = newWord3;

                respiratoryRate = responseResult.replaceAll("\\`.*", "");

                String newWord4 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord4.trim();

                responseResult = newWord4;

                roomTemperature = responseResult.replaceAll("\\`.*", "");

                String newWord5 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord5.trim();

                responseResult = newWord5;

                humidity = responseResult.replaceAll("\\`.*", "");

                String newWord6 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord6.trim();

                responseResult = newWord6;

                airQuality = responseResult.replaceAll("\\%.*", "");

                String newWord7 = responseResult.substring(responseResult.indexOf("%") + 1);
                newWord7.trim();

                responseResult = newWord7;

                if(pulseRate.equals("0") || pulseRate.equals(" ") || pulseRate.equals("")){
                    pulse_rate_value.setText(" Add value ");
                    pulse_rate_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    pulse_rate_value.setText(pulseRate + " BPM ");
                }

                if(oxygenSaturation.equals("0") || oxygenSaturation.equals(" ") || oxygenSaturation.equals("")){
                    oxygen_saturation_value.setText(" Add value ");
                    oxygen_saturation_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    oxygen_saturation_value.setText(oxygenSaturation + " % ");
                }

                if(bodyTemperature.equals("0") || bodyTemperature.equals(" ") || bodyTemperature.equals("")){
                    body_temperature_value.setText(" Add value ");
                    body_temperature_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    body_temperature_value.setText(bodyTemperature + " °C ");
                }

                if(bloodPressure.equals("0") || bloodPressure.equals(" ") || bloodPressure.equals("")){
                    blood_pressure_value.setText(" Add value ");
                    blood_pressure_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    blood_pressure_value.setText(bloodPressure + " mm Hg ");
                }

                if(respiratoryRate.equals("0") || respiratoryRate.equals(" ") || respiratoryRate.equals("")){
                    respiratory_rate_value.setText(" Add value ");
                    respiratory_rate_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    respiratory_rate_value.setText(respiratoryRate + " bpm ");
                }

                if(roomTemperature.equals("0") || roomTemperature.equals(" ") || roomTemperature.equals("")){
                    room_temperature_value.setText(" Add value ");
                    room_temperature_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    room_temperature_value.setText(roomTemperature + " °C ");
                }

                if(humidity.equals("0") || humidity.equals(" ") || humidity.equals("")){
                    humidity_value.setText(" Add value ");
                    humidity_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    humidity_value.setText(humidity + " % ");
                }

                if(airQuality.equals("0") || airQuality.equals(" ") || airQuality.equals("")){
                    air_quality_value.setText(" Add value ");
                    air_quality_value.setTextColor(Color.parseColor("#0EB021"));
                }

                else{
                    air_quality_value.setText(airQuality + " PPM ");
                }

            }

        }

        TextView countdown = findViewById(R.id.countdown);

        new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdown.setText(String.valueOf(counter));
                counter--;
            }
            @Override
            public void onFinish() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        counter = 5;
                        countdown.setText(String.valueOf(counter));
                        getPatientHealthStatus(patientID);
                    }}, 1000);

            }
        }.start();

        //here
        getPatientName();

    }

    private void getPatientName(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientNameCurrent.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ang response is halin sa API

                if(!response.equals("failure")){

                    if(response.equals("")){
                        TextView patientName = findViewById(R.id.patientName);
                        patientName.setText(response);
                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog10 = new ProgressDialog(Monitoring.this);
                            progressDialog10.show();
                            progressDialog10.setContentView(R.layout.connectionfailure);
                            progressDialog10.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog10.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);
                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            TextView patientName = findViewById(R.id.patientName);
                            patientName.setText(response);
                            LinearLayout middleLayout = findViewById(R.id.middleLayout);
                            middleLayout.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();
                        }
                    }
                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog11 = new ProgressDialog(Monitoring.this);
                    progressDialog11.show();
                    progressDialog11.setContentView(R.layout.error_retrieve_name);
                    progressDialog11.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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

                progressDialog12 = new ProgressDialog(Monitoring.this);
                progressDialog12.show();
                progressDialog12.setContentView(R.layout.connectionfailure);
                progressDialog12.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog12.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getPatientInformation() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientInformation.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog13.dismiss();
                        setPatientInfo(response);
                    }

                    else{
                        String responseString = response.substring(0,3);

                        if(responseString.equals("<br")){
                            progressDialog13.dismiss();

                            progressDialog14 = new ProgressDialog(Monitoring.this);
                            progressDialog14.show();
                            progressDialog14.setContentView(R.layout.connectionfailure);
                            progressDialog14.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog14.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

                                        }
                                    });
                                }
                            }, 2000);
                        }

                        else {
                            progressDialog13.dismiss();
                            setPatientInfo(response);
                        }
                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog13.dismiss();

                    progressDialog15 = new ProgressDialog(Monitoring.this);
                    progressDialog15.show();
                    progressDialog15.setContentView(R.layout.failure_patient_info);
                    progressDialog15.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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
                progressDialog13.dismiss();

                progressDialog16 = new ProgressDialog(Monitoring.this);
                progressDialog16.show();
                progressDialog16.setContentView(R.layout.connectionfailure);
                progressDialog16.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog16.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

            }

        }

        openDialogBoxEditPatientInfo1();

    }

    public void openDialogBoxEditPatientInfo1(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Monitoring.this);


        View inflater2 = getLayoutInflater().inflate(R.layout.edit_patient_info, null);
        name = inflater2.findViewById(R.id.name);
        gender = inflater2.findViewById(R.id.gender);
        birthdate = inflater2.findViewById(R.id.birthdate);
        address = inflater2.findViewById(R.id.address);
        phonenumber = inflater2.findViewById(R.id.number);
        disease = inflater2.findViewById(R.id.disease);
        room = inflater2.findViewById(R.id.room);
        device = inflater2.findViewById(R.id.device);
        phonenumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        room.setInputType(InputType.TYPE_CLASS_NUMBER);
        device.setInputType(InputType.TYPE_CLASS_NUMBER);

        name.setText(nameResponse);
        gender.setText(genderResponse);
        birthdate.setText(birthdateResponse);
        address.setText(addressResponse);
        phonenumber.setText(numberResponse);
        disease.setText(numberResponse);
        room.setText(roomResponse);
        device.setText(deviceResponse);

        builder.setView(inflater2);
        android.app.AlertDialog alertDialog2 = builder.create();
        alertDialog2.show();

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();
                disease.clearFocus();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Monitoring.this);
                View inflater = getLayoutInflater().inflate(R.layout.gender_selection, null);
                TextView male = inflater.findViewById(R.id.male);
                TextView female = inflater.findViewById(R.id.female);
                builder.setView(inflater);

                View inflater3 = getLayoutInflater().inflate(R.layout.edit_patient_info, null);
                LinearLayout genderInputLine = inflater3.findViewById(R.id.genderInputLine);

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
                address.clearFocus();
                phonenumber.clearFocus();
                new DatePickerDialog(Monitoring.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        device.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                name.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();

                progressDialog = new ProgressDialog(Monitoring.this);
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
                address.clearFocus();
                phonenumber.clearFocus();

                progressDialog = new ProgressDialog(Monitoring.this);
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
        address.setEnabled(false);
        phonenumber.setEnabled(false);
        disease.setEnabled(false);
        room.setEnabled(false);
        device.setEnabled(false);
        name.setTextColor(Color.parseColor("#6B6C6B"));
        gender.setTextColor(Color.parseColor("#6B6C6B"));
        birthdate.setTextColor(Color.parseColor("#6B6C6B"));
        address.setTextColor(Color.parseColor("#6B6C6B"));
        phonenumber.setTextColor(Color.parseColor("#6B6C6B"));
        disease.setTextColor(Color.parseColor("#6B6C6B"));
        room.setTextColor(Color.parseColor("#6B6C6B"));
        device.setTextColor(Color.parseColor("#6B6C6B"));

        edit = inflater2.findViewById(R.id.edit);
        save = inflater2.findViewById(R.id.save);
        cancel = inflater2.findViewById(R.id.cancel);
        close = inflater2.findViewById(R.id.close);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                gender.setEnabled(true);
                birthdate.setEnabled(true);
                address.setEnabled(true);
                phonenumber.setEnabled(true);
                disease.setEnabled(true);
                room.setEnabled(true);
                device.setEnabled(true);

                name.setTextColor(Color.parseColor("#0EB021"));
                gender.setTextColor(Color.parseColor("#0EB021"));
                birthdate.setTextColor(Color.parseColor("#0EB021"));
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
                String addressValue = address.getText().toString();
                String numberValue = phonenumber.getText().toString();
                String diseaseValue = disease.getText().toString();
                String roomValue = room.getText().toString();
                String deviceValue = device.getText().toString();

                LinearLayout nameInputLine, genderInputLine, birthdateInputLine, ageInputLine, addressInputLine, numberInputLine, diseaseInputLine, roomInputLine, deviceInputLine;
                nameInputLine = inflater2.findViewById(R.id.nameInputLine);
                genderInputLine = inflater2.findViewById(R.id.genderInputLine);
                birthdateInputLine = inflater2.findViewById(R.id.birthDateInputLine);
                ageInputLine = inflater2.findViewById(R.id.ageInputLine);
                addressInputLine = inflater2.findViewById(R.id.addressInputLine);
                numberInputLine = inflater2.findViewById(R.id.numberInputLine);
                diseaseInputLine = inflater2.findViewById(R.id.diseaseInputLine);
                roomInputLine = inflater2.findViewById(R.id.roomInputLine);
                deviceInputLine = inflater2.findViewById(R.id.deviceInputLine);

                if(nameValue.equals("") || addressValue.equals("") || numberValue.equals("")){
                    if(nameValue.equals("")){
                        nameInputLine.setBackgroundColor(Color.RED);
                        name.requestFocus();
                        Toast.makeText(Monitoring.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(Monitoring.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(Monitoring.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(Monitoring.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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
                    new androidx.appcompat.app.AlertDialog.Builder(Monitoring.this)
                            .setMessage("Are you sure you want to register this patient?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = new ProgressDialog(Monitoring.this);
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_bar);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    progressDialog.setCancelable(false);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            updatePatientInformation(nameValue, genderValue, birthdateValue, addressValue, diseaseValue, numberValue, roomValue, deviceValue, patientID);
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
                address.setEnabled(false);
                phonenumber.setEnabled(false);
                disease.setEnabled(false);
                room.setEnabled(false);
                device.setEnabled(false);

                name.setTextColor(Color.parseColor("#6B6C6B"));
                gender.setTextColor(Color.parseColor("#6B6C6B"));
                birthdate.setTextColor(Color.parseColor("#6B6C6B"));
                address.setTextColor(Color.parseColor("#6B6C6B"));
                phonenumber.setTextColor(Color.parseColor("#6B6C6B"));
                disease.setTextColor(Color.parseColor("#6B6C6B"));
                room.setTextColor(Color.parseColor("#6B6C6B"));
                device.setTextColor(Color.parseColor("#6B6C6B"));

                LinearLayout nameInputLine, genderInputLine, birthdateInputLine, ageInputLine, addressInputLine, numberInputLine, diseaseInputLine, roomInputLine, deviceInputLine;
                nameInputLine = inflater2.findViewById(R.id.nameInputLine);
                genderInputLine = inflater2.findViewById(R.id.genderInputLine);
                birthdateInputLine = inflater2.findViewById(R.id.birthDateInputLine);
                ageInputLine = inflater2.findViewById(R.id.ageInputLine);
                addressInputLine = inflater2.findViewById(R.id.addressInputLine);
                numberInputLine = inflater2.findViewById(R.id.numberInputLine);
                diseaseInputLine = inflater2.findViewById(R.id.diseaseInputLine);
                roomInputLine = inflater2.findViewById(R.id.roomInputLine);
                deviceInputLine = inflater2.findViewById(R.id.deviceInputLine);

                nameInputLine.setBackgroundColor(Color.parseColor("#CED5CE"));

                name.setText(nameResponse);
                gender.setText(genderResponse);
                birthdate.setText(birthdateResponse);
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
                returnPress = returnPress;

                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                intentAbout.putExtra("USER_ID", currentUser);
                intentAbout.putExtra("RETURN", returnPress);
                intentAbout.putExtra("FIRST_TIME", "no");
                startActivity(intentAbout);
            }
        });

        alertDialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    returnPress = returnPress;

                    Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                    intentAbout.putExtra("USER_ID", currentUser);
                    intentAbout.putExtra("RETURN", returnPress);
                    intentAbout.putExtra("FIRST_TIME", "no");
                    startActivity(intentAbout);
                    return true;
                }
                return false;
            }
        });

//        alertDialog2.setOnCancelListener(new DialogInterface.OnCancelListener()
//        {
//            @Override
//            public void onCancel(DialogInterface dialog)
//            {
//                returnPress = returnPress;
//
//                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
//                intentAbout.putExtra("USER_ID", currentUser);
//                intentAbout.putExtra("RETURN", returnPress);
//                intentAbout.putExtra("FIRST_TIME", "no");
//                startActivity(intentAbout);
//            }
//        });

        getCurrentID();

    }

    public void openDialogBoxEditPatientInfo(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Monitoring.this);

        View inflater2 = getLayoutInflater().inflate(R.layout.update_record, null);


        pulseUpdate = inflater2.findViewById(R.id.pulse);
        oxygenUpdate = inflater2.findViewById(R.id.oxygen);
        bodyTempUpdate = inflater2.findViewById(R.id.bodyTemperature);
        bloodpressureUpdate = inflater2.findViewById(R.id.bloodPressure);
        respiratoryUpdate = inflater2.findViewById(R.id.respiratoryRate);
        roomTempUpdate = inflater2.findViewById(R.id.roomTemperature);
        humidityEditTextUpdate = inflater2.findViewById(R.id.humidity);
        airQualityEditTextUpdate = inflater2.findViewById(R.id.airQuality);

        save1 = inflater2.findViewById(R.id.saveBtn);
        cancel1 = inflater2.findViewById(R.id.cancelBtn);

        builder.setView(inflater2);
        android.app.AlertDialog alertDialog1 = builder.create();
        alertDialog1.show();


        getRecords();


        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog35 = new ProgressDialog(Monitoring.this);
                progressDialog35.show();
                progressDialog35.setContentView(R.layout.progress_bar);
                progressDialog35.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //String pulse_rate_value, String air_quality_value, String blood_pressure_value, String body_temperature_value,
                        //String humidity_value, String oxygen_saturation_value, String respiratory_rate_value, String room_temperature_value
                        insertData(currentUser, patientID, pulseUpdate.getText().toString(), airQualityEditTextUpdate.getText().toString(), bloodpressureUpdate.getText().toString(),
                                bodyTempUpdate.getText().toString(), humidityEditTextUpdate.getText().toString(), oxygenUpdate.getText().toString(), respiratoryUpdate.getText().toString(), roomTempUpdate.getText().toString());
                    }
                }, 500);
            }
        });

        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnPress = returnPress;

                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                intentAbout.putExtra("USER_ID", currentUser);
                intentAbout.putExtra("RETURN", returnPress);
                intentAbout.putExtra("FIRST_TIME", "no");
                startActivity(intentAbout);
            }
        });

        alertDialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    returnPress = returnPress;

                    Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                    intentAbout.putExtra("USER_ID", currentUser);
                    intentAbout.putExtra("RETURN", returnPress);
                    intentAbout.putExtra("FIRST_TIME", "no");
                    startActivity(intentAbout);
                    return true;
                }
                return false;
            }
        });

    }

    private void getRecords() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getHealthLog.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")) {

                    if (response.equals("")) {
                        progressDialog34.dismiss();
                    }

                    else {
                        String profileString = response.substring(0, 3);

                        if (profileString.equals("<br")) {

                            progressDialog34.dismiss();

                            progressDialog36 = new ProgressDialog(Monitoring.this);
                            progressDialog36.show();
                            progressDialog36.setContentView(R.layout.failure_retrieve_data);
                            progressDialog36.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog36.dismiss();

                                }
                            }, 2000);
                        }

                        else {
                            addData(response);
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog34.dismiss();

                    progressDialog37 = new ProgressDialog(Monitoring.this);
                    progressDialog37.show();
                    progressDialog37.setContentView(R.layout.failure_retrieve_data);
                    progressDialog37.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog37.dismiss();
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
                progressDialog34.dismiss();

                progressDialog38 = new ProgressDialog(Monitoring.this);
                progressDialog38.show();
                progressDialog38.setContentView(R.layout.connectionfailure);
                progressDialog38.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog38.dismiss();
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

    public void addData(String responseResult) {

        String pulseRate;
        String oxygenSaturation;
        String bodyTemperature;
        String bloodPressure;
        String respiratoryRate;
        String roomTemperature;
        String humidity;
        String airQuality;

        int i = 0;
        TextView newLayoutClick;
        TextView numberOfRegisteredPatient;

        for (; ; ) {

            if (responseResult.equals(" ") || responseResult.equals("")) {
                break;
            }

            else {
                pulseRate = responseResult.replaceAll("\\`.*", "");

                String newWord = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord.trim();

                responseResult = newWord;

                oxygenSaturation = responseResult.replaceAll("\\`.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord1.trim();

                responseResult = newWord1;

                bodyTemperature = responseResult.replaceAll("\\`.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord2.trim();

                responseResult = newWord2;

                bloodPressure = responseResult.replaceAll("\\`.*", "");

                String newWord3 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord3.trim();

                responseResult = newWord3;

                respiratoryRate = responseResult.replaceAll("\\`.*", "");

                String newWord4 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord4.trim();

                responseResult = newWord4;

                roomTemperature = responseResult.replaceAll("\\`.*", "");

                String newWord5 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord5.trim();

                responseResult = newWord5;

                humidity = responseResult.replaceAll("\\`.*", "");

                String newWord6 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord6.trim();

                responseResult = newWord6;

                airQuality = responseResult.replaceAll("\\%.*", "");

                String newWord7 = responseResult.substring(responseResult.indexOf("%") + 1);
                newWord7.trim();

                responseResult = newWord7;

                pulseUpdate.setText(pulseRate);
                oxygenUpdate.setText(oxygenSaturation);
                bodyTempUpdate.setText(bodyTemperature);
                bloodpressureUpdate.setText(bloodPressure);
                respiratoryUpdate.setText(respiratoryRate);
                roomTempUpdate.setText(roomTemperature);
                humidityEditTextUpdate.setText(humidity);
                airQualityEditTextUpdate.setText(airQuality);
            }

        }
    }



    private void updatePatientInformation(String name, String gender, String birthdate, String address, String disease,String number, String room, String device, String patientID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updatePatientInformation.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){

                        progressDialog.dismiss();

                        progressDialog18 = new ProgressDialog(Monitoring.this);
                        progressDialog18.show();
                        progressDialog18.setContentView(R.layout.success_update_patient_info);
                        progressDialog18.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                View inflater6 = getLayoutInflater().inflate(R.layout.edit_patient_info, null);
                                EditText name = inflater6.findViewById(R.id.name);
                                EditText gender = inflater6.findViewById(R.id.gender);
                                EditText birthdate = inflater6.findViewById(R.id.birthdate);
                                EditText address = inflater6.findViewById(R.id.address);
                                EditText phonenumber = inflater6.findViewById(R.id.number);
                                EditText disease = inflater6.findViewById(R.id.disease);
                                EditText room = inflater6.findViewById(R.id.room);
                                EditText device = inflater6.findViewById(R.id.device);

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

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);
                            }
                        }, 2000);

//                        }
//                    }, 2000);
                }

                else if(!response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog19 = new ProgressDialog(Monitoring.this);
                    progressDialog19.show();
                    progressDialog19.setContentView(R.layout.connectionfailure);
                    progressDialog19.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog19.dismiss();

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

                            LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(Monitoring.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(Monitoring.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                    intentAbout.putExtra("USER_ID", currentUser);
                                    intentAbout.putExtra("RETURN", returnPress);
                                    intentAbout.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAbout);

                                }
                            });
                        }
                    }, 2000);
                }

                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog20 = new ProgressDialog(Monitoring.this);
                    progressDialog20.show();
                    progressDialog20.setContentView(R.layout.failed_patient_info_update);
                    progressDialog20.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

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
                progressDialog17.dismiss();

                progressDialog21 = new ProgressDialog(Monitoring.this);
                progressDialog21.show();
                progressDialog21.setContentView(R.layout.connectionfailure);
                progressDialog21.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog21.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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
                params.put("birthdate", birthdate);
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

    private void getDeviceID(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/deviceID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);
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
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog.dismiss();
                            addDeviceID(response);
                        }


                        else {
                            progressDialog.dismiss();

                            progressDialog23 = new ProgressDialog(Monitoring.this);
                            progressDialog23.show();
                            progressDialog23.setContentView(R.layout.connectionfailure);
                            progressDialog23.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog23.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

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

                    progressDialog24 = new ProgressDialog(Monitoring.this);
                    progressDialog24.show();
                    progressDialog24.setContentView(R.layout.failure_get_device_id);
                    progressDialog24.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

                        }
                    }, 2000);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog25 = new ProgressDialog(Monitoring.this);
                progressDialog25.show();
                progressDialog25.setContentView(R.layout.connectionfailure);
                progressDialog25.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog25.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

    private void getRoom(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/room.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

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
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog.dismiss();
                            addRoom(response);
                        }

                        else {
                            progressDialog.dismiss();
                            progressDialog27 = new ProgressDialog(Monitoring.this);
                            progressDialog27.show();
                            progressDialog27.setContentView(R.layout.connectionfailure);
                            progressDialog27.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog27.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                            intentAbout.putExtra("USER_ID", currentUser);
                                            intentAbout.putExtra("RETURN", returnPress);
                                            intentAbout.putExtra("FIRST_TIME", "no");
                                            startActivity(intentAbout);

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

                    progressDialog28 = new ProgressDialog(Monitoring.this);
                    progressDialog28.show();
                    progressDialog28.setContentView(R.layout.failure_get_room_number);
                    progressDialog28.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

                        }
                    }, 2000);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();
                progressDialog29.show();
                progressDialog29.setContentView(R.layout.connectionfailure);
                progressDialog29.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog29.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(Monitoring.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Monitoring.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Monitoring.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                                intentAbout.putExtra("USER_ID", currentUser);
                                intentAbout.putExtra("RETURN", returnPress);
                                intentAbout.putExtra("FIRST_TIME", "no");
                                startActivity(intentAbout);

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

        android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(Monitoring.this);
        View inflater5 = getLayoutInflater().inflate(R.layout.room_selection, null);
        LinearLayout roomNumberLayout = inflater5.findViewById(R.id.roomNumberLayout);
        builder4.setView(inflater5);

        android.app.AlertDialog alertDialog5 = builder4.create();
        alertDialog5.show();

        for(;;){

            if(responseResultRoom.equals(" ") || responseResultRoom.equals("")){
                if(count == 0){
                    TextView roomNumber = new TextView(Monitoring.this);
                    LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    roomNumber.setLayoutParams(paramsSharerName);
                    roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    roomNumber.setPadding(25,25,25,25);
                    roomNumber.setText("No room number available");
                    Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
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

                TextView roomNumber = new TextView(Monitoring.this);
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                roomNumber.setLayoutParams(paramsSharerName);
                roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                roomNumber.setPadding(25,25,25,25);
                roomNumber.setText(roomNumberValue);
                Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                roomNumber.setTypeface(typeface);
                roomNumber.setForeground(ContextCompat.getDrawable(Monitoring.this, resID));
//                roomNumber.setForeground(ContextCompat.getDrawable(getActivity(), resID));
                roomNumber.setTextColor(Color.BLACK);
                roomNumberLayout.addView(roomNumber);

                if(!responseResultRoom.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(Monitoring.this);
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
                                alertDialog5.dismiss();
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
        android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(Monitoring.this);
        View inflater3 = getLayoutInflater().inflate(R.layout.deviceid_selection, null);
        LinearLayout deviceIDLayout = inflater3.findViewById(R.id.deviceIDLayout);
        builder2.setView(inflater3);

        android.app.AlertDialog alertDialog3 = builder2.create();
        alertDialog3.show();

        int  count = 0;

        for(;;){

            if(responseResult.equals(" ") || responseResult.equals("")){
                if(count == 0){
                    if(count == 0){
                        TextView deviceID = new TextView(Monitoring.this);
                        LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        deviceID.setLayoutParams(paramsSharerName);
                        deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        deviceID.setPadding(25,25,25,25);
                        deviceID.setText("No device ID available");
                        Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
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

                TextView deviceID = new TextView(Monitoring.this);
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                deviceID.setLayoutParams(paramsSharerName);
                deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                deviceID.setPadding(25,25,25,25);
                deviceID.setText(datavalue);
                Typeface typeface = ResourcesCompat.getFont(Monitoring.this, R.font.poppinsmedium);
                deviceID.setTypeface(typeface);
                deviceID.setForeground(ContextCompat.getDrawable(Monitoring.this, resID));
                deviceID.setTextColor(Color.BLACK);
                deviceIDLayout.addView(deviceID);

                if(!responseResult.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(Monitoring.this);
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
                                alertDialog3.dismiss();
                            }
                        }, 200);
                    }
                });

            }

        }

    }

    //String pulse_rate_value, String air_quality_value, String blood_pressure_value, String body_temperature_value,
    //String humidity_value, String oxygen_saturation_value, String respiratory_rate_value, String room_temperature_value
    private void insertData(String currentUser, String patientID, String pulse_rate_value, String air_quality_value, String blood_pressure_value, String body_temperature_value, String humidity_value, String oxygen_saturation_value, String respiratory_rate_value, String room_temperature_value) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updatePatientHealth.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Monitoring.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){
                    progressDialog35.dismiss();

                    progressDialog42 = new ProgressDialog(Monitoring.this);
                    progressDialog42.show();
                    progressDialog42.setContentView(R.layout.success_data_update);
                    progressDialog42.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog42.dismiss();

                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);

                        }
                    }, 2000);
                }

                else if(!response.equals("failure")){
                    progressDialog35.dismiss();

                    progressDialog39 = new ProgressDialog(Monitoring.this);
                    progressDialog39.show();
                    progressDialog39.setContentView(R.layout.failure_to_update_data);
                    progressDialog39.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog39.dismiss();

                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                        }
                    }, 2000);
                }

                else if(response.equals("failure")){
                    progressDialog35.dismiss();

                    progressDialog30 = new ProgressDialog(Monitoring.this);
                    progressDialog30.show();
                    progressDialog30.setContentView(R.layout.failure_to_update_data);
                    progressDialog30.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog30.dismiss();

                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
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

                progressDialog35.dismiss();

                progressDialog41 = new ProgressDialog(Monitoring.this);
                progressDialog41.show();
                progressDialog41.setContentView(R.layout.connectionfailure);
                progressDialog41.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog41.dismiss();

                        returnPress = returnPress;

                        Intent intentAbout = new Intent(Monitoring.this, Monitoring.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);
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
                params.put("currentUser", currentUser);
                params.put("patientID", patientID);
                params.put("pulse_rate_value", pulse_rate_value);
                params.put("air_quality_value", air_quality_value);
                params.put("blood_pressure_value", blood_pressure_value);
                params.put("body_temperature_value", body_temperature_value);
                params.put("humidity_value", humidity_value);
                params.put("oxygen_saturation_value", oxygen_saturation_value);
                params.put("respiratory_rate_value", respiratory_rate_value);
                params.put("room_temperature_value", room_temperature_value);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }


    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void applyTexts(String value) {
        pulse_rate_value.setText(value);
    }

    @Override
    public void applyTexts1(String value) {
        air_quality_value.setText(value);
    }

    @Override
    public void applyTexts2(String value) {
        blood_pressure_value.setText(value);
    }

    @Override
    public void applyTexts3(String value) {
        body_temperature_value.setText(value);
    }

    @Override
    public void applyTexts4(String value) {
        humidity_value.setText(value);
    }

    @Override
    public void applyTexts5(String value) {
        oxygen_saturation_value.setText(value);
    }

    @Override
    public void applyTexts6(String value) {
        respiratory_rate_value.setText(value);
    }

    @Override
    public void applyTexts7(String value) {
        room_temperature_value.setText(value);
    }
}