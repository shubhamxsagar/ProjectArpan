package com.cmsh.projectarpan.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmsh.projectarpan.repositories.DataRepository;
import com.cmsh.projectarpan.repositories.LocationRepository;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.LocationResponse;

public class LocationViewModel  extends ViewModel {

    private final LocationRepository dataRepository;

    public LocationViewModel() {
        // Initialize the DataRepository here
        dataRepository = new LocationRepository();
    }

    public LiveData<LocationResponse> getMaidsLocationList(String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (dataRepository != null) {
            return dataRepository.getMaidsLocationList(token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

    public LiveData<LocationResponse> getContractorsLocationList(String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (dataRepository != null) {
            return dataRepository.getContractorsLocationList(token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

}
