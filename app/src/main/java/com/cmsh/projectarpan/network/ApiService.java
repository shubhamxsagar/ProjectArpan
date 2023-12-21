package com.cmsh.projectarpan.network;

import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.OtpData;
import com.cmsh.projectarpan.responses.RegisterData;
import com.cmsh.projectarpan.responses.RegisterResponse;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
    Call<DataResponse> getMaidBySearch(@Field("name") String name,@Field("page") int page, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("getContractorBySearchList")
    Call<DataResponse> getContractorBySearch(@Field("name") String search,@Field("page") int page, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("getOTP")
    Call<OtpData> getOTP(@Field("mobile") String mobile, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("verifyOTP")
    Call<OtpData> verifyOtp(@Field("mobile") String mobile,@Field("orderId") String orderId,@Field("otp") String otp, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("resendOTP")
    Call<OtpData> resendOtp(@Field("orderId") String orderId, @Header("Authorization") String token);
    @FormUrlEncoded
    @POST("register")
    Call<DataResponse> register(@Field("name") String name, @Field("mobile") String mobile, @Field("location") String location, @Field("email") String email, @Field("profilePic") MultipartBody.Part profilePic, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("isRegistered")
    Call<RegisterResponse> isRegistered(@Field("mobile") String mobile, @Header("Authorization") String token);


}
