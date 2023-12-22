package com.cmsh.projectarpan.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmsh.projectarpan.network.ApiClient;
import com.cmsh.projectarpan.network.ApiService;
import com.cmsh.projectarpan.responses.LocationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {

    private ApiService apiService;

    public LocationRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<LocationResponse> getMaidsLocationList(String token){
        MutableLiveData<LocationResponse> data = new MutableLiveData<>();
        Call<LocationResponse> call = apiService.getMaidsLocationList("Bearer " + token);

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse( Call<LocationResponse> call,  Response<LocationResponse> response) {
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
            public void onFailure( Call<LocationResponse> call, Throwable t) {
                Log.e("TAG", "Error: "+t );
                if (t!=null){
                    data.setValue(null);
                }
            }
        });
        return data;
    }

    public LiveData<LocationResponse> getContractorsLocationList(String token){
        MutableLiveData<LocationResponse> data = new MutableLiveData<>();
        Call<LocationResponse> call = apiService.getContractorsLocationList("Bearer " + token);

        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse( Call<LocationResponse> call,  Response<LocationResponse> response) {
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
            public void onFailure( Call<LocationResponse> call, Throwable t) {
                Log.e("TAG", "Error: "+t );
                if (t!=null){
                    data.setValue(null);
                }
            }
        });
        return data;
    }


}
