package com.imeja.bmi.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.imeja.bmi.Controller;
import com.imeja.bmi.MainActivity;
import com.imeja.bmi.R;
import com.imeja.bmi.databinding.ActivityLoginBinding;
import com.imeja.bmi.databinding.ActivityRegisterBinding;
import com.imeja.bmi.viewmodels.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        binding.btnSubmit.setOnClickListener(v -> {
            email = binding.edtEmail.getText().toString();
            password = binding.edtPassword.getText().toString();
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
            authViewModel.checkLogin(LoginActivity.this, binding, email, password);
            authViewModel.liveData.observe(LoginActivity.this, login -> {
                if (login != null) {
                    if (login.loginDetails != null) {
                        try {
                            Controller.updateProfile(login.loginDetails);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}