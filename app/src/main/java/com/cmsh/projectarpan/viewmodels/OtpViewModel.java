package com.cmsh.projectarpan.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmsh.projectarpan.repositories.DataRepository;
import com.cmsh.projectarpan.repositories.OtpRepository;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.OtpData;

public class OtpViewModel extends ViewModel {

    private final OtpRepository otpRepository;

    public OtpViewModel() {
        // Initialize the DataRepository here
        otpRepository = new OtpRepository();
    }

    public LiveData<OtpData> getOtp(String phone, String token) {
        // Ensure that otpRepository is not null before calling its methods
        if (otpRepository != null) {
            return otpRepository.getOTP(phone, token);
        } else {
            // Handle the case where otpRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }
    public LiveData<OtpData> verifyOtp(String phone, String orderId, String otp, String token) {
        // Ensure that otpRepository is not null before calling its methods
        if (otpRepository != null) {
            return otpRepository.verifyOtp(phone, orderId, otp, token);
        } else {
            // Handle the case where otpRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }
    public LiveData<OtpData> resendOtp(String orderId, String token) {
        // Ensure that otpRepository is not null before calling its methods
        if (otpRepository != null) {
            return otpRepository.resendOtp(orderId, token);
        } else {
            // Handle the case where otpRepository is null (e.g., log an error, return null, etc.)
            return null;
        }
    }

}
