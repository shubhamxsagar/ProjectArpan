package com.cmsh.projectarpan.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmsh.projectarpan.network.ApiClient;
import com.cmsh.projectarpan.network.ApiService;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.RegisterResponse;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private ApiService apiService;

    public DataRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<DataResponse> getMAidList(int page, String token){
        MutableLiveData<DataResponse> data = new MutableLiveData<>();
        Call<DataResponse> call = apiService.getMaidList(page, "Bearer " + token);

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse( Call<DataResponse> call,  Response<DataResponse> response) {
                Log.e("TAG", "onResponse: "+response.message() );
                if (response!=null){
                    if(response.isSuccessful()){
                        data.setValue(response.body());
                    }
                }else{
                    Log.e("TAG", "onResponse: "+response.message() );
                }
            }

            @Override
            public void onFailure( Call<DataResponse> call, Throwable t) {
                Log.e("TAG", "Error: "+t );
                if (t!=null){
                    data.setValue(null);
                }
            }
        });
        return data;
    }


    public LiveData<DataResponse> getContractorList(int page, String token){
        MutableLiveData<DataResponse> data = new MutableLiveData<>();
        Call<DataResponse> call = apiService.getContractorList(page, "Bearer " + token);
    call.enqueue(new Callback<DataResponse>() {
        @Override
        public void onResponse( Call<DataResponse> call,  Response<DataResponse> response) {
            Log.e("TAG", "onResponse: "+response.message() );
            if (response!=null){
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }
            }else{
                Log.e("TAG", "onResponse: "+response.message() );
            }
        }

        @Override
        public void onFailure( Call<DataResponse> call, Throwable t) {
            Log.e("TAG", "Error: "+t );
            if (t!=null){
                data.setValue(null);
            }
        }
    });
        return data;
}
    public LiveData<DataResponse> register(RequestBody name, RequestBody mobile, RequestBody location, RequestBody email, MultipartBody.Part profile, String token){
        MutableLiveData<DataResponse> data = new MutableLiveData<>();
        Call<DataResponse> call = apiService.register(name,mobile,location,email,profile, "Bearer " + token);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse( Call<DataResponse> call,  Response<DataResponse> response) {
                Log.e("TAG", "onResponse: "+response.message() );
                if (response!=null){
                    if(response.isSuccessful()){
                        data.setValue(response.body());
                    }
                }else{
                    Log.e("TAG", "onResponse: "+response.message() );
                }
            }

            @Override
            public void onFailure( Call<DataResponse> call, Throwable t) {
                Log.e("TAG", "Error: "+t );
                if (t!=null){
                    data.setValue(null);
                }
            }
        });
        return data;
    }

    public LiveData<RegisterResponse> isRegistered(String mobile,String token){
        MutableLiveData<RegisterResponse> data = new MutableLiveData<>();
        Call<RegisterResponse> call = apiService.isRegistered(mobile, "Bearer " + token);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse( Call<RegisterResponse> call,  Response<RegisterResponse> response) {
                Log.e("TAG", "onResponse: "+response.message() );
                if (response!=null){
                    if(response.isSuccessful()){
                        data.setValue(response.body());
                    }
                }else{
                    Log.e("TAG", "onResponse: "+response.message() );
                }
            }

            @Override
            public void onFailure( Call<RegisterResponse> call, Throwable t) {
                Log.e("TAG", "Error: "+t );
                if (t!=null){
                    data.setValue(null);
                }
            }
        });
        return data;
    }

}
