package com.cmsh.projectarpan.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmsh.projectarpan.network.ApiClient;
import com.cmsh.projectarpan.network.ApiService;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.OtpData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRepository {

    private ApiService apiService;

    public OtpRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<OtpData> getOTP(String phone, String token) {
        MutableLiveData<OtpData> data = new MutableLiveData<>();
        Call<OtpData> call = apiService.getOTP(phone, "Bearer " + token);

        call.enqueue(new Callback<OtpData>() {
            @Override
            public void onResponse(Call<OtpData> call, Response<OtpData> response) {
                Log.e("TAG", "onResponse: " + response.message());
                if (response != null) {
                    if (response.isSuccessful()) {
                        data.setValue(response.body());
                    }
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OtpData> call, Throwable t) {
                Log.e("TAG", "Error: " + t);
                if (t != null) {
                    data.setValue(null);
                }
            }
        });
        return data;
    }

    public LiveData<OtpData> verifyOtp(String phone, String orderId, String otp, String token) {
        MutableLiveData<OtpData> data = new MutableLiveData<>();
        Call<OtpData> call = apiService.verifyOtp(phone,orderId,otp, "Bearer " + token);

        call.enqueue(new Callback<OtpData>() {
            @Override
            public void onResponse(Call<OtpData> call, Response<OtpData> response) {
                Log.e("TAG", "onResponse: " + response.message());
                if (response != null) {
                    if (response.isSuccessful()) {
                        data.setValue(response.body());
                    }
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OtpData> call, Throwable t) {
                Log.e("TAG", "Error: " + t);
                if (t != null) {
                    data.setValue(null);
                }
            }
        });
        return data;
    }public LiveData<OtpData> resendOtp(String orderId, String token) {
        MutableLiveData<OtpData> data = new MutableLiveData<>();
        Call<OtpData> call = apiService.resendOtp(orderId, "Bearer " + token);

        call.enqueue(new Callback<OtpData>() {
            @Override
            public void onResponse(Call<OtpData> call, Response<OtpData> response) {
                Log.e("TAG", "onResponse: " + response.message());
                if (response != null) {
                    if (response.isSuccessful()) {
                        data.setValue(response.body());
                    }
                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<OtpData> call, Throwable t) {
                Log.e("TAG", "Error: " + t);
                if (t != null) {
                    data.setValue(null);
                }
            }
        });
        return data;
    }
}