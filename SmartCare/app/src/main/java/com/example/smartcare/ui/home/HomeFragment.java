package com.example.smartcare.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartcare.Login;
import com.example.smartcare.MainActivity;
import com.example.smartcare.R;

import android.widget.Toast;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LinearLayout register_patient_btn = (LinearLayout) getView().findViewById(R.id.register_patient_btn);
    TextView myAwesomeTextView = (TextView) getView().findViewById(R.id.hello);

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        myAwesomeTextView.setText("My Awesome Text");

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }


}