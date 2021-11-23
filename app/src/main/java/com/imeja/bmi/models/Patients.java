package com.imeja.bmi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patients {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("unique")
    @Expose
    public String unique;
    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("lastname")
    @Expose
    public String lastname;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("reg_date")
    @Expose
    public String reg_date;
    @SerializedName("created_at")
    @Expose
    public String created_at;
    @SerializedName("updated_at")
    @Expose
    public String updated_at;

}
