package com.imeja.bmi.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.imeja.bmi.Controller;
import com.imeja.bmi.databinding.ActivityVitalBinding;
import com.imeja.bmi.models.Patients;
import com.imeja.bmi.models.WhereTo;
import com.imeja.bmi.viewmodels.PatientViewModel;
import com.imeja.bmi.views.visits.AVisitActivity;
import com.imeja.bmi.views.visits.BVisitActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VitalActivity extends AppCompatActivity {
    private PatientViewModel viewModel;
    private ActivityVitalBinding binding;
    private Map<String, String> stringMap;
    private ArrayAdapter adapter;
    private List<Patients> patientsList;
    private List<String> stringList;
    private String visit_date, height, weight, bmi, patient_id, patient_name;
    private WhereTo whereTo;
    private Calendar mCalendar;
    private String mFormat;
    private SimpleDateFormat mSdf;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private double double_height,  double_weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mFormat = "yyyy-MM-dd";
        mSdf = new SimpleDateFormat(mFormat, Locale.US);
        mCalendar = Calendar.getInstance();
        dateSetListener = (view1, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            displayDate();

        };
        disableTextInputEditText(binding.edtVisitDate);

        patientsList = new ArrayList<>();
        stringList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        loadPatients();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.btnSubmit.setOnClickListener(v -> {
            checkInputs();
        });


        binding.edtVisitDate.setOnClickListener(v -> new DatePickerDialog(VitalActivity.this,
                dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());

        binding.edtWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (!editable.toString().isEmpty()) {
                        String newValue = editable.toString();

                        double_weight = Double.parseDouble(newValue);
                        binding.edtWeight.removeTextChangedListener(this);
                        int position = binding.edtWeight.getSelectionEnd();
                        binding.edtWeight.setText(String.valueOf((int) double_weight));
                        if (position > binding.edtWeight.getText().length()) {
                            binding.edtWeight.setSelection(binding.edtWeight.getText().length());
                        } else {
                            binding.edtWeight.setSelection(position);
                        }
                        binding.edtWeight.addTextChangedListener(this);

                        if (!binding.edtHeight.getText().toString().isEmpty()) {
                            double_height = Double.parseDouble(binding.edtHeight.getText().toString());
                          calculateBMI(double_weight,double_height);
                        }else{
                            Toast.makeText(VitalActivity.this, "Enter Height First", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void calculateBMI(double weight,double height) {
       try {
           double BMI = weight / (height * height);
           binding.edtBmi.setText(String.valueOf(BMI));
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void disableTextInputEditText(TextInputEditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }
    private void displayDate() {
        binding.edtVisitDate.setText(mSdf.format(mCalendar.getTime()));
    }

    private void loadPatients() {

        viewModel.loadPatients(VitalActivity.this);
        viewModel.patientsResponseMutableLiveData.observe(this, patientsResponse -> {
            if (patientsResponse != null) {
                try {
                    patientsList = patientsResponse.visits;
                    stringMap = new HashMap<>();
                    for (Patients patients : patientsList) {
                        stringList.add(patients.firstname);
                        stringMap.put(patients.firstname, patients.id);
                    }

                    adapter = new ArrayAdapter(VitalActivity.this,
                            android.R.layout.simple_list_item_1, stringList);
                    binding.autName.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void checkInputs() {
        patient_name = binding.autName.getText().toString();
        if (patient_name.isEmpty()){
            Toast.makeText(VitalActivity.this, "Select a Patient", Toast.LENGTH_SHORT).show();
            return;
        }
        patient_id = getIdFromName(patient_name);
        visit_date = binding.edtVisitDate.getText().toString();
        height = binding.edtHeight.getText().toString();
        weight = binding.edtWeight.getText().toString();
        bmi = binding.edtBmi.getText().toString();


        if (patient_id.isEmpty()) {
            Toast.makeText(VitalActivity.this, "Select Patient", Toast.LENGTH_SHORT).show();
            return;
        }
        if (visit_date.isEmpty()) {
            binding.edtVisitDate.setError("Enter Date");
            binding.edtVisitDate.requestFocus();
            return;
        }
        if (height.isEmpty()) {
            binding.edtHeight.setError("Enter Height");
            binding.edtHeight.requestFocus();
            return;
        }
        if (weight.isEmpty()) {
            binding.edtWeight.setError("Enter Weight");
            binding.edtWeight.requestFocus();
            return;
        }
        if (bmi.isEmpty()) {
            binding.edtBmi.setError("Enter BMI");
            binding.edtBmi.requestFocus();
            return;
        }
        viewModel.addVital(VitalActivity.this, binding, patient_id, visit_date, height, weight, bmi);
        viewModel.vitalResponseMutableLiveData.observe(this, vital -> {
            if (vital != null) {
                try {
                    whereTo = vital.data;
                    Controller.updateVital(whereTo);
                    if (whereTo.slug.equalsIgnoreCase("0")) {
                        startActivity(new Intent(VitalActivity.this, AVisitActivity.class));
                    } else {
                        startActivity(new Intent(VitalActivity.this, BVisitActivity.class));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String getIdFromName(String patient_name) {
        return stringMap.get(patient_name);
    }
}