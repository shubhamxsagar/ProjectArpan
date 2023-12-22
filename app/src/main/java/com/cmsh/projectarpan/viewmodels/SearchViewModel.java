package com.cmsh.projectarpan.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmsh.projectarpan.repositories.DataRepository;
import com.cmsh.projectarpan.repositories.SearchRepository;
import com.cmsh.projectarpan.responses.DataResponse;

public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository;

    public SearchViewModel() {
        // Initialize the DataRepository here
        searchRepository = new SearchRepository();
    }

    public LiveData<DataResponse> getMaidList(String name, String location, int page, String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (searchRepository != null) {
            return searchRepository.getMaidSearch(name, location, page, token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }
    public LiveData<DataResponse> getContractorBySearch(String name, String location, int page, String token) {
        // Ensure that dataRepository is not null before calling its methods
        if (searchRepository != null) {
            return searchRepository.getContractorBySearch(name, location, page, token);
        } else {
            // Handle the case where dataRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

}
