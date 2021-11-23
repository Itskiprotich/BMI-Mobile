package com.imeja.bmi.viewmodels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imeja.bmi.databinding.ActivityLoginBinding;
import com.imeja.bmi.databinding.ActivityRegisterBinding;
import com.imeja.bmi.network.APIService;
import com.imeja.bmi.network.RetrofitInstance;
import com.imeja.bmi.responses.LoginResponse;
import com.imeja.bmi.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private APIService mApiService;
    private static final String TAG = AuthViewModel.class.getSimpleName();
    public MutableLiveData<LoginResponse> liveData;
    public MutableLiveData<RegisterResponse> responseMutableLiveData;
    public MutableLiveData<Integer> responseCode;
    private Call<LoginResponse> loginResponseCall;
    private Call<RegisterResponse> registerResponseCall;

    public AuthViewModel() {
        liveData = new MutableLiveData<>();
        responseMutableLiveData = new MutableLiveData<>();
        responseCode = new MutableLiveData<>();
    }

    public MutableLiveData<LoginResponse> handleLogin() {
        return liveData;
    }


    public void checkLogin(Context context, ActivityLoginBinding binding,
                           String email, String password) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        loginResponseCall = mApiService.checkLogin(email, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                responseCode.postValue(response.code());
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                    handleLogin();
                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    responseCode.postValue(null);
                    liveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                liveData.postValue(null);
                Log.w(TAG, "onResponse: " + t);
                System.out.println(call);
            }
        });
    }

    public void createAccount(Context context, ActivityRegisterBinding binding, String firstname,
                              String lastname, String email, String password) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        registerResponseCall = mApiService.checkRegister(firstname, lastname, email, password);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                responseCode.postValue(response.code());
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    responseMutableLiveData.postValue(response.body());
                    handleLogin();
                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    responseCode.postValue(null);
                    responseMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                responseCode.postValue(null);
                responseMutableLiveData.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                Log.w(TAG, "onResponse: " + t);
                System.out.println(call);
            }
        });
    }
}