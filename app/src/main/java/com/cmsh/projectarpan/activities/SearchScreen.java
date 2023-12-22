package com.cmsh.projectarpan.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.adaptes.ListAdapter;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.databinding.ActivitySearchScreenBinding;
import com.cmsh.projectarpan.listeners.ListListener;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.ListResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.cmsh.projectarpan.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchScreen extends AppCompatActivity implements ListListener {
    ActivitySearchScreenBinding binding;
    SearchViewModel searchViewModel;
    private List<ListResponse> list=new ArrayList<>();
    private ListAdapter listAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        listAdapter = new ListAdapter(list, this);
//
//        binding.tabSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//
//                    doInitializationSearchMaid();
//                }
//                return false; // Let the event be handled by other listeners
//            }
//        });
//
//        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentPage=1;
//                listAdapter.clearList();
//                doInitializationSearchMaid();
//            }
//        });
//
//    }
//
//    private void doInitializationSearchMaid(){
//        Log.e("TAG", "doInitializationSearchMaid: ");
//        binding.tvShowsRecyclerView.setHasFixedSize(true);
//        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
//        listAdapter = new ListAdapter(list, this);
//        binding.tvShowsRecyclerView.setAdapter(listAdapter);
//
//        binding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!binding.tvShowsRecyclerView.canScrollVertically(1)){
//                    if (currentPage <= totalAvailablePage){
//                        currentPage += 1;
//                        getListSearchMaid();
//                    }
//                }
//            }
//        });
//        getListSearchMaid();
//    }
//
//    private void getListSearchMaid(){
//        toggleLoading();
//        searchViewModel.getMaidList(binding.tabSearch.getText().toString(), currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
//            @Override
//            public void onChanged(DataResponse dataResponse) {
//                SearchScreen.this.toggleLoading();
//                if (dataResponse != null) {
//                    totalAvailablePage = dataResponse.getData().getPages();
//                    if (dataResponse.getData() != null) {
//                        int oldCount = list.size();
//                        list.addAll(dataResponse.getData().getMaidList());
//                        listAdapter.notifyItemRangeInserted(oldCount, list.size());
//                    }
//                }
//
//            }
//        });
//    }
//
//    private void doInitializationSearchContractor(){
//        Log.e("TAG", "doInitializationSearchMaid: ");
//        binding.tvShowsRecyclerView.setHasFixedSize(true);
//        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
//        listAdapter = new ListAdapter(list, this);
//        binding.tvShowsRecyclerView.setAdapter(listAdapter);
//
//        binding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!binding.tvShowsRecyclerView.canScrollVertically(1)){
//                    if (currentPage <= totalAvailablePage){
//                        currentPage += 1;
//                        getListSearchContractor();
//                    }
//                }
//            }
//        });
//        getListSearchContractor();
//    }
//
//    private void getListSearchContractor(){
//        toggleLoading();
//        searchViewModel.getMaidList(binding.tabSearch.getText().toString(), currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
//            @Override
//            public void onChanged(DataResponse dataResponse) {
//                SearchScreen.this.toggleLoading();
//                if (dataResponse != null) {
//                    totalAvailablePage = dataResponse.getData().getPages();
//                    if (dataResponse.getData() != null) {
//                        int oldCount = list.size();
//                        list.addAll(dataResponse.getData().getMaidList());
//                        listAdapter.notifyItemRangeInserted(oldCount, list.size());
//                    }
//                }
//
//            }
//        });
//    }
//
//    private void toggleLoading(){
//        if (currentPage == 1){
//            if (binding.getIsLoading()!= null && binding.getIsLoading()){
//                binding.setIsLoading(false);
//            }else {
//                binding.setIsLoading(true);
//            }
//        } else {
//            if (binding.getIsLoadingMore()!= null && binding.getIsLoadingMore()){
//                binding.setIsLoadingMore(false);
//            }else {
//                binding.setIsLoadingMore(true);
//            }
//        }
    }

    @Override
    public void onListClicked(ListResponse listResponse) {
        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
        intent.putExtra("name", listResponse.getName());
        intent.putExtra("mobile", listResponse.getMobile());
        startActivity(intent);
    }
}