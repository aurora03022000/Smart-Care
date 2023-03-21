package com.example.smartcare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class NewHumidityDialogBox extends AppCompatDialogFragment {

    private NewHumidityDialogBox.dialogListener listener;
    private String ipAddress = "192.168.254.114:8080";
    String patientID, valueToEdit;

    ProgressDialog progressDialog, progressDialog1, progressDialog2, progressDialog3, progressDialog4, progressDialog5, progressDialog6;

    @DrawableRes
    public int getSelectableItemBackground() {
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box_humidity, null);
        LinearLayout cancelBtn = view.findViewById(R.id.cancelBtn);
        LinearLayout saveBtn = view.findViewById(R.id.saveBtn);
        EditText newValue = view.findViewById(R.id.newValue);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newValue.getText().toString();
                listener.applyTexts4(value);
                dismiss();
            }
        });

        /*saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                progressDialog3 = new ProgressDialog(getActivity());
                progressDialog3.show();
                progressDialog3.setContentView(R.layout.progress_bar);
                progressDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                progressDialog3.setCancelable(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String humidity = newValue.getText().toString();

                        getPatientID(humidity);
                    }
                }, 2000);

            }
        });*/

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        setCancelable(false);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewHumidityDialogBox.dialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement dialogListener");
        }
    }

    public interface dialogListener{
        void applyTexts4(String value);
    }

    private void getPatientID(String humidity) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getPatientID.php";

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
                        progressDialog3.dismiss();
                        patientID = response;

                        valueToEdit = "Humidity";

                        updateData(humidity, valueToEdit, patientID);
                    }

                    else {
                        String firstChar = response.substring(0,1);

                        boolean isNumeric = firstChar.chars().allMatch( Character::isDigit );

                        if(isNumeric){
                            progressDialog3.dismiss();
                            patientID = response;

                            valueToEdit = "Humidity";

                            updateData(humidity, valueToEdit, patientID);
                        }

                        else {
                            progressDialog3.dismiss();

                            dismiss();
                        }
                    }
                }

                //kung ang response is success
                else if(response.equals("failure")){
                    progressDialog3.dismiss();

                    dismiss();

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
                progressDialog3.dismiss();

                dismiss();

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

    private void updateData(String value, String valueToEdit, String currentPatientID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/updateStatus.php";

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
                    progressDialog3.dismiss();

                    progressDialog6 = new ProgressDialog(getActivity());
                    progressDialog6.show();
                    progressDialog6.setContentView(R.layout.success_save_humidity);
                    progressDialog6.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                            Intent intentAccount = new Intent(getActivity(), Monitoring.class);
                            startActivity(intentAccount);
                        }
                    }, 2000);

                }

                else if(!response.equals("failure")){
                    progressDialog3.dismiss();

                    dismiss();

                }

                else if(response.equals("failure")){
                    progressDialog3.dismiss();

                    dismiss();

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
                progressDialog3.dismiss();

                dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("value", value);
                params.put("valueToEdit", valueToEdit);
                params.put("currentPatientID", currentPatientID);

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
