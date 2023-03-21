package com.example.smartcare;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Monitoring extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring);

        String patientID = getIntent().getStringExtra("PATIENT_ID");
        Toast.makeText(Monitoring.this, patientID, Toast.LENGTH_SHORT).show();

    }
}
