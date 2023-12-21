package com.cmsh.projectarpan.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.databinding.ActivityCategoryScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CategoryScreen extends AppCompatActivity {

    ActivityCategoryScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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