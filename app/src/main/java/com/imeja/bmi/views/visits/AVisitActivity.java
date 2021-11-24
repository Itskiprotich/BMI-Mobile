package com.imeja.bmi.views.visits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.imeja.bmi.Controller;
import com.imeja.bmi.MainActivity;
import com.imeja.bmi.R;
import com.imeja.bmi.databinding.ActivityAvisitBinding;
import com.imeja.bmi.databinding.ActivityVitalBinding;
import com.imeja.bmi.models.Patients;
import com.imeja.bmi.viewmodels.PatientViewModel;
import com.imeja.bmi.views.AddPatientActivity;
import com.imeja.bmi.views.VitalActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AVisitActivity extends AppCompatActivity {
    private Map<String, String> stringMap;
    private ArrayAdapter adapter;
    private List<Patients> patientsList;
    private List<String> stringList;
    private PatientViewModel viewModel;
    private ActivityAvisitBinding binding;
    private Calendar mCalendar;
    private String mFormat;
    private SimpleDateFormat mSdf;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String patient_name, visit_date, general_health, comments, patient_id;
    private SweetAlertDialog dialog;
    private boolean on_diet, on_drugs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvisitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFormat = "yyyy-MM-dd";
        mSdf = new SimpleDateFormat(mFormat, Locale.US);
        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        stringList = new ArrayList<>();
        patientsList = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        disableTextInputEditText(binding.edtVisitDate);

        dateSetListener = (view1, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();

        };
        loadPatients(Controller.getCurrentPatient());

        binding.edtVisitDate.setOnClickListener(v -> new DatePickerDialog(AVisitActivity.this,
                dateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show());

        binding.btnSubmit.setOnClickListener(v -> {
            checkInput();
        });

    }

    private void disableTextInputEditText(TextInputEditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    private void checkInput() {
        try {
            patient_name = binding.autName.getText().toString();
            visit_date = binding.edtVisitDate.getText().toString();
            comments = binding.etComment.getText().toString();
            if (patient_name.isEmpty()) {
                Toast.makeText(AVisitActivity.this, "Select Patient ID", Toast.LENGTH_SHORT).show();
                return;
            }
            patient_id = getIdFromName(patient_name);
            if (patient_id != null) {
                if (patient_id.isEmpty()) {
                    Toast.makeText(AVisitActivity.this, "Select Patient ID", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (visit_date.isEmpty()) {
                binding.edtVisitDate.setError("Enter Visit Date");
                binding.edtVisitDate.requestFocus();
                return;
            }
            if (binding.rbGood.isChecked()) {
                general_health = "Good";
            } else if (binding.rbPoor.isChecked()) {
                general_health = "Good";
            }
            if (binding.rbDietYes.isChecked()) {
                on_diet = true;
            } else if (binding.rbDietNo.isChecked()) {
                on_diet = false;
            }
            if (general_health.isEmpty()) {
                Toast.makeText(AVisitActivity.this, "Select Health Status", Toast.LENGTH_SHORT).show();
                return;
            }

            on_drugs = false;

            if (comments.isEmpty()) {
                binding.etComment.setError("Enter Comment");
                binding.etComment.requestFocus();
                return;
            }
            viewModel.addVisit(AVisitActivity.this, binding, patient_id, visit_date, general_health, on_diet, on_drugs, comments);
            viewModel.visitResponseMutableLiveData.observe(this, visitResponse -> {
                if (visitResponse != null) {
                    try {
                        if (visitResponse.data.slug.equalsIgnoreCase("0")) {
                            dialog = new SweetAlertDialog(AVisitActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            dialog.setTitleText("Success")
                                    .setContentText("Visit saved successfully")
                                    .setConfirmClickListener(on -> {
                                        AVisitActivity.this.finishAffinity();
                                        startActivity(new Intent(AVisitActivity.this, MainActivity.class));
                                        on.dismiss();
                                    })
                                    .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                            dialog.show();
                        } else {
                            dialog = new SweetAlertDialog(AVisitActivity.this, SweetAlertDialog.ERROR_TYPE);
                            dialog.setTitleText("Failed")
                                    .setContentText(visitResponse.data.message)
                                    .setConfirmClickListener(on -> {

                                        on.dismiss();
                                    })
                                    .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                            dialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AVisitActivity.this, "Check All Data fields", Toast.LENGTH_SHORT).show();
        }

    }

    private String getIdFromName(String patient_name) {
        return stringMap.get(patient_name);
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

    private void updateDate() {
        binding.edtVisitDate.setText(mSdf.format(mCalendar.getTime()));
    }


    private void loadPatients(String currentPatient) {

        viewModel.loadCurrentPatients(AVisitActivity.this, currentPatient);
        viewModel.patientsResponseMutableLiveData.observe(this, patientsResponse -> {
            if (patientsResponse != null) {
                try {
                    patientsList = patientsResponse.visits;
                    stringMap = new HashMap<>();
                    for (Patients patients : patientsList) {
                        stringList.add(patients.firstname);
                        stringMap.put(patients.firstname, patients.id);
                    }

                    adapter = new ArrayAdapter(AVisitActivity.this,
                            android.R.layout.simple_list_item_1, stringList);
                    binding.autName.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

}