package com.imeja.bmi.ui.home;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imeja.bmi.databinding.FragmentHomeBinding;
import com.imeja.bmi.network.APIService;
import com.imeja.bmi.network.RetrofitInstance;
import com.imeja.bmi.responses.VisitsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private APIService mApiService;
    public MutableLiveData<VisitsResponse> liveData;
    public MutableLiveData<Integer> responseCode;
    private Call<VisitsResponse> patientReponseCall;

    public HomeViewModel() {
        responseCode=new MutableLiveData<>();
        liveData=new MutableLiveData<>();
    }


    public void loadVisits(Context context, FragmentHomeBinding binding, String date) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        patientReponseCall = mApiService.loadPatients(date);
        patientReponseCall.enqueue(new Callback<VisitsResponse>() {
            @Override
            public void onResponse(Call<VisitsResponse> call, Response<VisitsResponse> response) {
                try {
                    responseCode.postValue(response.code());
                    binding.pbLoading.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        liveData.postValue(response.body());

                    } else {
                        responseCode.postValue(null);
                        liveData.postValue(null);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<VisitsResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                liveData.postValue(null);
                System.out.println(call);
            }
        });
    }
}