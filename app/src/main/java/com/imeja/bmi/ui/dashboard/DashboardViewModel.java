package com.imeja.bmi.ui.dashboard;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imeja.bmi.databinding.FragmentDashboardBinding;
import com.imeja.bmi.network.APIService;
import com.imeja.bmi.network.RetrofitInstance;
import com.imeja.bmi.responses.PatientsResponse;
import com.imeja.bmi.responses.VisitsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {
    private APIService mApiService;
    public MutableLiveData<PatientsResponse> liveData;
    public MutableLiveData<Integer> responseCode;
    private Call<PatientsResponse> patientReponseCall;

    public DashboardViewModel() {
        liveData = new MutableLiveData<>();
        responseCode = new MutableLiveData<>();
    }


    public void loadPatients(Context context, FragmentDashboardBinding binding) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        patientReponseCall = mApiService.loadPatientData();
        patientReponseCall.enqueue(new Callback<PatientsResponse>() {
            @Override
            public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                try {
                    responseCode.postValue(response.code());
                    binding.pbLoading.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        liveData.postValue(response.body());

                    } else {
                        responseCode.postValue(null);
                        liveData.postValue(null);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PatientsResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                liveData.postValue(null);
                System.out.println(call);
            }
        });
    }
}