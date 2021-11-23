package com.imeja.bmi.viewmodels;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imeja.bmi.Controller;
import com.imeja.bmi.databinding.ActivityAddPatientBinding;
import com.imeja.bmi.databinding.ActivityAvisitBinding;
import com.imeja.bmi.databinding.ActivityBvisitBinding;
import com.imeja.bmi.databinding.ActivityVitalBinding;
import com.imeja.bmi.databinding.FragmentDashboardBinding;
import com.imeja.bmi.network.APIService;
import com.imeja.bmi.network.RetrofitInstance;
import com.imeja.bmi.responses.AddPatientResponse;
import com.imeja.bmi.responses.LoginResponse;
import com.imeja.bmi.responses.PatientsResponse;
import com.imeja.bmi.responses.RegisterResponse;
import com.imeja.bmi.responses.VisitResponse;
import com.imeja.bmi.responses.VitalResponse;
import com.imeja.bmi.views.AddPatientActivity;
import com.imeja.bmi.views.VitalActivity;
import com.imeja.bmi.views.visits.AVisitActivity;
import com.imeja.bmi.views.visits.BVisitActivity;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientViewModel extends ViewModel {
    private APIService mApiService;
    private static final String TAG = PatientViewModel.class.getSimpleName();
    public MutableLiveData<AddPatientResponse> liveData;
    public MutableLiveData<Integer> responseCode;
    private Call<AddPatientResponse> addPatientResponseCall;

    public MutableLiveData<PatientsResponse> patientsResponseMutableLiveData;
    private Call<PatientsResponse> patientReponseCall;

    public MutableLiveData<VitalResponse> vitalResponseMutableLiveData;
    private Call<VitalResponse> vitalResponseCall;

    public MutableLiveData<VisitResponse> visitResponseMutableLiveData;
    private Call<VisitResponse> visitResponseCall;

    public PatientViewModel() {
        liveData = new MutableLiveData<>();
        visitResponseMutableLiveData = new MutableLiveData<>();
        patientsResponseMutableLiveData = new MutableLiveData<>();
        vitalResponseMutableLiveData = new MutableLiveData<>();
        responseCode = new MutableLiveData<>();
    }

    public void loadPatients(Context context) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);

        patientReponseCall = mApiService.loadPatientData();
        patientReponseCall.enqueue(new Callback<PatientsResponse>() {
            @Override
            public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                try {
                    responseCode.postValue(response.code());

                    if (response.isSuccessful()) {
                        patientsResponseMutableLiveData.postValue(response.body());

                    } else {
                        responseCode.postValue(null);
                        patientsResponseMutableLiveData.postValue(null);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PatientsResponse> call, Throwable t) {
                responseCode.postValue(null);

                patientsResponseMutableLiveData.postValue(null);
                System.out.println(call);
            }
        });
    }

    public void addPatient(Context context, ActivityAddPatientBinding binding,
                           String unique, String fname, String lname, String gender, String dob, String reg_date) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        addPatientResponseCall = mApiService.addPatient(unique, fname, lname, gender, dob, reg_date);
        addPatientResponseCall.enqueue(new Callback<AddPatientResponse>() {
            @Override
            public void onResponse(Call<AddPatientResponse> call, Response<AddPatientResponse> response) {
                responseCode.postValue(response.code());
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());

                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    responseCode.postValue(null);
                    liveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<AddPatientResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                liveData.postValue(null);
                Log.w(TAG, "onResponse: " + t);
                System.out.println(call);
            }
        });
    }

    public void addVital(Context context, ActivityVitalBinding binding,
                         String patient_id, String visit_date, String height, String weight, String bmi) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        vitalResponseCall = mApiService.addVital(patient_id, visit_date, height, weight, bmi);
        vitalResponseCall.enqueue(new Callback<VitalResponse>() {
            @Override
            public void onResponse(Call<VitalResponse> call, Response<VitalResponse> response) {
                responseCode.postValue(response.code());
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    vitalResponseMutableLiveData.postValue(response.body());

                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    responseCode.postValue(null);
                    vitalResponseMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<VitalResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                vitalResponseMutableLiveData.postValue(null);
                Log.w(TAG, "onResponse: " + t);
                System.out.println(call);
            }
        });
    }

    public void addVisit(Context context, ActivityAvisitBinding binding,
                         String patient_id,  String visit_date, String general_health, boolean on_diet,
                         boolean on_drugs, String comments) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        String vital_id= Controller.getVital();
        visitResponseCall = mApiService.addVisit(patient_id,vital_id,visit_date, general_health, on_diet, on_drugs, comments);
        visitResponseCall.enqueue(new Callback<VisitResponse>() {
            @Override
            public void onResponse(Call<VisitResponse> call, Response<VisitResponse> response) {
                responseCode.postValue(response.code());
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    visitResponseMutableLiveData.postValue(response.body());

                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    responseCode.postValue(null);
                    visitResponseMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<VisitResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                visitResponseMutableLiveData.postValue(null);
                Log.w(TAG, "onResponse: " + t);
                System.out.println(call);
            }
        });
    }

    public void addVisitB(Context context, ActivityBvisitBinding binding, String patient_id, String visit_date, String general_health, boolean on_diet, boolean on_drugs, String comments) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.btnSubmit.setVisibility(View.GONE);
        String vital_id= Controller.getVital();
        visitResponseCall = mApiService.addVisit(patient_id,vital_id ,visit_date,general_health, on_diet, on_drugs, comments);
        visitResponseCall.enqueue(new Callback<VisitResponse>() {
            @Override
            public void onResponse(Call<VisitResponse> call, Response<VisitResponse> response) {
                responseCode.postValue(response.code());
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    visitResponseMutableLiveData.postValue(response.body());

                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    responseCode.postValue(null);
                    visitResponseMutableLiveData.postValue(null);

                }
            }

            @Override
            public void onFailure(Call<VisitResponse> call, Throwable t) {
                responseCode.postValue(null);
                binding.pbLoading.setVisibility(View.GONE);
                binding.btnSubmit.setVisibility(View.VISIBLE);
                visitResponseMutableLiveData.postValue(null);
                Log.w(TAG, "onResponse: " + t);
                System.out.println(call);
            }
        });
    }

    public void loadCurrentPatients(Context context, String currentPatient) {
        mApiService = RetrofitInstance.getRetroClient(context).create(APIService.class);

        patientReponseCall = mApiService.loadCurrentPatients(currentPatient);
        patientReponseCall.enqueue(new Callback<PatientsResponse>() {
            @Override
            public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                try {
                    responseCode.postValue(response.code());

                    if (response.isSuccessful()) {
                        patientsResponseMutableLiveData.postValue(response.body());

                    } else {
                        responseCode.postValue(null);
                        patientsResponseMutableLiveData.postValue(null);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PatientsResponse> call, Throwable t) {
                responseCode.postValue(null);

                patientsResponseMutableLiveData.postValue(null);
                System.out.println(call);
            }
        });
    }
}
