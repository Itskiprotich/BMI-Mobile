package com.imeja.bmi.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static String BASE_CLIENT = "1";
    public static String BASE_URL = "https://smp.imejadevelopers.co.ke/api/";

    private static Retrofit sRetrofit;
    private static HttpLoggingInterceptor sHttpLoggingInterceptor;
    private static OkHttpClient sOkHttpClient;
    private static TokenInterceptor sInteceptor;


    public static Retrofit getRetroClient(Context context){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        sHttpLoggingInterceptor = new HttpLoggingInterceptor();
        sHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        sInteceptor = new TokenInterceptor(context);
        sOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(sHttpLoggingInterceptor)
                .addInterceptor(sInteceptor)
                .build();

        if(sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(getUnsafeOkHttpClient().build())
                    .client(sOkHttpClient)
                    .build();
        }
        else {
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            System.out.println("Empty Empty");
        }


        return sRetrofit;


    }

    public static Retrofit getRetroClientWithToken(String  token){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        sHttpLoggingInterceptor = new HttpLoggingInterceptor();
        sHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        sInteceptor = new TokenInterceptor(token);
        sOkHttpClient = new OkHttpClient.Builder()

                .addInterceptor(sHttpLoggingInterceptor)
                .addInterceptor(sInteceptor)
                .build();

        if(sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(sOkHttpClient)
                    .build();
        }
        else {
            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
            System.out.println("Empty Retrofit with token");
        }


        return sRetrofit;


    }




}
