package com.imeja.bmi.network;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("customer/signin")
    Call<LoginResponse> checkLogin(
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("customer/signup")
    Call<RegisterResponse> checkRegister(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password,
            @Field("type") String type,
            @Field("devicename") String devicename,
            @Field("device_id") String device_id


    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("customer/qualification/{id}")
    Call<PrequalificationResponse> loadPrequalification(
            @Path("id") String id,
            @Field("client") String client
    );

    @Headers("Accept: application/json")
    @GET("customer/view/{id}")
    Call<ProfileResponse> loadAccount(
            @Path("id")  String id);

    @Headers("Accept: application/json")
    @GET("savings/view/{id}")
    Call<SavingsResponse> fetchSavings(
            @Path("id") String id
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("savings/add")
    Call<AddSavingsResponse> addSavings(
            @Field("phone") String phone,
            @Field("amount") String amount
    );

    @Headers("Accept: application/json")
    @GET("admin/parameters/loantype/view")
    Call<LoanTypesResponse> checkLoanTypes();


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("loans/verify")
    Call<VerificationResponse> checkVerification(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("loans/apply")
    Call<ApplyResponse> handleApply(
            @Field("phone") String phone,
            @Field("principle") String principle,
            @Field("loan_code") String loan_code,
            @Field("access_code") String access_code
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("loans/history")
    Call<LoanHistoryResponse> fetchLoans(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("loans/pay")
    Call<PaymentResponse> makePayment(
            @Field("phone") String phone,
            @Field("amount") String amount
    );


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("loans/active")
    Call<ActiveLoanResponse> loadActiveLoan(
            @Field("phone") String phone
    );

    @Headers("Accept: application/json")
    @GET("notification/view/{id}")
    Call<NotificationResponse> fetchNotifications(
            @Path("id") String id
    );
}
