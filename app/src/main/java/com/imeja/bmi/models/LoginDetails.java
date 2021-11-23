package com.imeja.bmi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDetails {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("access_token")
    @Expose
    public String access_token;
    @SerializedName("created_at")
    @Expose
    public String created_at;
}
