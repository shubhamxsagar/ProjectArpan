package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpData {

    @SerializedName("status")
    @Expose
    boolean status;

    @SerializedName("msg")
    @Expose
    String msg;

    @SerializedName("data")
    @Expose
    OtpOrderId data;

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

    public OtpOrderId getData() {
        return data;
    }

    public void setData(OtpOrderId data) {
        this.data = data;
    }

    public OtpData(boolean status, String msg, OtpOrderId data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
