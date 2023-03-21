package com.example.smartcare;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {
    String currentUser;
    LinearLayout register_btn;
    private String ipAddress = "192.168.43.218";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        currentUser = getIntent().getStringExtra("USER_VALUE");

        getNumberOfPatient(currentUser);

//        home_btn = findViewById(R.id.home);
          register_btn = findViewById(R.id.registerButton);
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
            register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // do something
                    Intent intent = new Intent(Menu.this, Register.class);
                    intent.putExtra("USER_VALUE", currentUser);
                    startActivity(intent);
                    finish();
                }
            });
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

    private void getNumberOfPatient(String currentUserID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientAmount.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Menu.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(!response.equals("failure")){
                    int integerResponse = Integer.parseInt(response);

                    createNewPatientLayout(integerResponse);
                }
                //kung ang response is success
                else if(response.equals("failure")){
                    Toast.makeText(Menu.this,  "Failure to Retrieve Patients", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Menu.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(Menu.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("failure")){
                    addPatientInfo(response);
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    Toast.makeText(Menu.this,  "Failed to retrieve patients information", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Menu.this, "Connection failure", Toast.LENGTH_SHORT).show();
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


    public void addPatientInfo(String responseResult) {
        String patientName;
        String roomNumber;
        String patienID;

        int i=0;
        TextView newLayoutClick;

        for(;;){

            if(responseResult.equals(" ") || responseResult.equals("")){
                break;
            }

            else{
                patientName = responseResult.replaceAll("\\,.*", "");

                String newWord = responseResult.substring(responseResult.indexOf(",")+1);
                newWord.trim();

                responseResult = newWord;

                patienID = responseResult.replaceAll("\\,.*", "");

                String newWord1 = responseResult.substring(responseResult.indexOf(",")+1);
                newWord1.trim();

                responseResult = newWord1;

                roomNumber = responseResult.replaceAll("\\%.*", "");

                String newWord2 = responseResult.substring(responseResult.indexOf("%")+1);
                newWord2.trim();

                responseResult = newWord2;

                int numberOfPatientName = patientName.length();
                String convertedPatientName = patientName.substring(0,21) + "...";

                TextView patientTextView = new TextView(this);
                    LinearLayout centerLayout;
                    centerLayout = findViewById(R.id.centerLinearLayout);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.setMargins(50, 30, 50, 0);
                    patientTextView.setLayoutParams(params);
                    patientTextView.setBackground(ContextCompat.getDrawable(this, R.drawable.patient_info_layout));
                    patientTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.patient, 0, R.drawable.arrow, 0);
                    patientTextView.setPadding(30, 30, 30, 30);
                    patientTextView.setCompoundDrawablePadding(10);
                    Typeface typeface = ResourcesCompat.getFont(this, R.font.poppinsmedium);
                    patientTextView.setTypeface(typeface);
                    patientTextView.setTextSize(12);
                    patientTextView.setTextColor(Color.WHITE);
                    patientTextView.setMaxLines(10);
                    patientTextView.setText("Name :  " + convertedPatientName + "\n" + "Patient ID :  " + patienID + "\n" + "Room Number :  " + roomNumber);

                    centerLayout.addView(patientTextView);

                    String patientID1 = patienID;
                    patientTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent1 = new Intent(Menu.this, Monitoring.class);
                            intent1.putExtra("PATIENT_ID", patientID1);
                            startActivity(intent1);
                            finish();
                        }
                    });

                if(i == 0){
                    params.setMargins(50, 60, 50, 0);
                    i++;
                }

                if(responseResult.equals(" ") || responseResult.equals("")){
                    params.setMargins(50, 30, 50, 60);
                }

            }

        }

    }
}
