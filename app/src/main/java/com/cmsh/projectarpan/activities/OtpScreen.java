package com.cmsh.projectarpan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityOtpScreenBinding;
import com.cmsh.projectarpan.responses.OtpData;
import com.cmsh.projectarpan.responses.RegisterResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.cmsh.projectarpan.viewmodels.OtpViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class OtpScreen extends AppCompatActivity {

    ActivityOtpScreenBinding binding;
    OtpViewModel otpViewModel;
    Long timeoutSecond = 60L;
    DataViewModel dataViewModel;
    private final long startTimeInMillis = 4000; // 60 seconds
    private long timeLeftInMillis = startTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        Intent intent =getIntent();
        String phoneno = intent.getStringExtra("phoneno");
        String orderId = intent.getStringExtra("orderId");
        binding.textView3.setText("sent to "+phoneno);

        otpViewModel = new ViewModelProvider(this).get(OtpViewModel.class);

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the visibility whenever the text changes
                if (s.length() == 0) {
                    binding.btn.setVisibility(View.GONE);
                }
                updateButtonVisibility(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this example
            }
        });

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgress(true);
                otpViewModel.verifyOtp(phoneno, orderId,binding.name.getText().toString(), Utils.INSTANCE.getToken()).observe(OtpScreen.this, new Observer<OtpData>() {
                    @Override
                    public void onChanged(OtpData otpData) {
                        Log.e("TAG", "onChanged: Next"+otpData.isStatus()+"  "+binding.name.getText().toString()+"   "+phoneno+"  "+orderId);
                        if(otpData!=null){
                            if (otpData.isStatus()){
                                setProgress(false);
                                dataViewModel.isRegistered(phoneno, Utils.INSTANCE.getToken()).observe(OtpScreen.this, new Observer<RegisterResponse>() {
                                    @Override
                                    public void onChanged(RegisterResponse registerResponse) {
                                        if (registerResponse.isStatus()){
                                            Intent intent1 = new Intent(OtpScreen.this, CategoryScreen.class);
                                            SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("savephone", phoneno);
                                            editor.apply();
                                            editor.commit();
                                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent1);
                                            finish();

                                        }else{
                                            Intent intent2 = new Intent(OtpScreen.this, RegisterScreen.class);
                                            intent2.putExtra("phoneno",phoneno);
                                            startActivity(intent2);
                                        }
                                    }
                                });
                            }else {
                                setProgress(false);
                                Toast.makeText(OtpScreen.this, otpData.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        setProgress(false);
                    }
                });
            }
        });
        startResendTimer();

        Log.e("TAG", "onChanged: order"+orderId);
        binding.resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgress(true);
                startResendTimer();
                otpViewModel.resendOtp(orderId,Utils.INSTANCE.getToken()).observe(OtpScreen.this, new Observer<OtpData>() {
                    @Override
                    public void onChanged(OtpData otpData) {
                        setProgress(false);
                        Log.e("TAG", "onChanged: "+otpData.getMsg());
                        if(otpData!=null){
                            if(otpData.isStatus()){
                                startResendTimer();
                            }
                        }
                    }
                });
            }
        });

    }

    void startResendTimer() {
        binding.resendOtp.setEnabled(false);

        final Handler handler = new Handler();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSecond--;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.timer.setText(" in "+String.valueOf(timeoutSecond));
                    }
                });

                if (timeoutSecond <= 0) {
                    timeoutSecond = 60L;
                    timer.cancel();

                    handler.post(new Runnable() {
                        @Override

                        public void run() {
                            binding.resendOtp.setEnabled(true);
                            binding.timer.setText("");
                        }
                    });
                }
            }
        }, 0, 1000);
    }
    private void setProgress(Boolean progress){
        if (progress){
            binding.progressbar.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.GONE);
        }else {
            binding.progressbar.setVisibility(View.GONE);
        }
    }
    private void updateButtonVisibility(int textLength) {
        if (textLength == 6) {
            binding.btn.setVisibility(View.VISIBLE);
        } else {
            binding.btn.setVisibility(View.GONE);
        }
    }
}