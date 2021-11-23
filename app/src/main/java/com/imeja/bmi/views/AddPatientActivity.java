package com.imeja.bmi.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.imeja.bmi.R;
import com.imeja.bmi.auth.RegisterActivity;
import com.imeja.bmi.databinding.ActivityAddPatientBinding;
import com.imeja.bmi.databinding.ActivityRegisterBinding;
import com.imeja.bmi.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class AddPatientActivity extends AppCompatActivity {
    private ActivityAddPatientBinding binding;
    private ArrayAdapter adapter;
    private List<String> genderList;
    private String unique, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        genderList = new ArrayList<>();

        unique = AppUtils.getAlphaNumericString(12);
        genderList.add("Male");
        genderList.add("Female");

        binding.edtUnique.setText(unique);
        adapter = new ArrayAdapter(AddPatientActivity.this,
                android.R.layout.simple_list_item_1, genderList);
        binding.autGender.setAdapter(adapter);

        binding.autGender.setOnItemClickListener((parent, view, position, id) -> {
            gender = binding.autGender.getText().toString();

        });
        binding.btnSubmit.setOnClickListener(v -> {
            checkInput();
        });

    }

    private void checkInput() {
        unique = binding.edtUnique.getText().toString();

    }
}