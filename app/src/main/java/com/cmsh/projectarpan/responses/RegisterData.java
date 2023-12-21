package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterData {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("profilePic")
    @Expose
    String profilePic;
    @SerializedName("location")
    @Expose
    String location;

    public RegisterData(String name, String mobile, String email, String profilePic, String location) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.profilePic = profilePic;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
