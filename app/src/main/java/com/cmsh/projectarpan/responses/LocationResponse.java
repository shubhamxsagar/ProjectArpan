package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LocationResponse {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("msg")
    @Expose
    String msg;

    @SerializedName("data")
    @Expose
    ArrayList<LocationListResponse> data;

    public LocationResponse(boolean status, String msg, ArrayList<LocationListResponse> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<LocationListResponse> getData() {
        return data;
    }

    public void setData(ArrayList<LocationListResponse> data) {
        this.data = data;
    }
}
