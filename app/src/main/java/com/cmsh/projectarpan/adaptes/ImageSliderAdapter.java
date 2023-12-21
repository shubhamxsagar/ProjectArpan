package com.cmsh.projectarpan.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.databinding.ItemContainerBinding;
import com.cmsh.projectarpan.databinding.SliderImageContainerBinding;
import com.squareup.picasso.Picasso;


public class ImageSliderAdapter extends  RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private String[] sliderImage;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImage){
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SliderImageContainerBinding sliderImageBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.slider_image_container, parent, false
        );
        return new ImageSliderViewHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder{

        private SliderImageContainerBinding itemContainerSliderImageBinding;

        public ImageSliderViewHolder(SliderImageContainerBinding itemContainerSliderImageBinding) {
            super(itemContainerSliderImageBinding.getRoot());
            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding;
        }

        public void bindSliderImage(String imageURL){
            Picasso.get().load(imageURL).into(itemContainerSliderImageBinding.imageURL);
//            itemContainerSliderImageBinding.setImageURL(imageURL);
        }
    }

}
