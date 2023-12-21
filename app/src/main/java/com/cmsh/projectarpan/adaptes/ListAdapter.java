package com.cmsh.projectarpan.adaptes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.databinding.ItemContainerBinding;
import com.cmsh.projectarpan.listeners.ListListener;
import com.cmsh.projectarpan.responses.ListResponse;
import com.cmsh.projectarpan.responses.ListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.TVShowViewHolder>{

    private List<ListResponse> list;
    private LayoutInflater layoutInflater;
    private ListListener listListener;

    public ListAdapter(List<ListResponse> list, ListListener listListener) {
        this.list = list;
        this.listListener = listListener;
    }
    public void clearList() {
        list.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerBinding itemContainerBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container, parent, false
        );
        return new TVShowViewHolder(itemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bind(list.get(position));
        Log.e("TAG", "bind: ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerBinding itemContainerBinding;

        public TVShowViewHolder(ItemContainerBinding itemContainerBinding){
            super(itemContainerBinding.getRoot());
            this.itemContainerBinding = itemContainerBinding;
        }

        public void bind(ListResponse listResponse){
            itemContainerBinding.setList(listResponse);
            itemContainerBinding.executePendingBindings();
            itemContainerBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listListener.onListClicked(listResponse);
                }
            });
            if (listResponse.getProfilePic()==null){
                itemContainerBinding.profile.setImageResource(R.drawable.male_user);
            }else{
                Picasso.get().load(listResponse.getProfilePic()).into(itemContainerBinding.profile);
            }
            itemContainerBinding.name.setText(listResponse.getName());
            itemContainerBinding.mobile.setText(listResponse.getMobile());
            itemContainerBinding.location.setText(listResponse.getLocation());
            itemContainerBinding.myRatingBar.setRating(Float.parseFloat(listResponse.getRating()));
            itemContainerBinding.price.setText("₹"+listResponse.getPrice());
        }
    }

}
