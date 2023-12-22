package com.cmsh.projectarpan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.databinding.ActivityCategoryScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.responses.RegisterResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

public class CategoryScreen extends AppCompatActivity {

    ActivityCategoryScreenBinding binding;
    private DataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.arpan_blue));


        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);
        String phone = sharedPreferences.getString("savephone", "");

        viewModel.isRegistered(phone, Utils.INSTANCE.getToken()).observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                Log.e("TAG", "onChanged: registerResponse "+registerResponse.getData().getName() );
                if (registerResponse!=null){
                    if (registerResponse.isStatus()){
                        if (registerResponse.getData().getProfilePic()==null){
                            binding.profile.setImageResource(R.drawable.male_user);
                        }else{
                            Picasso.get().load(registerResponse.getData().getProfilePic()).into(binding.profile);
                        }
                    }
                }
            }
        });



        binding.maid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryScreen.this, HomeScreen.class);
                intent.putExtra("category","maid");
                startActivity(intent);
            }
        });

        binding.beautician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.compounders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.beautician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });

        binding.beautician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBottom();
            }
        });



        binding.contractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryScreen.this, HomeScreen.class);
                intent.putExtra("category","contractor");
                startActivity(intent);
            }
        });

    }
    void startBottom(){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CategoryScreen.this, R.style.DialogStyle);
        bottomSheetDialog.setContentView(R.layout.bottom_login_otp_layout);
        ImageButton cancel = bottomSheetDialog.findViewById(R.id.cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
}