package com.cmsh.projectarpan.network;

import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.LocationResponse;
import com.cmsh.projectarpan.responses.OtpData;
import com.cmsh.projectarpan.responses.RegisterData;
import com.cmsh.projectarpan.responses.RegisterResponse;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("getMaidList")
    Call<DataResponse> getMaidList(@Field("page") int page, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("getContractorList")
    Call<DataResponse> getContractorList(@Field("page") int page, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("getMaidBySearchList")
    Call<DataResponse> getMaidBySearch(@Field("name") String name, @Field("location") String location,@Field("page") int page, @Header("Authorization") String token);

    @POST("getMaidsLocationList")
    Call<LocationResponse> getMaidsLocationList(@Header("Authorization") String token);

    @POST("getContractorsLocationList")
    Call<LocationResponse> getContractorsLocationList(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("getContractorBySearchList")
    Call<DataResponse> getContractorBySearch(@Field("name") String name, @Field("location") String location,@Field("page") int page, @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("getOTP")
    Call<OtpData> getOTP(@Field("mobile") String mobile, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("verifyOTP")
    Call<OtpData> verifyOtp(@Field("mobile") String mobile,@Field("orderId") String orderId,@Field("otp") String otp, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("resendOTP")
    Call<OtpData> resendOtp(@Field("orderId") String orderId, @Header("Authorization") String token);
    @Multipart
    @POST("register")
    Call<DataResponse> register(@Part("name") RequestBody name, @Part("mobile") RequestBody mobile, @Part("location") RequestBody location, @Part("email") RequestBody email, @Part MultipartBody.Part profilePic, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("isRegistered")
    Call<RegisterResponse> isRegistered(@Field("mobile") String mobile, @Header("Authorization") String token);


}
