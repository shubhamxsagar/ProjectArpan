package com.cmsh.projectarpan.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmsh.projectarpan.network.ApiClient;
import com.cmsh.projectarpan.network.ApiService;
import com.cmsh.projectarpan.responses.DataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private ApiService apiService;

    public SearchRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<DataResponse> getMaidSearch(String name,int page, String token){
        MutableLiveData<DataResponse> data = new MutableLiveData<>();
        Call<DataResponse> call = apiService.getMaidBySearch(name, page, "Bearer " + token);

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

    public LiveData<DataResponse> getContractorBySearch(String name,int page, String token){
        MutableLiveData<DataResponse> data = new MutableLiveData<>();
        Call<DataResponse> call = apiService.getContractorBySearch(name, page, "Bearer " + token);

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




}
