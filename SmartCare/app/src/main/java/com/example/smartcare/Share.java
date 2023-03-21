package com.example.smartcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
public class Share extends AppCompatActivity{

    RelativeLayout sharePatient, pendingRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);

        sharePatient = findViewById(R.id.share_patient);
        pendingRequest = findViewById(R.id.pending_request);

        sharePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePatient.setVisibility(View.VISIBLE);
                pendingRequest.setVisibility(View.GONE);
            }
        });

        pendingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePatient.setVisibility(View.GONE);
                pendingRequest.setVisibility(View.VISIBLE);
            }
        });
    }
}
