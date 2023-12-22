package com.cmsh.projectarpan.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cmsh.projectarpan.FilesUtils;
import android.Manifest;

import com.cmsh.projectarpan.MainActivity;
import com.cmsh.projectarpan.ProfileImageUploader;
import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.RealPathUtil;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityOtpScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityRegisterScreenBinding;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterScreen extends AppCompatActivity {

    ActivityRegisterScreenBinding binding;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    DataViewModel dataViewModel;
    Uri selectedImageUri;
    String path;
    RequestBody requestFile, cus_name, cus_reference;
    RequestBody description, reqName, reqMobile, reqLocation, reqPhone, reqProfile, reqEmail;
    MultipartBody.Part body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        Intent intent = getIntent();
        String phonno = intent.getStringExtra("phoneno");

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterScreen.this, CategoryScreen.class));
            }
        });
//        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK) {
//                Intent data = result.getData();
//                if (data != null && data.getData() != null) {
//                    selectedImageUri = data.getData();
//                    // Update your UI to show the selected image if needed
//                }
//            }
//        });

//        binding.addProfile.setOnClickListener((v)->{
//            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
//                    .createIntent(new Function1<Intent, Unit>() {
//                        @Override
//                        public Unit invoke(Intent intent) {
//                            imagePickerLauncher.launch(intent);
//                            return null;
//                        }
//                    });
//        });

        binding.addProfile.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent34 = new Intent();
                intent34.setType("image/*");
                intent34.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent34, 10);
            } else {
                ActivityCompat.requestPermissions(RegisterScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MultipartBody.Part imagePart = ProfileImageUploader.getProfileImagePart(RegisterScreen.this, selectedImageUri);
//                if (selectedImageUri != null) {
//                    // Check if selectedImageUri is not null before creating a File object
//                    // Ensure that the imageFile exists before proceeding
                Log.e("TAG", "onClick:path "+path);

                if (path!=null){
                    File file = new File(path);
                    requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    body = MultipartBody.Part.createFormData("profilePic", file.getName(), requestFile);
                    String descriptionString = "Sample description";
                    reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), binding.email.getText().toString());
                    reqMobile = RequestBody.create(MediaType.parse("multipart/form-data"), phonno);
                    reqName = RequestBody.create(MediaType.parse("multipart/form-data"), binding.name.getText().toString());
                    reqLocation = RequestBody.create(MediaType.parse("multipart/form-data"), binding.location.getText().toString());
                }

                Log.e("TAG", "onClick: "+ body);
                        dataViewModel.register(reqName,reqMobile,reqLocation, reqEmail,body, Utils.INSTANCE.getToken()).observe(RegisterScreen.this, new Observer<DataResponse>() {
                            @Override
                            public void onChanged(DataResponse dataResponse) {
                                Log.e("TAG", "onChanged: "+dataResponse.getMsg());
                                if (dataResponse!=null){
                                    if (dataResponse.isStatus()){
                                        Intent intent1 = new Intent(RegisterScreen.this, CategoryScreen.class);

                                        SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("savephone", phonno);
                                        editor.apply();
                                        editor.commit();
                                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent1);
                                        finish();
                                    }
                                }
                            }
                        });
                    }
//                    else {
//                        // Handle the case where the imageFile doesn't exist
//                        Log.e("TAG", "Image file does not exist");
//                    }
//            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = RegisterScreen.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.addProfile.setImageBitmap(bitmap);

        }
    }
}