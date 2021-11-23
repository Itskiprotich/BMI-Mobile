package com.imeja.bmi.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imeja.bmi.R;
import com.imeja.bmi.databinding.ActivityAddPatientBinding;
import com.imeja.bmi.databinding.ActivityVisitsBinding;

public class VisitsActivity extends AppCompatActivity {

    private ActivityVisitsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        binding = ActivityVisitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}