package com.cmsh.projectarpan.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmsh.projectarpan.repositories.DataRepository;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.RegisterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DataViewModel extends ViewModel {

    private final DataRepository dataRepository;

    public DataViewModel() {
        // Initialize the DataRepository here
        dataRepository = new DataRepository();
    }

    public LiveData<DataResponse> getMaidList(int page, String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (dataRepository != null) {
            return dataRepository.getMAidList(page, token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

    public LiveData<DataResponse> getContractorList(int page, String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (dataRepository != null) {
            return dataRepository.getContractorList(page, token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

    public LiveData<DataResponse> register(RequestBody name, RequestBody mobile, RequestBody location, RequestBody email, MultipartBody.Part profile, String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (dataRepository != null) {
            return dataRepository.register(name,mobile,location, email,profile, token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

    public LiveData<RegisterResponse> isRegistered(String mobile, String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (dataRepository != null) {
            return dataRepository.isRegistered(mobile,token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

}
