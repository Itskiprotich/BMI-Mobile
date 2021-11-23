package com.imeja.bmi.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imeja.bmi.models.Patients;
import com.imeja.bmi.models.Visits;

import java.util.List;

public class PatientsResponse {

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
    public List<Patients> visits;
}
