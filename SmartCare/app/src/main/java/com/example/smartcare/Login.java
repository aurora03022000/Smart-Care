package com.example.smartcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private TextView invisibleUserID;
    private String ipAddress = "192.168.43.218";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // initializing our views
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        // adding on click listener to our button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (usernameInput.getText().toString().isEmpty() && passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postDataUsingVolley(usernameInput.getText().toString(), passwordInput.getText().toString());
            }
        });
    }

    private void postDataUsingVolley(String username, String password) {
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/login.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Login.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                if(response.equals("success")){
                    usernameInput.setText("");
                    passwordInput.setText("");

                    getUserID(username, password);
                }

                //kung ang response is failure
                else if(response.equals("failure")){
                    usernameInput.setText("");
                    passwordInput.setText("");
                    Toast.makeText(Login.this,  "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String username = respObj.getString("username");
                    String password = respObj.getString("password");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Login.this, "Invalid Network Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("username", username);
                params.put("password", password);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }


    private void getUserID(String username, String password){
        // url to post our data
        String url = "http://"+ipAddress+"/SmartCare/getUserID.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Login.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //ang response is halin sa API

                //kung ang response is success
                if(!response.equals("failure")){

                    Toast.makeText(Login.this,  "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("USER_VALUE", response);
                    startActivity(intent);
                    finish();
                }
                //kung ang response is failure
                else if(response.equals("failure")){
                    Toast.makeText(Login.this,  "Login Failure", Toast.LENGTH_SHORT).show();
                }

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String username = respObj.getString("username");
                    String password = respObj.getString("password");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Para ni sa error kung wala ka naka connect sa wifi sang hospital
                Toast.makeText(Login.this, "Connection Failure", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("username", username);
                params.put("password", password);

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