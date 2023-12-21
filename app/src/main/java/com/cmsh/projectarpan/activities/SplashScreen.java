package com.cmsh.projectarpan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.databinding.ActivityRegisterScreenBinding;
import com.cmsh.projectarpan.databinding.ActivitySplashScreenBinding;
import com.cmsh.projectarpan.responses.RegisterResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;

public class SplashScreen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
//    DataViewModel dataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);
                String phone = sharedPreferences.getString("savephone", "");
                Log.e("TAG", "run: "+phone);
                Log.e("TAG", "onTransitionCompleted: " );

                if (!phone.equals("")){
                    startActivity(new Intent(SplashScreen.this, CategoryScreen.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashScreen.this, PhoneActivity.class));
                    finish();
                }

//                dataViewModel.isRegistered(phone, Utils.INSTANCE.getToken()).observe(SplashScreen.this, new Observer<RegisterResponse>() {
//                    @Override
//                    public void onChanged(RegisterResponse registerResponse) {
//                        Log.e("TAG", "onChanged: "+registerResponse.isStatus() );
//                        if (registerResponse.isStatus()){
//                            startActivity(new Intent(SplashScreen.this, CategoryScreen.class));
//                            finish();
//                        }else{
//                            startActivity(new Intent(SplashScreen.this, PhoneActivity.class));
//                            finish();
//                        }
//                    }
//                });
            }
        },1000);

    }
}