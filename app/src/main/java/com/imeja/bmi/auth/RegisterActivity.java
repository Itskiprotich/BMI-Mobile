package com.imeja.bmi.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.imeja.bmi.R;
import com.imeja.bmi.databinding.ActivityRegisterBinding;
import com.imeja.bmi.utils.AppUtils;
import com.imeja.bmi.viewmodels.AuthViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;
    private String firstname, lastname, email, password, confirm_password;
    private SweetAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnSubmit.setOnClickListener(v -> {
            checkInput();
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

    private void checkInput() {
        firstname = binding.edtFirstname.getText().toString();
        lastname = binding.edtLastname.getText().toString();
        email = binding.edtEmail.getText().toString();
        password = binding.edtPassword.getText().toString();
        confirm_password = binding.edtConfirmPassword.getText().toString();

        if (firstname.isEmpty()) {
            binding.edtFirstname.setError("Enter First Name");
            binding.edtFirstname.requestFocus();
            return;
        }
        if (lastname.isEmpty()) {
            binding.edtLastname.setError("Enter Last Name");
            binding.edtLastname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            binding.edtEmail.setError("Enter Email Address");
            binding.edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.edtPassword.setError("Enter Password");
            binding.edtPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.edtPassword.setError("Password should be 6 characters");
            binding.edtPassword.requestFocus();
            return;
        }
        if (confirm_password.isEmpty()) {
            binding.edtConfirmPassword.setError("Enter Confirm Password");
            binding.edtConfirmPassword.requestFocus();
            return;
        }
        if (!password.equalsIgnoreCase(confirm_password)) {
            binding.edtConfirmPassword.setError("Password Does not Match");
            binding.edtConfirmPassword.requestFocus();
            return;
        }
        if (AppUtils.isOnline(RegisterActivity.this)) {
            authViewModel.createAccount(RegisterActivity.this, binding, firstname,
                    lastname, email, password);
            authViewModel.responseMutableLiveData.observe(this, response -> {
                if (response != null) {
                    try {
                        if (response.data != null) {
                            if (response.data.proceed.equalsIgnoreCase("0")) {
                                dialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.setTitleText("Success")
                                        .setContentText("Registration successful")
                                        .setConfirmClickListener(on -> {
                                            RegisterActivity.this.finish();
                                            on.dismiss();
                                        })
                                        .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                            } else {
                                dialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                                dialog.setTitleText("Failed")
                                        .setContentText(response.data.message)
                                        .setConfirmClickListener(on -> {
                                            RegisterActivity.this.finish();
                                            on.dismiss();
                                        })
                                        .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                            }

                        } else {
                            dialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                            dialog.setTitleText("Request Failed!!")
                                    .setContentText("Experience problems loading data, please try again")
                                    .setConfirmClickListener(on -> {
                                        on.dismiss();
                                    })
                                    .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                        }
                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                        dialog.setTitleText("Request Failed!!")
                                .setContentText("Experience problems loading data, please try again")
                                .setConfirmClickListener(on -> {
                                    on.dismiss();
                                })
                                .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
                        dialog.show();
                    }

                }
            });
        } else {
            dialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("Internet Connection!!")
                    .setContentText("An active Internet connection is required")
                    .setConfirmClickListener(on -> {
                        on.dismiss();
                    })
                    .setNeutralButtonTextColor(Color.parseColor("#297545")).setCancelable(false);
            dialog.show();
        }
    }
}