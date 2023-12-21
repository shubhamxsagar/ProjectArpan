package com.cmsh.projectarpan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityPhoneBinding;
import com.cmsh.projectarpan.responses.OtpData;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.cmsh.projectarpan.viewmodels.OtpViewModel;

public class PhoneActivity extends AppCompatActivity {

    ActivityPhoneBinding binding;
    OtpViewModel otpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateButtonVisibility(binding.name.getText().length());

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the visibility whenever the text changes
                updateButtonVisibility(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this example
            }
        });


        otpViewModel = new ViewModelProvider(this).get(OtpViewModel.class);

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgress(true);
                otpViewModel.getOtp(binding.name.getText().toString(), Utils.INSTANCE.getToken()).observe(PhoneActivity.this, new Observer<OtpData>() {
                    @Override
                    public void onChanged(OtpData otpData) {
                        Log.e("TAG", "onChanged: "+binding.name.getText().toString()+"   "+otpData.isStatus());
                        if(otpData!=null){
                            if (otpData.isStatus()){
                                Intent intent = new Intent(PhoneActivity.this, OtpScreen.class);
                                intent.putExtra("phoneno", binding.name.getText().toString());
                                intent.putExtra("orderId", otpData.getData().getOrderId());
                                startActivity(intent);
                                setProgress(false);
                            }else {
                                setProgress(false);
                            }
                        }else {
                            setProgress(false);
                        }
                    }
                });
            }
        });
    }
    private void updateButtonVisibility(int textLength) {
        if (textLength == 10) {
            binding.btn.setVisibility(View.VISIBLE);
        } else {
            binding.btn.setVisibility(View.GONE);
        }
    }

    private void setProgress(Boolean progress){
       if (progress){
           binding.progressbar.setVisibility(View.VISIBLE);
           binding.btn.setVisibility(View.GONE);
       }else {
           binding.progressbar.setVisibility(View.GONE);
           binding.btn.setVisibility(View.VISIBLE);
       }
    }

}