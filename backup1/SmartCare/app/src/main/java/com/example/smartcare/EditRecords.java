package com.example.smartcare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class EditRecords extends AppCompatDialogFragment {

    private String ipAddress = "192.168.254.114:8080";
    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4;
    LinearLayout cancel, save;
    EditText pulse, oxygen, bodyTemp, bloodpressure, respiratory, roomTemp, humidityEditText, airQualityEditText;
    String currentUser, returnPress, firstTime, concatinatedReturnPress, copyOfReturnPress;

    @DrawableRes
    public int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_record, null);
        pulse = view.findViewById(R.id.pulse);
        oxygen = view.findViewById(R.id.oxygen);
        bodyTemp = view.findViewById(R.id.bodyTemperature);
        bloodpressure = view.findViewById(R.id.bloodPressure);
        respiratory = view.findViewById(R.id.respiratoryRate);
        roomTemp = view.findViewById(R.id.roomTemperature);
        humidityEditText = view.findViewById(R.id.humidity);
        airQualityEditText = view.findViewById(R.id.airQuality);

        returnPress = getActivity().getIntent().getStringExtra("RETURN");
        copyOfReturnPress = returnPress;

        save = view.findViewById(R.id.saveBtn);
        cancel = view.findViewById(R.id.cancelBtn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog4 = new ProgressDialog(getActivity());
                progressDialog4.show();
                progressDialog4.setContentView(R.layout.progress_bar);
                progressDialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        insertData();
                    }
                }, 2000);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getRecords();

        builder.setView(view);

        setCancelable(true);

        return builder.create();
    }

    private void getRecords() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getHealthLog.php";

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
                if(!response.equals("failure")) {

                    if (response.equals("")) {
                        progressDialog.dismiss();
                    }

                    else {
                        String profileString = response.substring(0, 3);

                        if (profileString.equals("<br")) {

                            progressDialog.dismiss();

                            progressDialog1 = new ProgressDialog(getActivity());
                            progressDialog1.show();
                            progressDialog1.setContentView(R.layout.failure_retrieve_data);
                            progressDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog1.dismiss();
                                    dismiss();
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
                    progressDialog.dismiss();

                    progressDialog3 = new ProgressDialog(getActivity());
                    progressDialog3.show();
                    progressDialog3.setContentView(R.layout.failure_retrieve_data);
                    progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog3.dismiss();
                            dismiss();
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

                progressDialog2 = new ProgressDialog(getActivity());
                progressDialog2.show();
                progressDialog2.setContentView(R.layout.connectionfailure);
                progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog2.dismiss();
                        dismiss();
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

                pulse.setText(pulseRate);
                oxygen.setText(oxygenSaturation);
                bodyTemp.setText(bodyTemperature);
                bloodpressure.setText(bloodPressure);
                respiratory.setText(respiratoryRate);
                roomTemp.setText(roomTemperature);
                humidityEditText.setText(humidity);
                airQualityEditText.setText(airQuality);
            }

        }
    }

    //String pulse_rate_value, String air_quality_value, String blood_pressure_value, String body_temperature_value,
    //String humidity_value, String oxygen_saturation_value, String respiratory_rate_value, String room_temperature_value
    private void insertData() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updatePatientHealth.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                returnPress = returnPress;
                progressDialog4.dismiss();

                Intent intentAbout = new Intent(getActivity(), Monitoring.class);
                intentAbout.putExtra("USER_ID", currentUser);
                intentAbout.putExtra("RETURN", returnPress);
                intentAbout.putExtra("FIRST_TIME", "no");
                startActivity(intentAbout);

                dismiss();

                //ang response is halin sa API
                if(response.equals("success")){
                    progressDialog4.dismiss();

                    //returnPress = returnPress;

                    //Intent intentAbout = new Intent(getActivity(), Monitoring.class);
//                    intentAbout.putExtra("USER_ID", currentUser);
//                    intentAbout.putExtra("RETURN", returnPress);
//                    intentAbout.putExtra("FIRST_TIME", "no");
                    //startActivity(intentAbout);

                    //dismiss();
                }

                else if(!response.equals("failure")){

                }

                else if(response.equals("failure")){

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

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
//                params.put("currentUser", currentUser);
//                params.put("patientID", patientID);
//                params.put("pulse_rate_value", pulse_rate_value);
//                params.put("air_quality_value", air_quality_value);
//                params.put("blood_pressure_value", blood_pressure_value);
//                params.put("body_temperature_value", body_temperature_value);
//                params.put("humidity_value", humidity_value);
//                params.put("oxygen_saturation_value", oxygen_saturation_value);
//                params.put("respiratory_rate_value", respiratory_rate_value);
//                params.put("room_temperature_value", room_temperature_value);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

}
