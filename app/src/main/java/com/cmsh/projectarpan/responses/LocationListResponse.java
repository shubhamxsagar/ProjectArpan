package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationListResponse {

    @SerializedName("location")
    @Expose
    String location;

    @SerializedName("count")
    @Expose
    String count;

    public LocationListResponse(String location, String count) {
        this.location = location;
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
