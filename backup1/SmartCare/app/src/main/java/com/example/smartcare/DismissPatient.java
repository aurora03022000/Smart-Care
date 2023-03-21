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

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DismissPatient extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    LinearLayout deleteAllButton;
    LinearLayout parentLayout, centerLayout;
    TextView numberOfRegisteredPatient;
    ScrollView scrollView;

    String currentUser, returnPress, concatinatedReturnPress, copyOfReturnPress;

    private String ipAddress = "192.168.254.114:8080";

    int numberOfPatient = 0;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6, progressDialog7;
    ProgressDialog progressDialog8, progressDialog9, progressDialog10, progressDialog11, progressDialog12, progressDialog13, progressDialog14, progressDialog15;

    String cancel = "0";

    String responseResult;

    @DrawableRes
    private int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Dismiss Patient");
        setContentView(R.layout.dismiss_patient);

        setUpToolbar();

        currentUser = getIntent().getStringExtra("USER_ID");
        returnPress = getIntent().getStringExtra("RETURN");

        copyOfReturnPress = returnPress;

        progressDialog = new ProgressDialog(DismissPatient.this);
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



        deleteAllButton = findViewById(R.id.deleteAllButton);

        deleteAllButton.setVisibility(View.GONE);

        deleteAllButton.setEnabled(false);
        deleteAllButton.setBackground(ContextCompat.getDrawable(this, R.drawable.disable_layout));

        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        returnPress = returnPress + "6";

                        Intent intentHome = new Intent(DismissPatient.this, Home.class);
                        intentHome.putExtra("USER_ID", currentUser);
                        intentHome.putExtra("RETURN", returnPress);
                        intentHome.putExtra("FIRST_TIME", "no");
                        startActivity(intentHome);
                        finish();
                        break;
                    case  R.id.contact:
                        returnPress = returnPress + "6";

                        Intent intentContact = new Intent(DismissPatient.this, Contact.class);
                        intentContact.putExtra("USER_ID", currentUser);
                        intentContact.putExtra("RETURN", returnPress);
                        intentContact.putExtra("FIRST_TIME", "no");
                        startActivity(intentContact);
                        finish();
                        break;

                    case  R.id.about:
                        returnPress = returnPress + "6";

                        Intent intentAbout = new Intent(DismissPatient.this, About.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);
                        finish();
                        break;

                    case  R.id.logout:

                        Intent intentLogout = new Intent(DismissPatient.this, Login.class);
                        new AlertDialog.Builder(DismissPatient.this)
                                .setMessage("Are you sure you want to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentLogout = new Intent(DismissPatient.this, Login.class);
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

            Intent intentAbout = new Intent(DismissPatient.this, Home.class);
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

            Intent intentAbout = new Intent(DismissPatient.this, Patient.class);
            intentAbout.putExtra("USER_ID", currentUser);
            intentAbout.putExtra("RETURN", returnPress);
            intentAbout.putExtra("FIRST_TIME", "no");
            startActivity(intentAbout);
            finish();
        }

//        else if(returnPress.equals("2")){
//            //bawasan nya ang value ka return press asta maubos
//            returnPress = copyOfReturnPress;
//            concatinatedReturnPress = returnPress.replaceFirst(".$","");
//            returnPress = concatinatedReturnPress;
//
//            Intent intentAbout = new Intent(DismissPatient.this, AllMenu.class);
//            intentAbout.putExtra("USER_ID", currentUser);
//            intentAbout.putExtra("RETURN", returnPress);
//            intentAbout.putExtra("FIRST_TIME", "no");
//            startActivity(intentAbout);
//            finish();
//        }

        else if(returnPress.equals("3")){
            //bawasan nya ang value ka return press asta maubos
            returnPress = copyOfReturnPress;
            concatinatedReturnPress = returnPress.replaceFirst(".$","");
            returnPress = concatinatedReturnPress;

            Intent intentAbout = new Intent(DismissPatient.this, About.class);
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

            Intent intentAbout = new Intent(DismissPatient.this, Account.class);
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

            Intent intentAbout = new Intent(DismissPatient.this, Contact.class);
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

            Intent intentDismiss = new Intent(DismissPatient.this, Monitoring.class);
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

            Intent intentPatientLogs = new Intent(DismissPatient.this, PatientLogs.class);
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

            Intent intentSharedPatient = new Intent(DismissPatient.this, SharePatient.class);
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

            Intent intentSearchShare = new Intent(DismissPatient.this, SearchUserShare.class);
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

            Intent intentRegister = new Intent(DismissPatient.this, Register.class);
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

            Intent intentShareMenu = new Intent(DismissPatient.this, SharingMenu.class);
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

            Intent intentShareMenu = new Intent(DismissPatient.this, PatientInfo.class);
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

            Intent intentShareMenu = new Intent(DismissPatient.this, LiveMonitoring.class);
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
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);

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
                        createNewPatientLayout();
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(DismissPatient.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                            createNewPatientLayout();
                        }
                    }



                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog2.dismiss();

                    progressDialog3 = new ProgressDialog(DismissPatient.this);
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.error_retrieve_name);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

                progressDialog1 = new ProgressDialog(DismissPatient.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);

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

                        Glide.with(DismissPatient.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                        editAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress + "6";

                                Intent intentAccount = new Intent(DismissPatient.this, Account.class);
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

                            Glide.with(DismissPatient.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                            editAccount.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress + "6";

                                    Intent intentAccount = new Intent(DismissPatient.this, Account.class);
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

                            progressDialog1 = new ProgressDialog(DismissPatient.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

                    progressDialog2 = new ProgressDialog(DismissPatient.this);
                    progressDialog2.show();
                    progressDialog2.setContentView(R.layout.error_retrieve_profile);
                    progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

                progressDialog1 = new ProgressDialog(DismissPatient.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

    private void createNewPatientLayout(){

        getPatientInfo(currentUser);

    }


    private void getPatientInfo(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientInfo.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                if(!response.equals("failure")){

                    if(response.equals("")){
                        addPatientInfo(response);
                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(DismissPatient.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                            addPatientInfo(response);
                        }
                    }


                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog4 = new ProgressDialog(DismissPatient.this);
                    progressDialog4.show();
                    progressDialog4.setContentView(R.layout.failure_patient_info);
                    progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

                progressDialog1 = new ProgressDialog(DismissPatient.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addPatientInfo(String responseResult) {
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
                int resId = getSelectableItemBackground();
                newLinearLayoutChildParent.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
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
                textViewPatientContent.setText("Patient Name :  " + patientName + "\n" + "Room Number :  " + roomNumber + "\n" + "Status : Registered ");
                Typeface typeface = ResourcesCompat.getFont(this, R.font.poppinsmedium);
                textViewPatientContent.setTypeface(typeface);
                textViewPatientContent.setTextColor(Color.WHITE);
                textViewPatientContent.setTextSize(11);
                textViewPatientContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dismiss_patient_icon1, 0);
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

                newLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(DismissPatient.this)
                                .setMessage("Are you sure you want to delete this patient?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog5 = new ProgressDialog(DismissPatient.this);
                                        progressDialog5.show();
                                        progressDialog5.setContentView(R.layout.progress_bar);
                                        progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        progressDialog5.setCancelable(false);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                removePatient(patientID1);
                                            }
                                        }, 2000);

                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });

                numberOfPatient = numberOfPatient;

                int numberOfPatientFinal = numberOfPatient;

                deleteAllButton = findViewById(R.id.deleteAllButton);

                deleteAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(DismissPatient.this)
                                .setMessage("Are you sure you want to delete all patient?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog5 = new ProgressDialog(DismissPatient.this);
                                        progressDialog5.show();
                                        progressDialog5.setContentView(R.layout.progress_bar);
                                        progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        progressDialog5.setCancelable(false);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                removeAllPatient(currentUser);
                                            }
                                        }, 2000);

                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                });



            }


        }

        numberOfRegisteredPatient = findViewById(R.id.numberOfRegisteredPatient);
        numberOfPatient = numberOfPatient - 1;
        getSharedPatientInfo(currentUser, numberOfPatient);

    }


    private void removePatient(String patientID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/removePatient.php";
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);


        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("success")){
                    progressDialog5.dismiss();

                    progressDialog7 = new ProgressDialog(DismissPatient.this);
                    progressDialog7.show();
                    progressDialog7.setContentView(R.layout.patient_remove_success);
                    progressDialog7.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            numberOfRegisteredPatient = findViewById(R.id.numberOfRegisteredPatient);
//                            numberOfPatient = numberOfPatient - 1;
//                            numberOfRegisteredPatient.setText("Total : " + numberOfPatient);
//                            if(numberOfPatient == 0){
//
//                                deleteAllButton.setEnabled(false);
//                                deleteAllButton.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.disable_layout));
//
//                                LinearLayout centerLayout;
//                                centerLayout = findViewById(R.id.centerLayout);
//
//                                LinearLayout newLinearLayoutChildParent = new LinearLayout(DismissPatient.this);
//                                LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
//                                        LinearLayout.LayoutParams.MATCH_PARENT,
//                                        LinearLayout.LayoutParams.WRAP_CONTENT
//                                );
//                                newLinearLayoutChildParent.setOrientation(LinearLayout.VERTICAL);
//                                newLinearLayoutChildParent.setLayoutParams(params1Parent);
//                                centerLayout.addView(newLinearLayoutChildParent);
//
//                                TextView noPatient = new TextView(DismissPatient.this);
//                                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
//                                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                                        LinearLayout.LayoutParams.WRAP_CONTENT
//                                );
//                                params3.setMargins(50, 40, 50, 0);
//                                params3.gravity = Gravity.CENTER_HORIZONTAL;
//                                noPatient.setText("No patient to be display, please add a patient.");
//                                Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
//                                noPatient.setLayoutParams(params3);
//                                noPatient.setGravity(Gravity.CENTER);
//                                noPatient.setTypeface(typeface);
//                                noPatient.setTextSize(12);
//                                newLinearLayoutChildParent.addView(noPatient);
//                            }
//
//                            else{
//                                deleteAllButton.setEnabled(true);
//                                deleteAllButton.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
//                            }

                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();

                        }
                    }, 2000);

                }

                else if(!response.equals("failure")){
                    progressDialog5.dismiss();

                    progressDialog6 = new ProgressDialog(DismissPatient.this);
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

                            LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
                                    intentAbout.putExtra("USER_ID", currentUser);
                                    intentAbout.putExtra("RETURN", returnPress);
                                    intentAbout.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAbout);
                                    finish();
                                }
                            });

//                            returnPress = returnPress + "6";
//
//                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
//                            intentAbout.putExtra("USER_ID", currentUser);
//                            intentAbout.putExtra("RETURN", returnPress);
//                            intentAbout.putExtra("FIRST_TIME", "no");
//                            startActivity(intentAbout);
//                            finish();


                        }
                    }, 2000);
                }

                else if(response.equals("failure")){
                    progressDialog5.dismiss();

                    progressDialog8 = new ProgressDialog(DismissPatient.this);
                    progressDialog8.show();
                    progressDialog8.setContentView(R.layout.failure_dismiss_patient);
                    progressDialog8.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                progressDialog5.dismiss();

                progressDialog6 = new ProgressDialog(DismissPatient.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

    private void removeAllPatient(String currentUserID){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/removeAllPatient.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //kung ang response is success
                if(response.equals("success")){
                    progressDialog5.dismiss();

                    progressDialog9 = new ProgressDialog(DismissPatient.this);
                    progressDialog9.show();
                    progressDialog9.setContentView(R.layout.all_patient_remove_success);
                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
                        }
                    }, 2000);

                }

                else  if(!response.equals("failure")){
                    progressDialog5.dismiss();

                    progressDialog6 = new ProgressDialog(DismissPatient.this);
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

                            LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                    progressDialog5.dismiss();

                    progressDialog9 = new ProgressDialog(DismissPatient.this);
                    progressDialog9.show();
                    progressDialog9.setContentView(R.layout.failure_remove_all_patient);
                    progressDialog9.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                progressDialog5.dismiss();

                progressDialog6 = new ProgressDialog(DismissPatient.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

    private void getSharedPatientInfo(String sharedPatiendID, int numberOfPatient){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getSharedPatientInfo.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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

                            progressDialog10 = new ProgressDialog(DismissPatient.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                            progressDialog.dismiss();
                        }
                    }


                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog11 = new ProgressDialog(DismissPatient.this);
                    progressDialog11.show();
                    progressDialog11.setContentView(R.layout.failure_get_shared_patient);
                    progressDialog11.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                progressDialog.dismiss(); // amo ni progressDialog gyapon ang i dismiss kay wala pa na dismiss ang
                // progressdialog umpisa pag loading nya kay amo ni ang final dismissal nya

                progressDialog10 = new ProgressDialog(DismissPatient.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                int resId = getSelectableItemBackground();
                newLinearLayoutChildParent.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
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
                params2.setMargins(0, 0, 10, 0);
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
                textViewPatientContent.setText("Patient Name :  " + patientName + "\n" + "Room Number :  " + roomNumber + "\n" + "Status : Shared ");
                Typeface typeface = ResourcesCompat.getFont(this, R.font.poppinsmedium);
                textViewPatientContent.setTypeface(typeface);
                textViewPatientContent.setTextColor(Color.WHITE);
                textViewPatientContent.setTextSize(11);
                textViewPatientContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dismiss_patient_icon1, 0);
                newLinearLayoutChild.addView(textViewPatientContent);

                LinearLayout newLinearLayoutSpace = new LinearLayout(this);
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params4.setMargins(0, 20, 0, 0);
                newLinearLayoutSpace.setLayoutParams(params4);
                centerLayout.addView(newLinearLayoutSpace);

                String patientID2 = patienID;
                newLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(DismissPatient.this)
                                .setMessage("Are you sure you want to delete this patient?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog12 = new ProgressDialog(DismissPatient.this);
                                        progressDialog12.show();
                                        progressDialog12.setContentView(R.layout.progress_bar);
                                        progressDialog12.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        progressDialog12.setCancelable(false);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                removeSharedPatient(patientID2);
                                            }
                                        }, 2000);

                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                });

                numberOfPatient = numberOfPatient;

                int numberOfPatientFinal = numberOfPatient;

                deleteAllButton = findViewById(R.id.deleteAllButton);

                deleteAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(DismissPatient.this)
                                .setMessage("Are you sure you want to delete all patient?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog12 = new ProgressDialog(DismissPatient.this);
                                        progressDialog12.show();
                                        progressDialog12.setContentView(R.layout.progress_bar);
                                        progressDialog12.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                        progressDialog12.setCancelable(false);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                removeAllPatient(currentUser);
                                            }
                                        }, 2000);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                });

            }


        }

        progressDialog.dismiss();

        LinearLayout middleLayout = findViewById(R.id.middleLayout);
        middleLayout.setVisibility(View.VISIBLE);

        deleteAllButton.setVisibility(View.VISIBLE);

        numberOfRegisteredPatient = findViewById(R.id.numberOfRegisteredPatient);
        numberOfPatient = numberOfPatient - 1;
        numberOfRegisteredPatient.setText("Total : " + numberOfPatient);

        if(numberOfPatient == 0){
            LinearLayout centerLayout;
            centerLayout = findViewById(R.id.centerLayout);

            deleteAllButton.setEnabled(false);
            deleteAllButton.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.disable_layout));

            LinearLayout newLinearLayoutChildParent = new LinearLayout(DismissPatient.this);
            LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            newLinearLayoutChildParent.setOrientation(LinearLayout.VERTICAL);
            newLinearLayoutChildParent.setLayoutParams(params1Parent);
            centerLayout.addView(newLinearLayoutChildParent);

            TextView noPatient = new TextView(DismissPatient.this);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params3.setMargins(50, 40, 50, 0);
            params3.gravity = Gravity.CENTER_HORIZONTAL;
            noPatient.setText("No patient to be display, please add a patient.");
            Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
            noPatient.setLayoutParams(params3);
            noPatient.setGravity(Gravity.CENTER);
            noPatient.setTypeface(typeface);
            noPatient.setTextSize(12);
            newLinearLayoutChildParent.addView(noPatient);

        }

        else{
            deleteAllButton.setEnabled(true);
            deleteAllButton.setBackground(ContextCompat.getDrawable(this, R.drawable.register_button_layout));
        }


        parentLayout = findViewById(R.id.parentLayout);
        centerLayout = findViewById(R.id.centerLayout);
        scrollView = findViewById(R.id.scrollView);
        LinearLayout deleteAllButton = findViewById(R.id.deleteAllButton);

        int parentHeight = parentLayout.getHeight();
//        int childHeight = centerLayout.getHeight();

        centerLayout.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int childHeight = centerLayout.getMeasuredHeight();

        String height = String.valueOf(parentHeight);
        String height1 = String.valueOf(childHeight);

        childHeight = childHeight + 200;

        if(childHeight < parentHeight){
            scrollView.setVerticalScrollBarEnabled(false);

            int parentHeight1 = parentLayout.getHeight();

            parentHeight1 = parentHeight1 - 200;

            LinearLayout.LayoutParams newParamsHeight = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    parentHeight1
            );
            centerLayout.setLayoutParams(newParamsHeight);
        }

        else{
            LinearLayout.LayoutParams paramsBottom = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsBottom.setMargins(100, 70, 100, 50);
            deleteAllButton.setLayoutParams(paramsBottom);
        }


    }

    private void removeSharedPatient(String patientID2){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/removeSharedPatient.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DismissPatient.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //kung ang response is success
                if(response.equals("success")){

                    progressDialog12.dismiss();

                    progressDialog15 = new ProgressDialog(DismissPatient.this);
                    progressDialog15.show();
                    progressDialog15.setContentView(R.layout.success_removed_shared_patient);
                    progressDialog15.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
                            intentAbout.putExtra("USER_ID", currentUser);
                            intentAbout.putExtra("RETURN", returnPress);
                            intentAbout.putExtra("FIRST_TIME", "no");
                            startActivity(intentAbout);
                            finish();
//                            numberOfRegisteredPatient = findViewById(R.id.numberOfRegisteredPatient);
//                            numberOfRegisteredPatient.setText("Total : " + numberOfPatient);

//                            if(numberOfPatient == 0){
//
//                                deleteAllButton.setEnabled(false);
//                                deleteAllButton.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.disable_layout));
//
//                                LinearLayout centerLayout;
//                                centerLayout = findViewById(R.id.centerLayout);
//
//                                LinearLayout newLinearLayoutChildParent = new LinearLayout(DismissPatient.this);
//                                LinearLayout.LayoutParams params1Parent = new LinearLayout.LayoutParams(
//                                        LinearLayout.LayoutParams.MATCH_PARENT,
//                                        LinearLayout.LayoutParams.WRAP_CONTENT
//                                );
//                                newLinearLayoutChildParent.setOrientation(LinearLayout.VERTICAL);
//                                newLinearLayoutChildParent.setLayoutParams(params1Parent);
//                                centerLayout.addView(newLinearLayoutChildParent);
//
//                                TextView noPatient = new TextView(DismissPatient.this);
//                                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
//                                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                                        LinearLayout.LayoutParams.WRAP_CONTENT
//                                );
//                                params3.setMargins(50, 40, 50, 0);
//                                params3.gravity = Gravity.CENTER_HORIZONTAL;
//                                noPatient.setText("No patient to be display, please add a patient.");
//                                Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
//                                noPatient.setLayoutParams(params3);
//                                noPatient.setGravity(Gravity.CENTER);
//                                noPatient.setTypeface(typeface);
//                                noPatient.setTextSize(12);
//                                newLinearLayoutChildParent.addView(noPatient);
//                            }
//
//                            else{
//                                deleteAllButton.setEnabled(true);
//                                deleteAllButton.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
//                            }
//                            progressDialog15.dismiss();
                        }
                    }, 2000);

                }

                else if(!response.equals("failure")){
                    progressDialog12.dismiss();

                    progressDialog13 = new ProgressDialog(DismissPatient.this);
                    progressDialog13.show();
                    progressDialog13.setContentView(R.layout.connectionfailure);
                    progressDialog13.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            progressDialog13.dismiss();

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

                            LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                    progressDialog12.dismiss();

                    progressDialog14 = new ProgressDialog(DismissPatient.this);
                    progressDialog14.show();
                    progressDialog14.setContentView(R.layout.failure_remove_shared_patient);
                    progressDialog14.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                progressDialog12.dismiss();

                progressDialog13 = new ProgressDialog(DismissPatient.this);
                progressDialog13.show();
                progressDialog13.setContentView(R.layout.connectionfailure);
                progressDialog13.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        progressDialog13.dismiss();

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

                        LinearLayout refreshLinearLayout = new LinearLayout(DismissPatient.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(DismissPatient.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(DismissPatient.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(DismissPatient.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(DismissPatient.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(DismissPatient.this, DismissPatient.class);
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
                params.put("patientID2", patientID2);

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