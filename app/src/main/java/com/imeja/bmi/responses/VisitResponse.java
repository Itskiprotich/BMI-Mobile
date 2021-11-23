package com.imeja.bmi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imeja.bmi.models.Visit;
import com.imeja.bmi.models.WhereTo;

public class VisitResponse {

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
    public Visit data;
}
