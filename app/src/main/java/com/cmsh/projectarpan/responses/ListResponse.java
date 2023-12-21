package com.cmsh.projectarpan.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse {

    @SerializedName("image1")
    @Expose
    String image1;
    @SerializedName("image2")
    @Expose
    String image2;
    @SerializedName("image3")
    @Expose
    String image3;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("price")
    @Expose
    String price;
    @SerializedName("profilePic")
    @Expose
    String profilePic;
    @SerializedName("rating")
    @Expose
    String rating;
    @SerializedName("text")
    @Expose
    String text;
    @SerializedName("whatsapp")
    @Expose
    String whatsapp;

    public ListResponse(String image1, String image2, String image3, String location, String mobile, String name, String price, String profilePic, String rating, String text, String whatsapp) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.location = location;
        this.mobile = mobile;
        this.name = name;
        this.price = price;
        this.profilePic = profilePic;
        this.rating = rating;
        this.text = text;
        this.whatsapp = whatsapp;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
