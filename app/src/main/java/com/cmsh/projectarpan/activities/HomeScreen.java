package com.cmsh.projectarpan.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.cmsh.projectarpan.adaptes.LocationListAdapter;
import com.cmsh.projectarpan.databinding.ActivityHomeScreenBinding;
import com.cmsh.projectarpan.listeners.ListListener;
import com.cmsh.projectarpan.listeners.RvLocationListener;
import com.cmsh.projectarpan.responses.DataResponse;
import com.cmsh.projectarpan.responses.ListResponse;
import com.cmsh.projectarpan.responses.LocationListResponse;
import com.cmsh.projectarpan.responses.LocationResponse;
import com.cmsh.projectarpan.responses.RegisterResponse;
import com.cmsh.projectarpan.viewmodels.DataViewModel;
import com.cmsh.projectarpan.viewmodels.LocationViewModel;
import com.cmsh.projectarpan.viewmodels.SearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements ListListener, RvLocationListener {

    SearchViewModel searchViewModel;
    private ActivityHomeScreenBinding binding;
    private DataViewModel viewModel;
    private List<ListResponse> list=new ArrayList<>();
    private List<LocationListResponse> locationList=new ArrayList<>();
    private ListAdapter listAdapter;
    private int currentPage = 1;
    private int totalAvailablePage = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    String category;
    String dName, dEmail, dLocation, dPhone, dImage;
    LocationViewModel locationViewModel;

    private LocationListAdapter locationListAdapter;
    Chip all, name, location;
    RecyclerView locationRv;
    TextView count;
    RvLocationListener listListener;
    BottomSheetDialog bottomSheetDialogFilter;

    String searchName, searchLocation;


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
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        bottomSheetDialogFilter = new BottomSheetDialog(HomeScreen.this, R.style.DialogStyle);
        bottomSheetDialogFilter.setContentView(R.layout.filter_screen);
        bottomSheetDialogFilter.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        locationRv = bottomSheetDialogFilter.findViewById(R.id.locationRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        locationRv.setLayoutManager(linearLayoutManager);
        count = bottomSheetDialogFilter.findViewById(R.id.count);

        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoordinatorLayout layout2 = bottomSheetDialogFilter.findViewById(R.id.coordinatorLayout);
                assert layout2 !=null;
                layout2.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                bottomSheetDialogFilter.show();

                if (category.equals("maid")){
                    count.setText("Available Maid");
                    getMaidsLocationList();
                } else if (category.equals("contractor")) {
                    count.setText("Available Contractor");
                    getContractorLocationList();
                }
            }
        });

        viewModel.isRegistered(phone,Utils.INSTANCE.getToken()).observe(this, new Observer<RegisterResponse>() {
            @Override
            public void onChanged(RegisterResponse registerResponse) {
                Log.e("TAG", "onChanged: registerResponse "+registerResponse.getData().getName() );
                if (registerResponse!=null){
                    if (registerResponse.isStatus()){
                        if (registerResponse.getData().getProfilePic()==null){
                            binding.profilePic.setImageResource(R.drawable.male_user);
                        }else{
                            Picasso.get().load(registerResponse.getData().getProfilePic()).into(binding.profilePic);
                        }
                        dName = registerResponse.getData().getName();
                        dEmail = registerResponse.getData().getEmail();
                        dLocation = registerResponse.getData().getLocation();
                        dPhone = registerResponse.getData().getMobile();
                        dImage = registerResponse.getData().getProfilePic();
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
                        searchName=binding.tabSearch.getText().toString();
                        doInitializationSearchMaid(searchName, searchLocation);
                        return true;
                    }
                }else if(category.equals("contractor")){
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                        currentPage=1;
                        listAdapter.clearList();
                        searchName=binding.tabSearch.getText().toString();
                        doInitializationSearchContractor(searchName, searchLocation);
                        return true;
                    }
                }
                return false;
            }
        });

        binding.searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.equals("maid")){
                    currentPage=1;
                    listAdapter.clearList();
                    searchName=binding.tabSearch.getText().toString();
                    doInitializationSearchMaid(searchName, searchLocation);
                } else if (category.equals("contractor")) {
                    currentPage=1;
                    listAdapter.clearList();
                    searchName=binding.tabSearch.getText().toString();
                    doInitializationSearchContractor(searchName, searchLocation);
                }
            }
        });

    }

    private void doInitializationSearchMaid(String searchName, String searchLocation){
        Log.e("TAG", "doInitializationSearchMaid: ");
        binding.tvShowsRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
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
                        getListSearchMaid(searchName, searchLocation);
                    }
                }
            }
        });
        getListSearchMaid(searchName, searchLocation);
    }

    private void getListSearchMaid(String searchName, String searchLocation){
        Log.e("TAG", "getListSearchMaid: "+searchName+" "+searchLocation);
        toggleLoading();
        searchViewModel.getMaidList(searchName, searchLocation, currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
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

    private void doInitializationSearchContractor(String searchName, String searchLocation){
        Log.e("TAG", "doInitializationSearchMaid: ");
        binding.tvShowsRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
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
                        getListSearchContractor(searchName, searchLocation);
                    }
                }
            }
        });
        getListSearchContractor(searchName, searchLocation);
    }

    private void getListSearchContractor(String searchName, String searchLocation){
        toggleLoading();
        searchViewModel.getContractorBySearch(searchName, searchLocation, currentPage, Utils.INSTANCE.getToken()).observe(this, new Observer<DataResponse>() {
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

    private void getMaidsLocationList(){

        Log.e("TAG", "getMaidsLocationList: ");

//        listAdapter.clearList();
        locationViewModel.getMaidsLocationList(Utils.INSTANCE.getToken()).observe(this, new Observer<LocationResponse>() {
            @Override
            public void onChanged(LocationResponse dataResponse) {

                if (dataResponse != null) {
                    if (dataResponse.getData() != null) {
//                        int oldCount=locationList.size();
//                        locationList.addAll(dataResponse.getData());
//                        listAdapter.notifyItemRangeInserted(oldCount, locationList.size());
                        locationRv.setHasFixedSize(true);
                        locationListAdapter = new LocationListAdapter(dataResponse.getData(), HomeScreen.this);
                        Log.e("TAG", "onChanged: dataResponse "+locationListAdapter);
                        locationRv.setAdapter(locationListAdapter);
                    }
                }

            }
        });
    }

    private void getContractorLocationList(){

        Log.e("TAG", "getMaidsLocationList: ");

//        listAdapter.clearList();
        locationViewModel.getContractorsLocationList(Utils.INSTANCE.getToken()).observe(this, new Observer<LocationResponse>() {
            @Override
            public void onChanged(LocationResponse dataResponse) {

                if (dataResponse != null) {
                    if (dataResponse.getData() != null) {
//                        int oldCount=locationList.size();
//                        locationList.addAll(dataResponse.getData());
//                        listAdapter.notifyItemRangeInserted(oldCount, locationList.size());
                        locationRv.setHasFixedSize(true);
                        locationListAdapter = new LocationListAdapter(dataResponse.getData(), HomeScreen.this);
                        Log.e("TAG", "onChanged: dataResponse "+locationListAdapter);
                        locationRv.setAdapter(locationListAdapter);
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

        Log.e("TAG", "startBottom: "+dName);

        if (dImage==null){
            image.setImageResource(R.drawable.male_user);
        }else {
            Picasso.get().load(dImage).into(image);
        }

        bottomSheetDialog.show();
    }

    @Override
    public void onListClicked(LocationListResponse locationListResponse) {
        bottomSheetDialogFilter.dismiss();
        currentPage=1;
        if (category.equals("maid")){
            doInitializationSearchMaid(searchName,locationListResponse.getLocation());
        } else if (category.equals("contractor")) {
            doInitializationSearchContractor(searchName, locationListResponse.getLocation());
        }
    }
}