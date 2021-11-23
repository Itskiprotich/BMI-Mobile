package com.imeja.bmi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthData {

    @SerializedName("proceed")
    @Expose
    public String proceed;
    @SerializedName("message")
    @Expose
    public String message;
}
