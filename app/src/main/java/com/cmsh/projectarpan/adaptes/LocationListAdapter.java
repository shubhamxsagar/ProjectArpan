package com.cmsh.projectarpan.adaptes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.databinding.RvLocationContainerBinding;
import com.cmsh.projectarpan.databinding.RvLocationContainerBinding;
import com.cmsh.projectarpan.listeners.ListListener;
import com.cmsh.projectarpan.listeners.RvLocationListener;
import com.cmsh.projectarpan.responses.LocationListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.TVShowViewHolder>{

    private List<LocationListResponse> list;
    private LayoutInflater layoutInflater;
    private RvLocationListener listListener;

    public LocationListAdapter(List<LocationListResponse> list, RvLocationListener listListener) {
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
        RvLocationContainerBinding itemContainerBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.rv_location_container, parent, false
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

        private RvLocationContainerBinding itemContainerBinding;

        public TVShowViewHolder(RvLocationContainerBinding itemContainerBinding){
            super(itemContainerBinding.getRoot());
            this.itemContainerBinding = itemContainerBinding;

        }

        public void bind(LocationListResponse listResponse){
            itemContainerBinding.setList(listResponse);
            itemContainerBinding.executePendingBindings();
            itemContainerBinding.location.setText(listResponse.getLocation());
            itemContainerBinding.count.setText(listResponse.getCount());

            itemContainerBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listListener.onListClicked(listResponse);
                }
            });

        }
    }

}
