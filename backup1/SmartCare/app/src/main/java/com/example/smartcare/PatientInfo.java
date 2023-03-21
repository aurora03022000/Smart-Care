package com.example.smartcare;

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

public class PatientInfo extends AppCompatActivity {
    String currentUser, returnPress, concatinatedReturnPress, copyOfReturnPress;
    LinearLayout monitor_btn;
    private String ipAddress = "192.168.254.114:8080";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    LinearLayout parentLayout, centerLayout;
    ScrollView scrollView;

    String nameResponse;
    String genderResponse;
    String birthdateResponse;
    String ageResponse;
    String addressResponse;
    String numberResponse;
    String roomResponse;
    String deviceResponse;
    String diseaseResponse;

    LinearLayout edit, save, cancel, close;
    EditText name, birthdate, age, address, phonenumber;
    EditText gender, room, device, disease;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6;
    ProgressDialog progressDialog7, progressDialog8, progressDialog9, progressDialog10, progressDialog11;
    ProgressDialog progressDialog12, progressDialog13, progressDialog14, progressDialog15, progressDialog16, progressDialog17, progressDialog18;
    ProgressDialog progressDialog19, progressDialog20, progressDialog21;
    String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Patient Information");
        setContentView(R.layout.patient_info);

        setUpToolbar();

        currentUser = getIntent().getStringExtra("USER_ID");
        returnPress = getIntent().getStringExtra("RETURN");

        copyOfReturnPress = returnPress;

        progressDialog = new ProgressDialog(PatientInfo.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        progressDialog.setCancelable(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getProfile(currentUser);
            }
        }, 500);


        monitor_btn = findViewById(R.id.monitor_btn);
        monitor_btn.setVisibility(View.GONE);

        monitor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnPress = returnPress + "d";
                Intent intent1 = new Intent(PatientInfo.this, Patient.class);
//                intent1.putExtra("PATIENT_ID", response);
                intent1.putExtra("USER_ID", currentUser);
                intent1.putExtra("RETURN", returnPress);
                intent1.putExtra("FIRST_TIME", "no");
                startActivity(intent1);
                finish();
            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        returnPress = returnPress + "d";
                        Intent intentHome = new Intent(PatientInfo.this, Home.class);
                        intentHome.putExtra("USER_ID", currentUser);
                        intentHome.putExtra("RETURN", returnPress);
                        intentHome.putExtra("FIRST_TIME", "no");
                        startActivity(intentHome);
                        finish();
                        break;

                    case  R.id.contact:
                        returnPress = returnPress + "d";
                        Intent intentContact = new Intent(PatientInfo.this, Contact.class);
                        intentContact.putExtra("USER_ID", currentUser);
                        intentContact.putExtra("RETURN", returnPress);
                        intentContact.putExtra("FIRST_TIME", "no");
                        startActivity(intentContact);
                        finish();
                        break;

                    case  R.id.about:
                        returnPress = returnPress + "d";
                        Intent intentAbout = new Intent(PatientInfo.this, About.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);
                        finish();
                        break;

                    case  R.id.logout:

                        new AlertDialog.Builder(PatientInfo.this)
                                .setMessage("Are you sure you want to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentLogout = new Intent(PatientInfo.this, Login.class);
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

//        home_btn = findViewById(R.id.home);
//        register_btn = findViewById(R.id.registerButton);
//        logout_btn = findViewById(R.id.logout);

//        home_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // do something
//                Intent intent = new Intent(Menu.this, Home.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        register_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // do something
//                Intent intent = new Intent(Patient.this, Register.class);
//                intent.putExtra("USER_ID", currentUser);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        logout_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // do something
//                Intent intent = new Intent(Menu.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });

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

            Intent intentAbout = new Intent(PatientInfo.this, Home.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

        if(returnPress.equals("1")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(PatientInfo.this, Patient.class);
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

            Intent intentAbout = new Intent(PatientInfo.this, About.class);
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

            Intent intentAbout = new Intent(PatientInfo.this, Account.class);
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

            Intent intentAbout = new Intent(PatientInfo.this, Contact.class);
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

            Intent intentAbout = new Intent(PatientInfo.this, DismissPatient.class);
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

            Intent intentDismiss = new Intent(PatientInfo.this, Monitoring.class);
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

            Intent intentPatientLogs = new Intent(PatientInfo.this, PatientLogs.class);
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

            Intent intentSharedPatient = new Intent(PatientInfo.this, SharePatient.class);
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

            Intent intentSearchShare = new Intent(PatientInfo.this, SearchUserShare.class);
            intentSearchShare.putExtra("USER_ID", currentUser);
            intentSearchShare.putExtra("RETURN", returnPress);
            intentSearchShare.putExtra("FIRST_TIME", "no");
            startActivity(intentSearchShare);
            finish();
        }

        else if(returnPress.equals("b")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentRegister = new Intent(PatientInfo.this, Register.class);
            intentRegister.putExtra("USER_ID", currentUser);
            intentRegister.putExtra("RETURN", returnPress);
            intentRegister.putExtra("FIRST_TIME", "no");
            startActivity(intentRegister);
            finish();
        }

        else if(returnPress.equals("c")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentShareMenu = new Intent(PatientInfo.this, SharingMenu.class);
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

            Intent intentShareMenu = new Intent(PatientInfo.this, LiveMonitoring.class);
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
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

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
                        getNumberOfPatient(currentUser);
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                            getNumberOfPatient(currentUser);
                        }
                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog3 = new ProgressDialog(PatientInfo.this);
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.error_retrieve_name);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

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

                        Glide.with(PatientInfo.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                        editAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress + "d";

                                Intent intentAccount = new Intent(PatientInfo.this, Account.class);
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

                            Glide.with(PatientInfo.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                            editAccount.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress + "d";

                                    Intent intentAccount = new Intent(PatientInfo.this, Account.class);
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
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                    progressDialog.dismiss();

                    progressDialog2 = new ProgressDialog(PatientInfo.this);
                    progressDialog2.show();
                    progressDialog2.setContentView(R.layout.error_retrieve_profile);
                    progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

    private void getNumberOfPatient(String currentUserID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientAmount.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

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
                        int integerResponse = Integer.parseInt(response);
                        createNewPatientLayout(integerResponse);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            int integerResponse = Integer.parseInt(response);
                            createNewPatientLayout(integerResponse);
                        }

                        else {
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                    progressDialog.dismiss();

                    progressDialog4 = new ProgressDialog(PatientInfo.this);
                    progressDialog4.show();
                    progressDialog4.setContentView(R.layout.failure_retrieve_patient_number);
                    progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

    private void createNewPatientLayout(int patientAmount){

        getPatientInfo(currentUser);

    }


    private void getPatientInfo(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientInfo.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {

                if(!response.equals("failure")){

                    if(response.equals("")){
//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        addPatientInfo(response);
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
//                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            addPatientInfo(response);
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog5 = new ProgressDialog(PatientInfo.this);
                    progressDialog5.show();
                    progressDialog5.setContentView(R.layout.failure_patient_info);
                    progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

    @DrawableRes
    public int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addPatientInfo(String responseResult) {
        String patientName;
        String roomNumber;
        String patienID;
        String share_id;
        String staffSharedName;

        int i=0;
        int numberOfPatient = 0;
        TextView newLayoutClick;
        TextView numberOfRegisteredPatient;

        for(;;){

            numberOfPatient++;

            if(responseResult.equals(" ") || responseResult.equals("")){
                break;
            }

            else{
                patientName = responseResult.replaceAll("\\`.*", "");

                String newWord = responseResult.substring(responseResult.indexOf("`")+1);
                newWord.trim();

                responseResult = newWord;

                patienID = responseResult.replaceAll("\\`.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf("`")+1);
                newWord1.trim();

                responseResult = newWord1;

                roomNumber = responseResult.replaceAll("\\`.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("`")+1);
                newWord2.trim();

                responseResult = newWord2;

                share_id = responseResult.replaceAll("\\`.*", "");

                String newWord3 = responseResult.substring(responseResult.indexOf("`")+1);
                newWord3.trim();

                responseResult = newWord3;

                staffSharedName = responseResult.replaceAll("\\%.*", "");

                String newWord4 = responseResult.substring(responseResult.indexOf("%")+1);
                newWord4.trim();

                responseResult = newWord4;

                int lengthOfPatientName = patientName.length();

                if(lengthOfPatientName > 21){
                    patientName = patientName.substring(0,21) + "...";
                }

                int resID = getSelectableItemBackground();

                LinearLayout centerLayout;
                centerLayout = findViewById(R.id.centerLayout);

                LinearLayout newLinearLayoutChildParent = new LinearLayout(this);
                LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                newLinearLayoutChildParent.setOrientation(LinearLayout.VERTICAL);
                newLinearLayoutChildParent.setForeground(ContextCompat.getDrawable(PatientInfo.this, resID));
                newLinearLayoutChildParent.setLayoutParams(params1Parent);
                centerLayout.addView(newLinearLayoutChildParent);

                if(!share_id.equals("0")){
                    LinearLayout newLinearLayoutChildShared = new LinearLayout(this);
                    LinearLayout.LayoutParams paramsShared = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            10
                    );
                    newLinearLayoutChildShared.setLayoutParams(paramsShared);
                    newLinearLayoutChildShared.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_list_layout3));
                    newLinearLayoutChildParent.addView(newLinearLayoutChildShared);

                    TextView textViewSharerName = new TextView(this);
                    LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    textViewSharerName.setLayoutParams(paramsSharerName);
                    textViewSharerName.setBackgroundColor(Color.parseColor("#0EB021"));
                    textViewSharerName.setPadding(20, 5, 0, 15);
                    textViewSharerName.setTextSize(10);
                    textViewSharerName.setTextColor(Color.WHITE);
                    textViewSharerName.setCompoundDrawablePadding(5);
                    textViewSharerName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shared_to_icon, 0, 0, 0);
                    textViewSharerName.setText("Shared to : " + staffSharedName);
                    newLinearLayoutChildParent.addView(textViewSharerName);

                    LinearLayout newLinearLayoutLine = new LinearLayout(this);
                    LinearLayout.LayoutParams paramsLine = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2
                    );
                    newLinearLayoutLine.setLayoutParams(paramsLine);
                    newLinearLayoutLine.setBackgroundColor(Color.WHITE);
                    newLinearLayoutChildParent.addView(newLinearLayoutLine);
                }

                LinearLayout newLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                newLinearLayout.setLayoutParams(params);
                newLinearLayoutChildParent.addView(newLinearLayout);

                LinearLayout newLinearLayoutChild = new LinearLayout(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                newLinearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);
                params1.gravity = Gravity.CENTER;
                newLinearLayoutChild.setLayoutParams(params1);
                newLinearLayoutChild.setPadding(40, 40, 40, 40);
                newLinearLayout.addView(newLinearLayoutChild);

                if(!share_id.equals("0")){
                    newLinearLayoutChild.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_list_layout1));
                }

                else{
                    newLinearLayoutChild.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_list_layout4));
                }

                TextView textViewPatientIcon = new TextView(this);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params2.setMargins(0, 2, 10, 0);
                params2.gravity = Gravity.CENTER;
                textViewPatientIcon.setLayoutParams(params2);
                textViewPatientIcon.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_icon));
                newLinearLayoutChild.addView(textViewPatientIcon);

                TextView textViewPatientContent = new TextView(this);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params3.setMargins(0, -10, 10, 0);
                textViewPatientContent.setLayoutParams(params3);
                textViewPatientContent.setText("Name :  " + patientName + "\n" + "Room Number :  " + roomNumber + "\n" + "Status : Registered ");
                Typeface typeface = ResourcesCompat.getFont(this, R.font.poppinsmedium);
                textViewPatientContent.setTypeface(typeface);
                textViewPatientContent.setTextColor(Color.WHITE);
                textViewPatientContent.setTextSize(11);
                textViewPatientContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.patient_view_icon, 0);
                newLinearLayoutChild.addView(textViewPatientContent);

                LinearLayout newLinearLayoutSpace = new LinearLayout(this);
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params4.setMargins(0, 20, 0, 0);
                newLinearLayoutSpace.setLayoutParams(params4);
                centerLayout.addView(newLinearLayoutSpace);

                String patientID1 = patienID;
                String currentUser1 = currentUser;
                newLinearLayoutChildParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog7 = new ProgressDialog(PatientInfo.this);
                        progressDialog7.show();
                        progressDialog7.setContentView(R.layout.progress_bar);
                        progressDialog7.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        progressDialog7.setCancelable(false);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateCurrentPatient(patientID1);
                            }
                        }, 500);

                    }
                });

//
//                    if(i == 0){
//                        params.setMargins(50, 60, 50, 0);
//                        i++;
//                    }
//
//                    if(responseResult.equals(" ") || responseResult.equals("")){
//                        params.setMargins(50, 30, 50, 60);
//                    }

            }

        }

        numberOfRegisteredPatient = findViewById(R.id.numberOfRegisteredPatient);
        numberOfPatient = numberOfPatient - 1;
        progressDialog.dismiss();
        getSharedPatientInfo(currentUser, numberOfPatient);

    }

    private void getSharedPatientInfo(String sharedPatiendID, int numberOfPatient){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getSharedPatientInfo.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("failure")){

                    if(response.equals("")){
                        addSharedPatient(response, numberOfPatient);
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                            addSharedPatient(response, numberOfPatient);
                        }
                    }

                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog6 = new ProgressDialog(PatientInfo.this);
                    progressDialog6.show();
                    progressDialog6.setContentView(R.layout.failure_retrieve_shared_patient_info);
                    progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog.dismiss();

                progressDialog1 = new ProgressDialog(PatientInfo.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                params.put("sharedPatiendID", sharedPatiendID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

//    public void updateCurrentPatientID(String patientID){
//        // url to post our data
//        String url = "http://"+ipAddress+"/SmartCare/updateCurrentPatientMonitored.php";
//
//        // creating a new variable for our request queue
//        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);
//
//        // on below line we are calling a string
//        // request method to post the data to our API
//        // in this we are calling a post method.
//        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if(response.equals("success")){
////                    returnPress = returnPress + "d";
////                    Intent intent1 = new Intent(PatientInfo.this, Monitoring.class);
////                    intent1.putExtra("PATIENT_ID", response);
////                    intent1.putExtra("USER_ID", currentUser);
////                    intent1.putExtra("RETURN", returnPress);
////                    intent1.putExtra("FIRST_TIME", "no");
////                    startActivity(intent1);
////                    finish();
//                }
//
//                else if(!response.equals("failure")){
//                    progressDialog7.dismiss();
//
//                    progressDialog8 = new ProgressDialog(PatientInfo.this);
//                    progressDialog8.show();
//                    progressDialog8.setContentView(R.layout.connectionfailure);
//                    progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressDialog8.dismiss();
//                        }
//                    }, 2000);
//                }
//
//                //kung ang response is success
//                else if(response.equals("failure")){
//                    progressDialog7.dismiss();
//
//                    progressDialog9 = new ProgressDialog(PatientInfo.this);
//                    progressDialog9.show();
//                    progressDialog9.setContentView(R.layout.failure_to_update_current_patient_id);
//                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressDialog9.dismiss();
//                        }
//                    }, 2000);
//                }
//
//                try {
//                    // on below line we are parsing the response
//                    // to json object to extract data from it.
//                    JSONObject respObj = new JSONObject(response);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
//                progressDialog7.dismiss();
//
//                progressDialog8 = new ProgressDialog(PatientInfo.this);
//                progressDialog8.show();
//                progressDialog8.setContentView(R.layout.connectionfailure);
//                progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog8.dismiss();
//                    }
//                }, 2000);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // below line we are creating a map for
//                // storing our values in key and value pair.
//                Map<String, String> params = new HashMap<String, String>();
//
//                // on below line we are passing our key
//                // and value pair to our parameters.
//                params.put("patientID", patientID);
//
//                // at last we are
//                // returning our params.
//                return params;
//            }
//        };
//        // below line is to make
//        // a json object request.
//        queue.add(request);
//
//    }

    public void addSharedPatient(String responseResult, int numberOfPatient) {

        String patientName;
        String roomNumber;
        String patienID;
        String share_id;
        String staffSharedName;

        int i=0;
        TextView newLayoutClick;
        TextView numberOfRegisteredPatient;

        for(;;){

            numberOfPatient++;

            if(responseResult.equals(" ") || responseResult.equals("")){
                break;
            }

            else{
                patientName = responseResult.replaceAll("\\`.*", "");

                String newWord = responseResult.substring(responseResult.indexOf("`")+1);
                newWord.trim();

                responseResult = newWord;

                patienID = responseResult.replaceAll("\\`.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf("`")+1);
                newWord1.trim();

                responseResult = newWord1;

                roomNumber = responseResult.replaceAll("\\`.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("`")+1);
                newWord2.trim();

                responseResult = newWord2;

                share_id = responseResult.replaceAll("\\`.*", "");

                String newWord3 = responseResult.substring(responseResult.indexOf("`")+1);
                newWord3.trim();

                responseResult = newWord3;

                staffSharedName = responseResult.replaceAll("\\%.*", "");

                String newWord4 = responseResult.substring(responseResult.indexOf("%")+1);
                newWord4.trim();

                responseResult = newWord4;

                int lengthOfPatientName = patientName.length();

                if(lengthOfPatientName > 21){
                    patientName = patientName.substring(0,21) + "...";
                }

                LinearLayout centerLayout;
                centerLayout = findViewById(R.id.centerLayout);

                LinearLayout newLinearLayoutChildParent = new LinearLayout(this);
                LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                newLinearLayoutChildParent.setOrientation(LinearLayout.VERTICAL);
                newLinearLayoutChildParent.setLayoutParams(params1Parent);
                centerLayout.addView(newLinearLayoutChildParent);

                if(!share_id.equals("0")){
                    LinearLayout newLinearLayoutChildShared = new LinearLayout(this);
                    LinearLayout.LayoutParams paramsShared = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            10
                    );
                    newLinearLayoutChildShared.setLayoutParams(paramsShared);
                    newLinearLayoutChildShared.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_list_layout5));
                    newLinearLayoutChildParent.addView(newLinearLayoutChildShared);

                    TextView textViewSharerName = new TextView(this);
                    LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    textViewSharerName.setLayoutParams(paramsSharerName);
                    textViewSharerName.setBackgroundColor(Color.parseColor("#FB7C41"));
                    textViewSharerName.setPadding(20, 5, 0, 15);
                    textViewSharerName.setTextSize(10);
                    textViewSharerName.setTextColor(Color.WHITE);
                    textViewSharerName.setCompoundDrawablePadding(5);
                    textViewSharerName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shared_to_icon, 0, 0, 0);
                    textViewSharerName.setText("Shared by : " + staffSharedName);
                    newLinearLayoutChildParent.addView(textViewSharerName);

                    LinearLayout newLinearLayoutLine = new LinearLayout(this);
                    LinearLayout.LayoutParams paramsLine = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2
                    );
                    newLinearLayoutLine.setLayoutParams(paramsLine);
                    newLinearLayoutLine.setBackgroundColor(Color.WHITE);
                    newLinearLayoutChildParent.addView(newLinearLayoutLine);
                }

                LinearLayout newLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                newLinearLayout.setLayoutParams(params);
                newLinearLayoutChildParent.addView(newLinearLayout);

                LinearLayout newLinearLayoutChild = new LinearLayout(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                newLinearLayoutChild.setOrientation(LinearLayout.HORIZONTAL);
                params1.gravity = Gravity.CENTER;
                newLinearLayoutChild.setLayoutParams(params1);
                newLinearLayoutChild.setPadding(40, 40, 40, 40);
                newLinearLayout.addView(newLinearLayoutChild);

                if(!share_id.equals("0")){
                    newLinearLayoutChild.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_list_layout2));
                }

                else{
                    newLinearLayoutChild.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_list_layout6));
                }

                TextView textViewPatientIcon = new TextView(this);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params2.setMargins(0, 2, 10, 0);
                params2.gravity = Gravity.CENTER;
                textViewPatientIcon.setLayoutParams(params2);
                textViewPatientIcon.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_icon));
                newLinearLayoutChild.addView(textViewPatientIcon);

                TextView textViewPatientContent = new TextView(this);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params3.setMargins(0, -10, 10, 0);
                textViewPatientContent.setLayoutParams(params3);
                textViewPatientContent.setText("Name :  " + patientName + "\n" + "Room Number :  " + roomNumber + "\n" + "Status : Shared ");
                Typeface typeface = ResourcesCompat.getFont(this, R.font.poppinsmedium);
                textViewPatientContent.setTypeface(typeface);
                textViewPatientContent.setTextColor(Color.WHITE);
                textViewPatientContent.setTextSize(11);
                textViewPatientContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.patient_view_icon, 0);
                newLinearLayoutChild.addView(textViewPatientContent);

                LinearLayout newLinearLayoutSpace = new LinearLayout(this);
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params4.setMargins(0, 20, 0, 0);
                newLinearLayoutSpace.setLayoutParams(params4);
                centerLayout.addView(newLinearLayoutSpace);

                String patientID1 = patienID;
                String currentUser1 = getIntent().getStringExtra("USER_ID");
                newLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog7 = new ProgressDialog(PatientInfo.this);
                        progressDialog7.show();
                        progressDialog7.setContentView(R.layout.progress_bar);
                        progressDialog7.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        progressDialog7.setCancelable(false);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateCurrentPatient(patientID1);
                            }
                        }, 500);
                    }
                });

//                if(i == 0){
//                    params.setMargins(50, 60, 50, 0);
//                    i++;
//                }
//
//                if(responseResult.equals(" ") || responseResult.equals("")){
//                    params.setMargins(50, 30, 50, 60);
//                }

            }

        }

        numberOfRegisteredPatient = findViewById(R.id.numberOfRegisteredPatient);
        numberOfPatient = numberOfPatient - 1;
        numberOfRegisteredPatient.setText("Total : " + numberOfPatient);

        monitor_btn.setVisibility(View.VISIBLE);

        if(numberOfPatient == 0){

            monitor_btn.setEnabled(false);
            monitor_btn.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.disable_layout));

            LinearLayout centerLayout;
            centerLayout = findViewById(R.id.centerLayout);

            LinearLayout newLinearLayoutChildParent = new LinearLayout(PatientInfo.this);
            LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            newLinearLayoutChildParent.setOrientation(LinearLayout.VERTICAL);
            newLinearLayoutChildParent.setLayoutParams(params1Parent);
            centerLayout.addView(newLinearLayoutChildParent);

            TextView noPatient = new TextView(PatientInfo.this);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params3.setMargins(50, 40, 50, 0);
            params3.gravity = Gravity.CENTER_HORIZONTAL;
            noPatient.setText("No patient to be display, please add a patient.");
            Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
            noPatient.setLayoutParams(params3);
            noPatient.setGravity(Gravity.CENTER);
            noPatient.setTypeface(typeface);
            noPatient.setTextSize(12);
            newLinearLayoutChildParent.addView(noPatient);

        }

        else {
            monitor_btn.setEnabled(true);
            monitor_btn.setBackground(ContextCompat.getDrawable(this, R.drawable.register_button_layout));
        }

        progressDialog.dismiss();
        LinearLayout middleLayout = findViewById(R.id.middleLayout);
        middleLayout.setVisibility(View.VISIBLE);
    }

    public void openDialogBoxEditPatientInfo(){
        progressDialog.dismiss();

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PatientInfo.this);
        View inflater2 = getLayoutInflater().inflate(R.layout.edit_patient_info, null);

        name = inflater2.findViewById(R.id.name);
        gender = inflater2.findViewById(R.id.gender);
        birthdate = inflater2.findViewById(R.id.birthdate);
        address = inflater2.findViewById(R.id.address);
        phonenumber = inflater2.findViewById(R.id.number);
        disease = inflater2.findViewById(R.id.disease);
        room = inflater2.findViewById(R.id.room);
        device = inflater2.findViewById(R.id.device);

        name.setText(nameResponse);
        gender.setText(genderResponse);
        birthdate.setText(birthdateResponse);
        address.setText(addressResponse);
        phonenumber.setText(numberResponse);
        disease.setText(diseaseResponse);
        room.setText(roomResponse);
        device.setText(deviceResponse);

        phonenumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        room.setInputType(InputType.TYPE_CLASS_NUMBER);
        device.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(inflater2);

        android.app.AlertDialog alertDialog1 = builder.create();
        alertDialog1.show();

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.clearFocus();
                address.clearFocus();
                phonenumber.clearFocus();
                disease.clearFocus();

                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(PatientInfo.this);
                View inflater3 = getLayoutInflater().inflate(R.layout.gender_selection, null);
                TextView male = inflater3.findViewById(R.id.male);
                TextView female = inflater3.findViewById(R.id.female);
                builder1.setView(inflater3);

                View inflater4 = getLayoutInflater().inflate(R.layout.edit_patient_info, null);
                LinearLayout genderInputLine = inflater4.findViewById(R.id.genderInputLine);

                android.app.AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();

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
                                alertDialog1.dismiss();
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
                                alertDialog1.dismiss();
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
                disease.clearFocus();
                new DatePickerDialog(PatientInfo.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                disease.clearFocus();

                progressDialog12 = new ProgressDialog(PatientInfo.this);
                progressDialog12.show();
                progressDialog12.setContentView(R.layout.progress_bar);
                progressDialog12.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog12.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceID();
                    }
                }, 500);

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
                disease.clearFocus();

                progressDialog13 = new ProgressDialog(PatientInfo.this);
                progressDialog13.show();
                progressDialog13.setContentView(R.layout.progress_bar);
                progressDialog13.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog13.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRoom();
                    }
                }, 500);
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

                InputMethodManager imm = (InputMethodManager)PatientInfo.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

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
                nameInputLine = inflater2.findViewById(R.id.nameInputLine);
                genderInputLine = inflater2.findViewById(R.id.genderInputLine);
                birthdateInputLine = inflater2.findViewById(R.id.birthDateInputLine);
                ageInputLine = inflater2.findViewById(R.id.ageInputLine);
                addressInputLine = inflater2.findViewById(R.id.addressInputLine);
                numberInputLine = inflater2.findViewById(R.id.numberInputLine);
                diseaseInputLine = inflater2.findViewById(R.id.diseaseInputLine);
                roomInputLine = inflater2.findViewById(R.id.roomInputLine);
                deviceInputLine = inflater2.findViewById(R.id.deviceInputLine);

                if(nameValue.equals("") || ageValue.equals("") || addressValue.equals("") || numberValue.equals("")){
                    if(nameValue.equals("")){
                        nameInputLine.setBackgroundColor(Color.RED);
                        name.requestFocus();
                        Toast.makeText(PatientInfo.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();


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

                        if(ageValue.equals("")){
                            ageInputLine.setBackgroundColor(Color.RED);
                        }

                        if(addressValue.equals("")){
                            addressInputLine.setBackgroundColor(Color.RED);
                        }

                        if(numberValue.equals("")){
                            numberInputLine.setBackgroundColor(Color.RED);
                        }

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(ageValue.equals("")){

                        ageInputLine.setBackgroundColor(Color.RED);
                        age.requestFocus();
                        Toast.makeText(PatientInfo.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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

                        if(addressValue.equals("")){
                            addressInputLine.setBackgroundColor(Color.RED);
                        }

                        if(numberValue.equals("")){
                            numberInputLine.setBackgroundColor(Color.RED);
                        }

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(addressValue.equals("")){
                        addressInputLine.setBackgroundColor(Color.RED);
                        address.requestFocus();
                        Toast.makeText(PatientInfo.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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

                        if(numberValue.equals("")){
                            numberInputLine.setBackgroundColor(Color.RED);
                        }

                        if(diseaseValue.equals("")){
                            diseaseInputLine.setBackgroundColor(Color.RED);
                        }

                    }

                    else if(numberValue.equals("")){
                        numberInputLine.setBackgroundColor(Color.RED);
                        phonenumber.requestFocus();
                        Toast.makeText(PatientInfo.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(PatientInfo.this, "Field/s should not be empty", Toast.LENGTH_SHORT).show();

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
                    new androidx.appcompat.app.AlertDialog.Builder(PatientInfo.this)
                            .setMessage("Are you sure you want to update this patient information?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog14 = new ProgressDialog(PatientInfo.this);
                                    progressDialog14.show();
                                    progressDialog14.setContentView(R.layout.progress_bar);
                                    progressDialog14.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    progressDialog14.setCancelable(false);

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            updatePatientInformation(nameValue, genderValue, birthdateValue, ageValue, addressValue, numberValue, diseaseValue, roomValue, deviceValue, patientID);
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

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });


    }

    private void updateCurrentPatient(String patientID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/setCurrentPatient.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("success")){
                    getCurrentPatientID();
                }

                else if(!response.equals("failure")){
                    progressDialog7.dismiss();

                    progressDialog8 = new ProgressDialog(PatientInfo.this);
                    progressDialog8.show();
                    progressDialog8.setContentView(R.layout.connectionfailure);
                    progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog8.dismiss();

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

                            LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(PatientInfo.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                    progressDialog7.dismiss();

                    progressDialog9 = new ProgressDialog(PatientInfo.this);
                    progressDialog9.show();
                    progressDialog9.setContentView(R.layout.failure_to_update_current_patient_id);
                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog7.dismiss();

                progressDialog8 = new ProgressDialog(PatientInfo.this);
                progressDialog8.show();
                progressDialog8.setContentView(R.layout.connectionfailure);
                progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog8.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

    private void getCurrentPatientID() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getCurrentPatientID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

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
                        getPatientInformation(response);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            patientID = response;
                            getPatientInformation(response);
                        }

                        else {
                            progressDialog7.dismiss();

                            progressDialog8 = new ProgressDialog(PatientInfo.this);
                            progressDialog8.show();
                            progressDialog8.setContentView(R.layout.connectionfailure);
                            progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog8.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                    progressDialog7.dismiss();

                    progressDialog10 = new ProgressDialog(PatientInfo.this);
                    progressDialog10.show();
                    progressDialog10.setContentView(R.layout.failure_to_retrieve_current_patient_id);
                    progressDialog10.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog7.dismiss();

                progressDialog8 = new ProgressDialog(PatientInfo.this);
                progressDialog8.show();
                progressDialog8.setContentView(R.layout.connectionfailure);
                progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog8.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog7.dismiss();
                        setPatientInfo(response);
                    }

                    else{
                        String responseString = response.substring(0,3);

                        if(responseString.equals("<br")){
                            progressDialog7.dismiss();

                            progressDialog8 = new ProgressDialog(PatientInfo.this);
                            progressDialog8.show();
                            progressDialog8.setContentView(R.layout.connectionfailure);
                            progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    progressDialog8.dismiss();
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                            progressDialog7.dismiss();
                            setPatientInfo(response);
                        }
                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog11 = new ProgressDialog(PatientInfo.this);
                    progressDialog11.show();
                    progressDialog11.setContentView(R.layout.failure_patient_info);
                    progressDialog11.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
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
                progressDialog7.dismiss();

                progressDialog8 = new ProgressDialog(PatientInfo.this);
                progressDialog8.show();
                progressDialog8.setContentView(R.layout.connectionfailure);
                progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog8.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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


        openDialogBoxEditPatientInfo();


    }

    private void getDeviceID(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/deviceID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                if(!response.equals("failure")){

                    if(response.equals("")){
                        progressDialog12.dismiss();
                        addDeviceID(response);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog12.dismiss();
                            addDeviceID(response);
                        }


                        else {
                            progressDialog12.dismiss();

                            progressDialog15 = new ProgressDialog(PatientInfo.this);
                            progressDialog15.show();
                            progressDialog15.setContentView(R.layout.connectionfailure);
                            progressDialog15.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog15.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                    progressDialog12.dismiss();

                    progressDialog16 = new ProgressDialog(PatientInfo.this);
                    progressDialog16.show();
                    progressDialog16.setContentView(R.layout.failure_get_device_id);
                    progressDialog16.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                progressDialog12.dismiss();

                progressDialog15 = new ProgressDialog(PatientInfo.this);
                progressDialog15.show();
                progressDialog15.setContentView(R.layout.connectionfailure);
                progressDialog15.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog15.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

    private void getRoom(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/room.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

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
                        progressDialog13.dismiss();
                        addRoom(response);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog13.dismiss();
                            addRoom(response);
                        }

                        else {
                            progressDialog13 = new ProgressDialog(PatientInfo.this);
                            progressDialog17.show();
                            progressDialog17.setContentView(R.layout.connectionfailure);
                            progressDialog17.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog17.dismiss();

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

                                    LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                    progressDialog13.dismiss();

                    progressDialog18 = new ProgressDialog(PatientInfo.this);
                    progressDialog18.show();
                    progressDialog18.setContentView(R.layout.failure_get_room_number);
                    progressDialog18.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
                progressDialog13.dismiss();
                progressDialog17.show();
                progressDialog17.setContentView(R.layout.connectionfailure);
                progressDialog17.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog17.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

        android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(PatientInfo.this);
        View inflater5 = getLayoutInflater().inflate(R.layout.room_selection, null);
        LinearLayout roomNumberLayout = inflater5.findViewById(R.id.roomNumberLayout);
        builder4.setView(inflater5);

        android.app.AlertDialog alertDialog5 = builder4.create();
        alertDialog5.show();

        for(;;){

            if(responseResultRoom.equals(" ") || responseResultRoom.equals("")){
                if(count == 0){
                    TextView roomNumber = new TextView(PatientInfo.this);
                    LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    roomNumber.setLayoutParams(paramsSharerName);
                    roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                    roomNumber.setPadding(25,25,25,25);
                    roomNumber.setText("No room number available");
                    Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
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

                TextView roomNumber = new TextView(PatientInfo.this);
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                roomNumber.setLayoutParams(paramsSharerName);
                roomNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                roomNumber.setPadding(25,25,25,25);
                roomNumber.setText(roomNumberValue);
                Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                roomNumber.setTypeface(typeface);
                roomNumber.setForeground(ContextCompat.getDrawable(PatientInfo.this, resID));
//                roomNumber.setForeground(ContextCompat.getDrawable(getActivity(), resID));
                roomNumber.setTextColor(Color.BLACK);
                roomNumberLayout.addView(roomNumber);

                if(!responseResultRoom.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(PatientInfo.this);
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
        android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(PatientInfo.this);
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
                        TextView deviceID = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        deviceID.setLayoutParams(paramsSharerName);
                        deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                        deviceID.setPadding(25,25,25,25);
                        deviceID.setText("No device ID available");
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
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

                TextView deviceID = new TextView(PatientInfo.this);
                LinearLayout.LayoutParams paramsSharerName = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                deviceID.setLayoutParams(paramsSharerName);
                deviceID.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                deviceID.setPadding(25,25,25,25);
                deviceID.setText(datavalue);
                Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                deviceID.setTypeface(typeface);
                deviceID.setForeground(ContextCompat.getDrawable(PatientInfo.this, resID));
                deviceID.setTextColor(Color.BLACK);
                deviceIDLayout.addView(deviceID);

                if(!responseResult.equals("")){
                    LinearLayout newLinearLayoutLine = new LinearLayout(PatientInfo.this);
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

    private void updatePatientInformation(String name, String gender, String birthdate, String age, String address, String number, String disease, String room, String device, String patientID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updatePatientInformation.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PatientInfo.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){

                    progressDialog14.dismiss();

                    progressDialog19 = new ProgressDialog(PatientInfo.this);
                    progressDialog19.show();
                    progressDialog19.setContentView(R.layout.success_update_patient_info);
                    progressDialog19.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog14.dismiss();
                            View inflater6 = getLayoutInflater().inflate(R.layout.edit_patient_info, null);
                            EditText name = inflater6.findViewById(R.id.name);
                            EditText gender = inflater6.findViewById(R.id.gender);
                            EditText birthdate = inflater6.findViewById(R.id.birthdate);
                            EditText address = inflater6.findViewById(R.id.address);
                            EditText phonenumber = inflater6.findViewById(R.id.number);
                            EditText room = inflater6.findViewById(R.id.room);
                            EditText device = inflater6.findViewById(R.id.device);

                            name.setEnabled(true);
                            gender.setEnabled(true);
                            birthdate.setEnabled(true);
                            address.setEnabled(true);
                            phonenumber.setEnabled(true);
                            room.setEnabled(true);
                            device.setEnabled(true);

                            edit.setVisibility(View.VISIBLE);
                            save.setVisibility(View.GONE);
                            cancel.setVisibility(View.GONE);

                            cancel.performClick();

                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                        }
                    }, 2000);

                }

                else if(!response.equals("failure")){
                    progressDialog14.dismiss();

                    progressDialog20 = new ProgressDialog(PatientInfo.this);
                    progressDialog20.show();
                    progressDialog20.setContentView(R.layout.connectionfailure);
                    progressDialog20.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog20.dismiss();

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

                            LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(PatientInfo.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

                else if(response.equals("failure")){
                    progressDialog14.dismiss();

                    progressDialog21 = new ProgressDialog(PatientInfo.this);
                    progressDialog21.show();
                    progressDialog21.setContentView(R.layout.failed_patient_info_update);
                    progressDialog21.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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
//                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                progressDialog14.dismiss();

                progressDialog20 = new ProgressDialog(PatientInfo.this);
                progressDialog20.show();
                progressDialog20.setContentView(R.layout.connectionfailure);
                progressDialog20.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog20.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(PatientInfo.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(PatientInfo.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(PatientInfo.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(PatientInfo.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(PatientInfo.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(PatientInfo.this, PatientInfo.class);
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

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
