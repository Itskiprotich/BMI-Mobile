package com.imeja.bmi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WhereTo {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("patient_id")
    @Expose
    public String patient_id;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("message")
    @Expose
    public String message;
}
