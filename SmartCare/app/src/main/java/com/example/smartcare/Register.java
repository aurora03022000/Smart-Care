package com.example.smartcare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.view.View;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText nameInputRegister ,numberInputRegister,birthdateInputRegister,ageInputRegister, addressInputRegister;
    private AutoCompleteTextView  deviceIDInputRegister, roomInputRegister, genderInputRegister;
    private Button buttonRegister;
    private String number,name, birthdate, age, gender, address, deviceID;
    Calendar calendar;
    private String Items;
    String currentUserID;
    private String ipAddress = "192.168.43.28";

    private static final String[] genderList = new String[]{
            "Male", "Female"
    };

    List<String> deviceIDList = new ArrayList<>();
    List<String> roomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        currentUserID = getIntent().getStringExtra("USER_VALUE");

        getDeviceID();
        getRoom();

        AutoCompleteTextView genderDropdownFirstInput = findViewById(R.id.genderInputRegister);
        AutoCompleteTextView deviceIDDropdownFirstInput = findViewById(R.id.deviceIDInputRegister);
        AutoCompleteTextView roomDropdownFirstInput = findViewById(R.id.roomInputRegister);

        genderDropdownFirstInput.setThreshold(0);
        deviceIDDropdownFirstInput.setThreshold(0);
        roomDropdownFirstInput.setThreshold(0);

        nameInputRegister = findViewById(R.id.nameInputRegister);
        genderInputRegister = findViewById(R.id.genderInputRegister);
        numberInputRegister = findViewById(R.id.mobileNumberInputRegister);
        birthdateInputRegister = findViewById(R.id.birthdateInputRegister);
        ageInputRegister = findViewById(R.id.ageInputRegister);
        addressInputRegister = findViewById(R.id.addressInputRegister);
        deviceIDInputRegister = findViewById(R.id.deviceIDInputRegister);
        roomInputRegister = findViewById(R.id.roomInputRegister);

        buttonRegister = findViewById(R.id.buttonRegister);

//        spinner = (Spinner) findViewById(R.id.genderInputRegister);

//        String[] genderList = getResources().getStringArray(R.array.genders);
//
        AutoCompleteTextView deviceIDInputRegister = findViewById(R.id.deviceIDInputRegister);
        ArrayAdapter<String> deviceIDAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, deviceIDList);
        deviceIDInputRegister.setAdapter(deviceIDAdapter);

        AutoCompleteTextView roomInputRegister = findViewById(R.id.roomInputRegister);
        ArrayAdapter<String> roomAdadapter = new ArrayAdapter<String>(this, R.layout.spinner_item, roomList);
        roomInputRegister.setAdapter(roomAdadapter);

        AutoCompleteTextView genderInputRegister = findViewById(R.id.genderInputRegister);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genderList);
        genderInputRegister.setAdapter(genderAdapter);


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
                String Format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                birthdateInputRegister.setText(sdf.format(calendar.getTime()));
            }
        };

        birthdateInputRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(Register.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // adding on click listener to our button.
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String genderValueString = genderInputRegister.getText().toString();
//                Integer.parseInt(deviceIDInputRegister.getText().toString());
                String deviceIDValueString = deviceIDInputRegister.getText().toString();
                String roomValueString = roomInputRegister.getText().toString();

                // validating if the text field is empty or not.
                if ( numberInputRegister.getText().toString().isEmpty() || birthdateInputRegister.getText().toString().isEmpty() || nameInputRegister.getText().toString().isEmpty() || ageInputRegister.getText().toString().isEmpty()
                || genderInputRegister.getText().toString().isEmpty() || addressInputRegister.getText().toString().isEmpty() || deviceIDValueString.equals("") || roomValueString.equals("")) {



                    if(nameInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Name should not be empty", Toast.LENGTH_SHORT).show();
                        nameInputRegister.requestFocus();
                        return;
                    }

                    else if(genderInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Gender should not be empty", Toast.LENGTH_SHORT).show();
                        genderInputRegister.requestFocus();
                        return;

                    }

                    else if(!genderValueString.equals("Male") && !genderValueString.equals("Female")){
                        Toast.makeText(Register.this, "Invalid gender selection", Toast.LENGTH_SHORT).show();
                        genderInputRegister.requestFocus();
                        return;
                    }


                    else if(numberInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Number should not be empty", Toast.LENGTH_SHORT).show();
                        numberInputRegister.requestFocus();
                        return;
                    }

                    else if(birthdateInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Birth Date should not be empty", Toast.LENGTH_SHORT).show();
                        birthdateInputRegister.requestFocus();
                        return;
                    }

                    else if(ageInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Age should not be empty", Toast.LENGTH_SHORT).show();
                        ageInputRegister.requestFocus();
                        return;
                    }

                    else if(addressInputRegister.getText().toString().isEmpty()){
                        Toast.makeText(Register.this, "Address should not be empty", Toast.LENGTH_SHORT).show();
                        addressInputRegister.requestFocus();
                        return;
                    }


                    else if(deviceIDValueString.equals("")){
                        Toast.makeText(Register.this, "Device ID should not be empty", Toast.LENGTH_SHORT).show();
                        deviceIDInputRegister.requestFocus();
                        return;
                    }

                    else if(roomValueString.equals("")){
                        Toast.makeText(Register.this, "Room number should not be empty", Toast.LENGTH_SHORT).show();
                        roomInputRegister.requestFocus();
                        return;
                    }
                }

                else{
                    validateDeviceID(deviceIDInputRegister.getText().toString());
                }

            }
        });
    }

    private void registerPatientData(String name, String gender, String number, String birthdate, String age, String address, String deviceID, String room, String currentUserID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/register.php";

        Integer.parseInt(room);
        Integer.parseInt(deviceID);
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                if(response.equals("success")){
                    nameInputRegister.setText("");
                    genderInputRegister.setText("");
                    numberInputRegister.setText("");
                    birthdateInputRegister.setText("");
                    ageInputRegister.setText("");
                    addressInputRegister.setText("");
                    deviceIDInputRegister.setText("");
                    roomInputRegister.setText("");

                    Toast.makeText(Register.this,  "Patient Registered Successfully", Toast.LENGTH_SHORT).show();
                }

                //kung ang response is failure
                else if(response.equals("failure")){
                    Toast.makeText(Register.this,  response, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this,  "Patient Registration Failed", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(Register.this,  "Connection with server is failed", Toast.LENGTH_SHORT).show();
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
                    String age = respObj.getString("age");
                    String address = respObj.getString("address");
                    String deviceID = respObj.getString("deviceID");
                    String room = respObj.getString("room");
                    String currentUserID = respObj.getString("currentUserID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Register.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
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
                params.put("age", age);
                params.put("address", address);
                params.put("deviceID", deviceID);
                params.put("room", room);
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

    private void getDeviceID(){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/deviceID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                addDeviceID(response);

                //kung ang response is failure
                if(response.equals("failure")){
                    Toast.makeText(Register.this,  "Failure to get Devices ID", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Register.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                addRoom(response);

                //kung ang response is failure
                if(response.equals("failure")){
                    Toast.makeText(Register.this,  "Failure to get Room Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Register.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void addRoom(String responseResultRoom) {
        // do something
        String roomNumberValue;

        for(;;){

            if(responseResultRoom.equals(" ") || responseResultRoom.equals("")){
                break;
            }

            else{
                roomNumberValue = responseResultRoom.replaceAll("\\,.*", "");

                roomList.add(roomNumberValue);

                String newWord1 = responseResultRoom.substring(responseResultRoom.indexOf(",")+1);
                newWord1.trim();

                responseResultRoom = newWord1;

            }

        }

    }

    public void addDeviceID(String responseResult) {
        // do something
        String datavalue;

        for(;;){

            if(responseResult.equals(" ") || responseResult.equals("")){
                break;
            }

            else{
                datavalue = responseResult.replaceAll("\\,.*", "");

                deviceIDList.add(datavalue);

                String newWord = responseResult.substring(responseResult.indexOf(",")+1);
                newWord.trim();

                responseResult = newWord;

            }

        }

    }

    private void validateDeviceID(String deviceID) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/validateDeviceIDExist.php";

        Integer.parseInt(deviceID);
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){
                    //nothing will happen
                    validateRoom(roomInputRegister.getText().toString());
                }
                //kung ang response is success
                else if(response.equals("failure")){
                    Toast.makeText(Register.this,  "Device ID does not exist", Toast.LENGTH_SHORT).show();
                    deviceIDInputRegister.requestFocus();
                }

                else{
                    Toast.makeText(Register.this,  "Connection with server is failed", Toast.LENGTH_SHORT).show();
                    deviceIDInputRegister.requestFocus();
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String deviceID = respObj.getString("deviceID");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Register.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("deviceID", deviceID);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void validateRoom(String roomNumber) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/validateRoomExist.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Register.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API
                if(response.equals("success")){
                    registerPatientData(nameInputRegister.getText().toString(), genderInputRegister.getText().toString() ,numberInputRegister.getText().toString(),
                            birthdateInputRegister.getText().toString(), ageInputRegister.getText().toString(),
                            addressInputRegister.getText().toString(), deviceIDInputRegister.getText().toString(), roomInputRegister.getText().toString(), currentUserID);
                }
                //kung ang response is success
                else if(response.equals("failure")){
                    Toast.makeText(Register.this,  "Room Number does not exist", Toast.LENGTH_SHORT).show();
                    roomInputRegister.requestFocus();
                }

                else{
                    Toast.makeText(Register.this,  "Connection with server is failed", Toast.LENGTH_SHORT).show();
                    roomInputRegister.requestFocus();
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String roomNumber = respObj.getString("roomNumber");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Register.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("roomNumber", roomNumber);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void cancelLabel(View view) {
        // do something
        Intent intent = new Intent(Register.this, Menu.class);
        intent.putExtra("USER_VALUE", currentUserID);
        startActivity(intent);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
