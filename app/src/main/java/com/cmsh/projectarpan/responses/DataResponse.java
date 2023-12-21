package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class DataResponse {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("msg")
    @Expose
    String msg;

    @SerializedName("data")
    @Expose
    PageResponse data;

    public DataResponse(boolean status, String msg, PageResponse data) {
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

    public PageResponse getData() {
        return data;
    }

    public void setData(PageResponse data) {
        this.data = data;
    }
}
