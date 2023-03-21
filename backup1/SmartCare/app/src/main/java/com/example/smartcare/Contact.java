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
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.util.HashMap;
import java.util.Map;

public class Contact extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    String currentUser, returnPress, concatinatedReturnPress, copyOfReturnPress;

    EditText messageContact;
    LinearLayout submit_btn;

    LinearLayout parentLayout, childLayout;

    ScrollView scrollView;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6;

    String cancel="0";

    ImageView facebookLink, emailLink, number;

    private String ipAddress = "192.168.254.114:8080";

    @DrawableRes
    private int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        setTitle("Contact");

        setUpToolbar();

        currentUser = getIntent().getStringExtra("USER_ID");
        returnPress = getIntent().getStringExtra("RETURN");

        copyOfReturnPress = returnPress;

        progressDialog = new ProgressDialog(Contact.this);
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

        facebookLink = findViewById(R.id.facebookLink);
        emailLink = findViewById(R.id.emailLink);
        number = findViewById(R.id.number);

        facebookLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Contact.this, "Facebook Link Copied Successfully", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Facebook Link", "www.facebook.com");
                if (clipboard == null || clip == null)
                    return;
                clipboard.setPrimaryClip(clip);
            }
        });

        emailLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Contact.this, "Email Link Copied Successfully", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Facebook Link", "www.gmail.com");
                if (clipboard == null || clip == null)
                    return;
                clipboard.setPrimaryClip(clip);
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Contact.this, "Mobile Number Copied Successfully", Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Facebook Link", "09691922320");
                if (clipboard == null || clip == null)
                    return;
                clipboard.setPrimaryClip(clip);
            }
        });


        messageContact = findViewById(R.id.messageContact);

        submit_btn = findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContactValue = messageContact.getText().toString();

                        if(messageContactValue.equals("")){
                            Toast.makeText(Contact.this,  "field/s should not be empty", Toast.LENGTH_SHORT).show();
                            messageContact .setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_empty_field_layout));
                            messageContact.requestFocus();

                            messageContact.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    messageContact.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.contact_input_layout));
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if(messageContact.getText().toString().isEmpty()){
                                        messageContact.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_empty_field_layout));
                                    }
                                }
                            });
                        }

                        else{
                            new AlertDialog.Builder(Contact.this)
                                    .setMessage("Are you sure you want to submit this request?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            progressDialog4 = new ProgressDialog(Contact.this);
                                            progressDialog4.show();
                                            progressDialog4.setContentView(R.layout.progress_bar);
                                            progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                            progressDialog4.setCancelable(false);

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    InputMethodManager imm = (InputMethodManager)Contact.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                                    messageContact.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.contact_input_layout));

                                                    messageContact.clearFocus();

                                                    submitRequest(currentUser, messageContactValue);
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
                        returnPress = returnPress + "5";

                        Intent intentHome = new Intent(Contact.this, Patient.class);
                        intentHome.putExtra("USER_ID", currentUser);
                        intentHome.putExtra("RETURN", returnPress);
                        intentHome.putExtra("FIRST_TIME", "no");
                        startActivity(intentHome);
                        finish();
                        break;

                    case  R.id.contact:
                        returnPress = returnPress + "5";

                        Intent intentContact = new Intent(Contact.this, Contact.class);
                        intentContact.putExtra("USER_ID", currentUser);
                        intentContact.putExtra("RETURN", returnPress);
                        intentContact.putExtra("FIRST_TIME", "no");
                        startActivity(intentContact);
                        finish();
                        break;

                    case  R.id.about:
                        returnPress = returnPress + "5";

                        Intent intentAbout = new Intent(Contact.this, About.class);
                        intentAbout.putExtra("USER_ID", currentUser);
                        intentAbout.putExtra("RETURN", returnPress);
                        intentAbout.putExtra("FIRST_TIME", "no");
                        startActivity(intentAbout);
                        finish();
                        break;

                    case  R.id.logout:

                        new AlertDialog.Builder(Contact.this)
                                .setMessage("Are you sure you want to logout?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentLogout = new Intent(Contact.this, Login.class);
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

            Intent intentAbout = new Intent(Contact.this, Home.class);
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

            Intent intentAbout = new Intent(Contact.this, Patient.class);
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
//            Intent intentAbout = new Intent(Contact.this, AllMenu.class);
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

            Intent intentAbout = new Intent(Contact.this, About.class);
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

            Intent intentAbout = new Intent(Contact.this, Account.class);
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

            Intent intentAbout = new Intent(Contact.this, DismissPatient.class);
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

            Intent intentDismiss = new Intent(Contact.this, Monitoring.class);
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

            Intent intentPatientLogs = new Intent(Contact.this, PatientLogs.class);
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

            Intent intentSharedPatient = new Intent(Contact.this, SharePatient.class);
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

            Intent intentSearchShare = new Intent(Contact.this, SearchUserShare.class);
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

            Intent intentRegister = new Intent(Contact.this, Register.class);
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

            Intent intentShareMenu = new Intent(Contact.this, SharingMenu.class);
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

            Intent intentShareMenu = new Intent(Contact.this, PatientInfo.class);
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

            Intent intentShareMenu = new Intent(Contact.this, LiveMonitoring.class);
            intentShareMenu.putExtra("USER_ID", currentUser);
            intentShareMenu.putExtra("RETURN", returnPress);
            intentShareMenu.putExtra("FIRST_TIME", "no");
            startActivity(intentShareMenu);
            finish();
        }

    }

    private void submitRequest(String currentUserID, String message) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/sendRequest.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Contact.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){
                    progressDialog4.dismiss();

                    progressDialog5 = new ProgressDialog(Contact.this);
                    progressDialog5.show();
                    progressDialog5.setContentView(R.layout.request_submitted_success);
                    progressDialog5.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messageContact.setText("");
                            progressDialog5.dismiss();
                        }
                    }, 2000);
                }

                else if(!response.equals("failure")){
                    progressDialog4.dismiss();

                    progressDialog1 = new ProgressDialog(Contact.this);
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

                            LinearLayout refreshLinearLayout = new LinearLayout(Contact.this);
                            LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                            refreshLinearLayout.setPadding(17,17,17,17);
                            refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            refreshLinearLayout.setForeground(ContextCompat.getDrawable(Contact.this, resId));
                            refreshLinearLayoutParams.gravity = Gravity.CENTER;
                            refreshLinearLayout.setGravity(Gravity.CENTER);
                            refreshLinearLayout.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_button_layout));
                            refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                            middleLayout.addView(refreshLinearLayout);


                            TextView textViewRefreshIcon = new TextView(Contact.this);
                            LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                            textViewRefreshIconParams.gravity = Gravity.CENTER;
                            textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.refresh_icon));
                            textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                            refreshLinearLayout.addView(textViewRefreshIcon);

                            TextView textViewRefreshText = new TextView(Contact.this);
                            LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                            textViewRefreshTextParams.gravity = Gravity.CENTER;
                            Typeface typeface = ResourcesCompat.getFont(Contact.this, R.font.poppinsbold);
                            textViewRefreshText.setTypeface(typeface);
                            textViewRefreshText.setTextColor(Color.WHITE);
                            textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                            textViewRefreshText.setText("Refresh");
                            textViewRefreshText.setTextSize(15);
                            refreshLinearLayout.addView(textViewRefreshText);

                            TextView textViewRefreshSubtext = new TextView(Contact.this);
                            LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            textViewRefreshSubtext.setGravity(Gravity.CENTER);
                            textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                            Typeface typeface1 = ResourcesCompat.getFont(Contact.this, R.font.poppinsmedium);
                            textViewRefreshSubtext.setTypeface(typeface1);
                            textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                            textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                            textViewRefreshSubtext.setTextSize(12);
                            middleLayout.addView(textViewRefreshSubtext);

                            refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress;

                                    Intent intentAbout = new Intent(Contact.this, Contact.class);
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
                    progressDialog4.dismiss();

                    progressDialog6 = new ProgressDialog(Contact.this);
                    progressDialog6.show();
                    progressDialog6.setContentView(R.layout.failure_send_request);
                    progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Contact.this, Contact.class);
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
                progressDialog4.dismiss();

                progressDialog1 = new ProgressDialog(Contact.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Contact.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Contact.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Contact.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Contact.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Contact.this, Contact.class);
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
                params.put("message", message);

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
        RequestQueue queue = Volley.newRequestQueue(Contact.this);

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
                        progressDialog.dismiss();
                        LinearLayout middleLayout = findViewById(R.id.middleLayout);
                        middleLayout.setVisibility(View.VISIBLE);

                    }

                    else {
                        String profileString = response.substring(0,3);

                        if(profileString.equals("<br")){
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Contact.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Contact.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Contact.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Contact.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Contact.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Contact.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Contact.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Contact.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Contact.this, Contact.class);
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

                        else{
                            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                            View header = navigationView.getHeaderView(0);
                            TextView staff_name = header.findViewById(R.id.staff_name);

                            staff_name.setText(response);
                            progressDialog.dismiss();
                            LinearLayout middleLayout = findViewById(R.id.middleLayout);
                            middleLayout.setVisibility(View.VISIBLE);
                        }
                    }

                }
                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog3 = new ProgressDialog(Contact.this);
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.error_retrieve_name);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Contact.this, Contact.class);
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

                progressDialog1 = new ProgressDialog(Contact.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Contact.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Contact.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Contact.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Contact.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Contact.this, Contact.class);
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
        RequestQueue queue = Volley.newRequestQueue(Contact.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){

                    if(response.equals("")){
                        getStaffName(currentUser);

                        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                        View header = navigationView.getHeaderView(0);
                        ImageView profile = header.findViewById(R.id.profile);
                        LinearLayout editAccount;
                        editAccount = header.findViewById(R.id.editAccount);

                        Glide.with(Contact.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                        editAccount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress + "5";

                                Intent intentAccount = new Intent(Contact.this, Account.class);
                                intentAccount.putExtra("USER_ID", currentUser);
                                intentAccount.putExtra("RETURN", returnPress);
                                intentAccount.putExtra("FIRST_TIME", "no");
                                startActivity(intentAccount);
                                finish();
                            }
                        });
                    }

                    else {
                        String profileString = response.substring(0,8);

                        if(profileString.equals("profile/")){
                            getStaffName(currentUser);

                            navigationView = (NavigationView) findViewById(R.id.navigation_menu);
                            View header = navigationView.getHeaderView(0);
                            ImageView profile = header.findViewById(R.id.profile);
                            LinearLayout editAccount;
                            editAccount = header.findViewById(R.id.editAccount);

                            Glide.with(Contact.this).load("http://192.168.254.114:8080/SmartCare/"+response).into(profile);

                            editAccount.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    returnPress = returnPress + "5";

                                    Intent intentAccount = new Intent(Contact.this, Account.class);
                                    intentAccount.putExtra("USER_ID", currentUser);
                                    intentAccount.putExtra("RETURN", returnPress);
                                    intentAccount.putExtra("FIRST_TIME", "no");
                                    startActivity(intentAccount);
                                    finish();
                                }
                            });
                        }

                        else{
                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(Contact.this);
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

                                    LinearLayout refreshLinearLayout = new LinearLayout(Contact.this);
                                    LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                                    refreshLinearLayout.setPadding(17,17,17,17);
                                    refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    refreshLinearLayout.setForeground(ContextCompat.getDrawable(Contact.this, resId));
                                    refreshLinearLayoutParams.gravity = Gravity.CENTER;
                                    refreshLinearLayout.setGravity(Gravity.CENTER);
                                    refreshLinearLayout.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_button_layout));
                                    refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                                    middleLayout.addView(refreshLinearLayout);


                                    TextView textViewRefreshIcon = new TextView(Contact.this);
                                    LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                                    textViewRefreshIconParams.gravity = Gravity.CENTER;
                                    textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.refresh_icon));
                                    textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                                    refreshLinearLayout.addView(textViewRefreshIcon);

                                    TextView textViewRefreshText = new TextView(Contact.this);
                                    LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                                    textViewRefreshTextParams.gravity = Gravity.CENTER;
                                    Typeface typeface = ResourcesCompat.getFont(Contact.this, R.font.poppinsbold);
                                    textViewRefreshText.setTypeface(typeface);
                                    textViewRefreshText.setTextColor(Color.WHITE);
                                    textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                                    textViewRefreshText.setText("Refresh");
                                    textViewRefreshText.setTextSize(15);
                                    refreshLinearLayout.addView(textViewRefreshText);

                                    TextView textViewRefreshSubtext = new TextView(Contact.this);
                                    LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textViewRefreshSubtext.setGravity(Gravity.CENTER);
                                    textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                                    Typeface typeface1 = ResourcesCompat.getFont(Contact.this, R.font.poppinsmedium);
                                    textViewRefreshSubtext.setTypeface(typeface1);
                                    textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                                    textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                                    textViewRefreshSubtext.setTextSize(12);
                                    middleLayout.addView(textViewRefreshSubtext);

                                    refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            returnPress = returnPress;

                                            Intent intentAbout = new Intent(Contact.this, Contact.class);
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

                    progressDialog2 = new ProgressDialog(Contact.this);
                    progressDialog2.show();
                    progressDialog2.setContentView(R.layout.error_retrieve_profile);
                    progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            returnPress = returnPress;

                            Intent intentAbout = new Intent(Contact.this, Contact.class);
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

                progressDialog1 = new ProgressDialog(Contact.this);
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

                        LinearLayout refreshLinearLayout = new LinearLayout(Contact.this);
                        LinearLayout.LayoutParams refreshLinearLayoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        refreshLinearLayoutParams.setMargins(125, 0, 125, 20);
                        refreshLinearLayout.setPadding(17,17,17,17);
                        refreshLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        refreshLinearLayout.setForeground(ContextCompat.getDrawable(Contact.this, resId));
                        refreshLinearLayoutParams.gravity = Gravity.CENTER;
                        refreshLinearLayout.setGravity(Gravity.CENTER);
                        refreshLinearLayout.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.register_button_layout));
                        refreshLinearLayout.setLayoutParams(refreshLinearLayoutParams);
                        middleLayout.addView(refreshLinearLayout);


                        TextView textViewRefreshIcon = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshIconParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshIconParams.setMargins(0, 0, 10, 0);
                        textViewRefreshIconParams.gravity = Gravity.CENTER;
                        textViewRefreshIcon.setBackground(ContextCompat.getDrawable(Contact.this, R.drawable.refresh_icon));
                        textViewRefreshIcon.setLayoutParams(textViewRefreshIconParams);
                        refreshLinearLayout.addView(textViewRefreshIcon);

                        TextView textViewRefreshText = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshTextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshTextParams.setMargins(0, 2, 10, 0);
                        textViewRefreshTextParams.gravity = Gravity.CENTER;
                        Typeface typeface = ResourcesCompat.getFont(Contact.this, R.font.poppinsbold);
                        textViewRefreshText.setTypeface(typeface);
                        textViewRefreshText.setTextColor(Color.WHITE);
                        textViewRefreshText.setLayoutParams(textViewRefreshTextParams);
                        textViewRefreshText.setText("Refresh");
                        textViewRefreshText.setTextSize(15);
                        refreshLinearLayout.addView(textViewRefreshText);

                        TextView textViewRefreshSubtext = new TextView(Contact.this);
                        LinearLayout.LayoutParams textViewRefreshSubtextParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        textViewRefreshSubtext.setGravity(Gravity.CENTER);
                        textViewRefreshSubtextParams.setMargins(50, 0, 50, 0);
                        Typeface typeface1 = ResourcesCompat.getFont(Contact.this, R.font.poppinsmedium);
                        textViewRefreshSubtext.setTypeface(typeface1);
                        textViewRefreshSubtext.setLayoutParams(textViewRefreshSubtextParams);
                        textViewRefreshSubtext.setText("An error had occurred, please refresh the page");
                        textViewRefreshSubtext.setTextSize(12);
                        middleLayout.addView(textViewRefreshSubtext);

                        refreshLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnPress = returnPress;

                                Intent intentAbout = new Intent(Contact.this, Contact.class);
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

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}