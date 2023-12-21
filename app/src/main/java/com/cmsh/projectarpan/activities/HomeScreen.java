package com.cmsh.projectarpan.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmsh.projectarpan.R;
import com.cmsh.projectarpan.Utils;
import com.cmsh.projectarpan.adaptes.ListAdapter;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.listeners.ListListener;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.ListResponse;
import com.cmsh.projectarpan.responses.RegisterResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.cmsh.projectarpan.viewmodels.SearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements ListListener {

    SearchViewModel searchViewModel;
    private ActivityHomeScreenBinding binding;
    private DataViewModel viewModel;
    private List<ListResponse> list=new ArrayList<>();
    private ListAdapter listAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    String category;

    String dName, dEmail, dLocation, dPhone, dImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.arpan_blue));

        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        if (category.equals("maid")) {
            binding.company.setText("Arpan Maid");
            binding.categoryName.setText("Maid List");
            doInitializationMaid();
        }else if(category.equals("contractor")){
            binding.company.setText("Arpan Contractor");
            binding.categoryName.setText("Contrator List");
            doInitializationContractor();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("credentials", MODE_PRIVATE);
        String phone = sharedPreferences.getString("savephone", "");

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        viewModel.isRegistered(phone,Utils.INSTANCE.getToken()).observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                if (registerResponse!=null){
                    if (registerResponse.isStatus()){
                        if (registerResponse.getData().getProfilePic()==null){
                            binding.profilePic.setImageResource(R.drawable.male_user);
                            dName = registerResponse.getData().getName();
                            dEmail = registerResponse.getData().getEmail();
                            dLocation = registerResponse.getData().getLocation();
                            dPhone = registerResponse.getData().getMobile();
                            dImage = registerResponse.getData().getProfilePic();
                        }else{
                            Picasso.get().load(registerResponse.getData().getProfilePic()).into(binding.profilePic);
                        }
                    }
                }
            }
        });

        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startBottom();

            }
        });

//        binding.tabSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(HomeScreen.this, SearchScreen.class);
//                intent2.putExtra("category",category);
//                startActivity(intent2);
//            }
//
//        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop the refreshing indicator after the timeout
                        if (binding.refresh.isRefreshing()) {
                            binding.refresh.setRefreshing(false);
                        }
                    }
                }, 2000);
                if (category.equals("maid")) {
                    currentPage=1;
                    binding.categoryName.setText("Maid List");
                    doInitializationMaid();
                }else if(category.equals("contractor")){
                    currentPage=1;
                    Log.e("TAG", "onRefresh: "+category );
                    binding.categoryName.setText("Contrator List");
                    doInitializationContractor();
                }
            }
        });

        binding.tabSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(category.equals("maid")){
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                        currentPage=1;
                        listAdapter.clearList();
                        doInitializationSearchMaid();
                        return true;
                    }
                }else if(category.equals("contractor")){
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                        currentPage=1;
                        listAdapter.clearList();
                        doInitializationSearchContractor();
                        return true;
                    }
                }
                return false;
            }
        });

        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage=1;
                listAdapter.clearList();
                doInitializationSearchMaid();
            }
        });

    }

    private void doInitializationSearchMaid(){
        Log.e("TAG", "doInitializationSearchMaid: ");
        binding.tvShowsRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        listAdapter = new ListAdapter(list, this);
        binding.tvShowsRecyclerView.setAdapter(listAdapter);

        binding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePage){
                        currentPage += 1;
                        getListSearchMaid();
                    }
                }
            }
        });
        getListSearchMaid();
    }

    private void getListSearchMaid(){
        toggleLoading();
        searchViewModel.getMaidList(binding.tabSearch.getText().toString(), currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
            @Override
            public void onChanged(DataResponse dataResponse) {
                HomeScreen.this.toggleLoading();
                if (dataResponse != null) {
                    totalAvailablePage = dataResponse.getData().getPages();
                    if (dataResponse.getData() != null) {
                        int oldCount = list.size();
                        list.addAll(dataResponse.getData().getMaidList());
                        listAdapter.notifyItemRangeInserted(oldCount, list.size());
                    }
                }

            }
        });
    }

    private void doInitializationSearchContractor(){
        Log.e("TAG", "doInitializationSearchMaid: ");
        binding.tvShowsRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        listAdapter = new ListAdapter(list, this);
        binding.tvShowsRecyclerView.setAdapter(listAdapter);

        binding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePage){
                        currentPage += 1;
                        getListSearchContractor();
                    }
                }
            }
        });
        getListSearchContractor();
    }

    private void getListSearchContractor(){
        toggleLoading();
        searchViewModel.getContractorBySearch(binding.tabSearch.getText().toString(), currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
            @Override
            public void onChanged(DataResponse dataResponse) {
                HomeScreen.this.toggleLoading();
                if (dataResponse != null) {
                    totalAvailablePage = dataResponse.getData().getPages();
                    if (dataResponse.getData() != null) {
                        int oldCount = list.size();
                        list.addAll(dataResponse.getData().getMaidList());
                        listAdapter.notifyItemRangeInserted(oldCount, list.size());
                    }
                }

            }
        });
    }

    private void doInitializationContractor(){
        Log.e("TAG", "doInitializationContractor: ");
        binding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        listAdapter = new ListAdapter(list, this);
        listAdapter.clearList();
        binding.tvShowsRecyclerView.setAdapter(listAdapter);
        binding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePage){
                        currentPage += 1;
                        getListContractor();
                    }
                }
            }
        });
        getListContractor();
    }

    private void getListContractor(){
        toggleLoading();
        viewModel.getContractorList(currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
            @Override
            public void onChanged(DataResponse dataResponse) {
                HomeScreen.this.toggleLoading();
                if (dataResponse != null) {
                    Log.e("TAG", "onChanged: Contractors  "+dataResponse.isStatus() );
                    totalAvailablePage = dataResponse.getData().getPages();
                    if (dataResponse.getData() != null) {
                        int oldCount = list.size();
                        list.addAll(dataResponse.getData().getMaidList());
                        listAdapter.notifyItemRangeInserted(oldCount, list.size());
                    }
                }

            }
        });
    }

    private void doInitializationMaid(){
        binding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        listAdapter = new ListAdapter(list, this);
        listAdapter.clearList();
        binding.tvShowsRecyclerView.setAdapter(listAdapter);
        binding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePage){
                        currentPage += 1;
                        getListMaid();
                    }
                }
            }
        });
        getListMaid();
    }

    private void getListMaid(){
        toggleLoading();
        viewModel.getMaidList(currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
            @Override
            public void onChanged(DataResponse dataResponse) {
                HomeScreen.this.toggleLoading();
                if (dataResponse != null) {
                    totalAvailablePage = dataResponse.getData().getPages();
                    if (dataResponse.getData() != null) {
                        int oldCount = list.size();
                        list.addAll(dataResponse.getData().getMaidList());
                        listAdapter.notifyItemRangeInserted(oldCount, list.size());
                    }
                }

            }
        });
    }

    private void toggleLoading(){
        if (currentPage == 1){
            if (binding.getIsLoading()!= null && binding.getIsLoading()){
                binding.setIsLoading(false);
            }else {
                binding.setIsLoading(true);
            }
        } else {
            if (binding.getIsLoadingMore()!= null && binding.getIsLoadingMore()){
                binding.setIsLoadingMore(false);
            }else {
                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onListClicked(ListResponse listResponse) {
        Intent intent = new Intent(getApplicationContext(), ListDetailScreen.class);
        intent.putExtra("image1", listResponse.getImage1());
        intent.putExtra("image2", listResponse.getImage2());
        intent.putExtra("image3", listResponse.getImage3());
        intent.putExtra("location", listResponse.getLocation());
        intent.putExtra("mobile", listResponse.getMobile());
        intent.putExtra("name", listResponse.getName());
        intent.putExtra("price", listResponse.getPrice());
        intent.putExtra("profilePic", listResponse.getProfilePic());
        intent.putExtra("rating", listResponse.getRating());
        intent.putExtra("text", listResponse.getText());
        intent.putExtra("whatsapp", listResponse.getWhatsapp());
        intent.putExtra("category", category);
        startActivity(intent);
    }

    void startBottom(){

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeScreen.this, R.style.DialogStyle);
        bottomSheetDialog.setContentView(R.layout.profile_drawer);
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        CoordinatorLayout layout = bottomSheetDialog.findViewById(R.id.bottomSheetId);
        assert layout !=null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

//        dialog.Window.AddFlags(WindowManagerFlags.DrawsSystemBarBackgrounds);
//        or
//        dialog.Window.AddFlags(WindowManagerFlags.TranslucentStatus);

        TextView name = bottomSheetDialog.findViewById(R.id.name);
        TextView phone = bottomSheetDialog.findViewById(R.id.phone);
        TextView email = bottomSheetDialog.findViewById(R.id.email);
        TextView location = bottomSheetDialog.findViewById(R.id.location);
        ImageView image = bottomSheetDialog.findViewById(R.id.profilePic);

        name.setText(dName);
        phone.setText(dPhone);
        email.setText(dEmail);
        location.setText(dLocation);

        if (dImage==null){
            image.setImageResource(R.drawable.male_user);
        }else {
            Picasso.get().load(dImage).into(image);
        }

        bottomSheetDialog.show();
    }

}