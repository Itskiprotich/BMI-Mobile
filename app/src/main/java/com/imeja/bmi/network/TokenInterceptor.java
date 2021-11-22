package com.imeja.bmi.network;

import android.content.Context;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.imeja.bmi.Controller;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    String token = "";
    String user_token = null;
    Context context;
    private Request request;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    public TokenInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        this.token = Controller.getToken();
        user_token = "Bearer " + this.token;

        Request request = original.newBuilder()
                .header("Authorization", user_token)
                .header("Accept", "application/json")
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);

    }

}
