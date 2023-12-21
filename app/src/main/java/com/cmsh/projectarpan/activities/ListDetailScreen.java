package com.cmsh.projectarpan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.adaptes.ImageSliderAdapter;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.databinding.ActivityListDetailScreenBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListDetailScreen extends AppCompatActivity {

    ActivityListDetailScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListDetailScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if(intent.getStringExtra("profilePic")==null){
            binding.profile.setImageResource(R.drawable.male_user);
        }else{
            Picasso.get().load(intent.getStringExtra("profilePic")).into(binding.profile);
        }
        binding.name.setText(intent.getStringExtra("name"));
        binding.category.setText(intent.getStringExtra("category"));
        binding.location.setText(intent.getStringExtra("location"));
        binding.price.setText("₹"+intent.getStringExtra("price"));
        binding.price1.setText("₹"+intent.getStringExtra("price"));
        binding.rating.setText(intent.getStringExtra("rating"));
        binding.textDescription.setText(intent.getStringExtra("text"));
        binding.ratingStar.setRating(Float.parseFloat(intent.getStringExtra("rating")));

        binding.textDescription.setVisibility(View.VISIBLE);
        binding.textReadMore.setVisibility(View.VISIBLE);
        binding.textReadMore.setOnClickListener(view -> {
            if (binding.textReadMore.getText().toString().equals("Read More")) {
                binding.textDescription.setMaxLines(Integer.MAX_VALUE);
                binding.textDescription.setEllipsize(null);
                binding.textReadMore.setText(R.string.read_less);
            } else {
                binding.textDescription.setMaxLines(4);
                binding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                binding.textReadMore.setText(R.string.read_more);
            }
        });

        Log.e("TAG", "onCreateMobile: "+ Uri.parse(intent.getStringExtra("location")));
        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + intent.getStringExtra("mobile")));

                // Check if there is an activity that can handle the intent
                if (dialIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(dialIntent);
                } else {
                    Toast.makeText(ListDetailScreen.this, "No Dial pad found", Toast.LENGTH_SHORT).show();
                    // Handle the case where no activity is available
                    // You may want to show an error message to the user
                }
            }
        });

//        binding.cardView2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                // Remove the listener to avoid multiple callbacks
//                binding.cardView2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//                // Get the width of the CardView
//                int cardWidth = binding.cardView2.getWidth();
//                int availableWidth = cardWidth - binding.ratingTxt.getPaddingStart() - cardWidth - binding.ratingTxt.getPaddingEnd();
//
//
//                // Set the height of the CardView to be equal to its width
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.cardView2.getLayoutParams();
////                adjustTextSize(binding.ratingTxt, availableWidth);
//                layoutParams.height = cardWidth;
//                binding.cardView2.setLayoutParams(layoutParams);
//            }
//        });

        binding.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Replace with the phone number you want to open the chat with
                String message = "Hello!";  // Optional: Replace with a pre-filled message
                // Create the intent to open WhatsApp
                Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + intent.getStringExtra("mobile") + "&text=" + message));
                // Start the WhatsApp activity
                startActivity(whatsappIntent);
            }
        });
//        binding.name.setText(intent.getStringExtra("whatsapp"));
//        binding.name.setText(intent.getStringExtra("mobile"));
        Log.e("TAG", "onCreateImg: "+intent.getStringExtra("image1"));
        String[] stringArray = {intent.getStringExtra("image1"),intent.getStringExtra("image2"), intent.getStringExtra("image3")};

        ListDetailScreen.this.loadImageSlider(stringArray);
    }

    private void setCurrentSliderIndicator(int position){
        int childCount = binding.layoutSliderIndicator.getChildCount();
        for (int i=0; i<childCount; i++){
            ImageView imageView = (ImageView) binding.layoutSliderIndicator.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive)
                );
            }
        }
    }

    public void setupSliderIndicator(int count){
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0; i<indicator.length; i++){
            indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicator[i].setLayoutParams(layoutParams);
            binding.layoutSliderIndicator.addView(indicator[i]);
        }
        binding.layoutSliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void adjustTextSize(TextView textView, int availableWidth) {
        // Get the current text size
        float textSize = textView.getTextSize();

        // Measure the text width with the current size
        float textWidth = textView.getPaint().measureText(textView.getText().toString());

        // Calculate the scaling factor based on the available width
        float scale = availableWidth / textWidth;

        // Apply the scale to the current text size
        float newSize = textSize * scale;

        // Set the adjusted text size
        textView.setTextSize(newSize);
    }
    private void loadImageSlider(String[] sliderImage){
        binding.sliderViewPager.setOffscreenPageLimit(1);
        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImage));
        binding.sliderViewPager.setVisibility(View.VISIBLE);
//        binding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(sliderImage.length);
        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }
}