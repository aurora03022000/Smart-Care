package com.example.smartcare;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

public class Home extends AppCompatActivity {
    String currentUser;
    private String ipAddress = "192.168.43.28";
    LinearLayout patient_list_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        currentUser = getIntent().getStringExtra("USER_VALUE");

        patient_list_btn = findViewById(R.id.patient_list_btn);

        patient_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this,  "Login Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, Menu.class);
                intent.putExtra("USER_VALUE", currentUser);
                startActivity(intent);
                finish();
            }
        });

    }
}
