package com.cmsh.projectarpan;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileImageUploader {

    public static MultipartBody.Part getProfileImagePart(Context context, Uri imageUri) {
        if (imageUri == null) {
            return null; // Handle the case where the image URI is null
        }

        File imageFile = new File(FilesUtils.getPathFromUri(context, imageUri));

        if (imageFile.exists()) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            return MultipartBody.Part.createFormData("profile_image", imageFile.getName(), requestFile);
        } else {
            return null; // Handle the case where the image file doesn't exist
        }
    }

}
