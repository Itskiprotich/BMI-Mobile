package com.imeja.bmi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.imeja.bmi.auth.RegisterActivity;
import com.imeja.bmi.databinding.ActivityAddPatientBinding;
import com.imeja.bmi.utils.AppUtils;
import com.imeja.bmi.viewmodels.PatientViewModel;
import com.imeja.bmi.views.visits.AVisitActivity;
import com.imeja.bmi.views.visits.BVisitActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddPatientActivity extends AppCompatActivity {
    private ActivityAddPatientBinding binding;
    private ArrayAdapter adapter;
    private List<String> genderList;
    private String unique, gender;
    private PatientViewModel viewModel;
    private SweetAlertDialog dialog;
    private Calendar mCalendar;
    private String mFormat;
    private SimpleDateFormat mSdf;
    private DatePickerDialog.OnDateSetListener dateSetListener, dateSetListenerDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPatientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        genderList = new ArrayList<>();
        mFormat = "yyyy-MM-dd";
        mSdf = new SimpleDateFormat(mFormat, Locale.US);


        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
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
        mCalendar = Calendar.getInstance();
        dateSetListener = (view1, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();

        };
        dateSetListenerDob = (view1, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDobDate();

        };

        binding.edtDob.setOnClickListener(v -> new DatePickerDialog(AddPatientActivity.this,
                dateSetListenerDob,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());

        binding.edtDate.setOnClickListener(v -> new DatePickerDialog(AddPatientActivity.this,
                dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());
        disableTextInputEditText(binding.edtDob);
        disableTextInputEditText(binding.edtDate);

    }

    private void disableTextInputEditText(TextInputEditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    private void updateDobDate() {
        binding.edtDob.setText(mSdf.format(mCalendar.getTime()));
    }

    private void updateDate() {
        binding.edtDate.setText(mSdf.format(mCalendar.getTime()));
    }

    private void checkInput() {
        unique = binding.edtUnique.getText().toString();
        String date = binding.edtDate.getText().toString();
        String fname = binding.edtFirstname.getText().toString();
        String lname = binding.edtLastname.getText().toString();
        gender = binding.autGender.getText().toString();
        String dob = binding.edtDob.getText().toString();
        if (date.isEmpty()) {
            binding.edtDate.setError("Enter Registration Date");
            binding.edtDate.requestFocus();
            return;
        }
        if (fname.isEmpty()) {
            binding.edtFirstname.setError("Enter First Name");
            binding.edtFirstname.requestFocus();
            return;
        }
        if (lname.isEmpty()) {
            binding.edtLastname.setError("Enter Last Name");
            binding.edtLastname.requestFocus();
            return;
        }
        if (gender.isEmpty()) {
            Toast.makeText(AddPatientActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
            return;
        }  if (gender.equalsIgnoreCase("Gender")) {
            Toast.makeText(AddPatientActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dob.isEmpty()) {
            binding.edtDob.setError("Enter Last Name");
            binding.edtDob.requestFocus();
            return;
        }
        viewModel.addPatient(AddPatientActivity.this, binding, unique, fname, lname, gender, dob, date);
        viewModel.liveData.observe(this, response -> {
            if (response != null) {
                try {
                    dialog = new SweetAlertDialog(AddPatientActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setTitleText("Success")
                            .setContentText("Registration successful")
                            .setConfirmClickListener(on -> {
                                AddPatientActivity.this.finish();
                                on.dismiss();
                            })
                            .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                    dialog.show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}