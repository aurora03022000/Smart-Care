package com.example.smartcare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewDialogLog extends AppCompatDialogFragment {

    private String ipAddress = "192.168.254.114:8080";

    TextView dateTime, pulseRate, oxygenSaturation, bodyTemperature, bloodPressure, respiratoryRate, roomTemperature, humidity, airQuality;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6;

    @DrawableRes
    public int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getLogs();
            }
        }, 500);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box_log, null);
        LinearLayout close = view.findViewById(R.id.close);
        dateTime = view.findViewById(R.id.dateTime);
        pulseRate = view.findViewById(R.id.pulseRate);
        oxygenSaturation = view.findViewById(R.id.oxygenSaturation);
        bodyTemperature = view.findViewById(R.id.bodyTemperature);
        bloodPressure = view.findViewById(R.id.bloodPressure);
        respiratoryRate = view.findViewById(R.id.respiratoryRate);
        roomTemperature = view.findViewById(R.id.roomTemperature);
        humidity = view.findViewById(R.id.humidity);
        airQuality = view.findViewById(R.id.airQuality);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        setCancelable(false);

        return builder.create();
    }

    private void getLogs() {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientClickedLog.php";

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
                        displayLog(response);
                    }

                    else {
                        String firstThreeChar = response.substring(0,3);

                        if(firstThreeChar.equals("<br")){
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

                        else{
                            progressDialog.dismiss();
                            displayLog(response);
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog.dismiss();

                    progressDialog2 = new ProgressDialog(getActivity());
                    progressDialog2.show();
                    progressDialog2.setContentView(R.layout.get_logs_failure);
                    progressDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog2.dismiss();
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

    public void displayLog(String responseResult) {
        String date;
        String time;
        String pulseRateResponse;
        String oxygenSaturationResponse;
        String bodyTemperatureResponse;
        String bloodPressureResponse;
        String respiratoryRateResponse;
        String roomTemperatureResponse;
        String humidityResponse;
        String airQualityResponse;


        for(;;) {


            if (responseResult.equals(" ") || responseResult.equals("")) {
                break;
            }

            else {


                time = responseResult.replaceAll("\\`.*", "");
                String newWord = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord.trim();
                responseResult = newWord;

                pulseRateResponse = responseResult.replaceAll("\\`.*", "");
                String newWord1 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord1.trim();
                responseResult = newWord1;

                oxygenSaturationResponse = responseResult.replaceAll("\\`.*", "");
                String newWord2 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord2.trim();
                responseResult = newWord2;

                bodyTemperatureResponse = responseResult.replaceAll("\\`.*", "");
                String newWord3 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord3.trim();
                responseResult = newWord3;

                bloodPressureResponse = responseResult.replaceAll("\\`.*", "");
                String newWord4 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord4.trim();
                responseResult = newWord4;

                respiratoryRateResponse = responseResult.replaceAll("\\`.*", "");
                String newWord5 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord5.trim();
                responseResult = newWord5;

                roomTemperatureResponse = responseResult.replaceAll("\\`.*", "");
                String newWord6 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord6.trim();
                responseResult = newWord6;

                humidityResponse = responseResult.replaceAll("\\`.*", "");
                String newWord7 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord7.trim();
                responseResult = newWord7;

                airQualityResponse = responseResult.replaceAll("\\`.*", "");
                String newWord8 = responseResult.substring(responseResult.indexOf("`") + 1);
                newWord8.trim();
                responseResult = newWord8;

                date = responseResult.replaceAll("\\%.*", "");
                String newWord9 = responseResult.substring(responseResult.indexOf("%") + 1);
                newWord9.trim();
                responseResult = newWord9;

                dateTime.setText(date + " - " + time);
                pulseRate.setText(" " + pulseRateResponse + " bpm ");
                oxygenSaturation.setText(" " + oxygenSaturationResponse + " % ");
                bodyTemperature.setText(" " + bodyTemperatureResponse + " °C ");
                bloodPressure.setText(" " + bloodPressureResponse + " mm Hg ");
                respiratoryRate.setText(" " + respiratoryRateResponse + " bpm ");
                roomTemperature.setText(" " + roomTemperatureResponse + " °C ");
                humidity.setText(" " + humidityResponse + " % ");
                airQuality.setText(" " + airQualityResponse + " AQI ");
            }

        }
    }
}
