package com.imeja.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.imeja.bmi.auth.LoginActivity;
import com.imeja.bmi.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isLoggedIn = Controller.isLoggedIn();

        new Handler().postDelayed(() -> {

            if (isLoggedIn) {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            } else {

                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            SplashActivity.this.finish();
        }, 6000);
    }
}