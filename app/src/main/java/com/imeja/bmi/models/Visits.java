package com.imeja.bmi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Visits {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("age")
    @Expose
    public String age;
    @SerializedName("status")
    @Expose
    public String status;
}
