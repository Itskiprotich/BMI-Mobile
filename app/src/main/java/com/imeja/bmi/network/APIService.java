package com.imeja.bmi.network;

import com.imeja.bmi.responses.LoginResponse;
import com.imeja.bmi.responses.PatientsResponse;
import com.imeja.bmi.responses.VisitsResponse;
import com.imeja.bmi.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("user/signin")
    Call<LoginResponse> checkLogin(
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("user/signup")
    Call<RegisterResponse> checkRegister(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("password") String password


    );
    @Headers("Accept: application/json")
    @GET("visits/view")
    Call<VisitsResponse> loadPatients();


    @Headers("Accept: application/json")
    @GET("patients/view")
    Call<PatientsResponse> loadPatientData();
}
