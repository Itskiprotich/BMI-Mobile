package com.imeja.bmi.network;

import com.imeja.bmi.responses.AddPatientResponse;
import com.imeja.bmi.responses.LoginResponse;
import com.imeja.bmi.responses.PatientsResponse;
import com.imeja.bmi.responses.VisitResponse;
import com.imeja.bmi.responses.VisitsResponse;
import com.imeja.bmi.responses.RegisterResponse;
import com.imeja.bmi.responses.VitalResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("visits/view")
    Call<VisitsResponse> loadPatients(
            @Field("visit_date") String date
    );


    @Headers("Accept: application/json")
    @GET("patients/view")
    Call<PatientsResponse> loadPatientData();

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("patients/register")
    Call<AddPatientResponse> addPatient(
            @Field("unique") String unique,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("reg_date") String reg_date
    );


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("vital/add")
    Call<VitalResponse> addVital(
            @Field("patient_id") String patient_id,
            @Field("visit_date") String visit_date,
            @Field("height") String height,
            @Field("weight") String weight,
            @Field("bmi") String bmi
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("visits/add")
    Call<VisitResponse> addVisit(
            @Field("patient_id") String patient_id,
            @Field("vital_id") String vital_id,
            @Field("visit_date") String visit_date,
            @Field("general_health") String general_health,
            @Field("on_diet") boolean on_diet,
            @Field("on_drugs") boolean on_drugs,
            @Field("comments") String comments
    );

    @Headers("Accept: application/json")
    @GET("patients/show/{id}")
    Call<PatientsResponse> loadCurrentPatients(
            @Path("id") String id
    );
}
