package com.imeja.bmi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imeja.bmi.models.AuthData;
import com.imeja.bmi.models.Proceed;

public class AddPatientResponse {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("data")
    @Expose
    public Proceed data;
}
