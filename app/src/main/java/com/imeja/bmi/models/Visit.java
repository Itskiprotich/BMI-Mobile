package com.imeja.bmi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Visit {

    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("message")
    @Expose
    public String message;
}
